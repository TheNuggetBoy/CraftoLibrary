package de.craftolution.craftolibrary.fx;

import java.io.IOException;
import java.net.URL;

import javax.annotation.Nullable;

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
public class RemoteScene extends Scene {

	/** TODO: Documentation */
	public static RemoteSceneBuilder builder() { return new RemoteSceneBuilder(); }

	private final Stage stage;
	private final URL sceneFile;
	@Nullable private final URL cssFile;

	RemoteScene(final Stage stage, final Parent root, final URL sceneFile, final URL cssFile) {
		super(root);
		this.stage = stage;
		this.sceneFile = sceneFile;
		this.cssFile = cssFile;
	}

	/** TODO: Documentation */
	public Stage getStage() { return this.stage; }

	/** TODO: Documentation */
	public URL getSceneFile() { return this.sceneFile; }

	/** TODO: Documentation */
	@Nullable public URL getCSSFile() { return this.cssFile; }

	/** TODO: Documentation */
	public RemoteScene show() { this.stage.show(); return this; }

	/** TODO: Documentation */
	public RemoteScene close() { this.stage.close(); return this; }

	/** TODO: Documentation */
	public RemoteScene hide() { this.stage.hide(); return this; }

	private static URL toURL(String urlAsString) {
		final URL url = RemoteScene.class.getClassLoader().getResource(urlAsString);
		if (url == null) { throw new IllegalArgumentException("Failed to find a url for the resource: " + urlAsString + "!"); }
		return url;
	}

	/** TODO: Documentation */
	public static RemoteScene of(String sceneFile) throws IOException { return of(new Stage(), toURL(sceneFile), null, null); }
	/** TODO: Documentation */
	public static RemoteScene of(URL sceneFile) throws IOException { return of(new Stage(), sceneFile, null, null); }
	/** TODO: Documentation */
	public static RemoteScene of(String sceneFile, String cssFile) throws IOException { return of(new Stage(), toURL(sceneFile), toURL(cssFile), null); }
	/** TODO: Documentation */
	public static RemoteScene of(URL sceneFile, URL cssFile) throws IOException { return of(new Stage(), sceneFile, cssFile, null); }
	/** TODO: Documentation */
	public static RemoteScene of(String sceneFile, String cssFile, Object controller) throws IOException { return of(new Stage(), toURL(sceneFile), toURL(cssFile), controller); }

	/** TODO: Documentation */
	public static RemoteScene of(Stage stage, String sceneFile) throws IOException { return of(stage, toURL(sceneFile), null, null); }
	/** TODO: Documentation */
	public static RemoteScene of(Stage stage, URL sceneFile) throws IOException { return of(stage, sceneFile, null, null); }
	/** TODO: Documentation */
	public static RemoteScene of(Stage stage, String sceneFile, String cssFile) throws IOException { return of(stage, toURL(sceneFile), toURL(cssFile), null); }
	/** TODO: Documentation */
	public static RemoteScene of(Stage stage, URL sceneFile, URL cssFile) throws IOException { return of(stage, sceneFile, cssFile, null); }
	/** TODO: Documentation */
	public static RemoteScene of(Stage stage, String sceneFile, String cssFile, Object controller) throws IOException { return of(stage, toURL(sceneFile), toURL(cssFile), controller); }

	/** TODO: Documentation */
	public static RemoteScene of(Stage stage, final URL sceneFile, @Nullable final URL cssFile, @Nullable Object controller) throws IOException {
		Check.nonNulls("The stage/sceneFile cannot be null!", stage, sceneFile);

		if (stage == null) { stage = new Stage(); }

		final FXMLLoader loader = new FXMLLoader(sceneFile);
		if (controller != null) { loader.setController(controller); }
		final Parent root = loader.load();
		controller = loader.getController();

		final RemoteScene scene = new RemoteScene(stage, root, sceneFile, cssFile);

		if (cssFile != null) {
			scene.getStylesheets().add(cssFile.toExternalForm());
		}

		stage.setScene(scene);

		return scene;
	}

	/** TODO: Documentation */
	public static class RemoteSceneBuilder {

		private Stage stage;
		private URL sceneFile;
		private URL cssFile;
		private Object controller;

		RemoteSceneBuilder() { }

		/** TODO: Documentation */
		public RemoteSceneBuilder stage(final Stage stage) {
			this.stage = stage;
			return this;
		}

		/** TODO: Documentation */
		public RemoteSceneBuilder scene(final URL sceneFile) {
			this.sceneFile = sceneFile;
			return this;
		}

		/** TODO: Documentation */
		public RemoteSceneBuilder scene(final String sceneFile) throws IllegalArgumentException {
			final URL url = this.getClass().getClassLoader().getResource(sceneFile);
			if (url == null) { throw new IllegalArgumentException("Failed to find a url for the scene file: " + sceneFile + "!"); }
			this.sceneFile = url;
			return this;
		}

		/** TODO: Documentation */
		public RemoteSceneBuilder style(final URL cssFile) {
			this.cssFile = cssFile;
			return this;
		}

		/** TODO: Documentation */
		public RemoteSceneBuilder style(final String cssFile) throws IllegalArgumentException {
			final URL url = this.getClass().getClassLoader().getResource(cssFile);
			if (url == null) { throw new IllegalArgumentException("Failed to find a url for the css file: " + cssFile + "!"); }
			this.cssFile = url;
			return this;
		}

		/** TODO: Documentation */
		public RemoteSceneBuilder controller(final Object controller) {
			this.controller = controller;
			return this;
		}

		/** TODO: Documentation */
		public RemoteSceneBuilder title(final String title) {
			if (this.stage == null) { this.stage = new Stage(); }
			this.stage.setTitle(title);
			return this;
		}

		/** TODO: Documentation */
		public RemoteSceneBuilder size(final int width, final int height) {
			if (this.stage == null) { this.stage = new Stage(); }
			this.stage.setWidth(width);
			this.stage.setHeight(height);
			return this;
		}

		/** TODO: Documentation */
		public RemoteSceneBuilder minSize(final int width, final int height) {
			if (this.stage == null) { this.stage = new Stage(); }
			this.stage.setMinWidth(width);
			this.stage.setMinHeight(height);
			return this;
		}

		/** TODO: Documentation */
		public RemoteSceneBuilder maxSize(final int width, final int height) {
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

			final FXMLLoader loader = new FXMLLoader(this.sceneFile);
			if (this.controller != null) { loader.setController(this.controller); }
			final Parent root = loader.load();
			this.controller = loader.getController();

			final RemoteScene scene = new RemoteScene(this.stage, root, this.sceneFile, this.cssFile);

			if (this.cssFile != null) {
				scene.getStylesheets().add(this.cssFile.toExternalForm());
			}

			this.stage.setScene(scene);

			return scene;
		}

	}

}