package de.craftolution.craftolibrary.network;

import java.io.IOException;

/**
 * TODO: Documentation
 *
 * @author Kevin
 * @since 13.09.2015
 */
public class Test {

	static int clientCount = 0;

	public static void main(final String[] args) throws IOException {
		final TCPListener listener = new TCPListener(4837);

		// When a new client connects
		listener.onNewClient(newClient -> {
			newClient.onMessage(message -> System.out.println("Client1 to server: " + message));
			newClient.onDisconnect(() -> System.out.println("Client disconnected from server!"));

			System.out.println("New client connected! (Total: " + (clientCount++) + ")");

			newClient.send("Hello Client!");
		});

		// Client 1
		final TCPClient client1 = new TCPClient("localhost", 4837)
				.onMessage(message -> System.out.println("Server to client1: " + message))
				.onDisconnect(() -> System.out.println("Client1 disconnected."));

		client1.send("Hello Server!");

		client1.disconnect();
	}

}