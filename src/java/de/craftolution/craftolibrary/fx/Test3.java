package de.craftolution.craftolibrary.fx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 20.03.2016
 */
public class Test3 extends Application {

	public static void main(final String[] args) { Application.launch(); }

	@Override
	public void start(final Stage primaryStage) throws Exception {
		final RemoteScene scene = RemoteScene.of(primaryStage, "de/craftolution/craftolibrary/fx/scene.fxml", "de/craftolution/craftolibrary/fx/style.css");
		primaryStage.setTitle("FIRST");

		final RemoteScene secondScene = RemoteScene.of("de/craftolution/craftolibrary/fx/secondscene.fxml", "de/craftolution/craftolibrary/fx/style.css");
		secondScene.getStage().setTitle("Second");
		secondScene.getStage().show();

		final SceneSwapper swapper = new SceneSwapper().addAll(scene, secondScene);
		swapper.swap(scene);
		swapper.showTo(primaryStage);

		primaryStage.show();

		System.out.println("Works yeah");

		new Thread(() -> {

			try { Thread.sleep(1000 * 5); }
			catch (final Exception e) { e.printStackTrace(); }

			Platform.runLater(() -> swapper.swap(secondScene));

		}).start();
	}

}