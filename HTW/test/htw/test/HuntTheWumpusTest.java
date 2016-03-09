package htw.test;

import htw.HuntTheWumpus;
import htw.console.Main;
import htw.factory.HtwFactory;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class HuntTheWumpusTest {

	private HuntTheWumpus huntTheWumpus;

	@Before
	public void setup() {
		huntTheWumpus = HtwFactory.makeGame("htw.game.HuntTheWumpusGame",
				new Main());
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
	public void checkForGoldTest() throws Exception {
		
	}

}
