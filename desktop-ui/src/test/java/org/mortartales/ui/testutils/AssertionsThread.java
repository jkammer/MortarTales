package org.mortartales.ui.testutils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * A thread which runs until all of the given assertions are successfull of a timeout is reached.
 */
public class AssertionsThread {

	private static final TemporalAmount DEFAULT_TIMEOUT = Duration.ofSeconds(5);
	private static final int SLEEP_MILLIS_BETWEEN_CHECKS = 50;
	
	private TemporalAmount timeout;
	
	private Queue<Runnable> assertionQueue = new LinkedList<>();
	
	public AssertionsThread() {
		this(DEFAULT_TIMEOUT);
	}
	
	public AssertionsThread(TemporalAmount timeout) {
		this.timeout = timeout;
	}
	
	public void addAssertion(Runnable assertionRunnable) {
		assertionQueue.add(assertionRunnable);
	}
	
	public Thread start() {
		Thread assertionThread = new Thread(this::runAssertionCheck);
		assertionThread.setName("Assertions Thread");
		assertionThread.start();
		return assertionThread;
	}
	
	public void startAndJoin() throws InterruptedException {
		Thread assertionThread = start();
		assertionThread.join();
	}
	
	private void runAssertionCheck() {
		LocalDateTime startTime = LocalDateTime.now();
		while (!allAssertionsSuccessful() && !isTimeExceeded(startTime)) {
			checkAssertions();
			sleep();
		}
	}
	
	private void sleep() {
		try {
			Thread.sleep(SLEEP_MILLIS_BETWEEN_CHECKS);
		} catch (InterruptedException ex) {
			throw new RuntimeException("Assertions thread interrupted", ex);
		}
	}
	
	private boolean isTimeExceeded(LocalDateTime startTime) {
		
		return startTime.plus(timeout).isBefore(LocalDateTime.now());
	}
	
	private boolean allAssertionsSuccessful() {
		return assertionQueue.isEmpty();
	}
	
	private void checkAssertions() {
		Iterator<Runnable> taskIt = assertionQueue.iterator();
		boolean lastCheckOk = true;
		while (taskIt.hasNext() && lastCheckOk) {
			if (lastCheckOk = isAssertionOk(taskIt.next())) {
				taskIt.remove();
			}
		}
	}
	
	private boolean isAssertionOk(Runnable assertionRunnable) {
		try {
			assertionRunnable.run();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public void verifyAssertions() {
		assertionQueue.stream().forEach((a) -> a.run());
	}
}
