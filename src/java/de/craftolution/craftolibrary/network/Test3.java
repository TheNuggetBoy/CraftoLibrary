package de.craftolution.craftolibrary.network;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 10.04.2016
 */
public class Test3 {

	public static void main(final String[] args) {
		final TCPServer server = new TCPServer(4837);

		final TCPScanner scannerClientSide = new TCPScanner("127.0.0.1", 4837);

		final TCPScanner scannerServerSide = server.nextConnection();

		scannerClientSide.send("Hello Server!");
		System.out.println("From client: " + scannerServerSide.nextLine() );

		scannerServerSide.send("Hello Client!");
		
		do {
			if (scannerClientSide.hasNext()) {
				System.out.println("Has next!");
				System.out.println("From server: " + scannerClientSide.nextLine() );
			}
		} while (!scannerClientSide.hasNext());
		

		server.close();
		scannerClientSide.close();
		scannerServerSide.close();
	}

}