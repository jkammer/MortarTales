package org.mortartales.core.game.fsm;

import java.util.Objects;
import org.mortartales.core.game.Game;
import org.mortartales.core.game.configuration.GameConfiguration;
import org.mortartales.core.game.setup.GameConfigurationSetup;

/**
 * Finite state machine of the game - cycles over phases of the game process.
 */
public class GameFSM {

	private static final String FSM_THREAD_NAME = "FSM-Thread";
	
	private GamePhase<GameConfigurationSetup, GameConfiguration> menuPhase;
	
	private Thread fsmThread;
	
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
	
	/**
	 * Sets logic of the game phase executed then the game closes.
	 * 
	 * @param closePhase
	 *          phase executed when the game is closed
	 * 
	 * @return <code>this</code> reference 
	 */
	public GameFSM withClosePhase(GamePhase<Void, Void> closePhase) {
		return this;
	}
	
	/**
	 * Starts execution of game phases.
	 */
	public void start() {
		
		Objects.requireNonNull(menuPhase, "menu phase not specified");
	
		fsmThread = new Thread(this::runGameFsm, FSM_THREAD_NAME);
		fsmThread.start();
	}
	
	private void runGameFsm() {
		menuPhase.run(null);
	}
}
