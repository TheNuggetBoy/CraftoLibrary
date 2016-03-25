package de.craftolution.craftolibrary.fx;

import java.io.IOException;
import java.net.URL;

import de.craftolution.craftolibrary.Check;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 20.03.2016
 */
public class RemoteScene {

	/** TODO: Documentation */
	public static RemoteSceneBuilder builder() { return new RemoteSceneBuilder(); }

	private final Stage stage;
	private final Scene scene;
	private final Parent container;
	private final URL sceneFile;
	private final URL cssFile;

	RemoteScene(Stage stage, Scene scene, Parent container, URL sceneFile, URL cssFile) {
		this.stage = stage;
		this.scene = scene;
		this.container = container;
		this.sceneFile = sceneFile;
		this.cssFile = cssFile;
	}

	/** TODO: Documentation */
	public Stage getStage() { return this.stage; }

	/** TODO: Documentation */
	public Scene getScene() { return this.scene; }

	/** TODO: Documentation */
	public Parent getContainer() { return this.container; }

	/** TODO: Documentation */
	public URL getSceneFile() { return this.sceneFile; }

	/** TODO: Documentation */
	public URL getCSSFile() { return this.cssFile; }

	/** TODO: Documentation */
	public RemoteScene show() { this.stage.show(); return this; }

	/** TODO: Documentation */
	public RemoteScene close() { this.stage.close(); return this; }
	
	/** TODO: Documentation */
	public RemoteScene hide() { this.stage.hide(); return this; }

	static class RemoteSceneBuilder {
		
		private Stage stage;
		private URL sceneFile;
		private URL cssFile;
		private Controller controller;

		RemoteSceneBuilder() { }

		/** TODO: Documentation */
		public RemoteSceneBuilder stage(Stage stage) {
			this.stage = stage;
			return this;
		}

		/** TODO: Documentation */
		public RemoteSceneBuilder scene(URL sceneFile) {
			this.sceneFile = sceneFile;
			return this;
		}

		/** TODO: Documentation */
		public RemoteSceneBuilder scene(String sceneFile) throws IllegalArgumentException {
			URL url = getClass().getClassLoader().getResource(sceneFile);
			if (url == null) { throw new IllegalArgumentException("Failed to find a url for the scene file: " + sceneFile + "!"); }
			this.sceneFile = url;
			return this;
		}

		/** TODO: Documentation */
		public RemoteSceneBuilder style(URL cssFile) {
			this.cssFile = cssFile;
			return this;
		}

		/** TODO: Documentation */
		public RemoteSceneBuilder style(String cssFile) throws IllegalArgumentException {
			URL url = getClass().getClassLoader().getResource(cssFile);
			if (url == null) { throw new IllegalArgumentException("Failed to find a url for the css file: " + cssFile + "!"); }
			this.cssFile = url;
			return this;
		}

		/** TODO: Documentation */
		public RemoteSceneBuilder controller(Controller controller) {
			this.controller = controller;
			return this;
		}

		/** TODO: Documentation */
		public RemoteSceneBuilder title(String title) {
			if (this.stage == null) { this.stage = new Stage(); }
			this.stage.setTitle(title);
			return this;
		}

		/** TODO: Documentation */
		public RemoteSceneBuilder size(int width, int height) {
			if (this.stage == null) { this.stage = new Stage(); }
			this.stage.setWidth(width);
			this.stage.setHeight(height);
			return this;
		}

		/** TODO: Documentation */
		public RemoteSceneBuilder minSize(int width, int height) {
			if (this.stage == null) { this.stage = new Stage(); }
			this.stage.setMinWidth(width);
			this.stage.setMinHeight(height);
			return this;
		}
		
		/** TODO: Documentation */
		public RemoteSceneBuilder maxSize(int width, int height) {
			if (this.stage == null) { this.stage = new Stage(); }
			this.stage.setMaxWidth(width);
			this.stage.setMaxHeight(height);
			return this;
		}

		/** TODO: Documentation */
		public RemoteSceneBuilder fullscreen() {
			if (this.stage == null) { this.stage = new Stage(); }
			this.stage.setFullScreen(true);
			return this;
		}

		/** TODO: Documentation */
		public RemoteSceneBuilder centerOnScreen() {
			if (this.stage == null) { this.stage = new Stage(); }
			this.stage.centerOnScreen();
			return this;
		}

		/** TODO: Documentation */
		public RemoteScene build() throws IllegalArgumentException, IOException {
			Check.notNull(this.sceneFile, "The sceneFile cannot be null!");
			
			if (this.stage == null) { this.stage = new Stage(); }

			FXMLLoader loader = new FXMLLoader(this.sceneFile);
			if (this.controller != null) { loader.setController(controller); }
			Parent container = loader.load();
			this.controller = loader.getController();
			
			Scene scene = new Scene(container);
			this.stage.setScene(scene);
			
			if (this.cssFile != null) {
				scene.getStylesheets().add(this.cssFile.toExternalForm());
			}
			
			return new RemoteScene(stage, scene, container, sceneFile, cssFile);
		}

	}

}