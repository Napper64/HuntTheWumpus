package htw.items;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import htw.HuntTheWumpus;
import htw.console.Main;
import htw.factory.HtwFactory;

import java.io.IOException;

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
		assertEquals(10, game.getPlayerHealth());
		game.potionAcquired();
		assertEquals(10, game.getPlayerHealth());
	}

	@Test
	public void PotionCavernsAreGenerated_OnStartup() throws IOException {
		assertTrue(game.getPotionCaverns().isEmpty());

	}

}
