package info.simplyapps.game.fishingcat.storage;

import info.simplyapps.appengine.AppEngineConstants;
import info.simplyapps.appengine.storage.dto.Configuration;
import info.simplyapps.game.fishingcat.SystemHelper;
import info.simplyapps.game.fishingcat.storage.dto.CurrentGame1;
import info.simplyapps.game.fishingcat.storage.dto.CurrentGame2;
import info.simplyapps.game.fishingcat.storage.dto.GameData1;
import info.simplyapps.game.fishingcat.storage.dto.GameData2;
import info.simplyapps.game.fishingcat.storage.dto.Inventory;

public class StoreDataNew extends info.simplyapps.appengine.storage.StoreData {

    public Inventory inventory;
    public CurrentGame1 currentGame1;
    public GameData1 gameData1;
    public CurrentGame2 currentGame2;
    public GameData2 gameData2;

    public StoreDataNew() {
        super();
        if (inventory == null) {
            inventory = new Inventory();
        }
        initGameMode1();
        initGameMode2();
    }

    /**
     *
     */
    private static final long serialVersionUID = 2982830586304674266L;

    public static StoreDataNew getInstance() {
        return (StoreDataNew) info.simplyapps.appengine.storage.StoreData.getInstance();
    }

    @Override
    public boolean update() {
        boolean persist = false;

        // For initial data creation
        if (migration < 1) {
            persist = true;
        }

        // Release 21 - 3.3.0
        if (migration < 21) {
            persist = true;
            inventory.catMode = false;
        }

        // Release 22 - 3.3.1
        if (migration < 22) {
            persist = true;
        }

        // Release 23 - 3.3.2
        if (migration < 23) {
            SystemHelper.setConfiguration(new Configuration(AppEngineConstants.CONFIG_ON_SERVER, AppEngineConstants.DEFAULT_CONFIG_ON_SERVER));
            SystemHelper.setConfiguration(new Configuration(AppEngineConstants.CONFIG_FORCE_UPDATE, AppEngineConstants.DEFAULT_CONFIG_FORCE_UPDATE));
            SystemHelper.setConfiguration(new Configuration(AppEngineConstants.CONFIG_LAST_CHECK, AppEngineConstants.DEFAULT_CONFIG_LAST_CHECK));
            persist = true;
        }

        // Release 24 - 3.3.3
        if (migration < 24) {
            persist = true;
        }

        // Release 25 - 3.4.0
        if (migration < 25) {
            persist = true;
        }

        migration = 25;
        return persist;
    }

    public void initGameMode1() {
        if (gameData1 == null) {
            gameData1 = new GameData1();
        }
        gameData1.selfcheck();
        // dynamic game data
        if (currentGame1 == null) {
            currentGame1 = new CurrentGame1();
        }
        currentGame1.selfcheck();
    }

    public void initGameMode2() {
        if (gameData2 == null) {
            gameData2 = new GameData2();
        }
        gameData2.selfcheck();
        // dynamic game data
        if (currentGame2 == null) {
            currentGame2 = new CurrentGame2();
        }
        currentGame2.selfcheck();
    }

}
