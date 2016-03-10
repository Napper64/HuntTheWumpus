package htw.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import htw.HuntTheWumpus.Direction;
import htw.HuntTheWumpus.Purchase;
import htw.fixtures.TestContext;

import org.junit.Before;
import org.junit.Test;

public class StoreMenuTest {

	private TestContext testContext;
	private final static String greetingsMessage = "STORE_GREETINGS";
	private final static String storeDisabled = "STORE_DISABLED";

	@Before
	public void setup() {
		testContext = new TestContext();
	}

	@Test
	public void playerTypesStore_MenuIsShown() {
		testContext.game.makeMoveCommand(Direction.STORE).execute();
		assertTrue(testContext.messages.contains(greetingsMessage));
	}

	@Test
	public void playerTypesQuitAtStore_PlayerIsReturnedToLastCavern() {
		testContext.game.makeMoveCommand(Direction.STORE).execute();
		testContext.messages.clear();
		testContext.game.makeMoveCommand(Direction.QUIT).execute();
		assertTrue(testContext.messages.contains(storeDisabled));
	}

	@Test
	public void playerPurchases_Potion() {
		testContext.game.makeMoveCommand(Direction.STORE).execute();
		testContext.game.setPlayerHealth(1);
		testContext.game.setPlayerGold(4);
		testContext.game.makePurchase(Purchase.POTION);
		assertEquals(4, testContext.game.getPlayerHealth());

	}
}
