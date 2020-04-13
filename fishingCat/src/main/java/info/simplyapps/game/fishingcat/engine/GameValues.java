package info.simplyapps.game.fishingcat.engine;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;

import info.simplyapps.game.fishingcat.Constants;
import info.simplyapps.game.fishingcat.R;
import info.simplyapps.game.fishingcat.sprites.Fish;

public class GameValues extends info.simplyapps.gameengine.system.GameValues {

	public static final long EXPANSION_TIME_ADD = 300000l;
	public static final int EXPANSION_LIFE_ADD = 9;

	// TODO add extensions after completing a certtain amount of games
	public static final int EXTENSION_LIFE_AMOUNT = 3;
	public static final int EXTENSION_TIME_AMOUNT = 3;

    public static final String[] extensionName = {
        Constants.EXTENSION_ITEM_LIFE
        ,Constants.EXTENSION_ITEM_TIME
    };
    
    public static ColorFilter cFilterBlue = new LightingColorFilter(Color.parseColor("#4dcaff"), 255);
    public static ColorFilter cFilterRed = new LightingColorFilter(Color.parseColor("#fe5858"), 1);
    public static ColorFilter cFilterGreen = new LightingColorFilter(Color.parseColor("#67fe44"), 1);
    public static ColorFilter cFilterYellow = new LightingColorFilter(Color.parseColor("#fdff63"), 1);
    public static ColorFilter cFilterOrange = new LightingColorFilter(Color.parseColor("#ffa735"), 1);
    public static ColorFilter cFilterDarkRed = new LightingColorFilter(Color.parseColor("#cb2525"), 1);

    public static final ColorFilter cFilterGold = new LightingColorFilter(Color.parseColor("#e5e723"), 1);
    public static final ColorFilter cFilterSilver = new LightingColorFilter(Color.parseColor("#dedede"), 1);
    public static final ColorFilter cFilterBronze = new LightingColorFilter(Color.parseColor("#b77b13"), 1);
    public static final ColorFilter cFilterInactive = new LightingColorFilter(Color.parseColor("#999999"), 1);
    
	public static final int GAMEMODE_EASY = 0;
	public static final int GAMEMODE_MEDIUM = 1;
	public static final int GAMEMODE_HARD = 2;
	
	public static final int GAMESYSTEM_CLASSIC = 0;
	public static final int GAMESYSTEM_ISLAND = 1;
	public static final int GAMESYSTEM_CLASSIC_SINGLE = 2;

	// Game mode 1
	public static final long m1clickDelay = 500l;
	public static final long m1catEyeDelay = 5000l;
	public static final long m1catPawDelay = 13000l;
	public static final long m1liveLostDelay = 2000l;
	public static final long m1catHideDelay = 3000l;
	
	public static final int m1fishMovement = 5;
	public static final int m1totalLives = 9;
	public static final int m1totalRounds = 9;
	public static final int m1fishesPerRound[] = { 5, 5, 6, 6, 7, 7, 8, 9, 10 };
    public static final int m1maxFishesAtOnce = 3;
    public static final long m1fishCreateTimeout = 100l;
	
