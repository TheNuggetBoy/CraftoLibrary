package de.craftolution.craftolibrary.network;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import de.craftolution.craftolibrary.Check;

/**
 * A simpler version of the {@link TCPConnection} by using a scanner
 * for reading packets from the connected socket.
 * 
 * Although the term 'scanner' refers to only reading from a source, this class
 * uses it for easier understanding.
 *
 * @author Fear837
 * @since 10.04.2016
 */
public class TCPScanner {

	private final InetAddress address;
	private final int port;

	private final Socket socket;
	private final Scanner scanner;
	private final DataOutputStream output;

	public TCPScanner(final Socket socket) {
		Check.notNull(socket, "The socket cannot be null!");
		try {
			this.address = socket.getInetAddress();
			this.port = socket.getPort();
			this.socket = socket;
			this.socket.setKeepAlive(true);
			this.scanner = new Scanner(this.socket.getInputStream());
			this.output = new DataOutputStream(this.socket.getOutputStream());
		}
		catch (final IOException e) { throw new IllegalArgumentException("Failed to accept connection " + this.address + " on port " + this.port + "!", e); }
	}

	public TCPScanner(final InetAddress address, final int port) {
		Check.notNull(address, "The address cannot be null!");
		Check.isTrue(port > 0, "The port has to be greather than 0!");
		try {
			this.address = address;
			this.port = port;
			this.socket = new Socket(address, port);
			this.socket.setKeepAlive(true);
			this.scanner = new Scanner(this.socket.getInputStream());
			this.output = new DataOutputStream(this.socket.getOutputStream());
		}
		catch (final IOException e) { throw new IllegalArgumentException("Failed to connect to tcp address " + address + " on port " + port + "!", e); }
	}

	public TCPScanner(final String address, final int port) {
		Check.notNullNotEmpty(address, "The address cannot be null or empty!");
		Check.isTrue(port > 0, "The port has to be greather than 0!");
		try {
			this.address = InetAddress.getByName(address);
			this.port = port;
			this.socket = new Socket(address, port);
			this.socket.setKeepAlive(true);
			this.scanner = new Scanner(this.socket.getInputStream());
			this.output = new DataOutputStream(this.socket.getOutputStream());
		}
		catch (final IOException e) { throw new IllegalArgumentException("Failed to connect to tcp address " + address + " on port " + port + "!", e); }
	}

	/** TODO: Documentation */
	public InetAddress getRemoteAddress() { return this.address; }

	/** TODO: Documentation */
	public int getRemotePort() { return this.port; }

	/** TODO: Documentation */
	public InetAddress getLocalAddress() { return this.socket.getLocalAddress(); }

	/** TODO: Documentation */
	public int getLocalPort() { return this.socket.getLocalPort(); }

	/** TODO: Documentation */
	public void close() {
		this.scanner.close();
		try { this.socket.close(); }
		catch (final IOException e) { }
	}

	// --- Sending ---

	/** TODO: Documentation */
	public void send(final String string) {
		try { this.output.write(new StringBuilder(string).append('\n').toString().getBytes()); this.output.flush(); }
		catch (final IOException e) { throw new IllegalStateException("Cant send to tcp address " + this.address + " on port " + this.port + "...", e); }
	}

	/** TODO: Documentation */
	public void send(final int integer) {
		this.send(String.valueOf(integer));
	}

	/** TODO: Documentation */
	public void send(final boolean bool) {
		this.send(String.valueOf(bool));
	}

	/** TODO: Documentation */
	public void send(final double d) {
		this.send(String.valueOf(d));
	}

	/** TODO: Documentation */
	public void send(final float f) {
		this.send(String.valueOf(f));
	}

	/** TODO: Documentation */
	public void send(final char c) {
		this.send(String.valueOf(c));
	}

	/** TODO: Documentation */
	public void send(final byte b) {
		this.send(String.valueOf(b));
	}

	// --- Reading ---

	/** TODO: Documentation */
	public boolean hasNext() { return this.scanner.hasNext(); }

	/** TODO: Documentation */
	public String next() { return this.scanner.next(); }

	/** TODO: Documentation */
	public boolean nextBoolean() { return this.scanner.nextBoolean(); }

	/** TODO: Documentation */
	public byte nextByte() { return this.scanner.nextByte(); }

	/** TODO: Documentation */
	public int nextInt() { return this.scanner.nextInt(); }

	/** TODO: Documentation */
	public String nextLine() { return this.scanner.nextLine(); }

	/** TODO: Documentation */
	public double nextDouble() { return this.scanner.nextDouble(); }

	/** TODO: Documentation */
	public float nextFloat() { return this.scanner.nextFloat(); }

	/** TODO: Documentation */
	public long nextLong() { return this.scanner.nextLong(); }

	/** TODO: Documentation */
	public short nextShort() { return this.scanner.nextShort(); }

	/** TODO: Documentation */
	public TCPScanner skip(final String pattern) { this.scanner.skip(pattern); return this; }

	/** TODO: Documentation */
	public TCPScanner remove() { this.scanner.remove(); return this; }

}