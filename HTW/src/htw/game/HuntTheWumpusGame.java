package htw.game;

import htw.HtwMessageReceiver;
import htw.HuntTheWumpus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Predicate;

public class HuntTheWumpusGame implements HuntTheWumpus {
	private List<Connection> connections = new ArrayList<>();

	private Set<String> caverns = new HashSet<>();
	private String playerCavern = "NONE";
	private HtwMessageReceiver messageReceiver;
	private Set<String> batCaverns = new HashSet<>();
	private Set<String> pitCaverns = new HashSet<>();
	private Set<String> goldCaverns = new HashSet<>();
	private Set<String> potionCaverns = new HashSet<>();
	private Set<String> batRepellantCaverns = new HashSet<>();
	private String wumpusCavern = "NONE";
	private String storeCavern = "store";
	private String previousPlayerCavern;
	private int quiver = 0;
	private Map<String, Integer> arrowsIn = new HashMap<>();
	private int playerHealth = 10;
	private int playerGold = 0;
	private int batRepellant = 0;

	public HuntTheWumpusGame(HtwMessageReceiver receiver) {
		this.messageReceiver = receiver;
	}

	public void setPlayerCavern(String playerCavern) {
		this.playerCavern = playerCavern;
	}

	public String getPlayerCavern() {
		return playerCavern;
	}

	private void reportStatus() {
		reportAvailableDirections();
		if (reportNearby(c -> batCaverns.contains(c.to)))
			messageReceiver.hearBats();
		if (reportNearby(c -> pitCaverns.contains(c.to)))
			messageReceiver.hearPit();
		if (reportNearby(c -> wumpusCavern.equals(c.to)))
			messageReceiver.smellWumpus();
	}

	private boolean reportNearby(Predicate<Connection> nearTest) {
		for (Connection c : connections)
			if (playerCavern.equals(c.from) && nearTest.test(c))
				return true;
		return false;
	}

	private void reportAvailableDirections() {
		for (Connection c : connections) {
			if (playerCavern.equals(c.from))
				messageReceiver.passage(c.direction);
		}
	}

	public void addBatCavern(String cavern) {
		batCaverns.add(cavern);
	}

	public void addPitCavern(String cavern) {
		pitCaverns.add(cavern);
	}

	public void addGoldCavern(String cavern) {
		goldCaverns.add(cavern);
	}

	public void addPotionCavern(String cavern) {
		potionCaverns.add(cavern);
	}

	public void addBatRepellantCavern(String cavern) {
		batRepellantCaverns.add(cavern);
	}

	public void setWumpusCavern(String wumpusCavern) {
		this.wumpusCavern = wumpusCavern;
	}

	public String getWumpusCavern() {
		return wumpusCavern;
	}

	protected void moveWumpus() {
		List<String> wumpusChoices = new ArrayList<>();
		for (Connection c : connections)
			if (wumpusCavern.equals(c.from))
				wumpusChoices.add(c.to);
		wumpusChoices.add(wumpusCavern);

		int nChoices = wumpusChoices.size();
		int choice = (int) (Math.random() * nChoices);
		wumpusCavern = wumpusChoices.get(choice);
	}

	private void randomlyTransportPlayer() {
		Set<String> transportChoices = new HashSet<>(caverns);
		transportChoices.remove(playerCavern);
		int nChoices = transportChoices.size();
		int choice = (int) (Math.random() * nChoices);
		String[] choices = new String[nChoices];
		playerCavern = transportChoices.toArray(choices)[choice];
	}

	public void setQuiver(int arrows) {
		this.quiver = arrows;
	}

	public int getQuiver() {
		return quiver;
	}

	public Integer getArrowsInCavern(String cavern) {
		return zeroIfNull(arrowsIn.get(cavern));
	}

	private int zeroIfNull(Integer integer) {
		if (integer == null)
			return 0;
		else
			return integer.intValue();
	}

	private class Connection {
		String from;
		String to;
		Direction direction;

		public Connection(String from, String to, Direction direction) {
			this.from = from;
			this.to = to;
			this.direction = direction;
		}
	}

	public void connectCavern(String from, String to, Direction direction) {
		connections.add(new Connection(from, to, direction));
		caverns.add(from);
		caverns.add(to);
	}

	public String findDestination(String cavern, Direction direction) {
		for (Connection c : connections)
			if (c.from.equals(cavern) && c.direction == direction)
				return c.to;
		return null;
	}

	public Command makeRestCommand() {
		return new RestCommand();
	}

	public Command makeShootCommand(Direction direction) {
		return new ShootCommand(direction);
	}

