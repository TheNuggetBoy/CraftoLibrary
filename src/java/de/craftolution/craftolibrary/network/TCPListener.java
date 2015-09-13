package de.craftolution.craftolibrary.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import de.craftolution.craftolibrary.ToStringable;

/**
 * TODO: Documentation
 *
 * @author Kevin
 * @since 13.09.2015
 */
public class TCPListener implements ToStringable {

	private final int port;
	private final ServerSocket listenerSocket;
	private final ExecutorService executorService;
	private final AtomicBoolean listening;

	private final LinkedBlockingQueue<TCPClient> newClientQueue = new LinkedBlockingQueue<>();

	@Nullable private Consumer<String> logger;
	@Nullable private Consumer<Exception> exceptionHandler;
	@Nullable private Consumer<TCPClient> clientHandler;

	/** TODO: Documentation */
	public TCPListener(final int port, @Nullable final Consumer<String> logger, @Nullable final Consumer<Exception> exceptionHandler, @Nullable final Consumer<TCPClient> clientHandler) throws IOException {
		this.port = port;
		this.listenerSocket = new ServerSocket(port);

		final ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat(this.toString() + "-ClientThread-%d").build();
		this.executorService = Executors.newCachedThreadPool(factory);

		this.listening = new AtomicBoolean(false);

		this.logger = logger;
		this.exceptionHandler = exceptionHandler;
		this.clientHandler = clientHandler;

		new Thread(this::listen, this.toString() + "-ListenerThread").start();
	}

	/** TODO: Documentation */
	public TCPListener(final int port, @Nullable final Consumer<Exception> exceptionHandler, @Nullable final Consumer<TCPClient> clientHandler) throws IOException {
		this(port, null, exceptionHandler, clientHandler);
	}

	/** TODO: Documentation */
	public TCPListener(final int port, @Nullable final Consumer<TCPClient> clientHandler) throws IOException {
		this(port, null, null, clientHandler);
	}

	/** TODO: Documentation */
	public TCPListener(final int port) throws IOException {
		this(port, null, null, null);
	}

	// --- Event handler ---

	/** TODO: Documentation */
	public TCPListener onLogMessage(@Nullable final Consumer<String> logger) { this.logger = logger; return this; }

	/** TODO: Documentation */
	public TCPListener onException(@Nullable final Consumer<Exception> exceptionHandler) { this.exceptionHandler = exceptionHandler; return this; }

	/** TODO: Documentation */
	public TCPListener onNewClient(@Nullable final Consumer<TCPClient> clientHandler) {
		this.clientHandler = clientHandler;
		while (this.clientHandler != null && !this.newClientQueue.isEmpty()) {
			try { this.clientHandler.accept(this.newClientQueue.poll()); } catch (final Exception e) { this.report(e); }
		}
		return this;
	}

	// --- Public methods ---

	/** TODO: Documentation */
	public boolean isListening() { return this.listening.get(); }

	/** TODO: Documentation */
	public void stop() {
		this.listening.set(false);

		try { this.listenerSocket.close(); }
		catch (final IOException ignore) { }
	}

	// --- Private methods ---

	private void listen() {
		this.log("Starting listening for new clients..");
		this.listening.set(true);

		while (this.listening.get()) {
			try {
				final Socket newSocket = this.listenerSocket.accept(); // Blocking

				if (!this.listening.get()) { break; }

				try {
					final TCPClient client = new TCPClient(newSocket, this.executorService)
							.onLogMessage(this.logger)
							.onException(this.exceptionHandler);

					this.log("A new client (" + newSocket.getInetAddress().getHostAddress() + ":" + newSocket.getPort() + ") connected!");

					this.newClient(client);
				}
				catch (final IOException e) { this.report(e); }
			}
			catch (final IOException e) { this.report(e); }
		}

		this.listening.set(false);
		this.log("Stopped listening for new clients.");
	}

	private void newClient(final TCPClient client) {
		if (this.clientHandler == null) { this.clientHandler.accept(client); }
		else { try { this.clientHandler.accept(client); } catch (final Exception e) { this.report(e); } }
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
				.with("port", this.port)
				.with("listening", this.listening == null ? "null" : this.listening.get())
				.toString();
		//		return "TCPListener{'"+this.port+"'}";
	}
}