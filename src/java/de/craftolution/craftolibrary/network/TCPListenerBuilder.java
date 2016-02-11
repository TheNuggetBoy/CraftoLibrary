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
import java.util.function.Consumer;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 24.12.2015
 */
public class TCPListenerBuilder {

	private ServerSocket socket;
	private int port;
	private Consumer<String> logger;
	private Consumer<Exception> exceptionListener;
	private Consumer<TCPConnection> connectionListener;

	TCPListenerBuilder() { }

	/**
	 * Sets the {@link ServerSocket} used by the {@link TCPListener}.
	 * Keep in mind that you can either use this method or use {@link #port(int)}
	 * which will cause the builder to initialize a new {@link ServerSocket} based
	 * on that given port.
	 *
	 * @param socket
	 * @return
	 */
	public TCPListenerBuilder socket(final ServerSocket socket) {
		this.socket = socket;
		return this;
	}

	/**
	 * Specifies the port used to accept new connections.
	 *
	 * Keep in mind that, if you use the {@link #socket(ServerSocket)} method, this
	 * method will become useless.
	 *
	 * @param port - The port
	 */
	public TCPListenerBuilder port(final int port) {
		this.port = port;
		return this;
	}

	/**
	 * Specifies the consumer that accepts log messages.
	 * This method is <b>optional</b>. When no logger is specified
	 * {@link System.out.println} will be used.
	 *
	 * @param logger - The logger
	 */
	public TCPListenerBuilder onLogMessage(final Consumer<String> logger) {
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
	public TCPListenerBuilder onException(final Consumer<Exception> exceptionListener) {
		this.exceptionListener = exceptionListener;
		return this;
	}

	/**
	 * Specifies the listener that is going to handle incoming connections.
	 * A {@link TCPConnectionBuilder} is returned (instead of a TCPConnection) to
	 * provide the functionality to modify certain attributes like the logger or
	 * exceptionHandler.
	 *
	 * Before the connectionListener is called, the {@link TCPConnection} will
	 * be provided with the same logger and exceptionHandler as the {@link TCPListener}
	 * was provided with.
	 *
	 * @param packetListener - The connection listener
	 */
	public TCPListenerBuilder onNewConnection(final Consumer<TCPConnection> connectionListener) {
		this.connectionListener = connectionListener;
		return this;
	}

	/**
	 * Builds the {@link TCPListener} instance and automatically starts it to accept new connections.
	 *
	 * @throws IOException If no {@link ServerSocket} was specified and an exception occured while initializing
	 * one from the specified port.
	 */
	public TCPListener build() throws IOException {
		if (this.socket == null) {
			this.socket = new ServerSocket(this.port);
		}

		return new TCPListener(this.socket, this.logger, this.exceptionListener, this.connectionListener);
	}
}