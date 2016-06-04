package de.craftolution.craftolibrary.fx;

import java.time.Duration;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 20.03.2016
 */
public class Test4 extends Application {

	public static void main(final String[] args) { Application.launch(); }

	public static double x = 10.0, y = 10.0, speed = 3.0;
	public static double WIDTH = 800.0, HEIGHT = 600.0;

	@Override
	public void start(final Stage primaryStage) throws Exception {
		final Canvas canvas = new Canvas(WIDTH, HEIGHT);
		final GraphicsContext gc = canvas.getGraphicsContext2D();
		
		final InputListener input = new InputListener(primaryStage);
		
		primaryStage.setScene(new Scene(new Group(canvas)));
		primaryStage.setTitle("Hallo");
		primaryStage.show();

		FXUtils.autoScaleCanvas(primaryStage, canvas);

		ScheduledRunnable.run(Duration.ofMillis(8), (r) -> {
			if (input.isPressed(KeyCode.W) && y > 0) { y -= speed; }
			if (input.isPressed(KeyCode.S) && y < HEIGHT - 50) { y += speed; }
			if (input.isPressed(KeyCode.A) && x > 0) { x -= speed; }
			if (input.isPressed(KeyCode.D) && x < WIDTH - 50) { x += speed; }
			if (input.isPressed(KeyCode.G)) { primaryStage.setFullScreen(true); }
			if (input.isPressed(KeyCode.Q)) { if (!primaryStage.isFullScreen()) { Platform.exit(); } }

			gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

			gc.setFill( Color.RED );
			gc.fillRect(x, y, 50, 50);
			
			gc.strokeText("Hello World", 200, 200);
			gc.fillText("Hello World", 100, 100);
			
			gc.setFill( Color.BLACK );
			gc.fillRect(0, 0, WIDTH, 2);
			gc.fillRect(0, 0, 2, HEIGHT);
			gc.fillRect(0, HEIGHT - 2, WIDTH, 2);
			gc.fillRect(WIDTH - 2, 0, 2, HEIGHT);
		});
	}

}