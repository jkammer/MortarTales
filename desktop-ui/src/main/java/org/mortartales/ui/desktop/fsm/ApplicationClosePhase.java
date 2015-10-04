package org.mortartales.ui.desktop.fsm;

import javafx.stage.Stage;
import org.mortartales.core.game.fsm.GamePhase;

/**
 * Game phase which closes the application.
 */
public class ApplicationClosePhase implements GamePhase<Void, Void> {

	public ApplicationClosePhase(Stage primaryStage) {
		
	}

	@Override
	public Void run(Void state) {
	
		return null;
	}
}
