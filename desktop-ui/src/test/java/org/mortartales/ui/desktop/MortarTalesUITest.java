package org.mortartales.ui.desktop;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Before;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mortartales.ui.desktop.menu.MenuScene;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.api.mockito.PowerMockito;
import org.mockito.InOrder;
import org.mortartales.core.game.setup.GameConfigurationSetup;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Stage.class, MenuScene.class})
public class MortarTalesUITest {

	private MortarTalesUI instance;

	private Stage mockStage;
	private MenuScene menuScene;
	
	private ArgumentCaptor<MenuScene> sceneArg;
	
    @Before
    public void setUp() throws Exception {
		
		mockStage = PowerMockito.mock(Stage.class);
		
		menuScene = mock(MenuScene.class);
		PowerMockito.mockStatic(MenuScene.class);
		when(MenuScene.createDefault()).thenReturn(menuScene, (MenuScene) null);
		
		sceneArg = ArgumentCaptor.forClass(MenuScene.class);
		
		instance = new MortarTalesUI();
    }

	@Test
	public void setsMenuSceneOnStartup() throws Exception{
		
		instance.start(mockStage);
		
		verify(mockStage).setScene(sceneArg.capture());
		assertThat(sceneArg.getValue()).isSameAs(menuScene);
	}

	@Test
	public void setsGameTitle() throws Exception{
		
		instance.start(mockStage);
		
		verify(mockStage).setTitle("Mortar Tales");
	}

	@Test
	public void setsInitialMenuConfigurationSetup() throws Exception{
		
		instance.start(mockStage);
		
		InOrder sceneInitOrder = inOrder(menuScene, mockStage);
		sceneInitOrder.verify(menuScene).setConfigurationSetup(any(GameConfigurationSetup.class));
		sceneInitOrder.verify(mockStage).show();
	}
}