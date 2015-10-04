package org.mortartales.ui.desktop;

import javafx.stage.Stage;
import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.api.mockito.PowerMockito;
import org.mockito.InOrder;
import org.mortartales.core.game.Game;
import org.mortartales.core.game.fsm.GameFSM;
import org.mortartales.core.game.fsm.GamePhase;
import org.mortartales.ui.desktop.fsm.ApplicationClosePhase;
import org.mortartales.ui.desktop.fsm.MenuPhase;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Stage.class, MortarTalesUI.class})
public class MortarTalesUITest {

	private MortarTalesUI instance;

	private GameFSM mockGameFsm;
	
	private Stage mockStage;
	
    @Before
    public void setUp() throws Exception {
		
		mockStage = PowerMockito.mock(Stage.class);
		
		mockGameFsm = mock(GameFSM.class);
		when(mockGameFsm.withMenuPhase(any(GamePhase.class))).thenReturn(mockGameFsm);
		when(mockGameFsm.withClosePhase(any(GamePhase.class))).thenReturn(mockGameFsm);
		
		PowerMockito
				.whenNew(GameFSM.class)
				.withArguments(isNotNull(Game.class))
				.thenReturn(mockGameFsm);
		
		instance = new MortarTalesUI();
    }

	@Test
	public void initialisesMenuFsmStage() throws Exception {
		
		instance.start(mockStage);
		
		verify(mockGameFsm).withMenuPhase(any(MenuPhase.class));
	}

	@Test
	public void initialisesApplicationCloseFsmStage() throws Exception {
		
		instance.start(mockStage);
		
		verify(mockGameFsm).withClosePhase(any(ApplicationClosePhase.class));
	}

	@Test
	public void startsGameFSMAfterSettingsGamePhases() throws Exception {
		
		instance.start(mockStage);
		
		InOrder gameFsmOrder = inOrder(mockGameFsm);
		gameFsmOrder.verify(mockGameFsm).withMenuPhase(any(MenuPhase.class));
		gameFsmOrder.verify(mockGameFsm).withClosePhase(any(ApplicationClosePhase.class));
		gameFsmOrder.verify(mockGameFsm).start();
	}
	
	@Test
	public void setsGameTitle() throws Exception{
		
		instance.start(mockStage);
		
		verify(mockStage).setTitle("Mortar Tales");
	}
}