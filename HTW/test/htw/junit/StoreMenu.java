package htw.junit;

import static org.junit.Assert.assertTrue;
import htw.HuntTheWumpus.Direction;
import htw.fixtures.TestContext;

import org.junit.Before;
import org.junit.Test;

public class StoreMenu {

	private TestContext testContext;
	private final static String greetingsMessage = "STORE_GREETINGS";

	@Before
	public void setup() {
		testContext = new TestContext();
	}

	@Test
	public void playerTypesStore_MenuIsShown() {
		testContext.game.makeMoveCommand(Direction.STORE).execute();
		assertTrue(testContext.messages.contains(greetingsMessage));
	}
}
