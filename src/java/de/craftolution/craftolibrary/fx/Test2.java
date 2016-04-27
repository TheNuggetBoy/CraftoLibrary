package de.craftolution.craftolibrary.fx;

import java.awt.Graphics;
import java.time.Duration;

import javax.swing.JFrame;

import de.craftolution.craftolibrary.RepeatingRunnable;
import de.craftolution.craftolibrary.fx.RenderFrame.Key;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 27.04.2016
 */
public class Test2 {

	public static void main(final String[] args) {
		new Test2();
	}

	RenderFrame renderFrame;
	int coord = 100;

	Test2() {
		final JFrame frame = new JFrame("Hallo Welt");
		frame.setSize(1600, 800);

		this.renderFrame = new RenderFrame(1600, 800, 3);
		this.renderFrame.renderTo(frame);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		RepeatingRunnable.run(Duration.ofMillis(16), this::update);
	}

	public void update() {
		if (this.renderFrame.isKeyPressed(Key.D)) {
			this.coord++;
		}

		this.render();
	}

	public void render() {
		final Graphics g = this.renderFrame.getGraphics();
		g.fillRect(this.coord, 100, 50, 50);
		g.fillRect(this.coord + 100, 100, 1, 1);

		System.out.println("HI " + Thread.currentThread().getName());
	}

}