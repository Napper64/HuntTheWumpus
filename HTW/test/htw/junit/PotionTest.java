package htw.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import htw.HuntTheWumpus;
import htw.HuntTheWumpus.Direction;
import htw.factory.HtwFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class PotionTest {

	private HuntTheWumpus game;

	@Before
	public void setup() throws IOException {
		TestableMain.main(new String[] { "arg1", "arg2", "arg3" });
		game = TestableMain.getCurrentGameInstance();
	}

	@Test
	public void PlayerHealth_At1_PotionIncreasesHealth_To4() {
		game.setPlayerHealth(1);
		assertEquals(1, game.getPlayerHealth());
		game.potionAcquired();
		assertEquals(4, game.getPlayerHealth());
	}

	@Test
	public void PlayerHealth_At9_PotionIncreasesHealth_To10() {
		game.setPlayerHealth(9);
		assertEquals(9, game.getPlayerHealth());
		game.potionAcquired();
		assertEquals(10, game.getPlayerHealth());
	}

	@Test
	public void PlayerHealth_At10_PotionIsNotUsed() {
		assertEquals(10, game.getPlayerHealth());
		game.potionAcquired();
		assertEquals(10, game.getPlayerHealth());
	}

	@Test
	public void PotionCavernsAreGenerated_OnStartup() throws IOException {
		assertFalse(game.getPotionCaverns().isEmpty());

	}

	@Test
	public void PlayerAtLowHealth_PicksUpPotion_WhenInSameCavern()
			throws IOException {
		game = HtwFactory.makeGame("htw.game.HuntTheWumpusGame",
				new TestableMain());
		game.setPlayerHealth(1);
		game.connectCavern("Top", "Bottom", Direction.SOUTH);
		game.connectCavern("Bottom", "Top", Direction.NORTH);
		game.setPlayerCavern("Top");
		Set<String> potionCaverns = new HashSet<String>();
		potionCaverns.add("Bottom");
		game.setPotionCaverns(potionCaverns);
		game.makeMoveCommand(Direction.SOUTH).execute();
		assertTrue(game.getPotionCaverns().isEmpty());
		assertEquals(4, game.getPlayerHealth());

	}

	@Test
	public void PlayerAtMaxHealth_DoesntPickUpPotion_WhenInSameCavern()
			throws IOException {
		game = HtwFactory.makeGame("htw.game.HuntTheWumpusGame",
				new TestableMain());
		game.connectCavern("Top", "Bottom", Direction.SOUTH);
		game.connectCavern("Bottom", "Top", Direction.NORTH);
		game.setPlayerCavern("Top");
		Set<String> potionCaverns = new HashSet<String>();
		potionCaverns.add("Bottom");
		game.setPotionCaverns(potionCaverns);
		game.makeMoveCommand(Direction.SOUTH).execute();
		assertFalse(game.getPotionCaverns().isEmpty());
		assertEquals(10, game.getPlayerHealth());

	}

}
