package org.mortartales.ui.desktop;

import javafx.application.Platform;

/**
 * UI interaction runner which runs the provided code in the JavaFX Application thread.
 */
public class JavaFxUIInteractionRunner implements UiInteractionRunner {

	private static final JavaFxUIInteractionRunner INSTANCE = new JavaFxUIInteractionRunner();
	
	private JavaFxUIInteractionRunner() { }
	
	public static JavaFxUIInteractionRunner getInstance() {
		return INSTANCE;
	}
	
	@Override
	public void runLater(Runnable runnable) {
		Platform.runLater(runnable);
	}
}
