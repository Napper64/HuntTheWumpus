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
	public void playerEntersBatCavern_DoesNotHaveRepellant_BatTransport() throws Exception {
		game = HtwFactory.makeGame("htw.game.HuntTheWumpusGame",
				new TestableMain());
		game.setPlayerBatRepellant(0);
		game.connectCavern("Top", "Bottom", Direction.SOUTH);
		game.connectCavern("Bottom", "Top", Direction.NORTH);
		game.setPlayerCavern("Top");
		Set<String> batCaverns = new HashSet<String>();
		batCaverns.add("Bottom");
		game.setBatCaverns(batCaverns);
		game.makeMoveCommand(Direction.SOUTH).execute();
		
		assertEquals("Top", game.getPlayerCavern());
	}
	
	@Test
	public void playerEntersBatCavern_HasRepellant_BatsFlyAway() throws Exception {
		game = HtwFactory.makeGame("htw.game.HuntTheWumpusGame",
				new TestableMain());
		game.setPlayerBatRepellant(1);
		game.connectCavern("Top", "Bottom", Direction.SOUTH);
		game.connectCavern("Bottom", "Top", Direction.NORTH);
		game.setPlayerCavern("Top");
		Set<String> batCaverns = new HashSet<String>();
		batCaverns.add("Bottom");
		game.setBatCaverns(batCaverns);
		game.makeMoveCommand(Direction.SOUTH).execute();
		
		assertEquals("Bottom", game.getPlayerCavern());
	}

}
