package com.juergenkleck.android.game.fishingcat.screens;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;

import java.util.Properties;

import com.juergenkleck.android.appengine.PermissionHelper;
import com.juergenkleck.android.appengine.screens.IPermissionHandler;
import com.juergenkleck.android.appengine.storage.dto.Configuration;
import com.juergenkleck.android.game.fishingcat.Constants;
import com.juergenkleck.android.game.fishingcat.Constants.RenderMode;
import com.juergenkleck.android.game.fishingcat.R;
import com.juergenkleck.android.game.fishingcat.SystemHelper;
import com.juergenkleck.android.game.fishingcat.engine.GameValues;
import com.juergenkleck.android.game.fishingcat.rendering.GameModeClassicSingle;
import com.juergenkleck.android.game.fishingcat.rendering.GameModeClassicTournament;
import com.juergenkleck.android.game.fishingcat.rendering.GameModeIsland;
import com.juergenkleck.android.game.fishingcat.rendering.Home1Renderer;
import com.juergenkleck.android.game.fishingcat.rendering.Home2Renderer;
import com.juergenkleck.android.game.fishingcat.rendering.LoadingRenderer;
import com.juergenkleck.android.game.fishingcat.rendering.MenuRenderer;
import com.juergenkleck.android.game.fishingcat.rendering.OptionRenderer;
import com.juergenkleck.android.game.fishingcat.rendering.RewardsRenderer;
import com.juergenkleck.android.game.fishingcat.rendering.StatisticRenderer;
import com.juergenkleck.android.game.fishingcat.rendering.TrophiesRenderer;
import com.juergenkleck.android.game.fishingcat.storage.DBDriver;
import com.juergenkleck.android.game.fishingcat.storage.StorageUtil;
import com.juergenkleck.android.game.fishingcat.storage.StoreDataNew;
import com.juergenkleck.android.gameengine.EngineConstants;
import com.juergenkleck.android.gameengine.RenderingSystem;
import com.juergenkleck.android.gameengine.engine.GameEngine;
import com.juergenkleck.android.gameengine.screens.HomeScreenTemplate;

