package org.mortartales.ui.desktop.menu;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import org.mortartales.core.game.setup.GameConfigurationSetup;

/**
 * Handles main menu controls.
 */
public class MenuController implements Initializable {

	@FXML private ChoiceBox locationCb;
	@FXML private Button startBt;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	/**
	 * Initialises menu controls with state of the given game configuration setup.
	 * 
	 * @param configurationSetup 
	 *              game configuration setup to set as current
	 */
	public void setConfigurationSetup(GameConfigurationSetup configurationSetup) {
		
	}
}
