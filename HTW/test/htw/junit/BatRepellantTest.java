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


}
