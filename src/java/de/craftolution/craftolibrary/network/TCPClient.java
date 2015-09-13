package de.craftolution.craftolibrary.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

	private final Socket socket;
	private final PrintWriter out;
	private final BufferedReader in;
	private final AtomicBoolean listening;

	private final LinkedBlockingQueue<String> newMessageQueue = new LinkedBlockingQueue<>();

	@Nullable private Boolean disconnectInformed = null;

	@Nullable private Consumer<String> logger;
	@Nullable private Consumer<Exception> exceptionHandler;
	@Nullable private Consumer<String> messageHandler;
	@Nullable private Runnable disconnectHandler;

	/** TODO: Documentation */
	public TCPClient(final Socket socket, @Nullable final Executor executor) throws IOException {
		this.socket = socket;
		this.out = new PrintWriter(socket.getOutputStream(), true);
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
	public TCPClient onMessage(final Consumer<String> messageHandler) {
		this.messageHandler = messageHandler;
		while (this.messageHandler != null && !this.newMessageQueue.isEmpty()) {
			this.messageHandler.accept(this.newMessageQueue.poll());
		}
		return this;
	}

	/** TODO: Documentation */
	public TCPClient onDisconnect(final Runnable disconnectHandler) {
		this.disconnectHandler = disconnectHandler;
		if (this.disconnectHandler != null && !this.isConnected() && this.disconnectInformed != null && this.disconnectInformed == false) {
			this.disconnectHandler.run();
		}
		return this;
	}

	// --- Public methods ---

	/** TODO: Documentation */
	public InetAddress getInetAddress() { return this.socket.getInetAddress(); }

	/** TODO: Documentation */
	public int getPort() { return this.socket.getPort(); }

	/** TODO: Documentation */
	public boolean isConnected() { return this.socket.isClosed(); }

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

		this.out.close();

		try { this.in.close(); }
		catch (final IOException ignore) { }

		try { this.socket.close(); }
		catch (final IOException ignore) { }
	}

	/** TODO: Documentation */
	public void send(final String message) {
		if (this.socket.isConnected()) {
			this.out.println(message);
		}
	}

	// --- Private methods ---

	private void listen() {
		this.log("Started listening for new messages on socket " + this.socket.getInetAddress().getHostAddress() + ":" + this.socket.getPort() + "...");

		String message = null;
		try {
			while (this.listening.get() && (message = this.in.readLine()) != null) { // readLine() is blocking
				if (!this.listening.get()) { break; }

				this.newMessage(message);
			}
		}
		catch (final IOException e) {
			if (!this.socket.isClosed()) {
				this.report(e);  // Only report if connection is still open
			}
		}

		this.disconnect();
		this.log("Stopped listening for new messages on socket " + this.socket.getInetAddress().getHostAddress() + ":" + this.socket.getPort() + "... ");
	}

	private void newMessage(final String message) {
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
		return this.buildToString()
				.with("ip", this.socket.getInetAddress().getHostAddress())
				.with("port", this.socket.getPort())
				.with("listening", this.listening.get())
				.with("connected", this.socket.isConnected())
				.toString();
		//		return "TCPClient{'"+this.socket.getInetAddress().getHostAddress()+":"+this.socket.getPort()+"'}";
	}
}