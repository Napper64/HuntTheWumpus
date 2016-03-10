package htw;

import htw.HuntTheWumpus.Purchase;

public interface HtwMessageReceiver {
	void noPassage();

	void hearBats();

	void hearPit();

	void smellWumpus();

	void passage(HuntTheWumpus.Direction direction);

	void noArrows();

	void arrowShot();

	void playerShootsSelfInBack();

	void playerKillsWumpus();

	void playerShootsWall();

	void arrowsFound(Integer arrowsFound);

	void fellInPit();

	void foundGold();

	void foundBatRepellent();

	void playerMovesToWumpus();

	void wumpusMovesToPlayer();

	void batsTransport();

	void batsEscape();

	void potionAcquired();

	void potionAcquiredAtMaxHealth();

	void storeGreeting();

	void storeExit();

	void playerPurchased(Purchase string);
}
