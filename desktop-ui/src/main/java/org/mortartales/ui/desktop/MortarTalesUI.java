package org.mortartales.ui.desktop;

import javafx.application.Application;
import javafx.stage.Stage;
import org.mortartales.ui.desktop.menu.MenuScene;

/**
 * Starts the application using a Desktop UI mode.
 */
public class MortarTalesUI extends Application {

	/**
	 * Application entry point.
	 * 
	 * @param args 
	 *          command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Mortar Tales");
		
		primaryStage.setScene(MenuScene.createDefault());
		
		primaryStage.show();
	}
}
