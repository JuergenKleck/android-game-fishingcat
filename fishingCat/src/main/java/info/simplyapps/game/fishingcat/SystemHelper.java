package info.simplyapps.game.fishingcat;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import info.simplyapps.game.fishingcat.R;
import info.simplyapps.game.fishingcat.engine.GameValues;
import info.simplyapps.game.fishingcat.screens.HomeScreen;
import info.simplyapps.game.fishingcat.storage.StoreDataNew;


/**
 * 
 * @author simplyapps.info
 */
public final class SystemHelper extends info.simplyapps.appengine.SystemHelper {

    public static int getTrophy(int i, boolean text, boolean logo) {
        if(GameValues.GAMESYSTEM_ISLAND == StoreDataNew.getInstance().inventory.gameSystem) {
            return m2Trophy(i, text, logo);
        } else {
            return m1Trophy(i, text, logo);
        }
    }
    
    private static int m1Trophy(int i, boolean text, boolean logo) {
        switch (i) {
        case 0:
            // move than 500 fish catched
            if(text) return R.string.trophy_0;
            if(logo) return 0;
            return 0;
        case 9:
            // move than 5000 fish catched
            if(text) return R.string.trophy_9;
            if(logo) return 0;
            return 1;
        case 18:
            // move than 50000 fish catched
            if(text) return R.string.trophy_18;
            if(logo) return 0;
            return 2;
        case 1:
            // move than 50 rare fish catched
            if(text) return R.string.trophy_1;
            if(logo) return 0;
            return 0;
        case 10:
            // move than 500 rare fish catched
            if(text) return R.string.trophy_10;
            if(logo) return 0;
            return 1;
        case 19:
            // move than 5000 rare fish catched
            if(text) return R.string.trophy_19;
            if(logo) return 0;
            return 2;
        case 2:
            // no lives lost in 3 games in a row
            if(text) return R.string.trophy_2;
            if(logo) return 1;
            return 0;
        case 11:
            // no lives lost in 6 games in a row
            if(text) return R.string.trophy_11;
            if(logo) return 1;
            return 1;
        case 20:
            // no lives lost in 9 games in a row
            if(text) return R.string.trophy_20;
            if(logo) return 1;
            return 2;
        case 3:
            // more fish in every round in one game
            if(text) return R.string.trophy_3;
            if(logo) return 0;
            return 0;
        case 12:
            // 10% more fish in every round in one game
            if(text) return R.string.trophy_12;
            if(logo) return 0;
            return 1;
        case 21:
            // 25% more fish in every round in one game
            if(text) return R.string.trophy_21;
            if(logo) return 0;
            return 2;
        case 4:
            // more fishes from game 1 to game 2
            if(text) return R.string.trophy_4;
            if(logo) return 0;
            return 0;
        case 13:
            // 10% more fishes from game 1 to game 2
            if(text) return R.string.trophy_13;
            if(logo) return 0;
            return 1;
        case 22:
            // 25% more fishes from game 1 to game 2
            if(text) return R.string.trophy_22;
            if(logo) return 0;
            return 2;
        case 5:
            // survived one game with 1 live
            if(text) return R.string.trophy_5;
            if(logo) return 1;
            return 0;
        case 14:
            // survived two games in a row with 1 live
            if(text) return R.string.trophy_14;
            if(logo) return 1;
            return 1;
        case 23:
            // survived three games in a row with 1 live
            if(text) return R.string.trophy_23;
            if(logo) return 1;
            return 2;
        case 6:
            // catched 1000 fish in one game
            if(text) return R.string.trophy_6;
            if(logo) return 0;
            return 0;
        case 15:
            // catched 2000 fish in one game
            if(text) return R.string.trophy_15;
            if(logo) return 0;
            return 1;
        case 24:
            // catched 3000 fish in one game
            if(text) return R.string.trophy_24;
            if(logo) return 0;
            return 2;
        case 7:
            // catched 10 rare fish in one game
            if(text) return R.string.trophy_7;
            if(logo) return 0;
            return 0;
        case 16:
            // catched 20 rare fish in one game
            if(text) return R.string.trophy_16;
            if(logo) return 0;
            return 1;
        case 25:
            // catched 30 rare fish in one game
            if(text) return R.string.trophy_25;
            if(logo) return 0;
            return 2;
        case 8:
            // all bronze trophies
            if(text) return R.string.trophy_8;
            if(logo) return 2;
            return 0;
        case 17:
            // all silver trophies
            if(text) return R.string.trophy_17;
            if(logo) return 2;
            return 1;
        case 26:
            // all gold trophies
            if(text) return R.string.trophy_26;
            if(logo) return 2;
            return 2;
        default:
            return -1;
        }
    }

    private static int m2Trophy(int i, boolean text, boolean logo) {
        switch (i) {
        case 0:
            // move than 500 fish catched
            if(text) return R.string.m2trophy_0;
            if(logo) return 0;
            return 0;
        case 6:
            // move than 5000 fish catched
            if(text) return R.string.m2trophy_6;
            if(logo) return 0;
            return 1;
        case 12:
            // move than 50000 fish catched
            if(text) return R.string.m2trophy_12;
            if(logo) return 0;
            return 2;
        case 1:
            // move than 50 rare fish catched
            if(text) return R.string.m2trophy_1;
            if(logo) return 0;
            return 0;
        case 7:
            // move than 500 rare fish catched
            if(text) return R.string.m2trophy_7;
            if(logo) return 0;
            return 1;
        case 13:
            // move than 5000 rare fish catched
            if(text) return R.string.m2trophy_13;
            if(logo) return 0;
            return 2;
        case 2:
            // more fishes from game 1 to game 2
            if(text) return R.string.m2trophy_2;
            if(logo) return 0;
            return 0;
        case 8:
            // 10% more fishes from game 1 to game 2
            if(text) return R.string.m2trophy_8;
            if(logo) return 0;
            return 1;
        case 14:
            // 25% more fishes from game 1 to game 2
            if(text) return R.string.m2trophy_14;
            if(logo) return 0;
            return 2;
        case 3:
            // catched 1000 fish in one game
            if(text) return R.string.m2trophy_3;
            if(logo) return 0;
            return 0;
        case 9:
            // catched 2000 fish in one game
            if(text) return R.string.m2trophy_9;
            if(logo) return 0;
            return 1;
        case 15:
            // catched 3000 fish in one game
            if(text) return R.string.m2trophy_15;
            if(logo) return 0;
            return 2;
        case 4:
            // catched 10 rare fish in one game
            if(text) return R.string.m2trophy_4;
            if(logo) return 0;
            return 0;
        case 10:
            // catched 20 rare fish in one game
            if(text) return R.string.m2trophy_10;
            if(logo) return 0;
            return 1;
        case 16:
            // catched 30 rare fish in one game
            if(text) return R.string.m2trophy_16;
            if(logo) return 0;
            return 2;
        case 5:
            // all bronze trophies
            if(text) return R.string.m2trophy_5;
            if(logo) return 2;
            return 0;
        case 11:
            // all silver trophies
            if(text) return R.string.m2trophy_11;
            if(logo) return 2;
            return 1;
        case 17:
            // all gold trophies
            if(text) return R.string.m2trophy_17;
            if(logo) return 2;
            return 2;
        default:
            return -1;
        }
    }
    
    
}
