package info.simplyapps.game.fishingcat.storage.dto;

import info.simplyapps.appengine.storage.dto.BasicTable;
import info.simplyapps.game.fishingcat.engine.GameValues;

import java.io.Serializable;

public class GameData1 extends BasicTable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6974204243261183587L;
	
    public int difficulty; // 0 = easy, 1 = medium, 2 = hard
    public int[] totalFish;
    public int[] totalRareFish;
    public int[] totalGames;
    public int[] totalGamesLost;
    public int[] totalLivesLost;
    
    // all data of the last game
    public int[] lastGameFish;
    public int[] lastGameRareFish;
    public int[] lastGameLivesLost;
    
    public int[] gamesWithoutLivesLost;
    public int[] gamesWithOneLive;
    public int[] fishesInLastGame;
    
    // collectable items
    public boolean[][] trophies;
    public boolean[][] rewards;

    public void selfcheck() {
        if(difficulty < 0 || difficulty > GameValues.GAMEMODE_HARD) {
            difficulty = GameValues.GAMEMODE_EASY;
        }
        if(totalFish == null) {
            totalFish = new int[3];
        }
        if(totalRareFish == null) {
            totalRareFish = new int[3];
        }
        if(totalGames == null) {
            totalGames = new int[3];
        }
        if(totalGamesLost == null) {
            totalGamesLost = new int[3];
        }
        if(totalLivesLost == null) {
            totalLivesLost = new int[3];
        }
        if(lastGameFish == null) {
            lastGameFish = new int[3];
        }
        if(lastGameRareFish == null) {
            lastGameRareFish = new int[3];
        }
        if(lastGameLivesLost == null) {
            lastGameLivesLost = new int[3];
        }
        if(gamesWithoutLivesLost == null) {
            gamesWithoutLivesLost = new int[3];
        }
        if(gamesWithOneLive == null) {
            gamesWithOneLive = new int[3];
        }
        if(fishesInLastGame == null) {
            fishesInLastGame = new int[3];
        }
        if(trophies == null) {
            trophies = new boolean[3][27];
        }
        if(rewards == null) {
            rewards = new boolean[3][3];
        }
    }
    
}
