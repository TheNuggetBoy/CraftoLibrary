package de.craftolution.craftolibrary.fx;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 20.03.2016
 */
public class SceneSwapper {

	private Scene container;
	private StackPane pane = new StackPane();

	private RemoteScene currentScene;
	private List<RemoteScene> sceneList = Lists.newArrayList();

	/** TODO: Documentation */
	public SceneSwapper add(RemoteScene scene) {
		scene.getContainer().setOpacity(0.0f);
		scene.getContainer().setDisable(true);

		this.pane.getChildren().add(scene.getContainer());
		this.sceneList.add(scene);
		return this;
	}

	/** TODO: Documentation */
	public SceneSwapper addAll(RemoteScene... scenes) {
		for (RemoteScene scene : scenes) {
			this.add(scene);
		}
		return this;
	}

	/** TODO: Documentation */
	public SceneSwapper showTo(Stage stage) {
		if (this.container == null) { this.container = new Scene(this.pane); }
		stage.setScene(this.container);
		return this;
	}

	/** TODO: Documentation */
	public SceneSwapper swap(RemoteScene newScene) {
		for (RemoteScene scene : this.sceneList) {
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
	public Collection<RemoteScene> getScenes() { return this.sceneList; }

	/** TODO: Documentation */
	public RemoteScene getCurrentScene() { return this.currentScene; }

}