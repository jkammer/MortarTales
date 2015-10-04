package org.mortartales.ui.desktop;

import javafx.application.Application;
import javafx.stage.Stage;

import org.mortartales.core.game.Game;
import org.mortartales.core.game.fsm.GameFSM;
import org.mortartales.core.game.setup.GameConfigurationSetup;
import org.mortartales.ui.desktop.fsm.ApplicationClosePhase;
import org.mortartales.ui.desktop.fsm.MenuPhase;
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
		
		primaryStage.show();
		primaryStage.close();

		new GameFSM(new Game())
				.withMenuPhase(new MenuPhase(primaryStage))
				.withClosePhase(new ApplicationClosePhase(primaryStage))
				.start();
	}
}
