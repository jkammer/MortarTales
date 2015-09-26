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
@PrepareForTest(Stage.class)
public class MortarTalesUITest {

	private MortarTalesUI instance;

	private Stage mockStage;
	
    @Before
    public void setUp() {
		
		mockStage = PowerMockito.mock(Stage.class);
		
		instance = new MortarTalesUI();
    }

	@Test
	public void setsMenuSceneOnStartup() throws Exception{
		
		instance.start(mockStage);
		
		ArgumentCaptor<Scene> sceneArg = ArgumentCaptor.forClass(Scene.class);
		verify(mockStage).setScene(sceneArg.capture());
		assertThat(sceneArg.getValue()).isInstanceOf(MenuScene.class);
	}

	@Test
	public void setsGameTitle() throws Exception{
		
		instance.start(mockStage);
		
		verify(mockStage).setTitle("Mortar Tales");
	}
}