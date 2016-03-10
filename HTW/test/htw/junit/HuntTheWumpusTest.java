package htw.junit;

import htw.HuntTheWumpus;
import htw.console.Main;
import htw.factory.HtwFactory;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class HuntTheWumpusTest {

	private HuntTheWumpus huntTheWumpus;
	int gold;

	@Before
	public void setup() {
		huntTheWumpus = HtwFactory.makeGame("htw.game.HuntTheWumpusGame",
				new Main());
		gold = 0;
	}

	@Test
	public void PlayersStartingHealth_is10Points() throws Exception {
		assertEquals(10, huntTheWumpus.getPlayerHealth());
	}

	@Test
	public void PlayerIsHit_By3Points_HealthIsDecreased_By3Points()
			throws Exception {
		assertEquals(10, huntTheWumpus.getPlayerHealth());
		huntTheWumpus.hitPlayerBy(3);
		assertEquals(7, huntTheWumpus.getPlayerHealth());
	}

	@Test
	public void checkForGold_GoldCountIsZeroTest() throws Exception {
		assertEquals(0, huntTheWumpus.getPlayerGold());
	}

	@Test
	public void checkForGold_GoldCountIncreasesWhenPlayerEntersCavernWithGoldTest() throws Exception {
		huntTheWumpus.setPlayerGold(goldFound());
		assertEquals(1, huntTheWumpus.getPlayerGold());
		
		huntTheWumpus.setPlayerGold(goldFound());
		assertEquals(2, huntTheWumpus.getPlayerGold());
	}

	private int goldFound() {
		return ++gold;
	}
	
}
