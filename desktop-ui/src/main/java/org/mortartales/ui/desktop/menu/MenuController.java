package org.mortartales.ui.desktop.menu;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

/**
 * Handles main menu controls.
 */
public class MenuController implements Initializable {

	@FXML private ChoiceBox locationCb;
	@FXML private Button startBt;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

}
