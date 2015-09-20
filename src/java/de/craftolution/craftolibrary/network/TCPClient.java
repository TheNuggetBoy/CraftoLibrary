package de.craftolution.craftolibrary.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import de.craftolution.craftolibrary.ToStringable;

/**
 * TODO: Documentation
 *
 * @author Kevin
 * @since 13.09.2015
 */
public class TCPClient implements ToStringable {

	private static final int DEFAULT_BYTE_ARRAY_TRESHOLD = 2048;

	private final Socket socket;
	private final DataOutputStream out;
	private final DataInputStream in;
	private final AtomicBoolean listening;

	private final LinkedBlockingQueue<byte[]> newMessageQueue = new LinkedBlockingQueue<>();

	private int byteArrayTreshold = TCPClient.DEFAULT_BYTE_ARRAY_TRESHOLD;

	@Nullable private Boolean disconnectInformed = null;

	@Nullable private Consumer<String> logger;
	@Nullable private Consumer<Exception> exceptionHandler;
	@Nullable private Consumer<byte[]> messageHandler;
	@Nullable private Runnable disconnectHandler;

	/** TODO: Documentation */
	public TCPClient(final Socket socket, @Nullable final Executor executor) throws IOException {
		this.socket = socket;
		this.out = new DataOutputStream(socket.getOutputStream());
		this.in = new DataInputStream(socket.getInputStream());
		this.listening = new AtomicBoolean(true);

		// Start listen()
		if (executor != null) { executor.execute(this::listen); }
		else { new Thread(this::listen, this.toString() + "-ListenerThread").start(); }
	}

	/** TODO: Documentation */
	public TCPClient(final Socket socket) throws IOException {
		this(socket, null);
	}

	/** TODO: Documentation */
	public TCPClient(final String host, final int port) throws UnknownHostException, IOException {
		this(new Socket(host, port));
	}

	/** TODO: Documentation */
	public TCPClient(final String host, final int port, final Executor executor) throws UnknownHostException, IOException {
		this(new Socket(host, port), executor);
	}

	// --- Event handlers ---

	/** TODO: Documentation */
	public TCPClient onLogMessage(final Consumer<String> logger) { this.logger = logger; return this; }

	/** TODO: Documentation */
	public TCPClient onException(final Consumer<Exception> exceptionHandler) { this.exceptionHandler = exceptionHandler; return this; }

	/** TODO: Documentation */
	public TCPClient onMessage(final Consumer<byte[]> messageHandler) {
		this.messageHandler = messageHandler;
		while (this.messageHandler != null && !this.newMessageQueue.isEmpty()) {
			try { this.messageHandler.accept(this.newMessageQueue.poll()); } catch (final Exception e) { this.report(e); }
		}
		return this;
	}

	/** TODO: Documentation */
	public TCPClient onDisconnect(final Runnable disconnectHandler) {
		this.disconnectHandler = disconnectHandler;
		if (this.disconnectHandler != null && !this.isConnected() && this.disconnectInformed != null && this.disconnectInformed == false) {
			try { this.disconnectHandler.run(); } catch (final Exception e) { this.report(e); }
		}
		return this;
	}

	/** TODO: Documentation */
	public TCPClient setByteArrayTreshold(final int length) { this.byteArrayTreshold = length; return this; }

	// --- Public methods ---

	/** TODO: Documentation */
	public InetAddress getInetAddress() { return this.socket.getInetAddress(); }

	/** TODO: Documentation */
	public int getPort() { return this.socket.getPort(); }

	/** TODO: Documentation */
	public boolean isConnected() { return !this.socket.isClosed(); }

	/** TODO: Documentation */
	public void disconnect() {
		if (this.disconnectHandler == null) {
			if (this.disconnectInformed == null) { this.disconnectInformed = false; }
		}
		else {
			if (this.disconnectInformed == null || this.disconnectInformed == false) {
				try { this.disconnectHandler.run(); } catch (final Exception e) { this.report(e); }
				this.disconnectInformed = true;
			}
		}

		this.listening.set(false);

		try { this.out.close(); }
		catch (final IOException ignore) { }

		try { this.in.close(); }
		catch (final IOException ignore) { }

		try { this.socket.close(); }
		catch (final IOException ignore) { }
	}

	/** TODO: Documentation */
	public boolean send(final byte[] bytes) {
		if (this.socket.isConnected()) {
			try {
				this.out.write(bytes, 0, bytes.length);
				this.out.flush();
				return true;
			}
			catch (final IOException e) {
				if (!this.socket.isClosed()) {
					this.report(e);
				}
			}
		}
		return false;
	}

	// --- Private methods ---

	private void listen() {
		this.log("Started listening for new messages on socket " + this.socket.getInetAddress().getHostAddress() + ":" + this.socket.getPort() + "...");

		final byte[] bytes = new byte[this.byteArrayTreshold];
		int affectedBytes = 0;

		try {
			while (this.listening.get() && (affectedBytes = this.in.read(bytes)) > -1) { // readLine() is blocking
				if (!this.listening.get()) { break; }

				if (affectedBytes > 0) {
					final byte[] realBytes = new byte[affectedBytes];
					System.arraycopy(bytes, 0, realBytes, 0, realBytes.length);

					this.newMessage(realBytes);
				}
			}
		}
		catch (final IOException e) {
			if (!this.socket.isClosed()) {
				this.report(e);  // Only report if connection is still open
			}
		}

		this.log("Stopped listening for new messages on socket " + this.socket.getInetAddress().getHostAddress() + ":" + this.socket.getPort() + "... ");
		this.disconnect();
	}

	private void newMessage(final byte[] message) {
		if (this.messageHandler == null) { this.newMessageQueue.add(message); }
		else { try { this.messageHandler.accept(message); } catch (final Exception e) { this.report(e); } }
	}

	private void log(final String message) {
		if (this.logger == null) { System.out.println("[" + this.toString() + "]: " + message); }
		else { try { this.logger.accept("[" + this.toString() + "]: " + message);  } catch (final Exception e) { this.report(e); } }
	}

	private void report(final Exception e) {
		if (this.exceptionHandler == null) { e.printStackTrace(); }
		else { try { this.exceptionHandler.accept(e);  } catch (final Exception newException) { e.printStackTrace(); } }
	}

	@Override
	public String toString() {
		//		return this.buildToString()
		//				.with("ip", this.socket.getInetAddress().getHostAddress())
		//				.with("port", this.socket.getPort())
		//				.with("listening", this.listening.get())
		//				.with("connected", this.socket.isConnected())
		//				.toString();
		return "TCPClient{'"+this.socket.getInetAddress().getHostAddress()+":"+this.socket.getPort()+"'}";
	}
}