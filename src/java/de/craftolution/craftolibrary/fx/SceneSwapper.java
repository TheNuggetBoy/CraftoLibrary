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
	private final StackPane pane = new StackPane();

	private Scene currentScene;
	private final List<Scene> sceneList = Lists.newArrayList();

	/** TODO: Documentation */
	public SceneSwapper add(final Scene scene) {
		scene.getRoot().setOpacity(0.0f);
		scene.getRoot().setDisable(true);

		this.pane.getChildren().add(scene.getRoot());
		this.sceneList.add(scene);
		return this;
	}

	/** TODO: Documentation */
	public SceneSwapper addAll(final RemoteScene... scenes) {
		for (final RemoteScene scene : scenes) {
			this.add(scene);
		}
		return this;
	}

	/** TODO: Documentation */
	public SceneSwapper showTo(final Stage stage) {
		if (this.container == null) { this.container = new Scene(this.pane); }
		stage.setScene(this.container);
		return this;
	}

	/** TODO: Documentation */
	public SceneSwapper swap(final RemoteScene newScene) {
		for (final Scene scene : this.sceneList) {
			if (scene.equals(newScene)) {
				scene.getRoot().setOpacity(1);
				scene.getRoot().setDisable(false);
				this.currentScene = scene;
			}
			else {
				scene.getRoot().setOpacity(0);
				scene.getRoot().setDisable(true);
			}
		}
		return this;
	}

	/** TODO: Documentation */
	public Collection<Scene> getScenes() { return this.sceneList; }

	/** TODO: Documentation */
	public Scene getCurrentScene() { return this.currentScene; }

}