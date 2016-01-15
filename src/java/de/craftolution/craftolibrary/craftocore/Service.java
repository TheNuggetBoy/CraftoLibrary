package de.craftolution.craftolibrary.craftocore;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import de.craftolution.craftolibrary.Scheduled;
import de.craftolution.craftolibrary.ToStringable;
import de.craftolution.craftolibrary.network.TCPConnection;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 15.01.2016
 */
public class Service implements ToStringable {

	private final byte serviceId;
	private final InetAddress coreAddress;
	private final int corePort;
	private final AtomicInteger packetCount = new AtomicInteger();

	/** TODO: Documentation */
	@Nullable private TCPConnection connection;

	// Listeners
	@Nullable private Consumer<String> logMessageHandler;
	@Nullable private Consumer<Exception> exceptionHandler;
	@Nullable private Consumer<byte[]> packetHandler;
	@Nullable private Runnable disconnectHandler;
	@Nullable private Executor executor;

	Service(final byte serviceId, final InetAddress coreAddress, final int corePort) {
		this.serviceId = serviceId;
		this.coreAddress = coreAddress;
		this.corePort = corePort;
	}

	/** TODO: Documentation */
	public byte getId() { return this.serviceId; }

	/** TODO: Documentation */
	public InetAddress getCoreAddress() { return this.coreAddress; }

	/** TODO: Documentation */
	public int getCorePort() { return this.corePort; }

	/**
	 * Returns whether or not this {@link TCPConnection} is still connected.
	 * @return {@code True} if still connected.
	 */
	public boolean isConnected() { return this.connection != null && this.connection.isConnected(); }

	/** TODO: Documentation */
	public Service connect() throws IOException {
		if (!this.isConnected()) {
			this.connection = new TCPConnection(this.coreAddress, this.corePort)
					.onDisconnect(this.disconnectHandler)
					.onException(this.exceptionHandler)
					.onLogMessage(this.logMessageHandler)
					.withExecutor(this.executor);

			this.connection.onPacket(bytes -> {
				Packet packet = Packet.ofBytes(bytes);
			});

			this.connection.connect();
		}
		return this;
	}

	/**
	 * Closes the socket.
	 *
	 * <p> Any thread currently blocked in an I/O operation upon this connection
	 * will throw a {@link SocketException}. </p>
	 *
	 * <p> Once the socket has been closed, it is not available for further networking
	 * use (i.e. can't be reconnected or rebound). A new socket needs to be
	 * created. </p>
	 *
	 * <p> Closing this socket will also close the socket's
	 * {@link java.io.InputStream InputStream} and
	 * {@link java.io.OutputStream OutputStream}. </p>
	 *
	 * <p> If this socket has an associated channel then the channel is closed
	 * as well. </p>
	 */
	public void disconnect() {
		if (this.disconnectHandler != null) {
			try { this.disconnectHandler.run(); }
			catch (final Exception e) { this.handleException(e); }
		}

		if (this.connection != null) { this.connection.disconnect(); }
	}
	
	/**
	 * Sends a byte array to the inputstream on the other end of this connection.
	 *
	 * If a {@link IOException} occurs during sending, it'll be handled by the specified
	 * {@link #exceptionListener} if available and also returned as an optional.
	 *
	 * Keep in mind that if {@link #isConnected()} returns {@code false} this method will
	 * return {@link Optional#empty()} immediately.
	 *
	 * @param bytes - The byte array to send.
	 * @return Returns an {@link IOException} if one occured.
	 */
	public Optional<IOException> send(PacketType type, byte[] bytes) {
		if (this.isConnected()) {
			final Packet packet = Packet.builder().service(this.serviceId).type(type.getId()).packetId(this.packetCount.getAndIncrement()).content(bytes).build();
			return this.connection.send(packet.toByteArray());
		}
		return Optional.empty();
	}

	public void subscribe(byte serviceId) {
		
	}

	public void unsubscribe(byte serviceId) {
		
	}

	public Scheduled<Optional<Integer>> askTunnel(byte serviceId) {
		return null;
	}

	public Scheduled<Optional<Long>> syncTime(byte serviceId) {
		return null;
	}

	/**
	 * Sets the handler for incoming log messages.
	 * @param logMessageHandler - The consumer that accepts logs.
	 * @return Returns itself.
	 */
	public Service onLogMessage(final Consumer<String> logMessageHandler) {
		this.logMessageHandler = logMessageHandler;
		return this;
	}

	/**
	 * Sets the handler for incoming exceptions.
	 * @param exceptionHandler - The consumer that accepts exceptions.
	 * @return Returns itself.
	 */
	public Service onException(final Consumer<Exception> exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
		return this;
	}

	/**
	 * Sets the handler for incoming packets.
	 * @param packetHandler - The consumer that accepts packets.
	 * @return Returns itself.
	 */
	public Service onPacket(final Consumer<byte[]> packetHandler) {
		this.packetHandler = packetHandler;
		return this;
	}

	/**
	 * Sets the handler that gets executed when this instance disconnects.
	 * @param disconnectHandler - The handler
	 * @return Returns itself.
	 */
	public Service onDisconnect(final Runnable disconnectHandler) {
		this.disconnectHandler = disconnectHandler;
		return this;
	}

	/**
	 * Sets an executor that provides a thread for listening on incoming packets for this instance.
	 * @param executor - The executor
	 * @return Returns itself.
	 */
	public Service withExecutor(final Executor executor) {
		this.executor = executor;
		return this;
	}

	// --- Convinience methods ---
	@SuppressWarnings("unused")
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
				.with("serviceId", this.getId())
				.with("coreAddress", this.coreAddress.getHostAddress())
				.with("corePort", this.corePort)
				.with("connected", this.isConnected())
				.toString();
	}

}