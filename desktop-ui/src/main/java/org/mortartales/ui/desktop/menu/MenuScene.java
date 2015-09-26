package org.mortartales.ui.desktop.menu;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

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
	public static MenuScene createDefault() throws Exception {
		FXMLLoader loader = new FXMLLoader(MenuScene.class.getResource("/ui/fxml/menu.fxml"));
		loader.setController(new MenuController());
		Parent root = (Parent) loader.load();
		return new MenuScene(root, 1024, 600);
	}
}
