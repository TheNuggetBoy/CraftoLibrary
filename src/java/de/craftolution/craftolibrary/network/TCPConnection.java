/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import de.craftolution.craftolibrary.Check;
import de.craftolution.craftolibrary.ToStringable;

/**
 * A {@link TCPConnection} is basically a wrapper around {@link Socket} specialized for
 * sending and receiven arrays of bytes. It also provides some listener functionality
 * to prevent excessive exception catching.
 *
 * @author Fear837
 * @since 13.09.2015
 * @version 2.0
 */
public class TCPConnection implements ToStringable {

	/** @return Creates and returns a new {@link TCPConnectionBuilder} instance. */
	public static TCPConnectionBuilder builder() { return new TCPConnectionBuilder(); }

	/** Whether or not this instance is still listening on the input stream. */
	private final AtomicBoolean listening = new AtomicBoolean();
	/** The address to which this socket is connected to. */
	private final InetAddress address;
	/** The port used for connection. */
	private final int port;
	/** The queue used to store packets in. */
	private final LinkedBlockingQueue<byte[]> packetQueue = new LinkedBlockingQueue<>();

	/** The socket instance used for the connection. */
	@Nullable private Socket socket;
	/** The stream used to send information. */
	@Nullable private DataOutputStream output;
	/** The stream used to receive/read information. */
	@Nullable private DataInputStream input;

	// Listeners
	@Nullable private Consumer<String> logMessageHandler;
	@Nullable private Consumer<Exception> exceptionHandler;
	@Nullable private Consumer<byte[]> packetHandler;
	@Nullable private Runnable disconnectHandler;
	@Nullable private Executor executor;

	TCPConnection(final Socket socket, final Consumer<String> logger, final Consumer<Exception> exceptionListener, final Consumer<byte[]> packetListener, final Runnable disconnectListener, @Nullable final Executor executor) throws IOException {
		this.address = socket.getInetAddress();
		this.port = socket.getPort();

		this.socket = socket;
		this.socket.setKeepAlive(true);

		this.output = new DataOutputStream(socket.getOutputStream());
		this.input = new DataInputStream(socket.getInputStream());

		this.logMessageHandler = logger;
		this.exceptionHandler = exceptionListener;
		this.packetHandler = packetListener;
		this.disconnectHandler = disconnectListener;
		this.executor = executor;

		this.listening.set(true);
		if (executor != null) { executor.execute(this::listen); }
		else { new Thread(this::listen, this.toString() + "-ListenerThread").start(); }
	}

	/**
	 * Initializes a new {@link TCPConnection} without using the {@link TCPConnectionBuilder}.
	 * In this case it is neccessary to use the {@link #connect()} method.
	 *
	 * @param address - The address to which this instance will connect to.
	 * @param port - The port used for the connection.
	 */
	public TCPConnection(final InetAddress address, final int port) {
		Check.notNull(address, "The hostAddress cannot be null!");
		Check.isTrue(port > 0, "The port has to be greather than 0!");
		this.listening.set(false);
		this.address = address;
		this.port = port;
	}

	/**
	 * Initializes a new {@link TCPConnection} without using the {@link TCPConnectionBuilder}.
	 * In this case it is neccessary to use the {@link #connect()} method.
	 *
	 * @param address - The address to which this instance will connect to.
	 * @param port - The port used for the connection.
	 */
	public TCPConnection(final String inetAddress, final int port) throws IllegalArgumentException {
		Check.notNull(inetAddress, "The hostAddress cannot be null!");
		Check.isTrue(port > 0, "The port has to be greather than 0!");
		this.listening.set(false);
		try { this.address = InetAddress.getByName(inetAddress); }
		catch (UnknownHostException e) { throw new IllegalArgumentException(e); }
		this.port = port;
	}

	/**
	 * Returns the address to which this {@link TCPConnection} is connected.
	 *
	 * <p> If the socket was connected prior to being closed,
	 * then this method will continue to return the connected address
	 * after the socket is closed. </p>
	 *
	 * @return The remote IP address to which this socket is connected, or {@code null} if the socket is not connected.
	 */
	public InetAddress getInetAddress() { return this.address; }

	/** TODO: Documentation */
	public LinkedBlockingQueue<byte[]> getPackets() { return this.packetQueue; }

	/**
	 * Returns the remote port number to which this socket is connected.
	 *
	 * <p> If the socket was connected prior to being {@link #close closed},
	 * then this method will continue to return the connected port number
	 * after the socket is closed. </p>
	 *
	 * @return The remote port number to which this socket is connected, or 0 if the socket is not connected yet.
	 */
	public int getPort() { return this.port; }

	/**
	 * Returns whether or not this {@link TCPConnection} is still connected.
	 * @return {@code True} if still connected.
	 */
	public boolean isConnected() { return this.socket != null && !this.socket.isClosed(); }

	/**
	 * Tries to connect to the specified end point.
	 * @return Returns itself.
	 * @throws IOException If something goes wrong while trying to connect.
	 */
	public TCPConnection connect() throws IOException {
		if (!this.isConnected() && !this.listening.get()) {
			this.socket = new Socket(this.address, this.port);
			this.socket.setKeepAlive(true);

			this.output = new DataOutputStream(this.socket.getOutputStream());
			this.input = new DataInputStream(this.socket.getInputStream());

			this.listening.set(true);
			if (this.executor != null) { this.executor.execute(this::listen); }
			else { new Thread(this::listen, this.toString() + "-ListenerThread").start(); }
		}
		return this;
	}

