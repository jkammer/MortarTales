package org.mortartales.ui.desktop.fsm;

import java.io.IOException;
import javafx.stage.Stage;
import org.mortartales.core.game.configuration.GameConfiguration;
import org.mortartales.core.game.fsm.GamePhase;
import org.mortartales.core.game.setup.GameConfigurationSetup;
import org.mortartales.ui.desktop.UiInteractionRunner;
import org.mortartales.ui.desktop.menu.MenuScene;

/**
 * Shows main menu in Desktop UI mode.
 */
public class MenuPhase implements GamePhase<GameConfigurationSetup, GameConfiguration> {

	private final Stage targetStage;
	private final UiInteractionRunner uiRunner;
	
	private MenuScene scene;
	
	public MenuPhase(Stage targetStage, UiInteractionRunner uiInteractionRunner) { 
	
		this.targetStage = targetStage;
		this.uiRunner = uiInteractionRunner;
	}
	
	@Override
	public GameConfiguration run(GameConfigurationSetup state) {
		
		try {
			initialiseScene(state);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		
		uiRunner.runLater(this::showMenuScene);
		
		return null;
	}
	
	private void initialiseScene(GameConfigurationSetup state) throws IOException {
		scene = MenuScene.createDefault();
		scene.setConfigurationSetup(state);		
	}
	
	private void showMenuScene() {
		try {
			targetStage.setScene(scene);
			targetStage.show();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: show exception box on stage and call main thread
		}
	}
}
