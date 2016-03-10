package htw;

import htw.game.HuntTheWumpusGame;

import java.util.Set;

public interface HuntTheWumpus {
	public enum Direction {
		STORE {
			@Override
			public Direction opposite() {
				return null;
			}
		},
		NORTH {
			public Direction opposite() {
				return SOUTH;
			}
		},
		SOUTH {
			public Direction opposite() {
				return NORTH;
			}
		},
		EAST {
			public Direction opposite() {
				return WEST;
			}
		},
		WEST {
			public Direction opposite() {
				return EAST;
			}
		},
		QUIT {
			@Override
			public Direction opposite() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		public abstract Direction opposite();

	}

	void setPlayerCavern(String playerCavern);

	String getPlayerCavern();

	void addBatCavern(String cavern);

	void addPitCavern(String cavern);

	void addGoldCavern(String cavern);

	void setWumpusCavern(String wumpusCavern);

	String getWumpusCavern();

	void setQuiver(int arrows);

	int getQuiver();

	Integer getArrowsInCavern(String cavern);

	void connectCavern(String from, String to, Direction direction);

	String findDestination(String cavern, Direction direction);

	HuntTheWumpusGame.Command makeRestCommand();

	HuntTheWumpusGame.Command makeShootCommand(Direction direction);

	HuntTheWumpusGame.Command makeMoveCommand(Direction direction);

	public interface Command {
		void execute();
	}

	public int getPlayerHealth();

	public void setPlayerHealth(int points);

	public int getPlayerBatRepellant();

	public void setPlayerBatRepellant(int point);

	public void hitPlayerBy(int points);

	// public void setPlayerGoldPoints(int gold);

	public void potionAcquired();

	public void addPotionCavern(String anyOther);

	public void addBatRepellantCavern(String anyOther);

	public Set<String> getPotionCaverns();

	public void setPotionCaverns(Set<String> potionCaverns);

	public int getPlayerGold();

	public void setPlayerGold(int playerGold);

	public boolean isGoldInCavern(String cavern);

}