	public static final float m1speedIncreasePerRound[][] = { 
		{ 0.0f, 0.0f, 0.1f, 0.2f, 0.3f, 0.3f, 0.4f, 0.4f, 0.5f }
		,{ 0.0f, 0.0f, 0.1f, 0.2f, 0.3f, 0.3f, 0.4f, 0.4f, 0.5f }
		,{ 0.0f, 0.0f, 0.1f, 0.2f, 0.3f, 0.3f, 0.4f, 0.4f, 0.5f }
	};
    public static final int m1timePerRound[][] = {
        { 300000, 300000, 300000, 240000, 240000, 240000, 180000, 180000, 180000 }
        ,{ 240000, 240000, 240000, 180000, 180000, 180000, 120000, 120000, 120000 }
        ,{ 180000, 180000, 180000, 120000, 120000, 120000, 90000, 90000, 90000 }
    };
	public static final float m1chanceRareFishPerRound[][] = {
		{ 0.09f, 0.11f, 0.13f, 0.15f, 0.17f, 0.19f, 0.21f, 0.23f, 0.25f }
		,{ 0.06f, 0.08f, 0.10f, 0.12f, 0.14f, 0.16f, 0.18f, 0.20f, 0.22f }
		,{ 0.04f, 0.06f, 0.08f, 0.10f, 0.12f, 0.14f, 0.16f, 0.18f, 0.20f }
	};
	public static final float m1chanceSharkPerRound[][] = {
		{ 0.00f, 0.01f, 0.02f, 0.03f, 0.04f, 0.05f, 0.06f, 0.07f, 0.08f }
		,{ 0.01f, 0.02f, 0.03f, 0.04f, 0.05f, 0.06f, 0.07f, 0.08f, 0.09f }
		,{ 0.02f, 0.03f, 0.04f, 0.05f, 0.06f, 0.07f, 0.08f, 0.09f, 0.10f }
	};
	public static final int m1rewardBronzeSharkImmunity[] = { 3,4,5 };
	public static final float m1rewardSilverMoreTime[] = { 0.10f, 0.15f, 0.20f };
	public static final float m1rewardGoldSlowerFishes[] = { 0.10f, 0.15f, 0.20f };

	
	// Game mode 2
	public static final float m2rewardBronzeLongerFloat[] = { 0.25f, 0.5f, 0.75f };
	public static final float m2rewardSilverMoreFishes[] = { 1.5f, 2.0f, 2.5f };
	public static final float m2rewardGoldSlowerSpeed[] = { 0.10f, 0.15f, 0.20f };

	public static final int m2fishesPerRound[] = { 10, 9, 8 };
	public static final float m2chanceRareFish[] = { 0.40f, 0.35f, 0.30f };
	
	public static final int m2fishMovement = 7;
	public static final long m2catFloatDelay = 1500l;
	public static final long m2catNoFloatDelay = 500l;
	public static final long m2catFPS = 50l;
	public static final int m2catMovement[] = { 8, 10, 12 };
	public static final int m2floatReduction = 5;
	public static final int m2bonusReduction = 5;
	public static final int m2catMinMovement = 2;
	public static final int m2catMinMovementBonus = 1;
	public static final int m2dragSpeed = 20;
	public static final int m2screenMovementPerSecond = 5;
	public static final int m2maxFishesAtOnce = 2;
    public static final long m2fishCreateTimeout = 200l;
	
	// Global information
	public static final long gameRoundDelay = 2500l;
	public static final long gamePauseDelay = 1000l;
	
	public static final int highestAnimationSpeed = 300;
	public static final int lowestAnimationSpeed = 100;
	public static final int rareAnimationSpeed = 300;
	
	public static final int smallFishMinSpeed = 5;
	public static final int smallFishMaxSpeed = 30;
	public static final int mediumFishMinSpeed = 25;
	public static final int mediumFishMaxSpeed = 50;
	public static final int largeFishMinSpeed = 50;
	public static final int largeFishMaxSpeed = 75;
	public static final int bigFishMinSpeed = 75;
	public static final int bigFixMaxSpeed = 100;
	public static final int sharkMinSpeed = 5;
	public static final int sharkMaxSpeed = 10;
	
