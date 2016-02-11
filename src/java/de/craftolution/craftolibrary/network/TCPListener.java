/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
public class TCPListener implements ToStringable {

	/** @return Creates and returns a new {@link TCPListenerBuilder} instance. */
	public static TCPListenerBuilder builder() {
		return new TCPListenerBuilder();
	}

	/** Whether or not this listener is still accepting new connections. */
	private final AtomicBoolean listening = new AtomicBoolean();
	/** The port to listen on. */
	private final int port;

	/** The socket instance used for accepting new connections. */
	private ServerSocket socket;

	// --- Listeners ---
	@Nullable private Consumer<String> logMessageHandler;
	@Nullable private Consumer<Exception> exceptionHandler;
	@Nullable private Consumer<TCPConnection> connectionHandler;

	TCPListener(final ServerSocket socket, final Consumer<String> logger, final Consumer<Exception> exceptionListener, final Consumer<TCPConnection> connectionListener) {
		this.port = socket.getLocalPort();
		this.socket = socket;

		this.logMessageHandler = logger;
		this.exceptionHandler = exceptionListener;
		this.connectionHandler = connectionListener;

		this.listening.set(true);

		new Thread(this::listen, this.toString() + "ListenerThread").start();
	}

	/**
	 * Initializes a new {@link TCPListener} without using the {@link TCPListenerBuilder}.
	 * In this case it is neccessary to use the {@link #start()} method.
	 *
	 * @param port - The port used to listen on new connections.
	 */
	public TCPListener(final int port) {
		this.port = port;
		this.listening.set(false);
	}

	/** @return Returns the port used for listening. */
	public int getPort() { return this.port; }

	/** @return Returns whether or not this instance is currently listening. */
	public boolean isListening() { return this.socket != null && this.listening.get(); }

	/**
	 * Tries to start listening for new connections.
	 * @throws IOException When something goes wrong while trying to start the {@link ServerSocket}.
	 * @return Returns itself.
	 */
	public TCPListener start() throws IOException {
		if (!this.isListening()) {
			this.listening.set(true);

			this.socket = new ServerSocket(this.port);
			new Thread(this::listen, this.toString() + "ListenerThread").start();

		}

		return this;
	}

	/** Stops the {@link TCPListener} from accepting new connections. */
	public void stop() {
		this.listening.set(false);

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
	public TCPListener onLogMessage(final Consumer<String> logMessageHandler) {
		this.logMessageHandler = logMessageHandler;
		return this;
	}

	/**
	 * Sets the handler for incoming exceptions.
	 * @param exceptionHandler - The consumer that accepts exceptions.
	 * @return Returns itself.
	 */
	public TCPListener onException(final Consumer<Exception> exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
		return this;
	}

	/**
	 * Sets the handler for incoming connections.
	 * @param connectionHandler - The consumer that accepts new connections.
	 * @return Returns itself.
	 */
	public TCPListener onNewConnection(final Consumer<TCPConnection> connectionHandler) {
		this.connectionHandler = connectionHandler;
		return this;
	}

	private void listen() {
		this.log("Started listening for new clients on port " + this.getPort() + "...");

		while (this.isListening()) {
			try {
				final Socket newSocket = this.socket.accept();

				if (!this.isListening()) { break; }

				final TCPConnection connection = new TCPConnection(newSocket, this.logMessageHandler, this.exceptionHandler, null, null, null);

				this.log("A new client (" + newSocket.getInetAddress().getHostAddress() + ":" + newSocket.getPort() + ") connected!");

				if (this.connectionHandler != null) {
					try { this.connectionHandler.accept(connection); }
					catch (final Exception e) { this.handleException(e); }
				}
			}
			catch (final IOException e) { this.handleException(e); }
		}

		this.listening.set(false);
		this.log("Stopped listening for new connections on port " + this.getPort() + ".");
		this.stop();
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
				.with("port", this.getPort())
				.with("listening", this.isListening())
				.toString();
	}
}