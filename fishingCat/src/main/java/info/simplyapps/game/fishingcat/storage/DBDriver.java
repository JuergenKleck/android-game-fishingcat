package info.simplyapps.game.fishingcat.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import info.simplyapps.appengine.storage.dto.BasicTable;
import info.simplyapps.game.fishingcat.storage.dto.CurrentGame1;
import info.simplyapps.game.fishingcat.storage.dto.CurrentGame2;
import info.simplyapps.game.fishingcat.storage.dto.GameData1;
import info.simplyapps.game.fishingcat.storage.dto.GameData2;
import info.simplyapps.game.fishingcat.storage.dto.Inventory;

public class DBDriver extends info.simplyapps.appengine.storage.DBDriver {

    private static final String SQL_CREATE_INVENTORY =
            "CREATE TABLE " + StorageContract.TableInventory.TABLE_NAME + " (" +
                    StorageContract.TableInventory._ID + " INTEGER PRIMARY KEY," +
                    StorageContract.TableInventory.COLUMN_CATMODE + TYPE_INT + COMMA_SEP +
                    StorageContract.TableInventory.COLUMN_GAMESYSTEM + TYPE_INT + COMMA_SEP +
                    StorageContract.TableInventory.COLUMN_MIGRATED + TYPE_INT +
                    " );";
    private static final String SQL_DELETE_INVENTORY =
            "DROP TABLE IF EXISTS " + StorageContract.TableInventory.TABLE_NAME;

    private static final String SQL_CREATE_CURRENTGAME1 =
            "CREATE TABLE " + StorageContract.TableCurrentGame1.TABLE_NAME + " (" +
                    StorageContract.TableCurrentGame1._ID + " INTEGER PRIMARY KEY," +
                    StorageContract.TableCurrentGame1.COLUMN_CURRENTGAME + TYPE_TEXT + COMMA_SEP +
                    StorageContract.TableCurrentGame1.COLUMN_CURRENTLIFE + TYPE_TEXT + COMMA_SEP +
                    StorageContract.TableCurrentGame1.COLUMN_GAME_BONUS + TYPE_TEXT + COMMA_SEP +
                    StorageContract.TableCurrentGame1.COLUMN_GAME_CATCHED + TYPE_TEXT + COMMA_SEP +
                    StorageContract.TableCurrentGame1.COLUMN_GAME_RARE + TYPE_TEXT + COMMA_SEP +
                    StorageContract.TableCurrentGame1.COLUMN_GAME_ROUND + TYPE_TEXT + COMMA_SEP +
                    StorageContract.TableCurrentGame1.COLUMN_GAME_TIME + TYPE_TEXT +
                    " );";
    private static final String SQL_DELETE_CURRENTGAME1 =
            "DROP TABLE IF EXISTS " + StorageContract.TableCurrentGame1.TABLE_NAME;

    private static final String SQL_CREATE_TABLEGAMEDATA1 =
            "CREATE TABLE " + StorageContract.TableGameData1.TABLE_NAME + " (" +
                    StorageContract.TableGameData1._ID + " INTEGER PRIMARY KEY," +
                    StorageContract.TableGameData1.COLUMN_DIFFICULTY + TYPE_INT + COMMA_SEP +
                    StorageContract.TableGameData1.COLUMN_FISHES_IN_LAST_GAME + TYPE_TEXT + COMMA_SEP +
                    StorageContract.TableGameData1.COLUMN_GAMES_WITH_ONE_LIVE + TYPE_TEXT + COMMA_SEP +
                    StorageContract.TableGameData1.COLUMN_GAMES_WITHOUT_LIVE_LOST + TYPE_TEXT + COMMA_SEP +
                    StorageContract.TableGameData1.COLUMN_LAST_GAME_FISH + TYPE_TEXT + COMMA_SEP +
                    StorageContract.TableGameData1.COLUMN_LAST_GAME_LIVES_LOST + TYPE_TEXT + COMMA_SEP +
                    StorageContract.TableGameData1.COLUMN_LAST_GAME_RARE_FISH + TYPE_TEXT + COMMA_SEP +
                    StorageContract.TableGameData1.COLUMN_REWARDS + TYPE_TEXT + COMMA_SEP +
                    StorageContract.TableGameData1.COLUMN_TOTAL_FISH + TYPE_TEXT + COMMA_SEP +
                    StorageContract.TableGameData1.COLUMN_TOTAL_GAMES + TYPE_TEXT + COMMA_SEP +
                    StorageContract.TableGameData1.COLUMN_TOTAL_GAMES_LOST + TYPE_TEXT + COMMA_SEP +
                    StorageContract.TableGameData1.COLUMN_TOTAL_LIVES_LOST + TYPE_TEXT + COMMA_SEP +
                    StorageContract.TableGameData1.COLUMN_TOTAL_RARE_FISH + TYPE_TEXT + COMMA_SEP +
                    StorageContract.TableGameData1.COLUMN_TROPHIES + TYPE_TEXT +
                    " );";
    private static final String SQL_DELETE_TABLEGAMEDATA1 =
            "DROP TABLE IF EXISTS " + StorageContract.TableGameData1.TABLE_NAME;

