package de.craftolution.craftolibrary.network;

import java.io.IOException;
import java.net.InetAddress;

import de.craftolution.craftolibrary.Check;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 26.01.2016
 */
public class Test2 {

	static int count = 100;
	static boolean[] checks = new boolean[Test2.count];

	public static void main(final String[] args) throws IOException, InterruptedException {
		// Listener erstelen
		final TCPListener listener = new TCPListener(4837)
				.onNewConnection(client -> {
					System.out.println("A client connected!");
					client.onDisconnect(() -> System.out.println("A client disconnected!"));
					client.onPacket(bytes -> Test2.handleMessage(bytes));
				});
		listener.start();

		// Client erstelen
		final TCPConnection conn = new TCPConnection(InetAddress.getLocalHost(), 4837);
		conn.onPacket(bytes -> System.out.println("From server: " + new String(bytes)));
		conn.onDisconnect(() -> System.out.println("Disconnected from server!"));
		conn.connect();

		System.out.println("Lets get ready to send 100 packets:");
		
		// 100 Packete schicken
		for (int i = 0; i < Test2.count; i++) {
			conn.send(("Hello " + i).getBytes());
		}

		Thread.sleep(1000);

		for (int i = 0; i < Test2.count; i++) {
			if (!Test2.checks[i]) {
				System.out.println("MISSING: " + i);
			}
		}
	}

	static void handleMessage(final byte[] bytesFromClient) {
		final String msg = new String(bytesFromClient);
		System.out.println("From client: " + msg);

		String indexAsString = msg.replace("Hello ", "");
		if (Check.isInt(indexAsString)) {
			final int index = Integer.parseInt(msg.replace("Hello ", ""));

			Test2.checks[index] = true;
		}
	}

}