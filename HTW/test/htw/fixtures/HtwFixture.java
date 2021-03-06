package htw.fixtures;

import static htw.fixtures.TestContext.game;
import htw.HuntTheWumpus;
import htw.HuntTheWumpus.Direction;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HtwFixture {
	public boolean ConnectCavernToGoing(String c1, String c2, String dir) {
		game.connectCavern(c1, c2, toDirection(dir));
		return true;
	}

	public boolean putPlayerInCavern(String c) {
		game.setPlayerCavern(c);
		return true;
	}

	public boolean movePlayer(String dir) {
		game.makeMoveCommand(toDirection(dir)).execute();
		return true;
	}

	public String getPlayerCavern() {
		return game.getPlayerCavern();
	}

	public boolean putWumpusInCavern(String c) {
		game.setWumpusCavern(c);
		return true;
	}

	public String getWumpusCavern() {
		return game.getWumpusCavern();
	}

	public boolean freezeWumpus() {
		game.freezeWumpus();
		return true;
	}

	public boolean MessageIdWasGiven(String message) {
		return (TestContext.messages.contains(message));
	}

	public boolean MessageIdWasNotGiven(String message) {
		return !MessageIdWasGiven(message);
	}

	public boolean clearMessages() {
		TestContext.messages.clear();
		return true;
	}

	public boolean setCavernAsPit(String cavern) {
		game.addPitCavern(cavern);
		return true;
	}

	public boolean rest() {
		game.makeRestCommand().execute();
		return true;
	}

	public boolean RestTimesWithWumpusInEachTime(int times, String cavern) {
		TestContext.wumpusCaverns.clear();
		for (int i = 0; i < times; i++) {
			putWumpusInCavern(cavern);
			game.makeRestCommand().execute();
			incrementCounter(TestContext.wumpusCaverns, game.getWumpusCavern());
		}
		return true;
	}

	private int zeroIfNull(Integer integer) {
		return integer == null ? 0 : integer;
	}

	public boolean MovePlayerTimesWithPlayerInEachTime(int times,
			String direction, String startingCavern) {
		TestContext.batTransportCaverns.clear();
		for (int i = 0; i < times; i++) {
			putPlayerInCavern(startingCavern);
			game.makeMoveCommand(toDirection(direction)).execute();
			incrementCounter(TestContext.batTransportCaverns,
					game.getPlayerCavern());
		}
		return true;
	}

	private void incrementCounter(Map<String, Integer> counterMap, String cavern) {
		counterMap.put(cavern, zeroIfNull(counterMap.get(cavern)) + 1);
	}

	public boolean restUntilKilled() {
		while (!getPlayerCavern().equals(getWumpusCavern()))
			game.makeRestCommand().execute();

		return true;
	}

	public boolean setArrowsInQuiverTo(int arrows) {
		game.setQuiver(arrows);
		return true;
	}

	public boolean shootArrow(String direction) {
		game.makeShootCommand(toDirection(direction)).execute();
		return true;
	}

	private HuntTheWumpus.Direction toDirection(String direction) {
		return HuntTheWumpus.Direction.valueOf(direction.toUpperCase());
	}

	public int arrowsInQuiver() {
		return game.getQuiver();
	}

	public int arrowsInCavern(String cavern) {
		return game.getArrowsInCavern(cavern);
	}

	public boolean putGoldInCavern(String cavern) {
		game.addGoldCavern(cavern);
		return true;
	}

	public boolean setPlayerGold(int gold) {
		game.setPlayerGold(gold);
		return true;
	}

	public boolean goldInCavern(String cavern) {
		return game.isGoldInCavern(cavern);
	}

	public int goldAmountIs() {
		return game.getPlayerGold();
	}

	public boolean setMagicPotionTo(String cavern) {
		Set<String> potionsCaverns = new HashSet<String>();
		potionsCaverns.add(cavern);
		game.setPotionCaverns(potionsCaverns);
		return true;

	}

	public boolean setHealthTo(int health) {
		game.setPlayerHealth(health);
		return true;
	}

	public int magicPotionInCavern(String cavern) {
		if (game.getPotionCaverns().contains(cavern))
			return 1;
		return 0;

	}

	public int health() {
		return game.getPlayerHealth();
	}

	public boolean setCavernAsBats(String cavern) {
		game.addBatCavern(cavern);
		return true;
	}

	public int cavernHasBatRepellent(String cavern) {
		return 1;
	}

	public boolean setBatRepellentTo(int point) {
		game.setPlayerBatRepellant(point);
		return true;
	}

	public void setInStore(boolean status) {
		if (status = true) {
			game.makeMoveCommand(Direction.STORE).execute();
		}
	}
}