    private static final String SQL_CREATE_CURRENTGAME2 =
            "CREATE TABLE " + StorageContract.TableCurrentGame2.TABLE_NAME + " (" +
                    StorageContract.TableCurrentGame2._ID + " INTEGER PRIMARY KEY," +
                    StorageContract.TableCurrentGame2.COLUMN_CURRENTGAME + TYPE_TEXT + COMMA_SEP +
                    StorageContract.TableCurrentGame2.COLUMN_GAME_BONUS + TYPE_TEXT + COMMA_SEP +
                    StorageContract.TableCurrentGame2.COLUMN_GAME_CATCHED + TYPE_TEXT + COMMA_SEP +
                    StorageContract.TableCurrentGame2.COLUMN_POSITION_X + TYPE_TEXT + COMMA_SEP +
                    StorageContract.TableCurrentGame2.COLUMN_POSITION_Y + TYPE_TEXT + COMMA_SEP +
                    StorageContract.TableCurrentGame2.COLUMN_GAME_RARE + TYPE_TEXT +
                    " );";
    private static final String SQL_DELETE_CURRENTGAME2 =
            "DROP TABLE IF EXISTS " + StorageContract.TableCurrentGame2.TABLE_NAME;

    private static final String SQL_CREATE_TABLEGAMEDATA2 =
            "CREATE TABLE " + StorageContract.TableGameData2.TABLE_NAME + " (" +
                    StorageContract.TableGameData2._ID + " INTEGER PRIMARY KEY," +
                    StorageContract.TableGameData2.COLUMN_DIFFICULTY + TYPE_INT + COMMA_SEP +
                    StorageContract.TableGameData2.COLUMN_FISHES_IN_LAST_GAME + TYPE_TEXT + COMMA_SEP +
                    StorageContract.TableGameData2.COLUMN_LAST_GAME_FISH + TYPE_TEXT + COMMA_SEP +
                    StorageContract.TableGameData2.COLUMN_LAST_GAME_RARE_FISH + TYPE_TEXT + COMMA_SEP +
                    StorageContract.TableGameData2.COLUMN_REWARDS + TYPE_TEXT + COMMA_SEP +
                    StorageContract.TableGameData2.COLUMN_TOTAL_FISH + TYPE_TEXT + COMMA_SEP +
                    StorageContract.TableGameData2.COLUMN_TOTAL_GAMES + TYPE_TEXT + COMMA_SEP +
                    StorageContract.TableGameData2.COLUMN_TOTAL_RARE_FISH + TYPE_TEXT + COMMA_SEP +
                    StorageContract.TableGameData2.COLUMN_TROPHIES + TYPE_TEXT +
                    " );";
    private static final String SQL_DELETE_TABLEGAMEDATA2 =
            "DROP TABLE IF EXISTS " + StorageContract.TableGameData2.TABLE_NAME;


    public DBDriver(String dataBaseName, int dataBaseVersion, Context context) {
        super(dataBaseName, dataBaseVersion, context);
    }

    public static DBDriver getInstance() {
        return (DBDriver) info.simplyapps.appengine.storage.DBDriver.getInstance();
    }

