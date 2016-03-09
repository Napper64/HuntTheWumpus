package htw.items;

import static org.junit.Assert.assertEquals;
import htw.HuntTheWumpus;
import htw.console.Main;
import htw.factory.HtwFactory;

import org.junit.Before;
import org.junit.Test;

public class PotionTest {

	private HuntTheWumpus game;

	@Before
	public void setup() {
		game = HtwFactory.makeGame("htw.game.HuntTheWumpusGame", new Main());
	}

	@Test
	public void PlayerHealth_At1_PotionIncreasesHealth_To4() {
		game.setPlayerHealth(1);
		assertEquals(1, game.getPlayerHealth());
		game.potionAcquired();
		assertEquals(4, game.getPlayerHealth());
	}

	@Test
	public void PlayerHealth_At10_PotionIsNotUsed() {
		game.setPlayerHealth(1);
		assertEquals(1, game.getPlayerHealth());
		game.potionAcquired();
		assertEquals(4, game.getPlayerHealth());
	}

}
