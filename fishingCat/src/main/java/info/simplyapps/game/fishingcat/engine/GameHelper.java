package info.simplyapps.game.fishingcat.engine;

import info.simplyapps.game.fishingcat.storage.StoreDataNew;

public class GameHelper {

    private static final int m1trophyCatchFishBronze = 10000;
    private static final int m1trophyCatchFishSilver = 20000;
    private static final int m1trophyCatchFishGold = 30000;
    private static final int m1trophyCatchRareFishBronze = 1500;
    private static final int m1trophyCatchRareFishSilver = 2500;
    private static final int m1trophyCatchRareFishGold = 3500;
    private static final int m1trophyNoLivesLostInGamesBronze = 3;
    private static final int m1trophyNoLivesLostInGamesSilver = 6;
    private static final int m1trophyNoLivesLostInGamesGold = 9;
    //	private static final float m1trophyMoreFishEveryRoundBronze = 0f;
    private static final float m1trophyMoreFishEveryRoundSilver = 0.10f;
    private static final float m1trophyMoreFishEveryRoundGold = 0.25f;
    //	private static final float m1trophyMoreFishesGametoGameBronze = 0f;
    private static final float m1trophyMoreFishesGametoGameSilver = 0.10f;
    private static final float m1trophyMoreFishesGametoGameGold = 0.25f;
    private static final int m1trophySurvivedGamesWithOneLifeBronze = 1;
    private static final int m1trophySurvivedGamesWithOneLifeSilver = 2;
    private static final int m1trophySurvivedGamesWithOneLifeGold = 3;
    private static final int m1trophyCatchedFishInGameBronze = 750;
    private static final int m1trophyCatchedFishInGameSilver = 1000;
    private static final int m1trophyCatchedFishInGameGold = 1250;
    private static final int m1trophyRareCatchedFishInGameBronze = 75;
    private static final int m1trophyRareCatchedFishInGameSilver = 100;
    private static final int m1trophyRareCatchedFishInGameGold = 125;

    // TODO adjust trophies for changed game mode 2
    private static final int m2trophyCatchFishBronze = 1000;
    private static final int m2trophyCatchFishSilver = 1500;
    private static final int m2trophyCatchFishGold = 2500;
    private static final int m2trophyCatchRareFishBronze = 100;
    private static final int m2trophyCatchRareFishSilver = 200;
    private static final int m2trophyCatchRareFishGold = 300;
    private static final float m2trophyMoreFishesGametoGameSilver = 0.05f;
    private static final float m2trophyMoreFishesGametoGameGold = 0.10f;
    private static final int m2trophyCatchedFishInGameBronze = 8;
    private static final int m2trophyCatchedFishInGameSilver = 10;
    private static final int m2trophyCatchedFishInGameGold = 12;
    private static final int m2trophyRareCatchedFishInGameBronze = 1;
    private static final int m2trophyRareCatchedFishInGameSilver = 2;
    private static final int m2trophyRareCatchedFishInGameGold = 3;

    public static boolean hasBronze() {
        return true;
    }

    public static boolean hasSilver() {
        return true;
    }

    public static boolean hasGold() {
        return true;
    }