/**
 * Android app - FishingCat
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class HomeScreen extends HomeScreenTemplate {

    // Application Data
    public static String ICICLE_KEY = "fishingcat-view";

    public static boolean mGameModeContinue = false;

    protected PermissionHelper permissionHelper = new PermissionHelper();

    RenderMode mLastRenderMode;

    MediaPlayer mPlayer;
    int mLastPlayed;

    public synchronized void prepareStorage(Context context) {
        StorageUtil.prepareStorage(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // super create must be the first call for android > 4.0
        super.onCreate(savedInstanceState);

        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mLastRenderMode != null) {
            mLastRenderMode = null;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // just have the View's thread save its state into our Bundle
        super.onSaveInstanceState(outState);
        getScreenView().getBasicEngine().saveState(outState);
    }

    public void actionRewards() {
        getScreenView().changeEngine(new RewardsRenderer(getScreenView().getContext(), getEngineProperties()));
    }

    @Override
    public void actionNewGame() {
        if (Home1Renderer.class.isInstance(mScreenView.getBasicEngine())) {
            Home1Renderer.class.cast(mScreenView.getBasicEngine()).setSubRenderMode(Constants.SubRenderMode.SELECT);
        }
        if (Home2Renderer.class.isInstance(mScreenView.getBasicEngine())) {
            actionGameMode2();
            activateGame();
        }
    }

    @Override
    public void actionContinueGame() {
        activateGame();
    }

    @Override
    public void actionOptions() {
        getScreenView().changeEngine(new MenuRenderer(getScreenView().getContext(), getEngineProperties()));
    }

    @Override
    public void actionQuit() {
        finish();
    }

    @Override
    public void actionAdditionalAction(int action) {
        switch (action) {
            case Constants.ACTION_HOME:
                actionHome();
                break;
            case Constants.ACTION_STATISTIC:
                actionStatistic();
                break;
            case Constants.ACTION_REWARDS:
                actionRewards();
                break;
            case Constants.ACTION_TROPHIES:
                actionTrophies();
                break;
            case Constants.ACTION_GAMEMODE_1:
                actionGameMode1();
                break;
            case Constants.ACTION_GAMEMODE_2:
                actionGameMode2();
                break;
            case Constants.ACTION_SWITCH:
                actionSwitch();
                break;
            case Constants.ACTION_SETTINGS:
                actionSettings();
                break;
            case Constants.ACTION_RESUME:
                actionResume();
                break;
            case Constants.ACTION_START_SINGLE:
                actionGameClassicSingle();
                break;
            case Constants.ACTION_START_TOURNAMENT:
                actionGameClassicTournament();
                break;
        }
    }

    public void actionSettings() {
        getScreenView().changeEngine(new OptionRenderer(getScreenView().getContext(), getEngineProperties()));
    }

    public void actionStatistic() {
        getScreenView().changeEngine(new StatisticRenderer(getScreenView().getContext(), getEngineProperties()));
    }

    public void actionTrophies() {
        getScreenView().changeEngine(new TrophiesRenderer(getScreenView().getContext(), getEngineProperties()));
    }

    public void actionGameMode1() {
        StoreDataNew.getInstance().inventory.gameSystem = GameValues.GAMESYSTEM_CLASSIC;
    }

    public void actionGameMode2() {
        StoreDataNew.getInstance().inventory.gameSystem = GameValues.GAMESYSTEM_ISLAND;
    }

    public void actionGameClassicSingle() {
        StoreDataNew.getInstance().inventory.gameSystem = GameValues.GAMESYSTEM_CLASSIC_SINGLE;
        activateGame();
    }

    public void actionGameClassicTournament() {
        StoreDataNew.getInstance().inventory.gameSystem = GameValues.GAMESYSTEM_CLASSIC;
        activateGame();
    }

    public void actionSwitch() {
        if (GameValues.GAMESYSTEM_ISLAND == StoreDataNew.getInstance().inventory.gameSystem) {
            StoreDataNew.getInstance().inventory.gameSystem = GameValues.GAMESYSTEM_CLASSIC;
        } else {
            StoreDataNew.getInstance().inventory.gameSystem = GameValues.GAMESYSTEM_ISLAND;
        }
        // persist
        DBDriver.getInstance().store(StoreDataNew.getInstance().inventory);
        actionHome();
    }

    public void actionHome() {
        if (getGameEngine() != null) {
            getScreenView().getBasicEngine().saveGameState();
            getScreenView().changeEngine(new LoadingRenderer(getScreenView().getContext(), getEngineProperties()));
        }

        if (GameValues.GAMESYSTEM_ISLAND == StoreDataNew.getInstance().inventory.gameSystem) {
            getScreenView().changeEngine(new Home2Renderer(getScreenView().getContext(), getEngineProperties()));
        } else {
            getScreenView().changeEngine(new Home1Renderer(getScreenView().getContext(), getEngineProperties()));
        }
    }

    public void actionResume() {
        if (mLastRenderMode != null && RenderMode.GAME1.equals(mLastRenderMode)) {
            mLastRenderMode = null;
            if (GameValues.GAMESYSTEM_ISLAND == StoreDataNew.getInstance().inventory.gameSystem) {
                getScreenView().changeEngine(new GameModeIsland(getScreenView().getContext(), getEngineProperties()));
            } else {
                getScreenView().changeEngine(new GameModeClassicTournament(getScreenView().getContext(), getEngineProperties()));
            }
            getScreenView().getBasicEngine().unpause();
        } else {
            actionOptions();
        }
    }

    @Override
    public String getViewKey() {
        return ICICLE_KEY;
    }

    @Override
    public int getScreenLayout() {
        return R.layout.homescreen;
    }

    @Override
    public int getViewLayoutId() {
        return R.id.homeview;
    }

    @Override
    public void doUpdateChecks() {
    }

    @Override
    public void activateGame() {
        mGameModeContinue = true;

        // ONE GAME SCREEN
        getScreenView().changeEngine(new LoadingRenderer(getScreenView().getContext(), getEngineProperties()));

        // install renderer
        if (GameValues.GAMESYSTEM_ISLAND == StoreDataNew.getInstance().inventory.gameSystem) {
            getScreenView().changeEngine(new GameModeIsland(getScreenView().getContext(), getEngineProperties()));
        } else if (GameValues.GAMESYSTEM_CLASSIC_SINGLE == StoreDataNew.getInstance().inventory.gameSystem) {
            getScreenView().changeEngine(new GameModeClassicSingle(getScreenView().getContext(), getEngineProperties()));
        } else {
            getScreenView().changeEngine(new GameModeClassicTournament(getScreenView().getContext(), getEngineProperties()));
        }

        // start renderer
        getScreenView().getBasicEngine().doStart();
    }

    /**
     * Get the game engine instance
     *
     * @return
     */
    public GameEngine getGameEngine() {
        return GameEngine.class.isInstance(mScreenView.getBasicEngine()) ? GameEngine.class.cast(mScreenView.getBasicEngine()) : null;
    }

    public void assignMediaPlayer(Context context, int id) {
        Configuration c = SystemHelper.getConfiguration(Constants.CONFIG_MUSIC, Constants.DEFAULT_CONFIG_MUSIC);
        if (mLastPlayed != id && Boolean.valueOf(c.value).booleanValue()) {
            if (mPlayer != null) {
                mPlayer.release();
                mPlayer = null;
            }
            mLastPlayed = id;
            mPlayer = MediaPlayer.create(context, id);
            getMediaPlayer().setLooping(true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    public synchronized MediaPlayer getMediaPlayer() {
        return mPlayer;
    }

    @Override
    public Properties getEngineProperties() {
        Properties p = new Properties();
        p.put(EngineConstants.GameProperties.RENDERING_SYSTEM, RenderingSystem.DEFAULT);
        p.put(EngineConstants.GameProperties.SCREEN_SCALE, 0);
        p.put(EngineConstants.GameProperties.LEVEL, 0);
        p.put(EngineConstants.GameProperties.SPACE_LR, Constants.spaceLR);
        p.put(EngineConstants.GameProperties.SPACE_TB, Constants.spaceTB);
        return p;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (getScreenView().getBasicEngine() instanceof IPermissionHandler) {
            permissionHelper.onRequestPermissionsResult((IPermissionHandler) getScreenView().getBasicEngine(), requestCode, permissions, grantResults);
        }
    }

    public PermissionHelper getPermissionHelper() {
        return permissionHelper;
    }
}