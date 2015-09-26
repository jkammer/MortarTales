package org.mortartales.ui.desktop.menu;

import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.mockito.InOrder;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MenuScene.class, FXMLLoader.class})
public class MenuSceneTest {

	private MenuScene instance;
	private MenuScene mockScene;
	
	private FXMLLoader fxmlLoader;
	private Parent sceneParent;
	
	private ArgumentCaptor<URL> fxmlUrlArg;
	private ArgumentCaptor<Object> controllerArg;
	
    @Before
    public void setUp() throws Exception {
		
		fxmlUrlArg = ArgumentCaptor.forClass(URL.class);
		controllerArg = ArgumentCaptor.forClass(Object.class);
		
		sceneParent = mock(Parent.class);
		
		fxmlLoader = mock(FXMLLoader.class);
		PowerMockito
				.whenNew(FXMLLoader.class)
				.withParameterTypes(URL.class)
				.withArguments(fxmlUrlArg.capture())
				.thenReturn(fxmlLoader);
		
		when(fxmlLoader.load()).thenReturn(sceneParent);
		
		mockScene = mock(MenuScene.class);
		PowerMockito
				.whenNew(MenuScene.class)
				.withAnyArguments()
				.thenReturn(mockScene);
    }

	@Test
	public void loadsMenuSceneDefinitionFromXml() throws Exception {
		
		instance = MenuScene.createDefault();
		
		PowerMockito.verifyNew(MenuScene.class)
				.withArguments(same(sceneParent), anyDouble(), anyDouble());
		assertThat(fxmlUrlArg.getValue().toExternalForm()).endsWith("menu.fxml");
	}

	@Test
	public void bindsUIControlsToMenuController() throws Exception {
		
		instance = MenuScene.createDefault();
		
		InOrder fxmlLoaderOrder = inOrder(fxmlLoader);
		fxmlLoaderOrder.verify(fxmlLoader).setController(controllerArg.capture());
		fxmlLoaderOrder.verify(fxmlLoader).load();
		
		assertThat(controllerArg.getValue()).isInstanceOf(MenuController.class);
	}

}