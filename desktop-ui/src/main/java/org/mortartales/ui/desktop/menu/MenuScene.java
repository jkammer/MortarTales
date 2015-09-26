package org.mortartales.ui.desktop.menu;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

/**
 * Renders controls of the game menu screen.
 */
public class MenuScene extends Scene {

	public MenuScene(Parent root, double width, double height) {
		super(root, width, height);
	}
	
	/**
	 * Creates a <code>MenuScene</code> with default configuration.
	 * 
	 * @return configured menu scene 
	 */
	public static MenuScene createDefault() {
		return new MenuScene(new StackPane(), 1024, 600);
	}
}
