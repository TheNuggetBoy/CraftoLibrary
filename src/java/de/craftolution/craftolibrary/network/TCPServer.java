package de.craftolution.craftolibrary.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 10.04.2016
 */
public class TCPServer {

	private final ServerSocket socket;
	private boolean listening = true;

	TCPServer(final int port) {
		try {
			this.socket = new ServerSocket(port);

		}
		catch (final IOException e) { throw new IllegalArgumentException("Failed to hist on port " + port + "!", e); }
	}

	/** TODO: Documentation */
	public void close() { this.listening = false; }

	/** TODO: Documentation */
	public TCPScanner nextConnection() {
		while (this.listening) {
			Socket newSocket;
			try { newSocket = this.socket.accept(); }
			catch (final IOException e) { throw new IllegalStateException("Failed to accept new connection!", e); }

			if (!this.listening) { return null; }

			final TCPScanner scanner = new TCPScanner(newSocket);
			return scanner;
		}

		return null;
	}

}