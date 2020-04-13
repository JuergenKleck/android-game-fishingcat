package info.simplyapps.game.fishingcat.storage.dto;

import info.simplyapps.appengine.storage.dto.BasicTable;

import java.io.Serializable;

public class CurrentGame1 extends BasicTable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6974204243261183587L;
	
	public int[] currentGame;
	public int[] currentLives;
	public int[][] gameCatched;
	public int[][] gameRare;
	public int[][] gameRound;
	public long[][] gameTime;
    public boolean bonus;

    public void selfcheck() {
        if(currentGame == null) {
            currentGame = new int[] { -1, -1, -1 };
        }
        if(currentLives == null) {
            currentLives = new int[3];
        }
        if(gameCatched == null) {
            gameCatched = new int[3][9];
        }
        if(gameRare == null) {
            gameRare = new int[3][9];
        }
        if(gameRound == null) {
            gameRound = new int[3][9];
        }
        if(gameTime == null) {
            gameTime = new long[3][9];
        }
    }
    
}