	public static final int[][][] fishGroups = {
		{ // normal
			{R.drawable.fish1_m0_ltr,R.drawable.fish1_m1_ltr,R.drawable.fish1_m0_ltr,R.drawable.fish1_m2_ltr,R.drawable.fish1_m0_rtl,R.drawable.fish1_m1_rtl,R.drawable.fish1_m0_rtl,R.drawable.fish1_m2_rtl}
			,{R.drawable.fish2_m0_ltr,R.drawable.fish2_m1_ltr,R.drawable.fish2_m0_ltr,R.drawable.fish2_m2_ltr,R.drawable.fish2_m0_rtl,R.drawable.fish2_m1_rtl,R.drawable.fish2_m0_rtl,R.drawable.fish2_m2_rtl}
			,{R.drawable.fish3_m0_ltr,R.drawable.fish3_m1_ltr,R.drawable.fish3_m0_ltr,R.drawable.fish3_m2_ltr,R.drawable.fish3_m0_rtl,R.drawable.fish3_m1_rtl,R.drawable.fish3_m0_rtl,R.drawable.fish3_m2_rtl}
			,{R.drawable.fish4_m0_ltr,R.drawable.fish4_m1_ltr,R.drawable.fish4_m0_ltr,R.drawable.fish4_m2_ltr,R.drawable.fish4_m0_rtl,R.drawable.fish4_m1_rtl,R.drawable.fish4_m0_rtl,R.drawable.fish4_m2_rtl}
			,{R.drawable.fish5_m0_ltr,R.drawable.fish5_m1_ltr,R.drawable.fish5_m0_ltr,R.drawable.fish5_m2_ltr,R.drawable.fish5_m0_rtl,R.drawable.fish5_m1_rtl,R.drawable.fish5_m0_rtl,R.drawable.fish5_m2_rtl}
			,{R.drawable.fish6_m0_ltr,R.drawable.fish6_m1_ltr,R.drawable.fish6_m0_ltr,R.drawable.fish6_m2_ltr,R.drawable.fish6_m0_rtl,R.drawable.fish6_m1_rtl,R.drawable.fish6_m0_rtl,R.drawable.fish6_m2_rtl}
			,{R.drawable.fish7_m0_ltr,R.drawable.fish7_m1_ltr,R.drawable.fish7_m0_ltr,R.drawable.fish7_m2_ltr,R.drawable.fish7_m0_rtl,R.drawable.fish7_m1_rtl,R.drawable.fish7_m0_rtl,R.drawable.fish7_m2_rtl}
			,{R.drawable.fish8_m0_ltr,R.drawable.fish8_m1_ltr,R.drawable.fish8_m0_ltr,R.drawable.fish8_m2_ltr,R.drawable.fish8_m0_rtl,R.drawable.fish8_m1_rtl,R.drawable.fish8_m0_rtl,R.drawable.fish8_m2_rtl}
			,{R.drawable.fish9_m0_ltr,R.drawable.fish9_m1_ltr,R.drawable.fish9_m0_ltr,R.drawable.fish9_m2_ltr,R.drawable.fish9_m0_rtl,R.drawable.fish9_m1_rtl,R.drawable.fish9_m0_rtl,R.drawable.fish9_m2_rtl}
			,{R.drawable.fish10_m0_ltr,R.drawable.fish10_m1_ltr,R.drawable.fish10_m0_ltr,R.drawable.fish10_m2_ltr,R.drawable.fish10_m0_rtl,R.drawable.fish10_m1_rtl,R.drawable.fish10_m0_rtl,R.drawable.fish10_m2_rtl}
			,{R.drawable.fish11_m0_ltr,R.drawable.fish11_m1_ltr,R.drawable.fish11_m0_ltr,R.drawable.fish11_m2_ltr,R.drawable.fish11_m0_rtl,R.drawable.fish11_m1_rtl,R.drawable.fish11_m0_rtl,R.drawable.fish11_m2_rtl}
			,{R.drawable.fish12_m0_ltr,R.drawable.fish12_m1_ltr,R.drawable.fish12_m0_ltr,R.drawable.fish12_m2_ltr,R.drawable.fish12_m0_rtl,R.drawable.fish12_m1_rtl,R.drawable.fish12_m0_rtl,R.drawable.fish12_m2_rtl}
			,{R.drawable.fish13_m0_ltr,R.drawable.fish13_m1_ltr,R.drawable.fish13_m0_ltr,R.drawable.fish13_m2_ltr,R.drawable.fish13_m0_rtl,R.drawable.fish13_m1_rtl,R.drawable.fish13_m0_rtl,R.drawable.fish13_m2_rtl}
			,{R.drawable.fish14_m0_ltr,R.drawable.fish14_m1_ltr,R.drawable.fish14_m0_ltr,R.drawable.fish14_m2_ltr,R.drawable.fish14_m0_rtl,R.drawable.fish14_m1_rtl,R.drawable.fish14_m0_rtl,R.drawable.fish14_m2_rtl}
			,{R.drawable.fish15_m0_ltr,R.drawable.fish15_m1_ltr,R.drawable.fish15_m0_ltr,R.drawable.fish15_m2_ltr,R.drawable.fish15_m0_rtl,R.drawable.fish15_m1_rtl,R.drawable.fish15_m0_rtl,R.drawable.fish15_m2_rtl}
			,{R.drawable.fish16_m0_ltr,R.drawable.fish16_m1_ltr,R.drawable.fish16_m0_ltr,R.drawable.fish16_m2_ltr,R.drawable.fish16_m0_rtl,R.drawable.fish16_m1_rtl,R.drawable.fish16_m0_rtl,R.drawable.fish16_m2_rtl}
			,{R.drawable.fish17_m0_ltr,R.drawable.fish17_m1_ltr,R.drawable.fish17_m0_ltr,R.drawable.fish17_m2_ltr,R.drawable.fish17_m0_rtl,R.drawable.fish17_m1_rtl,R.drawable.fish17_m0_rtl,R.drawable.fish17_m2_rtl}
			,{R.drawable.fish18_m0_ltr,R.drawable.fish18_m1_ltr,R.drawable.fish18_m0_ltr,R.drawable.fish18_m2_ltr,R.drawable.fish18_m0_rtl,R.drawable.fish18_m1_rtl,R.drawable.fish18_m0_rtl,R.drawable.fish18_m2_rtl}
			,{R.drawable.fish19_m0_ltr,R.drawable.fish19_m1_ltr,R.drawable.fish19_m0_ltr,R.drawable.fish19_m2_ltr,R.drawable.fish19_m0_rtl,R.drawable.fish19_m1_rtl,R.drawable.fish19_m0_rtl,R.drawable.fish19_m2_rtl}
			,{R.drawable.fish20_m0_ltr,R.drawable.fish20_m1_ltr,R.drawable.fish20_m0_ltr,R.drawable.fish20_m2_ltr,R.drawable.fish20_m0_rtl,R.drawable.fish20_m1_rtl,R.drawable.fish20_m0_rtl,R.drawable.fish20_m2_rtl}
		}
		,{ // rare
			{R.drawable.rare1_m0_ltr,R.drawable.rare1_m1_ltr,R.drawable.rare1_m0_rtl,R.drawable.rare1_m1_rtl}
			,{R.drawable.rare2_m0_ltr,R.drawable.rare2_m1_ltr,R.drawable.rare2_m0_rtl,R.drawable.rare2_m1_rtl}
			,{R.drawable.rare3_m0,R.drawable.rare3_m0,R.drawable.rare3_m0,R.drawable.rare3_m0}
		}
		,{ // shark
			{R.drawable.shark_m0_ltr,R.drawable.shark_m0_ltr,R.drawable.shark_m0_rtl,R.drawable.shark_m0_rtl}
		}
	};

