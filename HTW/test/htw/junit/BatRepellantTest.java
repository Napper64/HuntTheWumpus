package htw.junit;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import htw.HuntTheWumpus;
import htw.HuntTheWumpus.Direction;
import htw.factory.HtwFactory;

public class BatRepellantTest {

	private HuntTheWumpus game;

	@Before
	public void setup() throws IOException {
		TestableMain.main(new String[] { "arg1", "arg2", "arg3" });
		game = TestableMain.getCurrentGameInstance();
	}

	@Test
	public void PlayerBatRepellant_At0_Increases_To1() {
		game.setPlayerBatRepellant(1);
		assertEquals(1, game.getPlayerBatRepellant());
	}

	@Test
	public void PlayerBatRepellant_At1_RepellantCannotBeAdded() {
		game.setPlayerBatRepellant(10);
		//assertEqual(game.isBatRepellantAcquired());
		//game.potionAcquired();
		assertEquals(0, game.getPlayerBatRepellant());
		game.setPlayerBatRepellant(1);
		assertEquals(1, game.getPlayerBatRepellant());
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
