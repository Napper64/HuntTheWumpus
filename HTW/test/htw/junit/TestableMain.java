package htw.junit;

import htw.HuntTheWumpus;
import htw.console.Main;

import java.io.IOException;

public class TestableMain extends Main {

	public static void main(String[] args) throws IOException {
		debugMode = true;
		Main.main(args);
	}

	public static HuntTheWumpus getCurrentGameInstance() {
		return game;
	}

}
