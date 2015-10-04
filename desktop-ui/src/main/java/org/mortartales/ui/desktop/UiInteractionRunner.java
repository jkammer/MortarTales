package org.mortartales.ui.desktop;

/**
 * Executes code which interacts with UI element in the proper thread.
 * 
 * This interface is provided for easier testing of components with use the 
 * Java FX Application thread.
 */
public interface UiInteractionRunner {

	/**
	 * Schedules the given code to be run in the UI interaction thread.
	 * 
	 * @param runnable 
	 *           code to run
	 */
	void runLater(Runnable runnable);
}
