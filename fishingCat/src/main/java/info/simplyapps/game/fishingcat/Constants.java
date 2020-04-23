package info.simplyapps.game.fishingcat;

public class Constants {

	public static final String GOOGLE_PLAY_URL = "https://play.google.com/store/apps/details?id=";
    public static final String UPDATE_CHECK_URL = "http://game.simplyapps.info/fishingcat.xml";

    public static final String DATABASE = "fishingcat.db";
    public static final int DATABASE_VERSION = 1;

    public static final String PACKAGE_NAME = "info.simplyapps.game.fishingcat";
    public static final String PREFERENCE_NS = "http://info.simplyapps.game.fishingcat.rendering";

    public static final String CONFIG_VIBRATION = "vibration";
    public static final String CONFIG_MUSIC = "music";
    public static final String DEFAULT_CONFIG_VIBRATION = Boolean.FALSE.toString();
    public static final String DEFAULT_CONFIG_MUSIC = Boolean.FALSE.toString();
    
    public static final String EXTENSION_ITEM_LIFE = "life"; 
    public static final String EXTENSION_ITEM_TIME = "time"; 

    public static final int EXTENSION_NUMBER_LIFE = 0; 
    public static final int EXTENSION_NUMBER_TIME = 1; 

    public static final int ACTION_STATISTIC = 100;
    public static final int ACTION_REWARDS = 101;
    public static final int ACTION_TROPHIES = 102;
    public static final int ACTION_SETTINGS = 104;
    public static final int ACTION_GAMEMODE_1 = 200;
    public static final int ACTION_GAMEMODE_2 = 201;
    public static final int ACTION_RESUME = 202;
    public static final int ACTION_START_SINGLE = 203;
    public static final int ACTION_START_TOURNAMENT = 204;
    public static final int ACTION_HOME = 300;
    public static final int ACTION_SWITCH = 302;

    public static final int spaceLR = 10;
    public static final int spaceTB = 8;
    public static final int spaceTBWide = 48;

    public static final float CHAR_SPACING = 0.35f;
    
    public enum RenderMode {
        HOME
        ,GAME1
        ,GAME2
        ,MENU
        ,WAIT
        ;
    }
    
    public enum SubRenderMode {
        SELECT
        ,BRONZE
        ,SILVER
        ,GOLD
        ,SCORE
        ,OPTIONS
        ,TROPHIES
        ,REWARDS
        ,PURCHASE
        ,NONE
        ;
    }
    
    public static final int[] m1HelpIds = {
        R.string.help_trophies_long0
        ,R.string.help_trophies_long1
        ,R.string.help_trophies_long2
        ,R.string.help_trophies_long3
        ,R.string.help_trophies_long4
        ,R.string.help_trophies_long5
        ,R.string.help_trophies_long6
        ,R.string.help_trophies_long7
    };
    
    public static final int[] m2HelpIds = {
        R.string.m2help_trophies_long0
        ,R.string.m2help_trophies_long1
        ,R.string.m2help_trophies_long2
        ,R.string.m2help_trophies_long3
        ,R.string.m2help_trophies_long4
    };
    
}