    @Override
    public void createTables(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_INVENTORY);
        db.execSQL(SQL_CREATE_CURRENTGAME1);
        db.execSQL(SQL_CREATE_TABLEGAMEDATA1);
        db.execSQL(SQL_CREATE_CURRENTGAME2);
        db.execSQL(SQL_CREATE_TABLEGAMEDATA2);
    }


    @Override
    public void upgradeTables(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_INVENTORY);
        db.execSQL(SQL_DELETE_CURRENTGAME1);
        db.execSQL(SQL_DELETE_TABLEGAMEDATA1);
        db.execSQL(SQL_DELETE_CURRENTGAME2);
        db.execSQL(SQL_DELETE_TABLEGAMEDATA2);
    }

    @Override
    public String getExtendedTable(BasicTable data) {
        return Inventory.class.isInstance(data) ? StorageContract.TableInventory.TABLE_NAME :
                CurrentGame1.class.isInstance(data) ? StorageContract.TableCurrentGame1.TABLE_NAME :
                        CurrentGame2.class.isInstance(data) ? StorageContract.TableCurrentGame2.TABLE_NAME :
                                GameData1.class.isInstance(data) ? StorageContract.TableGameData1.TABLE_NAME :
                                        GameData2.class.isInstance(data) ? StorageContract.TableGameData2.TABLE_NAME : null;
    }

    @Override
    public void storeExtended(info.simplyapps.appengine.storage.StoreData data) {
        store(StoreDataNew.class.cast(data).inventory);
        store(StoreDataNew.class.cast(data).currentGame1);
        store(StoreDataNew.class.cast(data).currentGame2);
        store(StoreDataNew.class.cast(data).gameData1);
        store(StoreDataNew.class.cast(data).gameData2);
    }

    @Override
    public void readExtended(info.simplyapps.appengine.storage.StoreData data, SQLiteDatabase db) {
        readInventory(StoreDataNew.class.cast(data), db);
        readCurrentGame1(StoreDataNew.class.cast(data), db);
        readCurrentGame2(StoreDataNew.class.cast(data), db);
        readGameData1(StoreDataNew.class.cast(data), db);
        readGameData2(StoreDataNew.class.cast(data), db);
    }

    @Override
    public info.simplyapps.appengine.storage.StoreData createStoreData() {
        return new StoreDataNew();
    }


    public boolean store(Inventory data) {
        ContentValues values = new ContentValues();
        values.put(StorageContract.TableInventory.COLUMN_CATMODE, data.catMode);
        values.put(StorageContract.TableInventory.COLUMN_GAMESYSTEM, data.gameSystem);
        values.put(StorageContract.TableInventory.COLUMN_MIGRATED, data.migrated);
        return persist(data, values, StorageContract.TableInventory.TABLE_NAME);
    }

    private void readInventory(StoreDataNew data, SQLiteDatabase db) {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                StorageContract.TableInventory._ID,
                StorageContract.TableInventory.COLUMN_CATMODE,
                StorageContract.TableInventory.COLUMN_GAMESYSTEM,
                StorageContract.TableInventory.COLUMN_MIGRATED
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = null;
        String selection = null;
        String[] selectionArgs = null;
        Cursor c = db.query(
                StorageContract.TableInventory.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        boolean hasResults = c.moveToFirst();
        while (hasResults) {
            Inventory i = new Inventory();
            i.id = c.getLong(c.getColumnIndexOrThrow(StorageContract.TableInventory._ID));
            i.gameSystem = c.getInt(c.getColumnIndexOrThrow(StorageContract.TableInventory.COLUMN_GAMESYSTEM));
            i.catMode = c.getInt(c.getColumnIndexOrThrow(StorageContract.TableInventory.COLUMN_CATMODE)) == 1;
            i.migrated = c.getInt(c.getColumnIndexOrThrow(StorageContract.TableInventory.COLUMN_MIGRATED)) == 1;
            data.inventory = i;
            hasResults = c.moveToNext();
        }
        c.close();
    }

    public boolean store(CurrentGame1 data) {
        ContentValues values = new ContentValues();
        values.put(StorageContract.TableCurrentGame1.COLUMN_CURRENTGAME, intToString(data.currentGame));
        values.put(StorageContract.TableCurrentGame1.COLUMN_CURRENTLIFE, intToString(data.currentLives));
        values.put(StorageContract.TableCurrentGame1.COLUMN_GAME_BONUS, data.bonus);
        values.put(StorageContract.TableCurrentGame1.COLUMN_GAME_CATCHED, intToString(data.gameCatched));
        values.put(StorageContract.TableCurrentGame1.COLUMN_GAME_RARE, intToString(data.gameRare));
        values.put(StorageContract.TableCurrentGame1.COLUMN_GAME_ROUND, intToString(data.gameRound));
        values.put(StorageContract.TableCurrentGame1.COLUMN_GAME_TIME, longToString(data.gameTime));
        return persist(data, values, StorageContract.TableCurrentGame1.TABLE_NAME);
    }

    private void readCurrentGame1(StoreDataNew data, SQLiteDatabase db) {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                StorageContract.TableCurrentGame1._ID,
                StorageContract.TableCurrentGame1.COLUMN_CURRENTGAME,
                StorageContract.TableCurrentGame1.COLUMN_CURRENTLIFE,
                StorageContract.TableCurrentGame1.COLUMN_GAME_BONUS,
                StorageContract.TableCurrentGame1.COLUMN_GAME_CATCHED,
                StorageContract.TableCurrentGame1.COLUMN_GAME_RARE,
                StorageContract.TableCurrentGame1.COLUMN_GAME_ROUND,
                StorageContract.TableCurrentGame1.COLUMN_GAME_TIME
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = null;
        String selection = null;
        String[] selectionArgs = null;
        Cursor c = db.query(
                StorageContract.TableCurrentGame1.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        boolean hasResults = c.moveToFirst();
        while (hasResults) {
            CurrentGame1 i = new CurrentGame1();
            i.id = c.getLong(c.getColumnIndexOrThrow(StorageContract.TableCurrentGame1._ID));
            i.bonus = c.getInt(c.getColumnIndexOrThrow(StorageContract.TableCurrentGame1.COLUMN_GAME_BONUS)) == 1;
            i.currentGame = stringToInt(c.getString(c.getColumnIndexOrThrow(StorageContract.TableCurrentGame1.COLUMN_CURRENTGAME)));
            i.currentLives = stringToInt(c.getString(c.getColumnIndexOrThrow(StorageContract.TableCurrentGame1.COLUMN_CURRENTLIFE)));
            i.gameCatched = stringToIntInt(c.getString(c.getColumnIndexOrThrow(StorageContract.TableCurrentGame1.COLUMN_GAME_CATCHED)));
            i.gameRare = stringToIntInt(c.getString(c.getColumnIndexOrThrow(StorageContract.TableCurrentGame1.COLUMN_GAME_RARE)));
            i.gameRound = stringToIntInt(c.getString(c.getColumnIndexOrThrow(StorageContract.TableCurrentGame1.COLUMN_GAME_ROUND)));
            i.gameTime = stringToLongLong(c.getString(c.getColumnIndexOrThrow(StorageContract.TableCurrentGame1.COLUMN_GAME_TIME)));
            data.currentGame1 = i;
            hasResults = c.moveToNext();
        }
        c.close();
    }


    public boolean store(CurrentGame2 data) {
        ContentValues values = new ContentValues();
        values.put(StorageContract.TableCurrentGame2.COLUMN_CURRENTGAME, intToString(data.currentGame));
        values.put(StorageContract.TableCurrentGame2.COLUMN_POSITION_X, intToString(data.positionX));
        values.put(StorageContract.TableCurrentGame2.COLUMN_POSITION_Y, intToString(data.positionY));
        values.put(StorageContract.TableCurrentGame2.COLUMN_GAME_BONUS, data.bonus);
        values.put(StorageContract.TableCurrentGame2.COLUMN_GAME_CATCHED, intToString(data.gameCatched));
        values.put(StorageContract.TableCurrentGame2.COLUMN_GAME_RARE, intToString(data.gameRare));
        return persist(data, values, StorageContract.TableCurrentGame2.TABLE_NAME);
    }

    private void readCurrentGame2(StoreDataNew data, SQLiteDatabase db) {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                StorageContract.TableCurrentGame2._ID,
                StorageContract.TableCurrentGame2.COLUMN_CURRENTGAME,
                StorageContract.TableCurrentGame2.COLUMN_GAME_BONUS,
                StorageContract.TableCurrentGame2.COLUMN_GAME_CATCHED,
                StorageContract.TableCurrentGame2.COLUMN_GAME_RARE,
                StorageContract.TableCurrentGame2.COLUMN_POSITION_X,
                StorageContract.TableCurrentGame2.COLUMN_POSITION_Y
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = null;
        String selection = null;
        String[] selectionArgs = null;
        Cursor c = db.query(
                StorageContract.TableCurrentGame2.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        boolean hasResults = c.moveToFirst();
        while (hasResults) {
            CurrentGame2 i = new CurrentGame2();
            i.id = c.getLong(c.getColumnIndexOrThrow(StorageContract.TableCurrentGame2._ID));
            i.bonus = c.getInt(c.getColumnIndexOrThrow(StorageContract.TableCurrentGame2.COLUMN_GAME_BONUS)) == 2;
            i.currentGame = stringToInt(c.getString(c.getColumnIndexOrThrow(StorageContract.TableCurrentGame2.COLUMN_CURRENTGAME)));
            i.gameCatched = stringToInt(c.getString(c.getColumnIndexOrThrow(StorageContract.TableCurrentGame2.COLUMN_GAME_CATCHED)));
            i.gameRare = stringToInt(c.getString(c.getColumnIndexOrThrow(StorageContract.TableCurrentGame2.COLUMN_GAME_RARE)));
            i.positionX = stringToInt(c.getString(c.getColumnIndexOrThrow(StorageContract.TableCurrentGame2.COLUMN_POSITION_X)));
            i.positionY = stringToInt(c.getString(c.getColumnIndexOrThrow(StorageContract.TableCurrentGame2.COLUMN_POSITION_Y)));
            data.currentGame2 = i;
            hasResults = c.moveToNext();
        }
        c.close();
    }


    public boolean store(GameData1 data) {
        ContentValues values = new ContentValues();
        values.put(StorageContract.TableGameData1.COLUMN_DIFFICULTY, data.difficulty);
        values.put(StorageContract.TableGameData1.COLUMN_FISHES_IN_LAST_GAME, intToString(data.fishesInLastGame));
        values.put(StorageContract.TableGameData1.COLUMN_GAMES_WITH_ONE_LIVE, intToString(data.gamesWithOneLive));
        values.put(StorageContract.TableGameData1.COLUMN_GAMES_WITHOUT_LIVE_LOST, intToString(data.gamesWithoutLivesLost));
        values.put(StorageContract.TableGameData1.COLUMN_LAST_GAME_FISH, intToString(data.lastGameFish));
        values.put(StorageContract.TableGameData1.COLUMN_LAST_GAME_LIVES_LOST, intToString(data.lastGameLivesLost));
        values.put(StorageContract.TableGameData1.COLUMN_LAST_GAME_RARE_FISH, intToString(data.lastGameRareFish));
        values.put(StorageContract.TableGameData1.COLUMN_REWARDS, boolToString(data.rewards));
        values.put(StorageContract.TableGameData1.COLUMN_TOTAL_FISH, intToString(data.totalFish));
        values.put(StorageContract.TableGameData1.COLUMN_TOTAL_GAMES, intToString(data.totalGames));
        values.put(StorageContract.TableGameData1.COLUMN_TOTAL_GAMES_LOST, intToString(data.totalGamesLost));
        values.put(StorageContract.TableGameData1.COLUMN_TOTAL_LIVES_LOST, intToString(data.totalLivesLost));
        values.put(StorageContract.TableGameData1.COLUMN_TOTAL_RARE_FISH, intToString(data.totalRareFish));
        values.put(StorageContract.TableGameData1.COLUMN_TROPHIES, boolToString(data.trophies));
        return persist(data, values, StorageContract.TableGameData1.TABLE_NAME);
    }

    private void readGameData1(StoreDataNew data, SQLiteDatabase db) {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                StorageContract.TableGameData1._ID,
                StorageContract.TableGameData1.COLUMN_DIFFICULTY,
                StorageContract.TableGameData1.COLUMN_FISHES_IN_LAST_GAME,
                StorageContract.TableGameData1.COLUMN_GAMES_WITH_ONE_LIVE,
                StorageContract.TableGameData1.COLUMN_GAMES_WITHOUT_LIVE_LOST,
                StorageContract.TableGameData1.COLUMN_LAST_GAME_FISH,
                StorageContract.TableGameData1.COLUMN_LAST_GAME_LIVES_LOST,
                StorageContract.TableGameData1.COLUMN_LAST_GAME_RARE_FISH,
                StorageContract.TableGameData1.COLUMN_REWARDS,
                StorageContract.TableGameData1.COLUMN_TOTAL_FISH,
                StorageContract.TableGameData1.COLUMN_TOTAL_GAMES,
                StorageContract.TableGameData1.COLUMN_TOTAL_GAMES_LOST,
                StorageContract.TableGameData1.COLUMN_TOTAL_LIVES_LOST,
                StorageContract.TableGameData1.COLUMN_TOTAL_RARE_FISH,
                StorageContract.TableGameData1.COLUMN_TROPHIES
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = null;
        String selection = null;
        String[] selectionArgs = null;
        Cursor c = db.query(
                StorageContract.TableGameData1.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        boolean hasResults = c.moveToFirst();
        while (hasResults) {
            GameData1 i = new GameData1();
            i.id = c.getLong(c.getColumnIndexOrThrow(StorageContract.TableGameData1._ID));
            i.difficulty = c.getInt(c.getColumnIndexOrThrow(StorageContract.TableGameData1.COLUMN_DIFFICULTY));
            i.fishesInLastGame = stringToInt(c.getString(c.getColumnIndexOrThrow(StorageContract.TableGameData1.COLUMN_FISHES_IN_LAST_GAME)));
            i.gamesWithOneLive = stringToInt(c.getString(c.getColumnIndexOrThrow(StorageContract.TableGameData1.COLUMN_GAMES_WITH_ONE_LIVE)));
            i.gamesWithoutLivesLost = stringToInt(c.getString(c.getColumnIndexOrThrow(StorageContract.TableGameData1.COLUMN_GAMES_WITHOUT_LIVE_LOST)));
            i.lastGameFish = stringToInt(c.getString(c.getColumnIndexOrThrow(StorageContract.TableGameData1.COLUMN_LAST_GAME_FISH)));
            i.lastGameLivesLost = stringToInt(c.getString(c.getColumnIndexOrThrow(StorageContract.TableGameData1.COLUMN_LAST_GAME_LIVES_LOST)));
            i.lastGameRareFish = stringToInt(c.getString(c.getColumnIndexOrThrow(StorageContract.TableGameData1.COLUMN_LAST_GAME_RARE_FISH)));
            i.rewards = stringToBool(c.getString(c.getColumnIndexOrThrow(StorageContract.TableGameData1.COLUMN_REWARDS)));
            i.totalFish = stringToInt(c.getString(c.getColumnIndexOrThrow(StorageContract.TableGameData1.COLUMN_TOTAL_FISH)));
            i.totalGames = stringToInt(c.getString(c.getColumnIndexOrThrow(StorageContract.TableGameData1.COLUMN_TOTAL_GAMES)));
            i.totalGamesLost = stringToInt(c.getString(c.getColumnIndexOrThrow(StorageContract.TableGameData1.COLUMN_TOTAL_GAMES_LOST)));
            i.totalLivesLost = stringToInt(c.getString(c.getColumnIndexOrThrow(StorageContract.TableGameData1.COLUMN_TOTAL_LIVES_LOST)));
            i.totalRareFish = stringToInt(c.getString(c.getColumnIndexOrThrow(StorageContract.TableGameData1.COLUMN_TOTAL_RARE_FISH)));
            i.trophies = stringToBool(c.getString(c.getColumnIndexOrThrow(StorageContract.TableGameData1.COLUMN_TROPHIES)));
            data.gameData1 = i;
            hasResults = c.moveToNext();
        }
        c.close();
    }


    public boolean store(GameData2 data) {
        ContentValues values = new ContentValues();
        values.put(StorageContract.TableGameData2.COLUMN_DIFFICULTY, data.difficulty);
        values.put(StorageContract.TableGameData2.COLUMN_FISHES_IN_LAST_GAME, intToString(data.fishesInLastGame));
        values.put(StorageContract.TableGameData2.COLUMN_LAST_GAME_FISH, intToString(data.lastGameFish));
        values.put(StorageContract.TableGameData2.COLUMN_LAST_GAME_RARE_FISH, intToString(data.lastGameRareFish));
        values.put(StorageContract.TableGameData2.COLUMN_REWARDS, boolToString(data.rewards));
        values.put(StorageContract.TableGameData2.COLUMN_TOTAL_FISH, intToString(data.totalFish));
        values.put(StorageContract.TableGameData2.COLUMN_TOTAL_GAMES, intToString(data.totalGames));
        values.put(StorageContract.TableGameData2.COLUMN_TOTAL_RARE_FISH, intToString(data.totalRareFish));
        values.put(StorageContract.TableGameData2.COLUMN_TROPHIES, boolToString(data.trophies));
        return persist(data, values, StorageContract.TableGameData2.TABLE_NAME);
    }

    private void readGameData2(StoreDataNew data, SQLiteDatabase db) {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                StorageContract.TableGameData2._ID,
                StorageContract.TableGameData2.COLUMN_DIFFICULTY,
                StorageContract.TableGameData2.COLUMN_FISHES_IN_LAST_GAME,
                StorageContract.TableGameData2.COLUMN_LAST_GAME_FISH,
                StorageContract.TableGameData2.COLUMN_LAST_GAME_RARE_FISH,
                StorageContract.TableGameData2.COLUMN_REWARDS,
                StorageContract.TableGameData2.COLUMN_TOTAL_FISH,
                StorageContract.TableGameData2.COLUMN_TOTAL_GAMES,
                StorageContract.TableGameData2.COLUMN_TOTAL_RARE_FISH,
                StorageContract.TableGameData2.COLUMN_TROPHIES
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = null;
        String selection = null;
        String[] selectionArgs = null;
        Cursor c = db.query(
                StorageContract.TableGameData2.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        boolean hasResults = c.moveToFirst();
        while (hasResults) {
            GameData2 i = new GameData2();
            i.id = c.getLong(c.getColumnIndexOrThrow(StorageContract.TableGameData2._ID));
            i.difficulty = c.getInt(c.getColumnIndexOrThrow(StorageContract.TableGameData2.COLUMN_DIFFICULTY));
            i.fishesInLastGame = stringToInt(c.getString(c.getColumnIndexOrThrow(StorageContract.TableGameData2.COLUMN_FISHES_IN_LAST_GAME)));
            i.lastGameFish = stringToInt(c.getString(c.getColumnIndexOrThrow(StorageContract.TableGameData2.COLUMN_LAST_GAME_FISH)));
            i.lastGameRareFish = stringToInt(c.getString(c.getColumnIndexOrThrow(StorageContract.TableGameData2.COLUMN_LAST_GAME_RARE_FISH)));
            i.rewards = stringToBool(c.getString(c.getColumnIndexOrThrow(StorageContract.TableGameData2.COLUMN_REWARDS)));
            i.totalFish = stringToInt(c.getString(c.getColumnIndexOrThrow(StorageContract.TableGameData2.COLUMN_TOTAL_FISH)));
            i.totalGames = stringToInt(c.getString(c.getColumnIndexOrThrow(StorageContract.TableGameData2.COLUMN_TOTAL_GAMES)));
            i.totalRareFish = stringToInt(c.getString(c.getColumnIndexOrThrow(StorageContract.TableGameData2.COLUMN_TOTAL_RARE_FISH)));
            i.trophies = stringToBool(c.getString(c.getColumnIndexOrThrow(StorageContract.TableGameData2.COLUMN_TROPHIES)));
            data.gameData2 = i;
            hasResults = c.moveToNext();
        }
        c.close();
    }

}
