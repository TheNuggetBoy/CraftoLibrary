package de.craftolution.craftolibrary.logging;

import java.io.IOException;

import org.apache.log4j.Level;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 16.09.2015
 */
public class Test {

	public static void main(final String[] args) throws IllegalStateException, IllegalArgumentException, IOException {
		Logger.builder()
		.name("TestLogger")
		.writer(new LogWriter())
		.output(System.out, Level.INFO, Level.DEBUG, Level.TRACE)
		.output(System.err, Level.FATAL, Level.ERROR, Level.WARN)
		.build()
		.info("This is a info message!")
		.warn("This is a warning...")
		.fatal("This is a fatal message!")
		.error("This is a error!")
		.debug("This is a debug message")
		.trace("This is a trace message")
		.info("Hello %s are you %d?", "Peter", "okay");
	}

}