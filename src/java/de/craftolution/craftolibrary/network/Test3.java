package de.craftolution.craftolibrary.network;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 10.04.2016
 */
public class Test3 {

	public static void main(String[] args) {
		TCPServer server = new TCPServer(4837);
		
		TCPScanner scannerClientSide = new TCPScanner("127.0.0.1", 4837);
		
		TCPScanner scannerServerSide = server.nextConnection();
		
		scannerClientSide.send("Hello Server!");
		System.out.println("From client: " + scannerServerSide.nextLine() );

		scannerServerSide.send("Hello Client!");
		System.out.println("From server: " + scannerClientSide.nextLine() );
		
		server.close();
		scannerClientSide.close();
		scannerServerSide.close();
	}

}