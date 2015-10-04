package org.mortartales.ui.desktop.fsm;

import java.io.IOException;
import javafx.stage.Stage;
import org.mortartales.core.game.configuration.GameConfiguration;
import org.mortartales.core.game.fsm.GamePhase;
import org.mortartales.core.game.setup.GameConfigurationSetup;
import org.mortartales.ui.desktop.menu.MenuScene;

/**
 * Shows main menu in Desktop UI mode.
 */
public class MenuPhase implements GamePhase<GameConfigurationSetup, GameConfiguration> {

	private final Stage targetStage;
	
	public MenuPhase(Stage targetStage) { 
	
		this.targetStage = targetStage;
	}
	
	@Override
	public GameConfiguration run(GameConfigurationSetup state) {
		
		try {
			MenuScene scene = MenuScene.createDefault();
			scene.setConfigurationSetup(state);
			targetStage.setScene(scene);

			targetStage.show();
			return null;
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
}
