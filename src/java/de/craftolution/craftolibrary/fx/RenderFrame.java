package de.craftolution.craftolibrary.fx;

import java.awt.Canvas;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 26.04.2016
 */
public class RenderFrame {

	RenderCanvas canvas = null;

	/** TODO: Documentation */
	public RenderFrame(final int width, final int height, final int buffering) {
		this.canvas = new RenderCanvas(width, height, buffering);
	}

	/** TODO: Documentation */
	public RenderFrame(final int width, final int height) {
		this(width, height, 3);
	}

	/** TODO: Documentation */
	public Graphics getGraphics() {
		final BufferStrategy bs = this.canvas.getBufferStrategy();
		if (bs == null) { this.canvas.createBufferStrategy(this.canvas.buffering); this.requestFocus(); }

		return this.canvas.getGraphics();
	}

	/** TODO: Documentation */
	public boolean isKeyPressed(final Key key) {
		return this.canvas.keys[key.getCode()];
	}

	/** TODO: Documentation */
	public int getWidth() { return this.canvas.getWidth(); }

	/** TODO: Documentation */
	public int getHeight() { return this.canvas.getHeight(); }

	/** TODO: Documentation */
	public boolean hasFocus() { return this.canvas.hasFocus(); }

	/** TODO: Documentation */
	public void requestFocus() { this.canvas.requestFocus(); }

	/** TODO: Documentation */
	public Canvas getCanvas() { return this.canvas; }

	/** TODO: Documentation */
	public void renderTo(final Container container) { container.add(this.canvas); }

	private static class RenderCanvas extends Canvas {

		private static final long serialVersionUID = 839766590404041914L;

		private final int buffering;
		final boolean[] keys = new boolean[128];

		RenderCanvas(final int width, final int height, final int buffering) {
			final Dimension dim = new Dimension(width, height);
			this.setMinimumSize(dim);
			this.setPreferredSize(dim);
			this.setMaximumSize(dim);

			this.buffering = buffering;
			this.addKeyListener(new KeyListener() {
				@Override
				public void keyTyped(final KeyEvent e) { }
				@Override
				public void keyPressed(final KeyEvent e) { RenderCanvas.this.keys[e.getKeyCode()] = true; }
				@Override
				public void keyReleased(final KeyEvent e) { RenderCanvas.this.keys[e.getKeyCode()] = false; }
			});
		}

	}

}