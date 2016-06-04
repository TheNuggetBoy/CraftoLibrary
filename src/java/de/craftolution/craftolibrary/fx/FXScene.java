/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.fx;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import javax.annotation.Nullable;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 10.01.2016
 */
@Deprecated
public class FXScene {

	/** The container that contains all elements of this scene. */
	Parent container;
	/** The controller of this scene. */
	Optional<Object> controller;
	/** The loader that reads the scene from a given resource. */
	FXMLLoader loader;
	/** TODO: Documentation */
	@Nullable Scene scene;

	/** TODO: Documentation */
	public FXScene(final URL fxmlFile) throws IOException {
		final FXMLLoader loader = new FXMLLoader(fxmlFile);
		this.container = loader.load();
		this.controller = Optional.ofNullable(loader.getController());
		this.loader = loader;
	}

	/** TODO: Documentation */
	public FXScene(final URL fxmlFile, final Object controller) throws IOException {
		final FXMLLoader loader = new FXMLLoader(fxmlFile);
		if (controller != null) { loader.setController(controller); }

		this.container = loader.load();
		this.controller = Optional.ofNullable(loader.getController());
		this.loader = loader;
	}

	/** TODO: Documentation */
	public Parent getContainer() { return this.container; }

	/** TODO: Documentation */
	public Optional<Object> getController() { return this.controller; }

	/** TODO: Documentation */
	@SuppressWarnings("unchecked")
	public <T extends Object> Optional<T> getController(final Class<T> clazz) { return (Optional<T>) this.controller; }

	/** TODO: Documentation */
	public FXMLLoader getLoader() { return this.loader; }

	/** TODO: Documentation */
	public boolean hasController() { return this.getController().isPresent(); }

	/** TODO: Documentation */
	public void displayTo(final Stage stage) {
		if (this.scene == null) { this.scene = new Scene(this.container); }
		stage.setScene(this.scene);
	}

}