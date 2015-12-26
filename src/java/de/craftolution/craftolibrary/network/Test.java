package de.craftolution.craftolibrary.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;

import de.craftolution.craftolibrary.Check;
import de.craftolution.craftolibrary.StringUtils;

/**
 * TODO: Documentation
 *
 * @author Kevin
 * @since 13.09.2015
 */
public class Test {

	static int clientCount = 0;

	public static void main(String[] args) throws IOException {
		TCPListener listener = null;

		final BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

		final ArrayList<TCPConnection> clients = new ArrayList<TCPConnection>();
		final ArrayList<TCPConnection> connectedClients = new ArrayList<>();

		System.out.println(">>>>>> Inputreader ready! Available commands:");
		System.out.println("> exit                    // Closes the application");
		System.out.println("> startserver <port>      // Starts the tcplistener");
		System.out.println("> startclient <ip> <port> // Starts a client with the given ip and port");
		System.out.println("> disconnect <clientid>   // Disconnects the client with the given id");
		System.out.println("> broadcast <message>     // Sends a message to every client connected to the listener");
		System.out.println("> send <id> <message>     // Tells the client with the given id to send the specified message");
		System.out.println("> listclients             // Lists every client that is currently active");

		try {
			System.out.print("> ");
			String input = null;
			while ((input = inputReader.readLine()) != null) {
				input = input.replace(">", "");
				input = input.trim();
				if (input.startsWith(" ")) {
					input = input.substring(1, input.length());
				}

				// EXIT
				if (input.toLowerCase().startsWith("exit")) {
					System.exit(1);
					break;
				}

				// STARTSERVER
				else if (input.toLowerCase().startsWith("startserver")) {
					if (listener != null) {
						System.err.println("> Error: The server is already active.");
						continue;
					}
					args = input.split(" ");
					if (args.length < 2) {
						System.err.println("> Error: You have to specifiy a port for the server!");
						continue;
					}
					if (!Check.isInt(args[1])) {
						System.err.println("> Error: The specified port is not a number!");
						continue;
					}

					listener = TCPListener.builder()
							.port(Integer.parseInt(args[1]))
							.onNewConnection(connection -> {
								clients.add(connection);
								connectedClients.add(connection);

								connection.onDisconnect(() -> System.err.println("Client " + clients.indexOf(connection) + ": disconnected from the server."));

								connection.onPacket(bytes -> System.out.println("Client " + clients.indexOf(connection) + ": " + new String(bytes)));

								connection.send("Welcome to the server!".getBytes());
							})
							.build();

					System.out.println("> Server started successfully.");
				}

				// STARTCLIENT
				else if (input.toLowerCase().startsWith("startclient")) {
					args = input.split(" ");
					if (args.length < 3) {
						System.err.println("> Error: You have to specify a ip and port!");
						continue;
					}
					final String ip = args[1];
					if (!Check.isInt(args[2])) {
						System.err.println("> Error: The specified port is not a number!");
						continue;
					}

					final TCPConnection connection = new TCPConnection(InetAddress.getByName(ip), Integer.parseInt(args[2]));
					connection.onPacket(bytes -> System.out.println("Server of client " + clients.indexOf(connection) + ": " + new String(bytes)))
					.onDisconnect(() -> System.err.println("Client " + clients.indexOf(connection) + " disconnected."))
					.connect();

					clients.add(connection);

					System.out.println("> Client " + clients.indexOf(connection) + " connected successfully.");
				}

				// DISCONNECT
				else if (input.toLowerCase().startsWith("disconnect")) {
					args = input.split(" ");
					if (args.length < 2) {
						System.err.println("> Error: You have to specifiy a id for the client!");
						continue;
					}
					if (!Check.isInt(args[1])) {
						System.err.println("> Error: The specified id is not a number!");
						continue;
					}
					final int id = Integer.parseInt(args[1]);
					if (id >= clients.size()) {
						System.err.println("> Error: The specified id is too high!");
						continue;
					}

					final TCPConnection client = clients.get(Integer.parseInt(args[1]));
					client.disconnect();

					clients.remove(id);

					System.out.println("> Client " + id + " disconnected.");
				}

				// BROADCAST
				else if (input.toLowerCase().startsWith("broadcast")) {
					args = input.split(" ");
					if (args.length < 2) {
						System.err.println("> Error: You have to specify a message first!");
						continue;
					}
					final String message = StringUtils.join(" ", 1, args.length, args);
					connectedClients.forEach(client -> client.send(message.getBytes()));

					System.out.println("> Broadcasted: " + message);
				}

				// SEND
				else if (input.toLowerCase().startsWith("send")) {
					args = input.split(" ");
					if (args.length < 2) {
						System.err.println("> Error: You have to specifiy a id for the client!");
						continue;
					}
					if (!Check.isInt(args[1])) {
						System.err.println("> Error: The specified id is not a number!");
						continue;
					}
					final int id = Integer.parseInt(args[1]);
					if (id >= clients.size()) {
						System.err.println("> Error: The specified id is too high!");
						continue;
					}

					final TCPConnection client = clients.get(Integer.parseInt(args[1]));
					if (args.length < 3) {
						System.out.println("> Error: You have to specify a message!");
						continue;
					}
					final String message = StringUtils.join(" ", 2, args.length, args);
					client.send(message.getBytes());
				}

				// LISTCLIENTS
				else if (input.toLowerCase().startsWith("listclients")) {
					if (clients.size() <= 0) {
						System.out.println("No clients available.");
					}
					else {
						for (int clientId = 0; clientId < clients.size(); clientId++) {
							final TCPConnection client = clients.get(clientId);
							final StringBuilder b = new StringBuilder("Client " + clientId + " ("
									+ client.getInetAddress().getHostAddress() + ":" + client.getPort() + ")");
							if (connectedClients.contains(client)) {
								b.append(" (SERVER-SIDE)");
							}
							else {
								b.append(" (STANDALONE)");
							}

							System.out.println(b.toString());
						}
					}
				}

				// UNKNOWN
				else {
					System.err.println("> Error: Specified unknown command!");
				}

				Thread.sleep(8);
				System.out.print("> ");
			}
		}
		catch (final Exception e) {
			e.printStackTrace();
		}

		if (listener != null) {
			listener.stop();
		}
		clients.forEach(client -> client.disconnect());

		System.out.println("Exiting...");
		System.exit(1);

		//		listener = new TCPListener(4837);
		//
		//		// When a new client connects
		//		listener.onNewClient(newClient -> {
		//			newClient.onMessage(message -> System.out.println("Client1 to server: " + new String(message)));
		//			newClient.onDisconnect(() -> System.out.println("Client disconnected from server!"));
		//
		//			System.out.println("New client connected! (Total: " + ((Test.clientCount+=1) + 1) + ")");
		//
		//			newClient.send("Hello Client!".getBytes());
		//		});
		//
		//		// Client 1
		//		final TCPClient client1 = new TCPClient("localhost", 4837)
		//				.onMessage(message -> System.out.println("Server to client1: " + new String(message)))
		//				.onDisconnect(() -> System.out.println("Client1 disconnected."));
		//
		//		client1.send("Hello Server!".getBytes());
		//
		//		client1.disconnect();
	}

}