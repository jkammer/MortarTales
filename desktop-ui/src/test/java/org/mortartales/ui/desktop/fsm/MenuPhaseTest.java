package org.mortartales.ui.desktop.fsm;

import java.io.IOException;
import javafx.stage.Stage;
import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mortartales.core.game.setup.GameConfigurationSetup;
import org.mortartales.ui.desktop.menu.MenuScene;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.mortartales.ui.desktop.SameThreadUiInteractionRunner;
import org.junit.After;
import org.mortartales.core.game.configuration.GameConfiguration;
import org.mortartales.ui.testutils.AssertionsThread;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MenuScene.class, Stage.class})
public class MenuPhaseTest {

	private MenuPhase instance;

	private Stage mockStage;
	private MenuScene menuScene;
	private GameConfigurationSetup mockGameConfigurationSetup;

	private ArgumentCaptor<MenuScene> sceneArg;

	private SameThreadUiInteractionRunner uiRunner;

	private Thread menuPhaseRunner;
	private GameConfiguration menuPhaseResult;
	
	private AssertionsThread assertionsThread;

	@Before
	public void setUp() throws IOException {
		
		assertionsThread = new AssertionsThread();

		uiRunner = new SameThreadUiInteractionRunner();

		mockStage = PowerMockito.mock(Stage.class);
		mockGameConfigurationSetup = mock(GameConfigurationSetup.class);

		menuScene = mock(MenuScene.class);
		PowerMockito.mockStatic(MenuScene.class);
		when(MenuScene.createDefault()).thenReturn(menuScene, (MenuScene) null);

		sceneArg = ArgumentCaptor.forClass(MenuScene.class);

		instance = new MenuPhase(mockStage, uiRunner);
	}

	@After
	public void tearDown() throws InterruptedException {
		if (menuPhaseRunner != null) {
			menuPhaseRunner.join(1000);
		}
	}

	private void runMenuPhase(GameConfigurationSetup gameConfigurationSetup) {
		menuPhaseRunner = new Thread(() -> {
			menuPhaseResult = instance.run(gameConfigurationSetup);
		});
		menuPhaseRunner.setName("MenuPhaseTest");
		menuPhaseRunner.start();
	}

	@Test
	public void setsMenuSceneOnStartup() throws Exception {

		assertionsThread.addAssertion(() -> verify(mockStage).setScene(sceneArg.capture()));
		assertionsThread.addAssertion(() -> assertThat(sceneArg.getValue()).isSameAs(menuScene));
		
		uiRunner.executeImmediatelly();
		
		runMenuPhase(mockGameConfigurationSetup);
		
		assertionsThread.startAndJoin();
		assertionsThread.verifyAssertions();
	}

	@Test
	public void showsTargetStage() throws Exception {
		assertionsThread.addAssertion(() -> verify(mockStage).show());

		uiRunner.executeImmediatelly();
		runMenuPhase(mockGameConfigurationSetup);
		
		assertionsThread.startAndJoin();
		assertionsThread.verifyAssertions();
	}

	@Test
	public void setsInitialMenuConfigurationSetup() throws Exception {
		assertionsThread.addAssertion(() -> {
			InOrder sceneInitOrder = inOrder(menuScene, mockStage);
			sceneInitOrder.verify(menuScene).setConfigurationSetup(mockGameConfigurationSetup);
			sceneInitOrder.verify(mockStage).show();
		});
		
		uiRunner.executeImmediatelly();
		runMenuPhase(mockGameConfigurationSetup);
		
		assertionsThread.startAndJoin();
		assertionsThread.verifyAssertions();
	}

	@Test
	public void doesNotInteractWithUIElementsInFSMThread() throws Exception {
		assertionsThread.addAssertion(() -> assertThat(uiRunner.hasTasksInQueue()).isTrue());
		assertionsThread.addAssertion(() -> verifyZeroInteractions(mockStage));

		runMenuPhase(mockGameConfigurationSetup);
		
		assertionsThread.startAndJoin();
		assertionsThread.verifyAssertions();
	}
}
