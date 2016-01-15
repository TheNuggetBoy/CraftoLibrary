package de.craftolution.craftolibrary.fx;

import java.util.ArrayList;
import java.util.Collection;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 10.01.2016
 */
public class SceneContainer {

	private Scene scene;
	private FXScene currentScene;
	private final ArrayList<FXScene> scenes = new ArrayList<>();
	private final StackPane pane = new StackPane();

	/** TODO: Documentation */
	public SceneContainer add(FXScene scene) {
		this.pane.getChildren().add(scene.getContainer());
		this.scenes.add(scene);
		return this;
	}

	/** TODO: Documentation */
	public SceneContainer addAll(FXScene... scenes) {
		for (FXScene scene : scenes) { this.add(scene); }
		return this;
	}

	/** TODO: Documentation */
	public SceneContainer displayTo(Stage stage) {
		if (this.scene == null) { this.scene = new Scene(this.pane); }
		stage.setScene(this.scene);
		return this;
	}

	/** TODO: Documentation */
	public SceneContainer switchScene(FXScene newScene) {
		for (FXScene scene : scenes) {
			if (scene.equals(newScene)) {
				scene.getContainer().setOpacity(1);
				scene.getContainer().setDisable(false);
				this.currentScene = scene;
			}
			else {
				scene.getContainer().setOpacity(0);
				scene.getContainer().setDisable(true);
			}
		}

		return this;
	}

	/** TODO: Documentation */
	public Collection<FXScene> getScenes() { return this.scenes; }

	/** TODO: Documentation */
	public FXScene getCurrentScene() { return this.currentScene; }

}