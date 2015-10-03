package org.mortartales.ui.desktop.menu;

import java.net.URL;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.mortartales.core.game.setup.GameConfigurationSetup;
import org.mockito.InOrder;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MenuScene.class, FXMLLoader.class, MenuController.class})
public class MenuSceneTest {

	private MenuScene instance;
	private MenuScene mockScene;
	
	private ObservableList<String> mockSceneStylesheets;
	
	private FXMLLoader fxmlLoader;
	private Parent sceneParent;
	private MenuController menuController;
	
	private ArgumentCaptor<URL> fxmlUrlArg;
	private ArgumentCaptor<Object> controllerArg;
	
    @Before
    public void setUp() throws Exception {
		
		fxmlUrlArg = ArgumentCaptor.forClass(URL.class);
		controllerArg = ArgumentCaptor.forClass(Object.class);
		
		sceneParent = mock(Parent.class);
		menuController = mock(MenuController.class);
		PowerMockito
				.whenNew(MenuController.class)
				.withNoArguments()
				.thenReturn(menuController);
		
		fxmlLoader = mock(FXMLLoader.class);
		PowerMockito
				.whenNew(FXMLLoader.class)
				.withParameterTypes(URL.class)
				.withArguments(fxmlUrlArg.capture())
				.thenReturn(fxmlLoader);
		
		when(fxmlLoader.load()).thenReturn(sceneParent);
    }
	
	private void initialiseMockScene() throws Exception {
		
		mockSceneStylesheets = mock(ObservableList.class);
		
		mockScene = PowerMockito.mock(MenuScene.class);
		when(mockScene.getStylesheets()).thenReturn(mockSceneStylesheets);
		PowerMockito
				.whenNew(MenuScene.class)
				.withAnyArguments()
				.thenReturn(mockScene);
		
		doCallRealMethod().when(mockScene)
				.setConfigurationSetup(any(GameConfigurationSetup.class));
	}

	@Test
	public void loadsMenuSceneDefinitionFromXml() throws Exception {
		initialiseMockScene();
		
		instance = MenuScene.createDefault();
		
		PowerMockito.verifyNew(MenuScene.class)
				.withArguments(same(sceneParent));
		assertThat(fxmlUrlArg.getValue().toExternalForm()).endsWith("menu.fxml");
	}

	@Test
	public void bindsUIControlsToMenuController() throws Exception {
		initialiseMockScene();
		
		instance = MenuScene.createDefault();
		
		InOrder fxmlLoaderOrder = inOrder(fxmlLoader);
		fxmlLoaderOrder.verify(fxmlLoader).setController(controllerArg.capture());
		fxmlLoaderOrder.verify(fxmlLoader).load();
		
		assertThat(controllerArg.getValue()).isInstanceOf(MenuController.class);
	}
	
	@Test
	public void initialisesMenuSceneWithInitialisedMenuController() throws Exception {
		initialiseMockScene();
		
		instance = MenuScene.createDefault();
		
		verify(fxmlLoader).setController(controllerArg.capture());
		assertThat(controllerArg.getValue()).isInstanceOf(MenuController.class);
		verify(instance).setController((MenuController) controllerArg.getValue());
	}
	
	@Test
	public void initialisesMenuSceneWithMenuStyleSheet() throws Exception {
		initialiseMockScene();
		
		instance = MenuScene.createDefault();
		
		verify(mockSceneStylesheets).add("ui/fxml/menu.css");
	}

	@Test
	public void initialisesMenuModelWithProvidedGameConfigurationSetup() throws Exception {
		initialiseMockScene();
		GameConfigurationSetup configurationSetup = mock(GameConfigurationSetup.class);
		doCallRealMethod().when(mockScene)
				.setController(any(MenuController.class));
		
		instance = MenuScene.createDefault();
		instance.setConfigurationSetup(configurationSetup);
		
		verify(menuController)
				.setConfigurationSetup(configurationSetup);
	}

}