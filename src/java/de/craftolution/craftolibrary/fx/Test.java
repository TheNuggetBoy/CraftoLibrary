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
public class Test extends Application {

	public static void main(final String[] args) { Application.launch(); }

	@Override
	public void start(final Stage primaryStage) throws Exception {
		final RemoteScene scene = RemoteScene.builder()
				.scene("de/craftolution/craftolibrary/fx/scene.fxml")
				.style(Test.class.getResource("style.css"))
				.build();

		final RemoteScene secondScene = RemoteScene.builder()
				.scene(Test.class.getResource("secondscene.fxml"))
				.style(Test.class.getResource("style.css"))
				.build();

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