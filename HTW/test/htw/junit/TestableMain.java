package htw.junit;

import htw.HuntTheWumpus;
import htw.console.Main;

import java.io.IOException;

public class TestableMain extends Main {

	public TestableMain() throws IOException {
		main(new String[] { "arg1", "arg2", "arg3" });
	}

	public static void main(String[] args) throws IOException {
		testMode = true;
		Main.main(args);
	}

	public static HuntTheWumpus getCurrentGameInstance() {
		return game;
	}

}
