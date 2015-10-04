package org.mortartales.core.game.fsm;

import java.util.Objects;
import org.mortartales.core.game.Game;
import org.mortartales.core.game.configuration.GameConfiguration;
import org.mortartales.core.game.setup.GameConfigurationSetup;

/**
 * Finite state machine of the game - cycles over phases of the game process.
 */
public class GameFSM {

	private GamePhase<GameConfigurationSetup, GameConfiguration> menuPhase;
	
	/**
	 * Constructs an unconfigured <code>GameFSM</code> for the given game.
	 * 
	 * @param game
	 *          game managed by this FSM
	 */
	public GameFSM(Game game) { }
	
	public GameFSM withMenuPhase(GamePhase<GameConfigurationSetup, GameConfiguration> menuPhase) {
		this.menuPhase = menuPhase;
		return this;
	}
	
	public GameFSM withClosePhase(GamePhase<Void, Void> closePhase) {
		return this;
	}
	
	/**
	 * Starts execution of game phases.
	 */
	public void start() {
		
		Objects.requireNonNull(menuPhase, "menu phase not specified");
		
		menuPhase.run(null);
	}
}
