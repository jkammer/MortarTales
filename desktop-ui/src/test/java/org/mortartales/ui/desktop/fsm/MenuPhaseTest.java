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

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MenuScene.class, Stage.class})
public class MenuPhaseTest {

	private MenuPhase instance;
	
	private Stage mockStage;
	private MenuScene menuScene;
	private GameConfigurationSetup mockGameConfigurationSetup;
	
	private ArgumentCaptor<MenuScene> sceneArg;

    @Before
    public void setUp() throws IOException {
		
		mockStage = PowerMockito.mock(Stage.class);
		mockGameConfigurationSetup = mock(GameConfigurationSetup.class);
		
		menuScene = mock(MenuScene.class);
		PowerMockito.mockStatic(MenuScene.class);
		when(MenuScene.createDefault()).thenReturn(menuScene, (MenuScene) null);
		
		sceneArg = ArgumentCaptor.forClass(MenuScene.class);
		
		instance = new MenuPhase(mockStage);
    }

	@Test
	public void testRun() {
	}

	@Test
	public void setsMenuSceneOnStartup() throws Exception{
		
		instance.run(mockGameConfigurationSetup);
		
		verify(mockStage).setScene(sceneArg.capture());
		assertThat(sceneArg.getValue()).isSameAs(menuScene);
	}
	
	@Test
	public void showsTargetStage() throws Exception{
		
		instance.run(mockGameConfigurationSetup);
		
		verify(mockStage).show();
	}

	@Test
	public void setsInitialMenuConfigurationSetup() throws Exception{
		
		instance.run(mockGameConfigurationSetup);
		
		InOrder sceneInitOrder = inOrder(menuScene, mockStage);
		sceneInitOrder.verify(menuScene).setConfigurationSetup(mockGameConfigurationSetup);
		sceneInitOrder.verify(mockStage).show();
	}
}