	public static final Fish[] m1fishes = {
		 new Fish(mediumFishMinSpeed,mediumFishMaxSpeed,0.15f,0, false, false)	
		,new Fish(smallFishMinSpeed,smallFishMaxSpeed,0.12f,1, false, false)	
		,new Fish(mediumFishMinSpeed,mediumFishMaxSpeed,0.15f,2, false, false)	
		,new Fish(largeFishMinSpeed,largeFishMaxSpeed,0.15f,3, false, false)	
		,new Fish(mediumFishMinSpeed,mediumFishMaxSpeed,0.15f,4, false, false)	
		,new Fish(smallFishMinSpeed,smallFishMaxSpeed,0.15f,5, false, false)	
		,new Fish(largeFishMinSpeed,largeFishMaxSpeed,0.15f,6, false, false)	
		,new Fish(mediumFishMinSpeed,mediumFishMaxSpeed,0.15f,7, false, false)	
		,new Fish(largeFishMinSpeed,largeFishMaxSpeed,0.12f,8, false, false)	
		,new Fish(smallFishMinSpeed,smallFishMaxSpeed,0.15f,9, false, false)	
		,new Fish(largeFishMinSpeed,largeFishMaxSpeed,0.15f,10, false, false)	
		,new Fish(bigFishMinSpeed,bigFixMaxSpeed,0.12f,11, false, false)	
		,new Fish(smallFishMinSpeed,smallFishMaxSpeed,0.15f,12, false, false)	
		,new Fish(mediumFishMinSpeed,mediumFishMaxSpeed,0.15f,13, false, false)	
		,new Fish(bigFishMinSpeed,bigFixMaxSpeed,0.15f,14, false, false)	
		,new Fish(largeFishMinSpeed,largeFishMaxSpeed,0.15f,15, false, false)	
		,new Fish(smallFishMinSpeed,smallFishMaxSpeed,0.15f,1, false, false)	
		,new Fish(smallFishMinSpeed,smallFishMaxSpeed,0.15f,17, false, false)	
		,new Fish(bigFishMinSpeed,bigFixMaxSpeed,0.15f,18, false, false)	
		,new Fish(mediumFishMinSpeed,mediumFishMaxSpeed,0.15f,19, false, false)	
	};
	
