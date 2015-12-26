package de.craftolution.craftolibrary.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 22.12.2015
 */
public class TCPConnectionBuilder {

	private InetAddress host;
	private int port;

	private Socket socket;
	private Consumer<String> logger;
	private Consumer<Exception> exceptionListener;
	private Consumer<byte[]> packetListener;
	private Runnable disconnectListener;
	private Executor executor;

	TCPConnectionBuilder() { }

	/**
	 * Sets the socket used by the {@link TCPConnection}.
	 * Keep in mind that you can either specify a {@link Socket} or a {@link InetAddress} & {@code port} in this port.
	 * If both are specified, the {@link Socket} will always be preffered.
	 *
	 * @param socket - The socket to use for the {@link TCPConnection}
	 */
	public TCPConnectionBuilder socket(final Socket socket) {
		this.socket = socket;
		return this;
	}

	/**
	 * Sets the host used by the {@link TCPConnection}.
	 *
	 * Keep in mind that, if you use the {@link #socket(Socket)} method, this method
	 * and {@link #port(int)} will be useless.
	 *
	 * @param hostname - The hostname to use.
	 * @throws UnknownHostException If no IP address for the host could be found, or if a scope_id was specified for a global IPv6 address.
	 */
	public TCPConnectionBuilder host(final String hostname) throws UnknownHostException {
		this.host = InetAddress.getByName(hostname);
		return this;
	}

	/**
	 * Sets the host used by the {@link TCPConnection}.
	 *
	 * Keep in mind that, if you use the {@link #socket(Socket)} method, this method
	 * and {@link #port(int)} will be useless.
	 *
	 * @param host - The hostname to use.
	 */
	public TCPConnectionBuilder host(final InetAddress host) {
		this.host = host;
		return this;
	}

	/**
	 * Specifies the port used by the {@link TCPConnection}.
	 *
	 * @param port - The port
	 */
	public TCPConnectionBuilder port(final int port) {
		this.port = port;
		return this;
	}

	/**
	 * Specifies the executor that will be used by the {@link TCPConnection} to start
	 * listening for incoming packets.
	 * This method is <b>optional</b>, because when no executor is specified the {@link TCPConnection}
	 * will just initialize a new {@link Thread} instance and use it for listening.
	 *
	 * @param executor - The executor
	 */
	public TCPConnectionBuilder executor(final Executor executor) {
		this.executor = executor;
		return this;
	}

	/**
	 * Specifies the consumer that accepts log messages.
	 * This method is <b>optional</b>. When no logger is specified
	 * {@link System.out.println} will be used.
	 *
	 * @param logger - The logger
	 */
	public TCPConnectionBuilder onLogMessage(final Consumer<String> logger) {
		this.logger = logger;
		return this;
	}

	/**
	 * Sets the consumer that accepts exceptions.
	 * This method is <b>optional</b>. When no exception listener is specified,
	 * {@code exception.printStackTrace();} is used.
	 *
	 * @param exceptionListener - The exception listener
	 */
	public TCPConnectionBuilder onException(final Consumer<Exception> exceptionListener) {
		this.exceptionListener = exceptionListener;
		return this;
	}

	/**
	 * Sets the consumer that accepts byte arrays / packets.
	 *
	 * @param packetListener - The packet listener
	 */
	public TCPConnectionBuilder onPacket(final Consumer<byte[]> packetListener) {
		this.packetListener = packetListener;
		return this;
	}

	/**
	 * Specifies a runnable that will be executed once the connection closes.
	 * This method is <b>optional</b>.
	 *
	 * @param disconnectListener - The runnable to execute when disconnecting.
	 */
	public TCPConnectionBuilder onDisconnect(final Runnable disconnectListener) {
		this.disconnectListener = disconnectListener;
		return this;
	}

	/**
	 * Builds the {@link TCPConnection} instance and automatically connects it to the specified end point.
	 *
	 * @throws IllegalStateException When no socket or inetAddress&port was specified.
	 * @throws IOException When something went wrong while connecting or opening the input/output streams.
	 */
	public TCPConnection build() throws IllegalStateException, IOException {
		if (this.socket == null && this.host == null) {
			throw new IllegalStateException("The socket or the hostname was not specified.");
		}

		if (this.port < 1) { throw new IllegalStateException("The specified port was smaller than 1!"); }

		if (this.socket == null) { this.socket = new Socket(this.host, this.port); }

		return new TCPConnection(this.socket, this.logger, this.exceptionListener, this.packetListener, this.disconnectListener, this.executor);
	}

}