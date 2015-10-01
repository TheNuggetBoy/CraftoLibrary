package de.craftolution.craftolibrary;

import java.awt.GraphicsEnvironment;

import javax.swing.JOptionPane;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 13.09.2015
 */
public class Main {

	private static final String MESSAGE = "\nHi, you just tried to execute the CraftoLibrary jar!"
			+ "\nVisit https://github.com/Craftolution/CraftoLibrary for further help on how to properly use this library.\n";

	public static void main(final String[] args) {
		System.err.println(Main.MESSAGE);
		if (!GraphicsEnvironment.isHeadless()) {
			JOptionPane.showMessageDialog(null, Main.MESSAGE, "You executed the wrong jar!", JOptionPane.ERROR_MESSAGE);
		}
	}

}