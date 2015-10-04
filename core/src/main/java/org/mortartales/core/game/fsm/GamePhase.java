package org.mortartales.core.game.fsm;

/**
 * A single phase of the game process (menu, game round etc.).
 * 
 * <p>Phases should be re-runnable - a phase should revert to its initial state each time the <code>run</code> method is called.
 * 
 * @param <T> 
 *          input of the game phase - initial state
 * @param <R> 
 *          result of the game phase
 */
public interface GamePhase<T, R> {

	/**
	 * Executes this game phase with the given initial state.
	 * 
	 * This method is always called from the FSM thread, and should only return if the phase is complete.
	 * 
	 * @param state
	 *           initial state of the game phase
	 * 
	 * @return result of phase execution
	 */
	R run(T state);
	
	/**
	 * Determines whether this game phase was cancelled, forcing the game to end.
	 * 
	 * @return <code>true</code> if the phase me was aborted, <code>false</code> otherwise
	 */
	default boolean isAborted() {
		return false;
	}
}
