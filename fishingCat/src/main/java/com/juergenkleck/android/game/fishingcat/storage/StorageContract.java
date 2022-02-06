package com.juergenkleck.android.game.fishingcat.storage;

import android.provider.BaseColumns;

/**
 * Android app - FishingCat
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class StorageContract extends
        com.juergenkleck.android.appengine.storage.StorageContract {

    public static abstract class TableInventory implements BaseColumns {
        public static final String TABLE_NAME = "inventory";
        public static final String COLUMN_CATMODE = "catmode";
        public static final String COLUMN_GAMESYSTEM = "gamesystem";
        public static final String COLUMN_MIGRATED = "migrated";
    }

    public static abstract class TableCurrentGame1 implements BaseColumns {
        public static final String TABLE_NAME = "currentgame1";
        public static final String COLUMN_CURRENTGAME = "currentgame";
        public static final String COLUMN_CURRENTLIFE = "currentlife";
        public static final String COLUMN_GAME_CATCHED = "gamecatched";
        public static final String COLUMN_GAME_RARE = "gamerare";
        public static final String COLUMN_GAME_ROUND = "gameround";
        public static final String COLUMN_GAME_TIME = "gametime";
        public static final String COLUMN_GAME_BONUS = "gamebonus";
    }

    public static abstract class TableGameData1 implements BaseColumns {
        public static final String TABLE_NAME = "gamedata1";
        public static final String COLUMN_DIFFICULTY = "difficulty";
        public static final String COLUMN_TOTAL_FISH = "totalfish";
        public static final String COLUMN_TOTAL_RARE_FISH = "totalrarefish";
        public static final String COLUMN_TOTAL_GAMES = "totalgames";
        public static final String COLUMN_TOTAL_LIVES_LOST = "totalliveslost";
        public static final String COLUMN_TOTAL_GAMES_LOST = "totalgameslost";
        public static final String COLUMN_LAST_GAME_FISH = "lastgamefish";
        public static final String COLUMN_LAST_GAME_RARE_FISH = "lastgamerarefish";
        public static final String COLUMN_LAST_GAME_LIVES_LOST = "lastgameliveslost";
        public static final String COLUMN_GAMES_WITHOUT_LIVE_LOST = "gameswithoutlivelost";
        public static final String COLUMN_GAMES_WITH_ONE_LIVE = "gameswithonelive";
        public static final String COLUMN_FISHES_IN_LAST_GAME = "fishesinlastgame";
        public static final String COLUMN_TROPHIES = "trophies";
        public static final String COLUMN_REWARDS = "rewards";
    }

    public static abstract class TableCurrentGame2 implements BaseColumns {
        public static final String TABLE_NAME = "currentgame2";
        public static final String COLUMN_CURRENTGAME = "currentgame";
        public static final String COLUMN_POSITION_X = "positionx";
        public static final String COLUMN_POSITION_Y = "positiony";
        public static final String COLUMN_GAME_CATCHED = "gamecatched";
        public static final String COLUMN_GAME_RARE = "gamerare";
        public static final String COLUMN_GAME_BONUS = "gamebonus";
    }

    public static abstract class TableGameData2 implements BaseColumns {
        public static final String TABLE_NAME = "gamedata2";
        public static final String COLUMN_DIFFICULTY = "difficulty";
        public static final String COLUMN_TOTAL_FISH = "totalfish";
        public static final String COLUMN_TOTAL_RARE_FISH = "totalrarefish";
        public static final String COLUMN_TOTAL_GAMES = "totalgames";
        public static final String COLUMN_LAST_GAME_FISH = "lastgamefish";
        public static final String COLUMN_LAST_GAME_RARE_FISH = "lastgamerarefish";
        public static final String COLUMN_FISHES_IN_LAST_GAME = "fishesinlastgame";
        public static final String COLUMN_TROPHIES = "trophies";
        public static final String COLUMN_REWARDS = "rewards";
    }

}
