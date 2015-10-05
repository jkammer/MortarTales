package org.mortartales.core.game.fsm;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Before;
import org.junit.Test;

import org.mortartales.core.game.Game;
import org.mortartales.core.game.configuration.GameConfiguration;
import org.mortartales.core.game.setup.GameConfigurationSetup;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameFSMTest {

	private GameFSM instance;
	
	private Game mockGame;
	
	private FakeGamePhase<GameConfigurationSetup, GameConfiguration> menuPhase;
	private FakeGamePhase<Void, Void> closePhase;
	
	private GameConfiguration mockGameConfiguration;

    @Before
    public void setUp() {
		
		mockGame = mock(Game.class);
		
		mockGameConfiguration = mock(GameConfiguration.class);
		
		menuPhase = new FakeGamePhase<>(mockGameConfiguration);
		closePhase = new FakeGamePhase<>(null);
		
		instance = new GameFSM(mockGame);
    }
	
	private void initialisePhases() {
		instance.withMenuPhase(menuPhase)
				.withClosePhase(closePhase);
	}

	@Test
	public void runsMenuPhaseWhenStarted() {
		
		initialisePhases();
		
		instance.start();
		
		menuPhase.waitFor();
		assertThat(menuPhase.isExecuted()).isTrue();
	}

	@Test
	public void runsMenuPhaseInFSMThread() {
		
		initialisePhases();
		
		instance.start();
		
		menuPhase.waitFor();
		assertThat(menuPhase.getThreadName())
				.isNotNull()
				.isNotEqualTo(Thread.currentThread().getName());
	}

	@Test
	public void doesNotStartIfMenuPhaseIsNotSet() {
		
		try {
			instance.start();
			fail("expected Exception");
		} catch (Exception ex) {
			assertThat(ex.getMessage()).isNotNull();
		}
	}

	private static class FakeGamePhase<T, R>
			implements GamePhase<T, R> {

		private final AtomicBoolean executed = new AtomicBoolean(false);
		
		private String threadName;
		
		private final R result;
		private T input;
		
		public FakeGamePhase(R result) {
			this.result = result;
		}
		
		@Override
		public R run(T input) {
			
			this.input = input;
			this.threadName = Thread.currentThread().getName();
			
			synchronized (executed) {
				executed.set(true);
				executed.notify();
			}
			return result;
		}
		
		public String getThreadName() {
			return threadName;
		}
		
		public void waitFor() {
			int loops = 0;
			while (!executed.get() && (loops++) < 5) {
				synchronized(executed) {
					try {
						executed.wait(200);
					} catch (InterruptedException ex) {
						throw new RuntimeException(ex);
					}
				}
			}
		}
		
		public boolean isExecuted() {
			return executed.get();
		}
	}
}