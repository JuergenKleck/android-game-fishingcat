package info.simplyapps.game.fishingcat.system;

public class GameRound extends info.simplyapps.gameengine.system.GameRound {

	public int catched;
	public int rare;

	public GameRound(int catched, int rare, int round, long time) {
	    super(round, time, 0);
		this.catched = catched;
		this.rare = rare;
	}

	public GameRound(int round, long time) {
        super(round, time, 0);
		this.catched = 0;
		this.rare = 0;
	}

	public GameRound() {
        super(0, 0, 0);
		this.catched = 0;
		this.rare = 0;
	}
}
