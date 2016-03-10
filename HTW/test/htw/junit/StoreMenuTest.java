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
	private final static String fullHealth = "FULL_HEALTH";

	@Before
	public void setup() {
		testContext = new TestContext();
		testContext.messages.clear();
	}

	@Test
	public void playerPurchases_Potion() {
		testContext.game.setPlayerHealth(1);
		testContext.game.setPlayerGold(4);
		testContext.game.makePurchase(Purchase.POTION);
		assertEquals(4, testContext.game.getPlayerHealth());

	}

	@Test
	public void playerAtMaxHealthPurchases_Potion_NoCost() {
		testContext.game.setPlayerGold(4);
		testContext.game.makePurchase(Purchase.POTION);
		assertEquals(10, testContext.game.getPlayerHealth());
		assertEquals(4, testContext.game.getPlayerGold());
		assertTrue(testContext.messages.contains(fullHealth));

	}

	@Test
	public void playerPurchases_BatRepellant() {
		testContext.game.setPlayerBatRepellant(0);
		testContext.game.setPlayerGold(4);
		testContext.game.makePurchase(Purchase.BAT_REPELLANT);
		assertEquals(1, testContext.game.getPlayerBatRepellant());

	}

	@Test
	public void playerPurchases_Arrow() {
		testContext.game.setQuiver(0);
		testContext.game.setPlayerGold(4);
		testContext.game.makePurchase(Purchase.ARROW);
		assertEquals(1, testContext.game.getQuiver());

	}

	@Test
	public void playerTypesQuitAtStore_PlayerIsReturnedToLastCavern() {
		testContext.game.setPlayerCavern("store");
		testContext.game.setPreviousPlayerCavern("middle");
		testContext.game.makeMoveCommand(Direction.QUIT).execute();
		assertTrue(testContext.messages.contains(storeDisabled));
		assertEquals(testContext.game.getPlayerCavern(), "middle");
	}
}
