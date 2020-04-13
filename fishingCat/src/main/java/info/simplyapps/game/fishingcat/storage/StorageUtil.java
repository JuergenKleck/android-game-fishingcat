package info.simplyapps.game.fishingcat.storage;

import android.content.Context;
import android.widget.Toast;

import info.simplyapps.appengine.AppEngineConstants;
import info.simplyapps.appengine.R;
import info.simplyapps.appengine.storage.DBDriver;
import info.simplyapps.appengine.storage.dto.Configuration;
import info.simplyapps.game.fishingcat.Constants;
import info.simplyapps.game.fishingcat.SystemHelper;
import info.simplyapps.gameengine.EngineConstants;

public final class StorageUtil {

    public synchronized static void prepareStorage(Context context) {
        if(DBDriver.getInstance() == null) {
            DBDriver.createInstance(new info.simplyapps.game.fishingcat.storage.DBDriver(Constants.DATABASE, Constants.DATABASE_VERSION, context));
        }
        
        // try to load
        if(StoreDataNew.getInstance() == null) {
            StoreDataNew.createInstance(DBDriver.getInstance().read());
        }
        // create
        if(StoreDataNew.getInstance() == null) {
            StoreDataNew.createInstance(new StoreDataNew());
        }
        // migration
        Configuration cMig = SystemHelper.getConfiguration(AppEngineConstants.CONFIG_MIGRATION, AppEngineConstants.DEFAULT_CONFIG_MIGRATION);
        if(!SystemHelper.hasConfiguration(AppEngineConstants.CONFIG_MIGRATION)) {
            // will store automatically if migration is lower than this value
            StoreDataNew.getInstance().configuration.add(cMig);
        }
        StoreDataNew.getInstance().migration = Integer.valueOf(cMig.value);
        
        // update
        if(StoreDataNew.getInstance().update()) {
            // store back the migration value
            Configuration c = SystemHelper.getConfiguration(AppEngineConstants.CONFIG_MIGRATION, AppEngineConstants.DEFAULT_CONFIG_MIGRATION);
            c.value = Integer.toString(StoreDataNew.getInstance().migration);
            DBDriver.getInstance().write(StoreDataNew.getInstance());
            Toast.makeText(context, R.string.update_complete, Toast.LENGTH_LONG).show();
        }
        if(!SystemHelper.hasConfiguration(EngineConstants.CONFIG_MUSIC)) {
            Configuration c = new Configuration(EngineConstants.CONFIG_MUSIC, EngineConstants.DEFAULT_CONFIG_MUSIC);
            if(DBDriver.getInstance().store(c)) {
                StoreDataNew.getInstance().configuration.add(c);
            }
        }
        if(!SystemHelper.hasConfiguration(EngineConstants.CONFIG_DIFFICULTY)) {
            Configuration c = new Configuration(EngineConstants.CONFIG_DIFFICULTY, EngineConstants.DEFAULT_CONFIG_DIFFICULTY);
            if(DBDriver.getInstance().store(c)) {
                StoreDataNew.getInstance().configuration.add(c);
            }
        }
        if(!SystemHelper.hasConfiguration(AppEngineConstants.CONFIG_FORCE_UPDATE)) {
            Configuration c = new Configuration(AppEngineConstants.CONFIG_FORCE_UPDATE, AppEngineConstants.DEFAULT_CONFIG_FORCE_UPDATE);
            if(DBDriver.getInstance().store(c)) {
                StoreDataNew.getInstance().configuration.add(c);
            }
        }
        if(!SystemHelper.hasConfiguration(AppEngineConstants.CONFIG_LAST_CHECK)) {
            Configuration c = new Configuration(AppEngineConstants.CONFIG_LAST_CHECK, AppEngineConstants.DEFAULT_CONFIG_LAST_CHECK);
            if(DBDriver.getInstance().store(c)) {
                StoreDataNew.getInstance().configuration.add(c);
            }
        }
        if(!SystemHelper.hasConfiguration(AppEngineConstants.CONFIG_ON_SERVER)) {
            Configuration c = new Configuration(AppEngineConstants.CONFIG_ON_SERVER, AppEngineConstants.DEFAULT_CONFIG_ON_SERVER);
            if(DBDriver.getInstance().store(c)) {
                StoreDataNew.getInstance().configuration.add(c);
            }
        }
        // store additional data

    }
    
}
