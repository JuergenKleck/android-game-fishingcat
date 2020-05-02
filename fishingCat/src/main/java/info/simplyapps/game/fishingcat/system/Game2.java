package info.simplyapps.game.fishingcat.system;

import info.simplyapps.gameengine.system.Game;


public class Game2 extends Game {

    public boolean started;
    public boolean onGround;
    public int catched;
    public int rare;
    public int positionX;
    public int positionY;
    public float bonus;

    public Game2() {
        super(new GameRound[0]);
    }

    @Override
    public boolean finished() {
        return started && onGround == true;
    }

    @Override
    public boolean hasGame() {
        return started;
    }


}
