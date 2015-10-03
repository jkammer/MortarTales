package org.mortartales.ui.desktop.menu;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.mortartales.core.game.setup.GameConfigurationSetup;

/**
 * Renders controls of the game menu screen.
 */
public class MenuScene extends Scene {

	private MenuController controller;
	
	public MenuScene(Parent root) {
		super(root);
	}
	
	protected void setController(MenuController menuController) {
		
		controller = menuController;
	}
	
	/**
	 * Initialises menu controls with state of the given game configuration setup.
	 * 
	 * @param configurationSetup 
	 *              game configuration setup to set as current
	 */
	public void setConfigurationSetup(GameConfigurationSetup configurationSetup) {
		controller.setConfigurationSetup(configurationSetup);
	}
	
	/**
	 * Creates a <code>MenuScene</code> with default configuration.
	 * 
	 * @return configured menu scene 
	 */
	public static MenuScene createDefault() throws IOException {
		
		MenuController menuController = new MenuController();
		Parent rootNode = createRootNodeFromFXML(menuController);
		return createMenuScene(rootNode, menuController);
	}
	
	private static Parent createRootNodeFromFXML(MenuController menuController) throws IOException {
		
		FXMLLoader loader = new FXMLLoader(MenuScene.class.getResource("/ui/fxml/menu.fxml"));
		loader.setController(menuController);
		return (Parent) loader.load();
	}
	
	private static MenuScene createMenuScene(Parent rootNode, MenuController menuController) {
		
		MenuScene menuScene = new MenuScene(rootNode);
		menuScene.setController(menuController);
		menuScene.getStylesheets().add("ui/fxml/menu.css");
		return menuScene;
	}
}