    public static void checkTrophiesGameMode1(StoreDataNew storeData) {
        // check each trophy
        for (int i = 0; i < storeData.gameData1.trophies[storeData.gameData1.difficulty].length; i++) {
            switch (i) {
                case 0:
                    // move than 500 fish catched
                    if (hasBronze() && !storeData.gameData1.trophies[storeData.gameData1.difficulty][i] && storeData.gameData1.totalFish[storeData.gameData1.difficulty] >= m1trophyCatchFishBronze) {
                        storeData.gameData1.trophies[storeData.gameData1.difficulty][i] = true;
                    }
                    break;
                case 9:
                    // move than 5000 fish catched
                    if (hasSilver() && !storeData.gameData1.trophies[storeData.gameData1.difficulty][i] && storeData.gameData1.totalFish[storeData.gameData1.difficulty] >= m1trophyCatchFishSilver) {
                        storeData.gameData1.trophies[storeData.gameData1.difficulty][i] = true;
                    }
                    break;
                case 18:
                    // move than 50000 fish catched
                    if (hasGold() && !storeData.gameData1.trophies[storeData.gameData1.difficulty][i] && storeData.gameData1.totalFish[storeData.gameData1.difficulty] >= m1trophyCatchFishGold) {
                        storeData.gameData1.trophies[storeData.gameData1.difficulty][i] = true;
                    }
                    break;
                case 1:
                    // move than 50 rare fish catched
                    if (hasBronze() && !storeData.gameData1.trophies[storeData.gameData1.difficulty][i] && storeData.gameData1.totalRareFish[storeData.gameData1.difficulty] >= m1trophyCatchRareFishBronze) {
                        storeData.gameData1.trophies[storeData.gameData1.difficulty][i] = true;
                    }
                    break;
                case 10:
                    // move than 500 rare fish catched
                    if (hasSilver() && !storeData.gameData1.trophies[storeData.gameData1.difficulty][i] && storeData.gameData1.totalRareFish[storeData.gameData1.difficulty] >= m1trophyCatchRareFishSilver) {
                        storeData.gameData1.trophies[storeData.gameData1.difficulty][i] = true;
                    }
                    break;
                case 19:
                    // move than 5000 rare fish catched
                    if (hasGold() && !storeData.gameData1.trophies[storeData.gameData1.difficulty][i] && storeData.gameData1.totalRareFish[storeData.gameData1.difficulty] >= m1trophyCatchRareFishGold) {
                        storeData.gameData1.trophies[storeData.gameData1.difficulty][i] = true;
                    }
                    break;
                case 2:
                    // no lives lost in 3 games in a row
                    if (hasBronze() && !storeData.gameData1.trophies[storeData.gameData1.difficulty][i] && storeData.gameData1.gamesWithoutLivesLost[storeData.gameData1.difficulty] >= m1trophyNoLivesLostInGamesBronze) {
                        storeData.gameData1.trophies[storeData.gameData1.difficulty][i] = true;
                    }
                    break;
                case 11:
                    // no lives lost in 6 games in a row
                    if (hasSilver() && !storeData.gameData1.trophies[storeData.gameData1.difficulty][i] && storeData.gameData1.gamesWithoutLivesLost[storeData.gameData1.difficulty] >= m1trophyNoLivesLostInGamesSilver) {
                        storeData.gameData1.trophies[storeData.gameData1.difficulty][i] = true;
                    }
                    break;
                case 20:
                    // no lives lost in 9 games in a row
                    if (hasGold() && !storeData.gameData1.trophies[storeData.gameData1.difficulty][i] && storeData.gameData1.gamesWithoutLivesLost[storeData.gameData1.difficulty] >= m1trophyNoLivesLostInGamesGold) {
                        storeData.gameData1.trophies[storeData.gameData1.difficulty][i] = true;
                    }
                    break;
                case 3:
                    // more fish in every round in one game
                    if (hasBronze() && !storeData.gameData1.trophies[storeData.gameData1.difficulty][i]) {
                        int last = 0;
                        boolean failed = false;
                        if (last > 0) {
                            for (int current : storeData.currentGame1.gameCatched[storeData.gameData1.difficulty]) {
                                if (current > last) {
                                    last = current;
                                } else {
                                    failed = true;
                                    break;
                                }
                            }
                        }
                        if (!failed) {
                            storeData.gameData1.trophies[storeData.gameData1.difficulty][i] = true;
                        }
                    }
                    break;
                case 12:
                    // 10% more fish in every round in one game
                    if (hasSilver() && !storeData.gameData1.trophies[storeData.gameData1.difficulty][i]) {
                        int last = 0;
                        float percent = 0.0f;
                        boolean failed = false;
                        if (last > 0) {
                            for (int current : storeData.currentGame1.gameCatched[storeData.gameData1.difficulty]) {
                                percent = (current - last) * 100 / last;
                                if (current > last && percent >= m1trophyMoreFishEveryRoundSilver) {
                                    last = current;
                                } else {
                                    failed = true;
                                    break;
                                }
                            }
                        }
                        if (!failed) {
                            storeData.gameData1.trophies[storeData.gameData1.difficulty][i] = true;
                        }
                    }
                    break;
                case 21:
                    // 25% more fish in every round in one game
                    if (hasGold() && !storeData.gameData1.trophies[storeData.gameData1.difficulty][i]) {
                        int last = 0;
                        float percent = 0.0f;
                        boolean failed = false;
                        if (last > 0) {
                            for (int current : storeData.currentGame1.gameCatched[storeData.gameData1.difficulty]) {
                                percent = (current - last) * 100 / last;
                                if (current > last && percent >= m1trophyMoreFishEveryRoundGold) {
                                    last = current;
                                } else {
                                    failed = true;
                                    break;
                                }
                            }
                        }
                        if (!failed) {
                            storeData.gameData1.trophies[storeData.gameData1.difficulty][i] = true;
                        }
                    }
                    break;
                case 4:
                    // more fishes from game 1 to game 2
                    if (hasBronze() && !storeData.gameData1.trophies[storeData.gameData1.difficulty][i]) {
                        int totalFish = 0;
                        for (int current : storeData.currentGame1.gameCatched[storeData.gameData1.difficulty]) {
                            totalFish += current;
                        }
                        if (storeData.gameData1.fishesInLastGame[storeData.gameData1.difficulty] > 0 && totalFish > storeData.gameData1.fishesInLastGame[storeData.gameData1.difficulty]) {
                            storeData.gameData1.trophies[storeData.gameData1.difficulty][i] = true;
                        }
                    }
                    break;
                case 13:
                    // 10% more fishes from game 1 to game 2
                    if (hasSilver() && !storeData.gameData1.trophies[storeData.gameData1.difficulty][i] && storeData.gameData1.fishesInLastGame[storeData.gameData1.difficulty] > 0) {
                        int totalFish = 0;
                        float percent = 0.0f;
                        for (int current : storeData.currentGame1.gameCatched[storeData.gameData1.difficulty]) {
                            totalFish += current;
                        }
                        if (storeData.gameData1.fishesInLastGame[storeData.gameData1.difficulty] > 0) {
                            percent = (totalFish - storeData.gameData1.fishesInLastGame[storeData.gameData1.difficulty]) * 100 / storeData.gameData1.fishesInLastGame[storeData.gameData1.difficulty];
                            if (percent >= m1trophyMoreFishesGametoGameSilver) {
                                storeData.gameData1.trophies[storeData.gameData1.difficulty][i] = true;
                            }
                        }
                    }
                    break;
                case 22:
                    // 25% more fishes from game 1 to game 2
                    if (hasGold() && !storeData.gameData1.trophies[storeData.gameData1.difficulty][i] && storeData.gameData1.fishesInLastGame[storeData.gameData1.difficulty] > 0) {
                        int totalFish = 0;
                        float percent = 0.0f;
                        for (int current : storeData.currentGame1.gameCatched[storeData.gameData1.difficulty]) {
                            totalFish += current;
                        }
                        if (storeData.gameData1.fishesInLastGame[storeData.gameData1.difficulty] > 0) {
                            percent = (totalFish - storeData.gameData1.fishesInLastGame[storeData.gameData1.difficulty]) * 100 / storeData.gameData1.fishesInLastGame[storeData.gameData1.difficulty];
                            if (percent >= m1trophyMoreFishesGametoGameGold) {
                                storeData.gameData1.trophies[storeData.gameData1.difficulty][i] = true;
                            }
                        }
                    }
                    break;
                case 5:
                    // survived one game with 1 live
                    if (hasBronze() && !storeData.gameData1.trophies[storeData.gameData1.difficulty][i]) {
                        if (storeData.gameData1.gamesWithOneLive[storeData.gameData1.difficulty] >= m1trophySurvivedGamesWithOneLifeBronze) {
                            storeData.gameData1.trophies[storeData.gameData1.difficulty][i] = true;
                        }
                    }
                    break;
                case 14:
                    // survived two games in a row with 1 live
                    if (hasSilver() && !storeData.gameData1.trophies[storeData.gameData1.difficulty][i]) {
                        if (storeData.gameData1.gamesWithOneLive[storeData.gameData1.difficulty] >= m1trophySurvivedGamesWithOneLifeSilver) {
                            storeData.gameData1.trophies[storeData.gameData1.difficulty][i] = true;
                        }
                    }
                    break;
                case 23:
                    // survived three games in a row with 1 live
                    if (hasGold() && !storeData.gameData1.trophies[storeData.gameData1.difficulty][i]) {
                        if (storeData.gameData1.gamesWithOneLive[storeData.gameData1.difficulty] >= m1trophySurvivedGamesWithOneLifeGold) {
                            storeData.gameData1.trophies[storeData.gameData1.difficulty][i] = true;
                        }
                    }
                    break;
                case 6:
                    // catched 1000 fish in one game
                    if (hasBronze() && !storeData.gameData1.trophies[storeData.gameData1.difficulty][i]) {
                        int totalFish = 0;
                        for (int current : storeData.currentGame1.gameCatched[storeData.gameData1.difficulty]) {
                            totalFish += current;
                        }
                        if (totalFish >= m1trophyCatchedFishInGameBronze) {
                            storeData.gameData1.trophies[storeData.gameData1.difficulty][i] = true;
                        }
                    }
                    break;
                case 15:
                    // catched 2000 fish in one game
                    if (hasSilver() && !storeData.gameData1.trophies[storeData.gameData1.difficulty][i]) {
                        int totalFish = 0;
                        for (int current : storeData.currentGame1.gameCatched[storeData.gameData1.difficulty]) {
                            totalFish += current;
                        }
                        if (totalFish >= m1trophyCatchedFishInGameSilver) {
                            storeData.gameData1.trophies[storeData.gameData1.difficulty][i] = true;
                        }
                    }
                    break;
                case 24:
                    // catched 3000 fish in one game
                    if (hasGold() && !storeData.gameData1.trophies[storeData.gameData1.difficulty][i]) {
                        int totalFish = 0;
                        for (int current : storeData.currentGame1.gameCatched[storeData.gameData1.difficulty]) {
                            totalFish += current;
                        }
                        if (totalFish >= m1trophyCatchedFishInGameGold) {
                            storeData.gameData1.trophies[storeData.gameData1.difficulty][i] = true;
                        }
                    }
                    break;
                case 7:
                    // catched 10 rare fish in one game
                    if (hasBronze() && !storeData.gameData1.trophies[storeData.gameData1.difficulty][i]) {
                        int totalFish = 0;
                        for (int current : storeData.currentGame1.gameRare[storeData.gameData1.difficulty]) {
                            totalFish += current;
                        }
                        if (totalFish >= m1trophyRareCatchedFishInGameBronze) {
                            storeData.gameData1.trophies[storeData.gameData1.difficulty][i] = true;
                        }
                    }
                    break;
                case 16:
                    // catched 20 rare fish in one game
                    if (hasSilver() && !storeData.gameData1.trophies[storeData.gameData1.difficulty][i]) {
                        int totalFish = 0;
                        for (int current : storeData.currentGame1.gameRare[storeData.gameData1.difficulty]) {
                            totalFish += current;
                        }
                        if (totalFish >= m1trophyRareCatchedFishInGameSilver) {
                            storeData.gameData1.trophies[storeData.gameData1.difficulty][i] = true;
                        }
                    }
                    break;
                case 25:
                    // catched 30 rare fish in one game
                    if (hasGold() && !storeData.gameData1.trophies[storeData.gameData1.difficulty][i]) {
                        int totalFish = 0;
                        for (int current : storeData.currentGame1.gameRare[storeData.gameData1.difficulty]) {
                            totalFish += current;
                        }
                        if (totalFish >= m1trophyRareCatchedFishInGameGold) {
                            storeData.gameData1.trophies[storeData.gameData1.difficulty][i] = true;
                        }
                    }
                    break;
                case 8:
                    // all bronze trophies
                    if (hasBronze() && !storeData.gameData1.trophies[storeData.gameData1.difficulty][i]) {
                        boolean failedBronze = false;
                        for (int j = 0; j < 9 && j < storeData.gameData1.trophies[storeData.gameData1.difficulty].length; j++) {
                            failedBronze |= !storeData.gameData1.trophies[storeData.gameData1.difficulty][j];
                        }
                        if (!failedBronze) {
                            storeData.gameData1.trophies[storeData.gameData1.difficulty][i] = true;
                            storeData.gameData1.rewards[storeData.gameData1.difficulty][0] = true;
                        }
                    }
                    break;
                case 17:
                    // all silver trophies
                    if (hasSilver() && !storeData.gameData1.trophies[storeData.gameData1.difficulty][i]) {
                        boolean failedSilver = false;
                        for (int j = 9; j < 18 && j < storeData.gameData1.trophies[storeData.gameData1.difficulty].length; j++) {
                            failedSilver |= !storeData.gameData1.trophies[storeData.gameData1.difficulty][j];
                        }
                        if (!failedSilver) {
                            storeData.gameData1.trophies[storeData.gameData1.difficulty][i] = true;
                            storeData.gameData1.rewards[storeData.gameData1.difficulty][1] = true;
                        }
                    }
                    break;
                case 26:
                    // all gold trophies
                    if (hasGold() && !storeData.gameData1.trophies[storeData.gameData1.difficulty][i]) {
                        boolean failedGold = false;
                        for (int j = 18; j < 27 && j < storeData.gameData1.trophies[storeData.gameData1.difficulty].length; j++) {
                            failedGold |= !storeData.gameData1.trophies[storeData.gameData1.difficulty][j];
                        }
                        if (!failedGold) {
                            storeData.gameData1.trophies[storeData.gameData1.difficulty][i] = true;
                            storeData.gameData1.rewards[storeData.gameData1.difficulty][2] = true;
                        }
                    }
                    break;
            }
        }

    }

