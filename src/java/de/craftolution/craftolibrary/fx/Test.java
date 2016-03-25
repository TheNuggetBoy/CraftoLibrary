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

	public static void main(String[] args) { launch(); }

	@Override
	public void start(Stage primaryStage) throws Exception {
		RemoteScene scene = RemoteScene.builder()
		.scene("de/craftolution/craftolibrary/fx/scene.fxml")
		.style(Test.class.getResource("style.css"))
		.build();
		
		RemoteScene secondScene = RemoteScene.builder()
		.scene(Test.class.getResource("secondscene.fxml"))
		.style(Test.class.getResource("style.css"))
		.build();
		
		SceneSwapper swapper = new SceneSwapper().addAll(scene, secondScene);
		swapper.swap(scene);
		swapper.showTo(primaryStage);
		
		primaryStage.show();

		System.out.println("Works yeah");
		
		new Thread(() -> {
			
			try { Thread.sleep(1000 * 5); }
			catch (Exception e) { e.printStackTrace(); }

			Platform.runLater(() -> swapper.swap(secondScene));
			
		}).start();
	}

}