	public static final Fish[] m1rareFishes = {
		new Fish(smallFishMinSpeed,smallFishMaxSpeed,0.20f,0, false, true)
		,new Fish(smallFishMinSpeed,smallFishMaxSpeed,0.10f,1, false, true)
		,new Fish(smallFishMinSpeed,smallFishMaxSpeed,0.05f,2, false, true)
	};

	public static final Fish[] m1shark = {
		new Fish(sharkMinSpeed,sharkMaxSpeed,0.40f,0, true, false)
	};

	public static final Fish[] m2fishes = {
	    new Fish(mediumFishMinSpeed,mediumFishMaxSpeed,0.12f,0, false, false)  
	    ,new Fish(smallFishMinSpeed,smallFishMaxSpeed,0.10f,1, false, false)    
	    ,new Fish(mediumFishMinSpeed,mediumFishMaxSpeed,0.12f,2, false, false)  
	    ,new Fish(largeFishMinSpeed,largeFishMaxSpeed,0.12f,3, false, false)    
	    ,new Fish(mediumFishMinSpeed,mediumFishMaxSpeed,0.12f,4, false, false)  
	    ,new Fish(smallFishMinSpeed,smallFishMaxSpeed,0.12f,5, false, false)    
	    ,new Fish(largeFishMinSpeed,largeFishMaxSpeed,0.12f,6, false, false)    
	    ,new Fish(mediumFishMinSpeed,mediumFishMaxSpeed,0.12f,7, false, false)  
	    ,new Fish(largeFishMinSpeed,largeFishMaxSpeed,0.10f,8, false, false)    
	    ,new Fish(smallFishMinSpeed,smallFishMaxSpeed,0.12f,9, false, false)    
	    ,new Fish(largeFishMinSpeed,largeFishMaxSpeed,0.12f,10, false, false)   
	    ,new Fish(bigFishMinSpeed,bigFixMaxSpeed,0.10f,11, false, false)    
	    ,new Fish(smallFishMinSpeed,smallFishMaxSpeed,0.12f,12, false, false)   
	    ,new Fish(mediumFishMinSpeed,mediumFishMaxSpeed,0.12f,13, false, false) 
	    ,new Fish(bigFishMinSpeed,bigFixMaxSpeed,0.12f,14, false, false)    
	    ,new Fish(largeFishMinSpeed,largeFishMaxSpeed,0.12f,15, false, false)   
	    ,new Fish(smallFishMinSpeed,smallFishMaxSpeed,0.12f,1, false, false)    
	    ,new Fish(smallFishMinSpeed,smallFishMaxSpeed,0.12f,17, false, false)   
	    ,new Fish(bigFishMinSpeed,bigFixMaxSpeed,0.12f,18, false, false)    
	    ,new Fish(mediumFishMinSpeed,mediumFishMaxSpeed,0.12f,19, false, false) 
	};
	
	public static final Fish[] m2rareFishes = {
	    new Fish(smallFishMinSpeed,smallFishMaxSpeed,0.18f,0, false, true)
	    ,new Fish(smallFishMinSpeed,smallFishMaxSpeed,0.08f,1, false, true)
	    ,new Fish(smallFishMinSpeed,smallFishMaxSpeed,0.04f,2, false, true)
	};
	
	public static final Fish[] m2shark = {
	    new Fish(sharkMinSpeed,sharkMaxSpeed,0.60f,0, true, false)
	};

}

