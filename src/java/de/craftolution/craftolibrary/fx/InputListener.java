package de.craftolution.craftolibrary.fx;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 02.06.2016
 */
public class InputListener {

	boolean[] keys = new boolean[KeyCode.values().length];
	long[] timings = new long[keys.length];

	/** TODO: Documentation */
	public InputListener(Stage stage) {
		stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> { 
			keys[event.getCode().ordinal()] = true;
			timings[event.getCode().ordinal()] = System.currentTimeMillis();
		});
		stage.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
			keys[event.getCode().ordinal()] = false;
		});
	}

	/** TODO: Documentation */
	public InputListener(Scene scene) {
		scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> { 
			keys[event.getCode().ordinal()] = true;
			timings[event.getCode().ordinal()] = System.currentTimeMillis();
		});
		scene.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
			keys[event.getCode().ordinal()] = false;
		});
	}

	/** TODO: Documentation */
	public boolean isPressed(KeyCode code) { return keys[code.ordinal()]; }

	/** TODO: Documentation */
	public long lastPressed(KeyCode code) { return timings[code.ordinal()]; }

}