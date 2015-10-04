package org.mortartales.ui.desktop;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Test implementation of UI interaction runner which runs the provided code
 * when the <code>runNow</code> method is called.
 */
public class SameThreadUiInteractionRunner implements UiInteractionRunner {

	private Queue<Runnable> taskQueue = new LinkedList<>();

	private boolean immediateExecution = false;
			
	@Override
	public void runLater(Runnable runnable) {
		if (immediateExecution) {
			runnable.run();
		} else {
			taskQueue.add(runnable);
		}
	}
	
	/**
	 * Executes all submitted tasks in the caller thread as soon as they are received
	 */
	public void executeImmediatelly() {
		immediateExecution = true;
	}
	
	/**
	 * Executes all scheduled tasks in the current thread.
	 */
	public void runNow() {
		while (!taskQueue.isEmpty()) {
			taskQueue.remove().run();
		}
	}

}