	public Command makeMoveCommand(Direction direction) {
		return new MoveCommand(direction);
	}

	public void makePurchase(Purchase purchase) {
		if (playerGold >= 1) {
			if (purchase.equals(Purchase.POTION))
				if (getPlayerHealth() == 10) {
					messageReceiver.playerAtFullHealth();
					setPlayerGold(playerGold + 1);
				} else {
					potionAcquired();
					messageReceiver.playerPurchased(purchase);
				}
			if (purchase.equals(Purchase.BAT_REPELLANT)) {
				setPlayerBatRepellant(1);
				messageReceiver.playerPurchased(purchase);
			}
			if (purchase.equals(Purchase.ARROW)) {
				quiver += 1;
				messageReceiver.playerPurchased(purchase);
			}
			setPlayerGold(playerGold - 1);
		} else {
			System.out.println("Insufficent Funds");
		}
	}

	public abstract class GameCommand implements Command {
		public void execute() {
			processCommand();
			if (!isStoreActive()) {
				moveWumpus();
				checkWumpusMovedToPlayer();
				reportStatus();
			}
		}

		private boolean isStoreActive() {
			if (playerCavern.equals(storeCavern))
				return true;
			return false;
		}

		protected void checkWumpusMovedToPlayer() {
			if (playerCavern.equals(wumpusCavern))
				messageReceiver.wumpusMovesToPlayer();
		}

		protected abstract void processCommand();

	}

	private class RestCommand extends GameCommand {
		public void processCommand() {
		}
	}

	private class ShootCommand extends GameCommand {
		private Direction direction;

		public ShootCommand(Direction direction) {
			this.direction = direction;
		}

		public void processCommand() {
			if (quiver == 0)
				messageReceiver.noArrows();
			else {
				messageReceiver.arrowShot();
				quiver--;
				ArrowTracker arrowTracker = new ArrowTracker(playerCavern)
						.trackArrow(direction);
				if (arrowTracker.arrowHitSomething())
					return;
				incrementArrowsInCavern(arrowTracker.getArrowCavern());
			}
		}

		private void incrementArrowsInCavern(String arrowCavern) {
			int arrows = getArrowsInCavern(arrowCavern);
			arrowsIn.put(arrowCavern, arrows + 1);
		}

		private class ArrowTracker {
			private boolean hitSomething = false;
			private String arrowCavern;

			public ArrowTracker(String startingCavern) {
				this.arrowCavern = startingCavern;
			}

			boolean arrowHitSomething() {
				return hitSomething;
			}

			public String getArrowCavern() {
				return arrowCavern;
			}

			public ArrowTracker trackArrow(Direction direction) {
				String nextCavern;
				for (int count = 0; (nextCavern = nextCavern(arrowCavern,
						direction)) != null; count++) {
					arrowCavern = nextCavern;
					if (shotSelfInBack())
						return this;
					if (shotWumpus())
						return this;
					if (count > 100)
						return this;
				}
				if (arrowCavern.equals(playerCavern))
					messageReceiver.playerShootsWall();
				return this;
			}

			private boolean shotWumpus() {
				if (arrowCavern.equals(wumpusCavern)) {
					messageReceiver.playerKillsWumpus();
					hitSomething = true;
					return true;
				}
				return false;
			}

			private boolean shotSelfInBack() {
				if (arrowCavern.equals(playerCavern)) {
					messageReceiver.playerShootsSelfInBack();
					hitSomething = true;
					return true;
				}
				return false;
			}

			private String nextCavern(String cavern, Direction direction) {
				for (Connection c : connections)
					if (cavern.equals(c.from) && direction.equals(c.direction))
						return c.to;
				return null;
			}
		}
	}

	private class MoveCommand extends GameCommand {
		private Direction direction;

		public MoveCommand(Direction direction) {
			this.direction = direction;
		}

		public void processCommand() {
			if (direction.equals(Direction.STORE)) {
				displayStore();
			} else if (movePlayer(direction)) {
				checkForWumpus();
				checkForPit();
				checkForBats();
				checkForGold();
				checkForArrows();
				checkForPotions();
				checkForBatRepellent();
			} else if (direction.equals(Direction.QUIT)) {
				setPlayerCavern(previousPlayerCavern);
				messageReceiver.storeExit();
			} else
				messageReceiver.noPassage();
		}

		private void displayStore() {
			previousPlayerCavern = getPlayerCavern();
			setPlayerCavern(storeCavern);
			displayItems();
		}

