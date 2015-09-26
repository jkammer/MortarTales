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
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Stage.class, MenuScene.class})
public class MortarTalesUITest {

	private MortarTalesUI instance;

	private Stage mockStage;
	private MenuScene menuScene;
	
    @Before
    public void setUp() throws Exception {
		
		mockStage = PowerMockito.mock(Stage.class);
		
		menuScene = mock(MenuScene.class);
		PowerMockito.mockStatic(MenuScene.class);
		when(MenuScene.createDefault()).thenReturn(menuScene);
		
		instance = new MortarTalesUI();
    }

	@Test
	public void setsMenuSceneOnStartup() throws Exception{
		
		instance.start(mockStage);
		
		ArgumentCaptor<Scene> sceneArg = ArgumentCaptor.forClass(Scene.class);
		verify(mockStage).setScene(sceneArg.capture());
		assertThat(sceneArg.getValue()).isSameAs(menuScene);
	}

	@Test
	public void setsGameTitle() throws Exception{
		
		instance.start(mockStage);
		
		verify(mockStage).setTitle("Mortar Tales");
	}
}