package de.craftolution.craftolibrary.fx;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 01.05.2016
 */
public class PixelFrame {

	private final GraphicsConfiguration graphicsConfig = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
	private final BufferedImage image;
	private final int[] pixels;
	private final int width, height;
	private final boolean translucent;

	/** TODO: Documentation */
	public PixelFrame(final int width, final int height, final boolean translucent) {
		this.image = this.graphicsConfig.createCompatibleImage(width, height, translucent ? Transparency.TRANSLUCENT : Transparency.OPAQUE);
		this.pixels = ((DataBufferInt) this.image.getRaster().getDataBuffer()).getData();
		this.width = width;
		this.height = height;
		this.translucent = translucent;
	}

	/** TODO: Documentation */
	public void renderTo(final Graphics g) {
		g.drawImage(this.image, 0, 0, this.width, this.height, null);
	}

	/** TODO: Documentation */
	public void renderTo(final Graphics g, final int offsetX, final int offsetY) {
		g.drawImage(this.image, offsetX, offsetY, this.width, this.height, null);
	}

	/** TODO: Documentation */
	public void renderTo(final Graphics g, final int offsetX, final int offsetY, final int width, final int height) {
		g.drawImage(this.image, offsetX, offsetY, width, height, null);
	}

	/** TODO: Documentation */
	public int getPixel(int x, int y) {
		return this.pixels[y * width + x];
	}

	/** TODO: Documentation */
	public void setPixel(int x, int y, int pixel) {
		this.pixels[y * width + x] = pixel;
	}

	/** TODO: Documentation */
	public int[] getPixels() { return this.pixels; }

	/** TODO: Documentation */
	public int getWidth() { return this.width; }

	/** TODO: Documentation */
	public int getHeight() { return this.height; }

	/** TODO: Documentation */
	public boolean isTranslucent() { return this.translucent; }

	/** TODO: Documentation */
	public BufferedImage getImage() { return this.image; }

	/** TODO: Documentation */
	public static int rgbToInt(int r, int g, int b, int a) {
		if (r > 255) { r = 255; } else if (r < 0) { r = 0; }
		if (g > 255) { g = 255; } else if (g < 0) { g = 0; }
		if (b > 255) { b = 255; } else if (b < 0) { b = 0; }
		if (a > 255) { a = 255; } else if (a < 0) { a = 0; }
		return (a & 0x0FF) << 24 | (r & 0x0FF) << 16 | (g & 0x0FF) << 8 | b & 0x0FF;
	}

	/** TODO: Documentation */
	public static Color intToRGB(int color) {
		int a = (color >> 24) & 0x0FF;
		int r = (color >> 16) & 0x0FF;
		int g = (color >> 8) & 0x0FF;
		int b = (color) & 0x0FF;
		return new Color(r, g, b, a);
	}

}