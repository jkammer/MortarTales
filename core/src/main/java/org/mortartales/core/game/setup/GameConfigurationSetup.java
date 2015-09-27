package org.mortartales.core.game.setup;

import java.util.Arrays;
import java.util.List;
import org.mortartales.core.game.configuration.Location;

/**
 * Configuration options which can be chosen for a single MortarTales game. Available options depend
 * on static game rules and available extensions.
 */
public class GameConfigurationSetup {

	public List<Location> getAvailableLocations() {
		return Arrays.<Location>asList(() -> "Saturn", () -> "Void");
	}
}