	/**
	 * Closes the socket.
	 *
	 * <p> Any thread currently blocked in an I/O operation upon this connection
	 * will throw a {@link SocketException}. </p>
	 *
	 * <p> Once the socket has been closed, it is not available for further networking
	 * use (i.e. can't be reconnected or rebound). A new socket needs to be
	 * created. </p>
	 *
	 * <p> Closing this socket will also close the socket's
	 * {@link java.io.InputStream InputStream} and
	 * {@link java.io.OutputStream OutputStream}. </p>
	 *
	 * <p> If this socket has an associated channel then the channel is closed
	 * as well. </p>
	 */
	public void disconnect() {
		if (this.disconnectHandler != null) {
			try { this.disconnectHandler.run(); }
			catch (final Exception e) { this.handleException(e); }
		}

		this.listening.set(false);

		if (this.output != null) {
			try { this.output.close(); }
			catch (final IOException ignore) { }
		}

		if (this.input != null) {
			try { this.input.close(); }
			catch (final IOException ignore) { }
		}

		if (this.socket != null) {
			try { this.socket.close(); }
			catch (final IOException ignore) { }
		}
	}

	/**
	 * Sets the handler for incoming log messages.
	 * @param logMessageHandler - The consumer that accepts logs.
	 * @return Returns itself.
	 */
	public TCPConnection onLogMessage(final Consumer<String> logMessageHandler) {
		this.logMessageHandler = logMessageHandler;
		return this;
	}

	/**
	 * Sets the handler for incoming exceptions.
	 * @param exceptionHandler - The consumer that accepts exceptions.
	 * @return Returns itself.
	 */
	public TCPConnection onException(final Consumer<Exception> exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
		return this;
	}

	/**
	 * Sets the handler for incoming packets.
	 * @param packetHandler - The consumer that accepts packets.
	 * @return Returns itself.
	 */
	public TCPConnection onPacket(final Consumer<byte[]> packetHandler) {
		this.packetHandler = packetHandler;
		return this;
	}

	/**
	 * Sets the handler that gets executed when this instance disconnects.
	 * @param disconnectHandler - The handler
	 * @return Returns itself.
	 */
	public TCPConnection onDisconnect(final Runnable disconnectHandler) {
		this.disconnectHandler = disconnectHandler;
		return this;
	}

	/**
	 * Sets an executor that provides a thread for listening on incoming packets for this instance.
	 * @param executor - The executor
	 * @return Returns itself.
	 */
	public TCPConnection withExecutor(final Executor executor) {
		this.executor = executor;
		return this;
	}

	/**
	 * Sends a byte array to the inputstream on the other end of this connection.
	 *
	 * If a {@link IOException} occurs during sending, it'll be handled by the specified
	 * {@link #exceptionListener} if available and also returned as an optional.
	 *
	 * Keep in mind that if {@link #isConnected()} returns {@code false} this method will
	 * return {@link Optional#empty()} immediately.
	 *
	 * @param bytes - The byte array to send.
	 * @return Returns an {@link IOException} if one occured.
	 */
	public Optional<IOException> send(final byte[] bytes) {
		if (this.isConnected()) {
			try {
				this.output.write(bytes, 0, bytes.length);
				this.output.flush();
			}
			catch (final IOException e) {
				if (!this.socket.isClosed()) {
					this.handleException(e);
					return Optional.of(e);
				}
			}
		}
		return Optional.empty();
	}

	private void listen() {
		this.log("Started listening for new packets on socket " + this.socket.getInetAddress().getHostAddress() + ":" + this.socket.getPort() + "...");

		try {
			int affectedBytes = 0;

			while (this.listening.get() && affectedBytes > -1) {
				final byte[] bytes = new byte[this.input.available()];
				affectedBytes = this.input.read(bytes);

				// If at least one byte was read by the last read process
				if (affectedBytes > 0) {
					byte[] realBytes = bytes;

					// If the bytes array length (received from input.available()) doesnt match affectedBytes, then use affectedBytes for new length.
					if (bytes.length != affectedBytes) {
						// Create a new byte array that (this time) really has the right size.
						realBytes = new byte[affectedBytes];
						System.arraycopy(bytes, 0, realBytes, 0, realBytes.length);
					}

					// Notify the packetListener if available.
					if (this.packetHandler != null) {
						try { this.packetHandler.accept(realBytes); }
						catch (final Exception e) { this.handleException(e); }
					}
					else { this.packetQueue.offer(realBytes); }
				}
			}
		}
		catch (final IOException e) {
			if (!this.socket.isClosed()) { this.handleException(e); }
		}

		this.listening.set(false);
		this.log("Stopped listening for new messages on socket " + this.socket.getInetAddress().getHostAddress() + ":" + this.socket.getPort() + "... ");
		this.disconnect();
	}

	// --- Convinience methods ---
	private void log(final String message) {
		if (this.logMessageHandler != null) {
			try { this.logMessageHandler.accept("[" + this.toString() + "]: " + message); return; }
			catch (final Exception e) { this.handleException(e); }
		}
		System.out.println("[" + this.toString() + "]: " + message);
	}

	private void handleException(final Exception e) {
		if (this.exceptionHandler != null) {
			try { this.exceptionHandler.accept(e); }
			catch (final Exception otherException) {
				e.printStackTrace();
				otherException.printStackTrace();
			}
			return;
		}
		e.printStackTrace();
	}

	@Override
	public String toString() {
		return this.buildToString()
				.with("ip", this.getInetAddress().getHostAddress())
				.with("port", this.getPort())
				.with("listening", this.listening.get())
				.with("connected", this.isConnected())
				.toString();
	}

}