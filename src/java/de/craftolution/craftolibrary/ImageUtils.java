package de.craftolution.craftolibrary;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 05.05.2016
 */
public class ImageUtils {

	/** TODO: Documentation */
	public static Image getImage(String classpath) {
		try {
			return ImageIO.read(ImageUtils.class.getResourceAsStream("/" + classpath));
		}
		catch (IOException e) {
			throw new IllegalArgumentException("The given path '" + classpath + "' doesnt exist..", e);
		}
	}

}