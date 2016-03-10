package htw.fixtures;

import htw.HtwMessageReceiver;
import htw.HuntTheWumpus;
import htw.HuntTheWumpus.Purchase;
import htw.factory.HtwFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TestContext implements HtwMessageReceiver {
	public static TestableHuntTheWumpus game;
	public static Set<String> messages = new HashSet<>();

	public static Map<String, Integer> wumpusCaverns = new HashMap<>();

	public static Map<String, Integer> batTransportCaverns = new HashMap<>();

	public TestContext() {
		game = (TestableHuntTheWumpus) HtwFactory.makeGame(
				"htw.fixtures.TestableHuntTheWumpus", this);
		messages.clear();
		wumpusCaverns.clear();
		batTransportCaverns.clear();
	}

	public void fellInPit() {
		messages.add("FELL_IN_PIT");
	}

	public void playerMovesToWumpus() {
		messages.add("PLAYER_MOVES_TO_WUMPUS");
	}

	public void batsTransport() {
		messages.add("BAT_TRANSPORT");
	}

	public void wumpusMovesToPlayer() {
		messages.add("WUMPUS_MOVES_TO_PLAYER");
	}

	public void noPassage() {
		messages.add("NO_PASSAGE");
	}

	public void hearBats() {
		messages.add("HEAR_BATS");
	}

	public void hearPit() {
		messages.add("HEAR_PIT");
	}

	public void smellWumpus() {
		messages.add("SMELL_WUMPUS");
	}

	public void arrowsFound(Integer arrowsFound) {
		messages.add(String.format("%d_ARROW_FOUND", arrowsFound));
	}

	public void passage(HuntTheWumpus.Direction direction) {
		messages.add(direction.name() + "_PASSAGE");
	}

	public void noArrows() {
		messages.add("NO_ARROWS");
	}

	public void arrowShot() {
		messages.add("ARROW_SHOT");
	}

	public void playerShootsSelfInBack() {
		messages.add("PLAYER_SHOOTS_SELF_IN_BACK");
	}

	public void playerKillsWumpus() {
		messages.add("WUMPUS_KILLED");
	}

	public void playerShootsWall() {
		messages.add("PLAYER_SHOOTS_WALL");
	}

	@Override
	public void foundGold() {
		messages.add("PLAYER_FOUND_GOLD");

	}

	@Override
	public void potionAcquired() {
		// TODO Auto-generated method stub

	}

	@Override
	public void potionAcquiredAtMaxHealth() {
		// TODO Auto-generated method stub

	}

	@Override
	public void storeGreeting() {
		messages.add("STORE_GREETINGS");

	}

	@Override
	public void storeExit() {
		messages.add("STORE_DISABLED");

	}

	@Override
	public void foundBatRepellent() {
		messages.add("FOUND_BAT_REPELLENT");

	}

	@Override
	public void batsEscape() {
		messages.add("BAT_ESCAPE");
	}

	@Override
	public void playerPurchased(Purchase item) {
		messages.add("PURCAHSED_" + item.toString());

	}

	@Override
	public void playerAtFullHealth() {
		messages.add("FULL_HEALTH");

	}
}
