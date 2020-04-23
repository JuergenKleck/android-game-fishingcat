package info.simplyapps.game.fishingcat.sprites;

import android.graphics.Paint;
import android.graphics.Rect;

import info.simplyapps.gameengine.rendering.objects.Animation;
import info.simplyapps.gameengine.rendering.objects.Graphic;
import info.simplyapps.gameengine.sprites.ViewSprites;

public class HomeViewSprites implements ViewSprites {
    // TODO extract generic elements into ViewSprites and transform to abstract class
    // main
    public Graphic gBackground;
    public Graphic gLogo;
    public Paint pBackground;
    public Rect rBackground;
    
    // generic buttons
    public Graphic gButton;
    public Graphic gButtonOverlay;

    // main menu
    public Rect rBtnStart;
    public Rect rBtnSettings;
    public Rect rBtnBack;
    public Rect rBtnSwitch;
    public Rect rBtnQuit;
    public Rect rBtnStartTournament;
    public Rect rBtnStartSingle;

    // menu screen
    public Rect rMsgMenu;
    public Rect rBtnOptions;
    public Rect rBtnStatistics;
    public Rect rBtnTrophies;
    public Rect rBtnRewards;

    // statistic screen
    public Rect rMsgGameTotal;
    public Rect rMsgTotalFish;
    public Rect rValueTotalFish;
    public Rect rMsgTotalRareFish;
    public Rect rValueTotalRareFish;
    public Rect rMsgTotalGames;
    public Rect rValueTotalGames;
    public Rect rMsgTotalLivesLost;
    public Rect rValueTotalLivesLost;
    public Rect rMsgGameLast;
    public Rect rMsgLastGameFish;
    public Rect rValueLastGameFish;
    public Rect rMsgLastGameRareFish;
    public Rect rValueLastGameRareFish;
    public Rect rMsgLastGameLivesLost;
    public Rect rValueLastGameLivesLost;
    
    // option screen
    public Rect rMsgOptions;
    public Rect rMsgMusic;
    public Rect rBtnMusic;
    public Rect rMsgVibration;
    public Rect rBtnVibration;
    public Rect rMsgDifficulty;
    public Rect rBtnDifficultyEasy;
    public Rect rBtnDifficultyMedium;
    public Rect rBtnDifficultyHard;
    public Rect rBtnResetGame;
    public Rect rMsgCatMode;
    public Rect rBtnCatMode;
    
    // trophies screen
    public Rect rMsgTrophies;
    public Rect rBtnTrophiesBronze;
    public Rect rBtnTrophiesSilver;
    public Rect rBtnTrophiesGold;
    public Rect[] rMsgTrophyNumber;
    public Graphic[] mTrophiesLogo;
    public Graphic[] mTrophiesCategory;
    public Graphic gNotYet;
    public Rect rBtnShowHelp;
    
    // rewards screen
    public Rect rMsgRewards;
    public Rect rMsgRewardBronze;
    public Rect rMsgRewardSilver;
    public Rect rMsgRewardGold;
    public Graphic gRewardBronzeCategory;
    public Graphic gRewardSilverCategory;
    public Graphic gRewardGoldCategory;
    public Graphic gRewardBronzeLogo;
    public Graphic gRewardSilverLogo;
    public Graphic gRewardGoldLogo;

    // loading screen
    public Rect rMessage;
    
    // mode 1
    public Graphic gSeashell;
	public Graphic gSeagrass;
	public Graphic[] gSeacrab;
	public Graphic[] gSeagrass2;
	public Graphic[] gBubbles;
	public Animation aSeacrab;
	public Animation aSeagrass2;
	public Animation aBubbles;
	
	// mode 2
	public Graphic[] gCat;
	public Animation aCat;
	
    @Override
    public void init() {
    }
    @Override
    public void clean() {
    }
	
}
