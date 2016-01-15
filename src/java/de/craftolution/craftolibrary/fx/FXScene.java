package de.craftolution.craftolibrary.fx;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 10.01.2016
 */
public class FXScene {

	/** The container that contains all elements of this scene. */
	Parent container;
	/** The controller of this scene. */
	Optional<Controller> controller;
	/** The loader that reads the scene from a given resource. */
	FXMLLoader loader;

	/** TODO: Documentation */
	public FXScene(URL fxmlFile) throws IOException {
		FXMLLoader loader = new FXMLLoader(fxmlFile);
		this.container = loader.load();
		this.controller = Optional.ofNullable(loader.getController());
		this.loader = loader;
	}

	/** TODO: Documentation */
	public Parent getContainer() { return this.container; }

	/** TODO: Documentation */
	public Optional<Controller> getController() { return this.controller; }

	/** TODO: Documentation */
	public FXMLLoader getLoader() { return this.loader; }

	/** TODO: Documentation */
	public boolean hasController() { return this.getController().isPresent(); }

}