    public static void checkTrophiesGameMode2(StoreDataNew storeData) {
        // check each trophy
        for (int i = 0; i < storeData.gameData2.trophies[storeData.gameData2.difficulty].length; i++) {
            switch (i) {
                case 0:
                    // move than 500 fish catched
                    if (hasBronze() && !storeData.gameData2.trophies[storeData.gameData2.difficulty][i] && storeData.gameData2.totalFish[storeData.gameData2.difficulty] >= m2trophyCatchFishBronze) {
                        storeData.gameData2.trophies[storeData.gameData2.difficulty][i] = true;
                    }
                    break;
                case 6:
                    // move than 5000 fish catched
                    if (hasSilver() && !storeData.gameData2.trophies[storeData.gameData2.difficulty][i] && storeData.gameData2.totalFish[storeData.gameData2.difficulty] >= m2trophyCatchFishSilver) {
                        storeData.gameData2.trophies[storeData.gameData2.difficulty][i] = true;
                    }
                    break;
                case 12:
                    // move than 50000 fish catched
                    if (hasGold() && !storeData.gameData2.trophies[storeData.gameData2.difficulty][i] && storeData.gameData2.totalFish[storeData.gameData2.difficulty] >= m2trophyCatchFishGold) {
                        storeData.gameData2.trophies[storeData.gameData2.difficulty][i] = true;
                    }
                    break;
                case 1:
                    // move than 50 rare fish catched
                    if (hasBronze() && !storeData.gameData2.trophies[storeData.gameData2.difficulty][i] && storeData.gameData2.totalRareFish[storeData.gameData2.difficulty] >= m2trophyCatchRareFishBronze) {
                        storeData.gameData2.trophies[storeData.gameData2.difficulty][i] = true;
                    }
                    break;
                case 7:
                    // move than 500 rare fish catched
                    if (hasSilver() && !storeData.gameData2.trophies[storeData.gameData2.difficulty][i] && storeData.gameData2.totalRareFish[storeData.gameData2.difficulty] >= m2trophyCatchRareFishSilver) {
                        storeData.gameData2.trophies[storeData.gameData2.difficulty][i] = true;
                    }
                    break;
                case 13:
                    // move than 5000 rare fish catched
                    if (hasGold() && !storeData.gameData2.trophies[storeData.gameData2.difficulty][i] && storeData.gameData2.totalRareFish[storeData.gameData2.difficulty] >= m2trophyCatchRareFishGold) {
                        storeData.gameData2.trophies[storeData.gameData2.difficulty][i] = true;
                    }
                    break;
                case 2:
                    // more fishes from game 1 to game 2
                    if (hasBronze() && !storeData.gameData2.trophies[storeData.gameData2.difficulty][i]) {
                        int totalFish = storeData.currentGame2.gameCatched[storeData.gameData2.difficulty];
                        if (storeData.gameData2.fishesInLastGame[storeData.gameData2.difficulty] > 0 && totalFish > storeData.gameData2.fishesInLastGame[storeData.gameData2.difficulty]) {
                            storeData.gameData2.trophies[storeData.gameData2.difficulty][i] = true;
                        }
                    }
                    break;
                case 8:
                    // 10% more fishes from game 1 to game 2
                    if (hasSilver() && !storeData.gameData2.trophies[storeData.gameData2.difficulty][i] && storeData.gameData2.fishesInLastGame[storeData.gameData2.difficulty] > 0) {
                        int totalFish = storeData.currentGame2.gameCatched[storeData.gameData2.difficulty];
                        float percent = 0.0f;
                        if (storeData.gameData2.fishesInLastGame[storeData.gameData2.difficulty] > 0) {
                            percent = (totalFish - storeData.gameData2.fishesInLastGame[storeData.gameData2.difficulty]) * 100 / storeData.gameData2.fishesInLastGame[storeData.gameData2.difficulty];
                            if (percent >= m2trophyMoreFishesGametoGameSilver) {
                                storeData.gameData2.trophies[storeData.gameData2.difficulty][i] = true;
                            }
                        }
                    }
                    break;
                case 14:
                    // 25% more fishes from game 1 to game 2
                    if (hasGold() && !storeData.gameData2.trophies[storeData.gameData2.difficulty][i] && storeData.gameData2.fishesInLastGame[storeData.gameData2.difficulty] > 0) {
                        int totalFish = storeData.currentGame2.gameCatched[storeData.gameData2.difficulty];
                        float percent = 0.0f;
                        if (storeData.gameData2.fishesInLastGame[storeData.gameData2.difficulty] > 0) {
                            percent = (totalFish - storeData.gameData2.fishesInLastGame[storeData.gameData2.difficulty]) * 100 / storeData.gameData2.fishesInLastGame[storeData.gameData2.difficulty];
                            if (percent >= m2trophyMoreFishesGametoGameGold) {
                                storeData.gameData2.trophies[storeData.gameData2.difficulty][i] = true;
                            }
                        }
                    }
                    break;
                case 3:
                    // catched 1000 fish in one game
                    if (hasBronze() && !storeData.gameData2.trophies[storeData.gameData2.difficulty][i]) {
                        int totalFish = storeData.currentGame2.gameCatched[storeData.gameData2.difficulty];
                        if (totalFish >= m2trophyCatchedFishInGameBronze) {
                            storeData.gameData2.trophies[storeData.gameData2.difficulty][i] = true;
                        }
                    }
                    break;
                case 9:
                    // catched 2000 fish in one game
                    if (hasSilver() && !storeData.gameData2.trophies[storeData.gameData2.difficulty][i]) {
                        int totalFish = storeData.currentGame2.gameCatched[storeData.gameData2.difficulty];
                        if (totalFish >= m2trophyCatchedFishInGameSilver) {
                            storeData.gameData2.trophies[storeData.gameData2.difficulty][i] = true;
                        }
                    }
                    break;
                case 15:
                    // catched 3000 fish in one game
                    if (hasGold() && !storeData.gameData2.trophies[storeData.gameData2.difficulty][i]) {
                        int totalFish = storeData.currentGame2.gameCatched[storeData.gameData2.difficulty];
                        if (totalFish >= m2trophyCatchedFishInGameGold) {
                            storeData.gameData2.trophies[storeData.gameData2.difficulty][i] = true;
                        }
                    }
                    break;
                case 4:
                    // catched 10 rare fish in one game
                    if (hasBronze() && !storeData.gameData2.trophies[storeData.gameData2.difficulty][i]) {
                        int totalFish = storeData.currentGame2.gameRare[storeData.gameData2.difficulty];
                        if (totalFish >= m2trophyRareCatchedFishInGameBronze) {
                            storeData.gameData2.trophies[storeData.gameData2.difficulty][i] = true;
                        }
                    }
                    break;
                case 10:
                    // catched 20 rare fish in one game
                    if (hasSilver() && !storeData.gameData2.trophies[storeData.gameData2.difficulty][i]) {
                        int totalFish = storeData.currentGame2.gameRare[storeData.gameData2.difficulty];
                        if (totalFish >= m2trophyRareCatchedFishInGameSilver) {
                            storeData.gameData2.trophies[storeData.gameData2.difficulty][i] = true;
                        }
                    }
                    break;
                case 16:
                    // catched 30 rare fish in one game
                    if (hasGold() && !storeData.gameData2.trophies[storeData.gameData2.difficulty][i]) {
                        int totalFish = storeData.currentGame2.gameRare[storeData.gameData2.difficulty];
                        if (totalFish >= m2trophyRareCatchedFishInGameGold) {
                            storeData.gameData2.trophies[storeData.gameData2.difficulty][i] = true;
                        }
                    }
                    break;
                case 5:
                    // all bronze trophies
                    if (hasBronze() && !storeData.gameData2.trophies[storeData.gameData2.difficulty][i]) {
                        boolean failedBronze = false;
                        for (int j = 0; j < 6 && j < storeData.gameData2.trophies[storeData.gameData2.difficulty].length; j++) {
                            failedBronze |= !storeData.gameData2.trophies[storeData.gameData2.difficulty][j];
                        }
                        if (!failedBronze) {
                            storeData.gameData2.trophies[storeData.gameData2.difficulty][i] = true;
                            storeData.gameData2.rewards[storeData.gameData2.difficulty][0] = true;
                        }
                    }
                    break;
                case 11:
                    // all silver trophies
                    if (hasSilver() && !storeData.gameData2.trophies[storeData.gameData2.difficulty][i]) {
                        boolean failedSilver = false;
                        for (int j = 6; j < 12 && j < storeData.gameData2.trophies[storeData.gameData2.difficulty].length; j++) {
                            failedSilver |= !storeData.gameData2.trophies[storeData.gameData2.difficulty][j];
                        }
                        if (!failedSilver) {
                            storeData.gameData2.trophies[storeData.gameData2.difficulty][i] = true;
                            storeData.gameData2.rewards[storeData.gameData2.difficulty][1] = true;
                        }
                    }
                    break;
                case 17:
                    // all gold trophies
                    if (hasGold() && !storeData.gameData2.trophies[storeData.gameData2.difficulty][i]) {
                        boolean failedGold = false;
                        for (int j = 12; j < 18 && j < storeData.gameData2.trophies[storeData.gameData2.difficulty].length; j++) {
                            failedGold |= !storeData.gameData2.trophies[storeData.gameData2.difficulty][j];
                        }
                        if (!failedGold) {
                            storeData.gameData2.trophies[storeData.gameData2.difficulty][i] = true;
                            storeData.gameData2.rewards[storeData.gameData2.difficulty][2] = true;
                        }
                    }
                    break;
            }
        }
    }
}
