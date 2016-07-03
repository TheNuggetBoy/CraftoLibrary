package de.craftolution.craftolibrary.fx;

import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 03.06.2016
 */
public class FXUtils {

	private FXUtils() { }

	/** TODO: Documentation */
	public static void autoScaleCanvas(final Stage stage, final Canvas canvas) {
		stage.widthProperty().addListener((obj, oldValue, newValue) -> {
			double difference = newValue.doubleValue() - oldValue.doubleValue();
			double oldCanvasWidth = canvas.getWidth();
			canvas.setWidth(oldCanvasWidth + difference);

			double scale = canvas.getWidth() / oldCanvasWidth;
			canvas.getGraphicsContext2D().scale(scale, 1.0);
		});

		stage.heightProperty().addListener((obj, oldValue, newValue) -> {
			double difference = newValue.doubleValue() - oldValue.doubleValue();
			double oldCanvasHeight = canvas.getHeight();
			canvas.setHeight(oldCanvasHeight + difference);
			
			double scale = canvas.getHeight() / oldCanvasHeight;
			canvas.getGraphicsContext2D().scale(1.0, scale);
		});
	}

	/** TODO: Documentation */
	public static void autoScaleChildren(Stage stage, Parent container) {
		final double originalWidth = stage.getWidth();
		final double originalHeight = stage.getHeight();
		
		stage.widthProperty().addListener((obj, oldValue, newValue) -> {
			double scale = newValue.doubleValue() / originalWidth;
			container.setScaleX(scale);
		});
		
		stage.heightProperty().addListener((obj, oldValue, newValue) -> {
			double scale = newValue.doubleValue() / originalHeight;
			container.setScaleY(scale);
		});
	}

}