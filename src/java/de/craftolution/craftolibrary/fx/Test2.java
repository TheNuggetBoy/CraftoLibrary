package de.craftolution.craftolibrary.fx;

import java.awt.Color;
import java.awt.Graphics;
import java.time.Duration;
import java.util.Random;

import javax.swing.JFrame;

import de.craftolution.craftolibrary.Threads;

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
	
	PixelFrame pixels = new PixelFrame(1600, 800, false);

	Test2() {
		final JFrame frame = new JFrame("Hallo Welt");
		frame.setSize(1600, 800);

		this.renderFrame = new RenderFrame(1600, 800, 3);
		this.renderFrame.renderTo(frame);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		while (true) {
			this.update();
			
			Threads.sleep(Duration.ofSeconds(1));
		}
	}

	Random r = new Random();
	
	public void update() {
		if (this.renderFrame.isKeyPressed(Key.D)) {
			this.coord++;
		}
		
		for (int y = 300; y < 400; y++) {
			for (int x = 300; x < 400; x++) {
				this.pixels.getPixels()[y * 1600 + x] = r.nextInt(12000);
			}
		}

		this.render();
	}

	public void render() {
		final Graphics g = this.renderFrame.getGraphics();
		this.pixels.renderTo(g);
		g.fillRect(this.coord, 100, 50, 50);
		g.fillRect(this.coord + 100, 100, 1, 1);
		g.setColor(Color.RED);
		g.fillOval(300, 300, 300, 300);
		g.setColor(Color.BLUE);
		g.fillOval(301, 301, 298, 298);

		System.out.println("HI " + Thread.currentThread().getName());
	}

}