		private void checkForPotions() {
			if (potionCaverns.contains(playerCavern)) {
				potionAcquired();
			}
		}

		private void checkForWumpus() {
			if (wumpusCavern.equals(playerCavern))
				messageReceiver.playerMovesToWumpus();
		}

		private void checkForBats() {
			if (batCaverns.contains(playerCavern)) {
				if (getPlayerBatRepellant() < 1) {
					messageReceiver.batsTransport();
					randomlyTransportPlayer();
				} else {
					messageReceiver.batsEscape();
					setPlayerBatRepellant(getPlayerBatRepellant() - 1);
				}
			}
		}

		public boolean movePlayer(Direction direction) {
			String destination = findDestination(playerCavern, direction);
			if (destination != null) {
				playerCavern = destination;
				return true;
			}
			return false;
		}

		private void checkForPit() {
			if (pitCaverns.contains(playerCavern))
				messageReceiver.fellInPit();
		}

		private void checkForGold() {
			if (goldCaverns.contains(playerCavern)) {
				messageReceiver.foundGold();
				goldCaverns.remove(getPlayerCavern());
				setPlayerGold(getPlayerGold() + 1);
			}
		}

		private void checkForBatRepellent() {
			if (batRepellantCaverns.contains(playerCavern)) {
				messageReceiver.foundBatRepellent();
				batRepellantCaverns.remove(getPlayerCavern());
				setPlayerBatRepellant(getPlayerBatRepellant() + 1);
			}
		}

		private void checkForArrows() {
			Integer arrowsFound = getArrowsInCavern(playerCavern);
			if (arrowsFound > 0)
				messageReceiver.arrowsFound(arrowsFound);
			quiver += arrowsFound;
			arrowsIn.put(playerCavern, 0);
		}
	}

	@Override
	public int getPlayerHealth() {
		return this.playerHealth;
	}

	@Override
	public void setPlayerHealth(int healthPoints) {
		this.playerHealth = healthPoints;
	}

	@Override
	public void hitPlayerBy(int points) {
		setPlayerHealth(playerHealth -= points);
		if (getPlayerHealth() <= 0) {
			System.out.println("You have died of your wounds.");
			System.exit(0);
		}
	}

	@Override
	public void potionAcquired() {
		if (getPlayerHealth() == 10) {
			messageReceiver.potionAcquiredAtMaxHealth();
		} else {
			setPlayerHealth(playerHealth + 3);
			if (getPlayerHealth() > 10)
				setPlayerHealth(10);
			try {
				potionCaverns.remove(getPlayerCavern());
			} catch (Exception e) {

			}
			if (!getPlayerCavern().equals(storeCavern))
				messageReceiver.potionAcquired();
		}
	}

	@Override
	public Set<String> getPotionCaverns() {
		return potionCaverns;
	}

	@Override
	public void setPotionCaverns(Set<String> potionCaverns) {
		this.potionCaverns = potionCaverns;
	}

	@Override
	public int getPlayerGold() {
		return playerGold;
	}

	public void setPlayerGold(int playerGold) {
		this.playerGold = playerGold;
	}

	@Override
	public boolean isGoldInCavern(String cavern) {
		for (String string : goldCaverns) {
			if (string.equalsIgnoreCase(cavern))
				return true;
		}
		return false;
	}

	@Override
	public int getPlayerBatRepellant() {
		return batRepellant;
	}

	@Override
	public void setPlayerBatRepellant(int point) {
		if ((point < 2)) {
			batRepellant = point;
		}
	}

	private void displayItems() {
		messageReceiver.storeGreeting();
		System.out
				.println("What would you like to purchase?\n1) Potion\n2) Bat Repellant\n3) Arrow\n4) Quit>");
		Scanner scanner = new Scanner(System.in);
		if (scanner.hasNextInt()) {
			switch (scanner.nextInt()) {
			case 1:
				makePurchase(Purchase.POTION);
				displayItems();
				break;
			case 2:
				makePurchase(Purchase.BAT_REPELLANT);
				displayItems();
				break;
			case 3:
				makePurchase(Purchase.ARROW);
				displayItems();
				break;
			case 4:
				playerCavern = previousPlayerCavern;
				break;
			default:
				System.out.println("Invalid Input");
				displayItems();
			}
		} else {
			System.out.println("Invalid Input");
			displayItems();
		}

	}

	public void setPreviousPlayerCavern(String previousPlayerCavern) {
		this.previousPlayerCavern = previousPlayerCavern;
	}

	public String getPreviousPlayerCavern() {
		return this.previousPlayerCavern;
	}
}
