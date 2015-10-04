package org.mortartales.core.game.fsm;

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
	
	private GamePhase<GameConfigurationSetup, GameConfiguration> menuPhase;
	private GamePhase<Void, Void> closePhase;

    @Before
    public void setUp() {
		
		mockGame = mock(Game.class);
		
		menuPhase = mock(GamePhase.class);
		closePhase = mock(GamePhase.class);
		
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
		
		verify(menuPhase).run(any(GameConfigurationSetup.class));
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

}