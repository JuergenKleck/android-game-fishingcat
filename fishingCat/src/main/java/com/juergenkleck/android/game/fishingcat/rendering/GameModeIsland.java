package com.juergenkleck.android.game.fishingcat.rendering;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import com.juergenkleck.android.game.fishingcat.Constants;
import com.juergenkleck.android.game.fishingcat.R;
import com.juergenkleck.android.game.fishingcat.engine.GameHelper;
import com.juergenkleck.android.game.fishingcat.engine.GameValues;
import com.juergenkleck.android.game.fishingcat.rendering.kits.AnimationKit;
import com.juergenkleck.android.game.fishingcat.rendering.kits.Renderkit;
import com.juergenkleck.android.game.fishingcat.rendering.objects.FishAnimation;
import com.juergenkleck.android.game.fishingcat.sprites.GameViewSpritesMode2;
import com.juergenkleck.android.game.fishingcat.storage.DBDriver;
import com.juergenkleck.android.game.fishingcat.storage.StoreDataNew;
import com.juergenkleck.android.game.fishingcat.system.Game2;
import com.juergenkleck.android.gameengine.EngineConstants;
import com.juergenkleck.android.gameengine.engine.GameEngine;
import com.juergenkleck.android.gameengine.rendering.kits.ScreenKit;
import com.juergenkleck.android.gameengine.rendering.kits.ScreenKit.ScreenPosition;
import com.juergenkleck.android.gameengine.rendering.objects.Animation;
import com.juergenkleck.android.gameengine.rendering.objects.Graphic;
import com.juergenkleck.android.gameengine.system.BasicGame;
import com.juergenkleck.android.gameengine.system.GameState;
import com.juergenkleck.android.gameengine.system.GameSubState;

/**
 * Android app - FishingCat
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class GameModeIsland extends FishRendererTemplate implements GameEngine {

    /**
     * The state of the game. One of READY, RUNNING, PAUSE, LOSE, or WIN
     */
    private GameState mMode;
    private GameSubState mSubMode;

    private static Random RND = new Random();

    final float standardNumberWidth = 0.05f;
    final float standardNumberHeight = 1.20f;

    private Game2 mGame;

    private boolean hasRare;
    private boolean purchase;
    private long delay = 0l;
    private long lastTime;
    boolean dragging;

    private long catFloat;
    private long catNoFloat;

    int difficulty;

    int catWidth;
    int catHeight;

    int hasSilverReward;
    int hasBronzeReward;
    int hasGoldReward;

    int maxFishes;
    boolean bonus;
    long catFPS = 0l;

    private int bubbleBoundaryTop;
    private long bubbleTimeout;

    int gameScreen;
    int screenPos;
    long createTimeout;

    public GameModeIsland(Context context, Properties p) {
        super(context, p);
    }

    public GameViewSpritesMode2 getSprites() {
        return GameViewSpritesMode2.class.cast(super.sprites);
    }

    private void createGame() {
        synchronized (StoreDataNew.getInstance()) {
            hasBronzeReward = -1;
            hasSilverReward = -1;
            hasGoldReward = -1;
            difficulty = StoreDataNew.getInstance().gameData2.difficulty;
            if (GameHelper.hasBronze()) {
                if (StoreDataNew.getInstance().gameData2.rewards[GameValues.GAMEMODE_EASY][0]) {
                    hasBronzeReward += 1;
                }
                if (StoreDataNew.getInstance().gameData2.rewards[GameValues.GAMEMODE_MEDIUM][0]) {
                    hasBronzeReward += 1;
                }
                if (StoreDataNew.getInstance().gameData2.rewards[GameValues.GAMEMODE_HARD][0]) {
                    hasBronzeReward += 1;
                }
            }
            if (GameHelper.hasSilver()) {
                if (StoreDataNew.getInstance().gameData2.rewards[GameValues.GAMEMODE_EASY][1]) {
                    hasSilverReward += 1;
                }
                if (StoreDataNew.getInstance().gameData2.rewards[GameValues.GAMEMODE_MEDIUM][1]) {
                    hasSilverReward += 1;
                }
                if (StoreDataNew.getInstance().gameData2.rewards[GameValues.GAMEMODE_HARD][1]) {
                    hasSilverReward += 1;
                }
            }
            if (GameHelper.hasGold()) {
                if (StoreDataNew.getInstance().gameData2.rewards[GameValues.GAMEMODE_EASY][2]) {
                    hasGoldReward += 1;
                }
                if (StoreDataNew.getInstance().gameData2.rewards[GameValues.GAMEMODE_MEDIUM][2]) {
                    hasGoldReward += 1;
                }
                if (StoreDataNew.getInstance().gameData2.rewards[GameValues.GAMEMODE_HARD][2]) {
                    hasGoldReward += 1;
                }
            }
        }
        mGame = new Game2();
        delay = GameValues.gameRoundDelay;

        maxFishes = GameValues.m2fishesPerRound[difficulty];
        if (hasSilverReward >= 0) {
            maxFishes = Float.valueOf(GameValues.m2fishesPerRound[difficulty] * GameValues.m2rewardSilverMoreFishes[hasSilverReward]).intValue();
        }
    }

    private void initCatPhysics() {

        // set cat
        catWidth = Float.valueOf(screenWidth * 0.22f).intValue();
        catHeight = catWidth * (getSprites().gCat[0].image.getBounds().bottom - getSprites().gCat[0].image.getBounds().top) / getSprites().gCat[0].image.getBounds().right;

        final int screenHeightCatStart = scaleHeight(100);
        final int screenWidthCatStart = screenWidth / 2 - catWidth / 4;

        getSprites().gCat[0].image.setBounds(screenWidthCatStart, screenHeightCatStart, screenWidthCatStart + catWidth, screenHeightCatStart + catHeight);

        getSprites().aCat.rect = getSprites().gCat[0].image.copyBounds();
        getSprites().aCat.coord.x = getSprites().gCat[0].image.getBounds().left;
        getSprites().aCat.coord.y = getSprites().gCat[0].image.getBounds().top;
    }

    private void clearFishGraphic(FishAnimation fa) {
        // check all fishgraphics in use
        boolean inUse = false;
        for (FishAnimation fa1 : getSprites().aFish) {
            if (fa1.fishNumber == fa.fishNumber && fa.rareFish && fa1.rareFish && fa.sharkFish && fa1.sharkFish) {
                inUse = true;
            }
        }

        if (!inUse) {
            getSprites().fishGraphics.remove(getFishKey(fa));
        }
    }

    private String getFishKey(FishAnimation fa) {
        int n = fa.fishNumber;
        if (fa.rareFish) {
            n += 100;
        }
        if (fa.sharkFish) {
            n += 1000;
        }
        if (fa.ltr) {
            n += 10000;
        }
        return Integer.toString(n);
    }

    private void createFishGraphic(FishAnimation fa) {
        if (!getSprites().fishGraphics.containsKey(getFishKey(fa))) {
            int drawFish = fa.sharkFish ? 2 : fa.rareFish ? 1 : 0;
            int maxJ = drawFish == 0 ? 8 : 4;
            Graphic[] g = new Graphic[maxJ / 2];
            // load fish graphics into memory
            int i = fa.fishNumber;
//			for(int s=0,i=0; i<GameValues.fishGroups[drawFish].length; i++) {
            // determine width of fish to automatically scale all fish
            final int fishWidth = Float.valueOf(screenWidth * (drawFish > 1 ? GameValues.m2shark[i].screenWidthScaling : drawFish == 1 ? GameValues.m2rareFishes[i].screenWidthScaling : GameValues.m2fishes[i].screenWidthScaling)).intValue();
            int j = fa.ltr ? 0 : maxJ / 2;
            int max = fa.ltr ? maxJ / 2 : maxJ;
            for (int s = 0; j < max; j++, s++) {
                g[s] = Renderkit.loadGraphic(mContext.getResources(), GameValues.fishGroups[drawFish][i][j], 0, 0);
                g[s].image.setBounds(0, 0, fishWidth, ((fishWidth * (g[s].image.getBounds().bottom - g[s].image.getBounds().top) / g[s].image.getBounds().right)));
            }
//			}
            getSprites().fishGraphics.put(getFishKey(fa), g);
        }
    }

    /**
     * Create fishes Assume 80% of the screenheight are available for fishes to
     * start the swimming ltr and rtl
     *
     * @param count
     * @param screenWidth
     * @param screenHeight
     */
    private void createFishes(int count, int screenWidth, int screenHeight, long time) {

        if (createTimeout > time) {
            return;
        }

        // animations
        for (int i = 0; i < count; i++) {
            if (i >= GameValues.m2maxFishesAtOnce) {
                createTimeout = time + GameValues.m2fishCreateTimeout;
                break;
            }

            boolean ltr = RND.nextBoolean();
            boolean isRare = !hasRare ? RND.nextFloat() < GameValues.m2chanceRareFish[difficulty] : false;
            final int fish = isRare ? RND.nextInt(GameValues.m2rareFishes.length) : RND.nextInt(GameValues.m2fishes.length);
            int rand = RND.nextInt(GameValues.highestAnimationSpeed);
            if (rand < GameValues.lowestAnimationSpeed) {
                rand = GameValues.lowestAnimationSpeed;
            }
            if (isRare) {
                rand = GameValues.rareAnimationSpeed;
            }
            int fishSpeed = RND.nextInt(Long.valueOf(isRare ? GameValues.m2rareFishes[fish].speedMax : GameValues.m2fishes[fish].speedMax).intValue());
            while (fishSpeed < Long.valueOf(isRare ? GameValues.m2rareFishes[fish].speedMin : GameValues.m2fishes[fish].speedMin).intValue()) {
                fishSpeed = RND.nextInt(Long.valueOf(isRare ? GameValues.m2rareFishes[fish].speedMax : GameValues.m2fishes[fish].speedMax).intValue());
            }
            // update speed with round increasement
//			fishSpeed = Float.valueOf(fishSpeed - (GameValues.m2speedIncreasePerRound[mGame.currentRound] * fishSpeed)).intValue();

//			int maxJ = !isRare ? 8 : 4;
            FishAnimation fa = new FishAnimation();
            getSprites().aFish.add(fa);
            fa.fishSpeed = Integer.valueOf(fishSpeed).longValue();
            fa.moveTime = time + fa.fishSpeed;
            fa.ltr = ltr;
            if (isRare) {
                fa.rareFish = isRare;
            }
//			if(isRare) {
//				hasRare = true;
//				AnimationKit.addFishAnimation(fa, fish*maxJ/2+0, rand, fish);
//				AnimationKit.addFishAnimation(fa, fish*maxJ/2+1, rand, fish);
//			} else {
//				AnimationKit.addFishAnimation(fa, fish*maxJ/2+0, rand, fish);
//				AnimationKit.addFishAnimation(fa, fish*maxJ/2+1, rand, fish);
//				AnimationKit.addFishAnimation(fa, fish*maxJ/2+2, rand, fish);
//				AnimationKit.addFishAnimation(fa, fish*maxJ/2+3, rand, fish);
//			}
            if (isRare) {
                hasRare = true;
                AnimationKit.addFishAnimation(fa, 0, rand, fish);
                AnimationKit.addFishAnimation(fa, 1, rand, fish);
            } else {
                AnimationKit.addFishAnimation(fa, 0, rand, fish);
                AnimationKit.addFishAnimation(fa, 1, rand, fish);
                AnimationKit.addFishAnimation(fa, 2, rand, fish);
                AnimationKit.addFishAnimation(fa, 3, rand, fish);
            }
            createFishGraphic(fa);
            calculateStartPosition(fa, screenWidth, screenHeight);
        }

    }

    public void calculateStartPosition(FishAnimation a, int screenWidth, int screenHeight) {

        // determine starting position
        float f;
        while ((f = RND.nextFloat()) < 0.20f) {
            // skip float determination until it is above 30 percent -
            // this automatically inverts the starting point to skip the top
            // screen space for the cat
            continue;
        }
        Graphic[] g = getSprites().fishGraphics.get(getFishKey(a));
//		final int fishWidth = a.rareFish ? getSprites().gRareFishLTR[a.fishNumber].image.getBounds().width() : getSprites().gFishLTR[a.fishNumber].image.getBounds().width();
//		final int fishHeight = a.rareFish ? getSprites().gRareFishLTR[a.fishNumber].image.getBounds().height() : getSprites().gFishLTR[a.fishNumber].image.getBounds().height();
        final int fishWidth = g[0].image.getBounds().width();
        final int fishHeight = g[0].image.getBounds().height();

        int screenHeightStart = Float.valueOf(screenHeight * f).intValue();
        int screenWidthStart = a.ltr ? 0 - fishWidth : screenWidth;
        if (screenHeightStart + fishHeight > screenHeight) {
            screenHeightStart -= fishHeight;
        }

//		if(a.rareFish) {
//			// start at bottom only
//			screenHeightStart = screenHeight - ScreenKit.scaleHeight(30, screenHeight) - fishHeight;
//		}

        // only set the target coords as the rect is already sized to the scaling factor
        a.coord.x = screenWidthStart;
        a.coord.y = screenHeightStart;
    }

    public synchronized BasicGame getGame() {
        return mGame;
    }

    /**
     * Starts the game, setting parameters for the current difficulty.
     */
    public void doStart() {
        if (mMode == GameState.NONE) {
            setMode(GameState.INIT);
        }
    }

    /**
     * Pauses the physics update & animation.
     */
    public synchronized void pause() {
        setSubMode(GameSubState.PAUSE);
    }

    /**
     * Resumes from a pause.
     */
    public synchronized void unpause() {
        if (purchase) {
            purchase = false;
        }
        //set state back to running
        delay = GameValues.gamePauseDelay;
        lastTime = System.currentTimeMillis();
        setSubMode(GameSubState.NONE);
    }

    public synchronized void restoreGameState() {
        synchronized (StoreDataNew.getInstance()) {
            StoreDataNew.getInstance().gameData2.selfcheck();

// NO GAME RESTORE IN ISLAND MODE		    
//			int currentGame = StoreDataNew.getInstance() != null ? StoreDataNew.getInstance().currentGame2.currentGame[difficulty] : 0;
//			if (currentGame >= 0) {
//				mGame.catched = StoreDataNew.getInstance().currentGame2.gameCatched[difficulty];
//				mGame.rare = StoreDataNew.getInstance().currentGame2.gameRare[difficulty];
//				mGame.positionX = StoreDataNew.getInstance().currentGame2.positionX[difficulty];
//				mGame.positionY = StoreDataNew.getInstance().currentGame2.positionY[difficulty];
//				mGame.bonus = StoreDataNew.getInstance().currentGame2.gameBonus[difficulty];
//				bonus = StoreDataNew.getInstance().currentGame2.bonus;
//
//				// update cat
//				if(getSprites().aCat != null) {
//					getSprites().aCat.coord.x = mGame.positionX;
//					getSprites().aCat.coord.y = mGame.positionY;
//				}
//				
//			} else {
            // we are coming from the HOME button and restored the game
            // or we are just resuming the game after the game has been finished
            StoreDataNew.getInstance().gameData2.totalGames[difficulty] += 1;
            createGame();
//			}
        }
    }

    public synchronized void saveGameState() {
        synchronized (StoreDataNew.getInstance()) {
            final boolean finished = mGame.finished() && !purchase;
            // always update current data
            StoreDataNew.getInstance().currentGame2.selfcheck();
            StoreDataNew.getInstance().currentGame2.positionX[difficulty] = mGame.positionX;
            StoreDataNew.getInstance().currentGame2.positionY[difficulty] = mGame.positionY;
            StoreDataNew.getInstance().currentGame2.gameCatched[difficulty] = mGame.catched;
            StoreDataNew.getInstance().currentGame2.gameRare[difficulty] = mGame.rare;
            StoreDataNew.getInstance().currentGame2.bonus = bonus;
            if (!finished) {
                StoreDataNew.getInstance().currentGame2.currentGame[difficulty] = 0;
            } else {
                StoreDataNew.getInstance().currentGame2.currentGame[difficulty] = -1;
            }

            if (mGame.finished()) {
                GameHelper.checkTrophiesGameMode2(StoreDataNew.getInstance());
            }
            DBDriver.getInstance().write(StoreDataNew.getInstance());
        }
    }

    /**
     * Restores game state from the indicated Bundle. Typically called when the
     * Activity is being restored after having been previously destroyed.
     *
     * @param savedState Bundle containing the game state
     */
    public synchronized void restoreState(Bundle savedState) {
        setMode(GameState.INIT);
        restoreGameState();
    }

    /**
     * Dump game state to the provided Bundle. Typically called when the
     * Activity is being suspended.
     *
     * @return Bundle with this view's state
     */
    public Bundle saveState(Bundle map) {
        if (map != null) {
//			if(mPlayer != null) {
//				mPlayer.pause();
//			}
            saveGameState();
        }
        return map;
    }

    private void updateStatistics() {
        synchronized (StoreDataNew.getInstance()) {
            StoreDataNew.getInstance().currentGame2.selfcheck();
            StoreDataNew.getInstance().gameData2.selfcheck();

            StoreDataNew.getInstance().currentGame2.currentGame[difficulty] = -1;
            int catched = mGame.catched;
            int rare = mGame.rare;

            StoreDataNew.getInstance().gameData2.fishesInLastGame[difficulty] = StoreDataNew.getInstance().gameData2.lastGameFish[difficulty] + StoreDataNew.getInstance().gameData2.lastGameRareFish[difficulty];
            StoreDataNew.getInstance().gameData2.totalFish[difficulty] += catched;
            StoreDataNew.getInstance().gameData2.totalRareFish[difficulty] += rare;

            StoreDataNew.getInstance().gameData2.lastGameFish[difficulty] = catched;
            StoreDataNew.getInstance().gameData2.lastGameRareFish[difficulty] = rare;
            DBDriver.getInstance().write(StoreDataNew.getInstance());
        }
    }

    /**
     * Sets the game mode. That is, whether we are running, paused, in the
     * failure state, in the victory state, etc.
     *
     * @param mode one of the GameState constants
     */
    public synchronized void setMode(GameState mode) {
        synchronized (mMode) {
            mMode = mode;
        }
    }

    public synchronized GameState getMode() {
        return mMode;
    }

    public synchronized void setSubMode(GameSubState mode) {
        mSubMode = mode;
    }

    public synchronized GameSubState getSubMode() {
        return mSubMode;
    }

    public boolean onTouchEvent(MotionEvent event) {
        boolean blocked = false;

        if (mMode == GameState.PLAY) {
            if (mSubMode == GameSubState.NONE) {

                // float the cat on click
                if (getSprites().aCat != null && getSprites().aCat.rect.contains(Float.valueOf(event.getX()).intValue(), Float.valueOf(event.getY()).intValue()) && catNoFloat == 0l && catFloat == 0l) {
                    // float the cat on the current position
                    catFloat = GameValues.m2catFloatDelay;
                    if (hasBronzeReward >= 0) {
                        catFloat = catFloat + Float.valueOf(catFloat * GameValues.m2rewardBronzeLongerFloat[hasBronzeReward]).longValue();
                    }
                }

                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        // touch gesture started
                        if (getSprites().gPressButtons != null && getSprites().gPressButtons.image.getBounds().contains(Float.valueOf(event.getX()).intValue(), Float.valueOf(event.getY()).intValue())) {
                            dragCatLeft();
                        }
                        if (getSprites().gPressButtons2 != null && getSprites().gPressButtons2.image.getBounds().contains(Float.valueOf(event.getX()).intValue(), Float.valueOf(event.getY()).intValue())) {
                            dragCatRight();
                        }
                        if (containsClick(getSprites().rBtnPause, event.getX(), event.getY())) {
                            setSubMode(GameSubState.PAUSE);
                            blocked = true;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        // touch gesture completed
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        if (getSprites().gPressButtons != null && getSprites().gPressButtons.image.getBounds().contains(Float.valueOf(event.getX()).intValue(), Float.valueOf(event.getY()).intValue())) {
                            dragCatLeft();
                        }
                        if (getSprites().gPressButtons2 != null && getSprites().gPressButtons2.image.getBounds().contains(Float.valueOf(event.getX()).intValue(), Float.valueOf(event.getY()).intValue())) {
                            dragCatRight();
                        }
                        break;
                }
            }
        }

        if (mMode == GameState.END && mGame.onGround) {
            delayedActionHandler(Constants.ACTION_HOME, Constants.ACTION_HOME);
        }

        if (!blocked && mSubMode == GameSubState.PAUSE) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    if (containsClick(getSprites().rBtnResume, event.getX(), event.getY())) {
                        setSubMode(GameSubState.NONE);
                    }
                    if (containsClick(getSprites().rBtnBack, event.getX(), event.getY())) {
                        delayedActionHandler(Constants.ACTION_HOME, Constants.ACTION_HOME);
                    }
                    break;
            }
        }

        return true;
    }

    private void dragCatLeft() {
        // left
        getSprites().aCat.coord.x -= GameValues.m2dragSpeed;
        // check boundary
        if (getSprites().aCat.coord.x <= 0) {
            getSprites().aCat.coord.x += GameValues.m2dragSpeed;
        }
    }

    private void dragCatRight() {
        // right
        getSprites().aCat.coord.x += GameValues.m2dragSpeed;
        // check boundary inclusive image width
        if (getSprites().aCat.coord.x + catWidth >= screenWidth) {
            getSprites().aCat.coord.x -= GameValues.m2dragSpeed;
        }
    }

    /**
     * Update the graphic x/y values in real time. This is called before the
     * draw() method
     */
    private void updatePhysics() {
        // the fixed time for drawing this frame
        final long time = System.currentTimeMillis();

        if (mMode == GameState.PLAY) {

            if (mSubMode == GameSubState.NONE) {

                // calculate screen movement
                if (catFPS < time) {
                    int movement = scaleHeight(GameValues.m2screenMovementPerSecond);
                    if (getSprites().rBackground.bottom > realScreenHeight) {
                        if (catFloat > 0l) {
                            movement -= GameValues.m2floatReduction;
                        }
                        if (mGame.bonus > 0f) {
                            movement -= GameValues.m2bonusReduction;
                        }
                        // minimum movement
                        if (movement < GameValues.m2catMinMovement) {
                            if (mGame.bonus > 0f) {
                                movement = GameValues.m2catMinMovementBonus;
                            } else {
                                movement = GameValues.m2catMinMovement;
                            }
                        }

                        screenPos += movement;
                        getSprites().rBackground.offsetTo(0, getSprites().rBackground.top - movement);
                        for (FishAnimation a : getSprites().aFish) {
                            a.coord.y -= movement;
                        }
                        Rect r = getSprites().gBackground.image.copyBounds();
                        r.offsetTo(0, getSprites().rBackground.bottom - getSprites().gBackground.image.getBounds().height());
                        getSprites().gBackground.image.setBounds(r);
                    }
                }


                hasRare = false;
                // check fishes here
                for (FishAnimation a : getSprites().aFish) {
                    // check if rare fish is available
                    if (a.rareFish) {
                        hasRare = true;
                    }
                }

                // create missing fishes
                if (maxFishes - getSprites().aFish.size() > 0) {
                    createFishes(maxFishes - getSprites().aFish.size(), screenWidth, screenHeight, time);
                }

                // remove catched fishes
                List<FishAnimation> removals = new ArrayList<FishAnimation>();
                for (FishAnimation a : getSprites().aFish) {
                    // remove all fishes whose frameTime is below the time - frameTime is the currentTime + clickDelay
                    if (a.catched && a.frameTime < time) {
                        removals.add(a);
                    }
                }
                if (removals.size() > 0) {
                    getSprites().aFish.removeAll(removals);
                    for (FishAnimation fa : removals) {
                        clearFishGraphic(fa);
                    }
                    removals.clear();
                }

                getSprites().aCat.nextFrame(time);
                if (catFPS < time) {
                    int catMovement = ScreenKit.scaleHeight(GameValues.m2catMovement[difficulty], screenHeight);

                    if (hasGoldReward >= 0) {
                        catMovement = catMovement - Float.valueOf(catMovement * GameValues.m2rewardGoldSlowerSpeed[hasGoldReward]).intValue();
                    }

                    // remove float from cat clicking
                    if (catFloat > 0l) {
                        catMovement -= GameValues.m2floatReduction;
                        catFloat -= (time - lastTime);
                        if (catFloat <= 0l) {
                            catFloat = 0l;
                            catNoFloat = GameValues.m2catNoFloatDelay;
                        }
                    }
                    // remove bonus from ad click
                    if (mGame.bonus > 0f) {
                        catMovement -= GameValues.m2bonusReduction;
                        mGame.bonus -= (time - lastTime);
                        if (mGame.bonus <= 0f) {
                            mGame.bonus = 0f;
                        }
                    }
                    // minimum movement
                    if (catMovement < GameValues.m2catMinMovement) {
                        if (mGame.bonus > 0f) {
                            catMovement = GameValues.m2catMinMovementBonus;
                        } else {
                            catMovement = GameValues.m2catMinMovement;
                        }
                    }

                    if (getSprites().rBackground.bottom <= realScreenHeight) {
                        // cat sinking
                        getSprites().aCat.coord.y += catMovement;
                    }

                    catFPS = time + GameValues.m2catFPS;
                }

                // update for game restore
                if (getSprites().aCat != null) {
                    mGame.positionX = getSprites().aCat.coord.x;
                    mGame.positionY = getSprites().aCat.coord.y;
                }

                // bubbles animation
                if (bubbleTimeout <= 0l && bubbleBoundaryTop <= 0 - getSprites().aBubbles.rect.height()) {
                    // reset timeout
                    bubbleTimeout = 3000l;
                }
                if (bubbleTimeout >= 0l) {
                    bubbleTimeout -= 100l;
                    // set current position for animation
                    if (bubbleTimeout <= 0l) {
                        bubbleBoundaryTop = getSprites().aCat.rect.top + (getSprites().aCat.rect.centerY() - getSprites().aCat.rect.top);
                        getSprites().aBubbles.coord.x = getSprites().aCat.rect.centerX();

                    }
                }

                // reduce no float delay
                if (catNoFloat > 0l) {
                    catNoFloat -= (time - lastTime);
                    if (catNoFloat <= 0l) {
                        catNoFloat = 0l;
                    }
                }

                // fish animation - move fishes
                for (FishAnimation a : getSprites().aFish) {
//				final AnimationFrame gFish = 
                    a.nextFrame(time);
                    if (a.catched) {
                        continue;
                    }
                    // move time expired - move fish, set new move time
                    if (a.moveTime < time) {
                        // calculate fish here dynamically as we use a endless loop
                        final int fishMovement = a.ltr ? ScreenKit.scaleWidth(GameValues.m2fishMovement, screenWidth) : ScreenKit.scaleWidth(GameValues.m2fishMovement, screenWidth) * -1;
                        // method on the current image only
                        a.coord.x += fishMovement;
//					a.coord.y += fishMovement;
                        a.moveTime = time + a.fishSpeed;
                    }

                    // fish moved outside of the screen - initialize again ( by removing the fish )
                    if ((a.ltr && a.coord.x > screenWidth) || (!a.ltr && a.coord.x + a.rect.width() < 0)) {
                        a.init = true;
                        removals.add(a);
                    }

                    // cat catches fish
                    if (!a.catched && a.rect.intersect(smallerCatRect())) {
                        // we catched the fish, count it
                        a.catched = true;
                        mGame.catched += 1;
                        if (a.rareFish) {
                            mGame.rare += 1;
                        }
                    }


                }
                // remove rare fishes
                if (removals.size() > 0) {
                    getSprites().aFish.removeAll(removals);
                    removals.clear();
                }
            }

        }

    }

    /**
     * Reduce the cat rect
     *
     * @return
     */
    private Rect smallerCatRect() {
        Rect r = new Rect(getSprites().aCat.rect);
        r.top += scaleHeight(20);
        r.bottom -= scaleHeight(20);
        r.left += scaleWidth(40);
        r.right -= scaleWidth(40);
        return r;
    }

    private void renderFishNumber(Canvas canvas, float logoWidth, float logoHeight, int total) {
        drawTextUnbounded(canvas, getSprites().rPoints, Integer.toString(total));
//		drawNumbers(canvas, getSprites().gIconFish.image.getBounds().right, scaleHeight(30), total, null, logoWidth, logoHeight);
    }

    public synchronized boolean isPurchase() {
        return purchase;
    }

    public synchronized void setPurchase(boolean b) {
        purchase = b;
    }

    public void setBonus(boolean b) {
        bonus = b;
    }

    @Override
    public void doInitThread(long time) {
        super.sprites = new GameViewSpritesMode2();

        mMode = GameState.NONE;
        mSubMode = GameSubState.NONE;

        gameScreen = realScreenHeight * 2;
        screenPos = 0;

        // button backgrounds
        getSprites().gButton = Renderkit.loadButtonGraphic(mContext.getResources(), R.drawable.button_background, 0, 0, EngineConstants.ACTION_NONE);
        getSprites().gButtonOverlay = Renderkit.loadGraphic(mContext.getResources(), R.drawable.button_background, 0, 0);

        getSprites().rBtnBack = getSprites().gButton.image.copyBounds();
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.BOTTOM_LEFT, 0.30f, 50, 25, getSprites().rBtnBack);

        getSprites().rBtnPause = getSprites().gButton.image.copyBounds();
        getSprites().rBtnResume = getSprites().gButton.image.copyBounds();
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_RIGHT, 0.3f, 30, 30, getSprites().rBtnPause);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_RIGHT, 0.3f, 30, 30, getSprites().rBtnResume);
        getSprites().rBtnPause.bottom -= getSprites().rBtnPause.height() / 3;
        getSprites().rBtnResume.bottom -= getSprites().rBtnResume.height() / 3;


        getSprites().gBackground = new Graphic(mContext.getResources().getDrawable(R.drawable.bg_island_game));
        getSprites().gBackground.image.setBounds(0, realScreenHeight - realScreenHeight / 100 * 65, screenWidth, realScreenHeight);

        Shader shader = new LinearGradient(0, 0, 0, realScreenHeight, Color.parseColor("#88c1d2"), Color.parseColor("#005aa2"), TileMode.CLAMP);
        getSprites().pBackground = new Paint();
        getSprites().pBackground.setShader(shader);
        getSprites().rBackground = new Rect(0, 0, screenWidth, gameScreen);

        // offset image to new position
        Rect r = getSprites().gBackground.image.copyBounds();
        r.offsetTo(0, getSprites().rBackground.bottom - getSprites().gBackground.image.getBounds().height());
        getSprites().gBackground.image.setBounds(r);

        getSprites().rMessage = getSprites().gButton.image.copyBounds();
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.CENTER, 0.6f, 0, 0, getSprites().rMessage);

        getSprites().aFish = new ArrayList<FishAnimation>();
        getSprites().fishGraphics = new HashMap<String, Graphic[]>();

        if (mGame == null) {
            createGame();
        }

        getSprites().gPressButtons = Renderkit.loadGraphic(mContext.getResources(), R.drawable.pressbuttons, 0, 0);
        getSprites().gPressButtons2 = Renderkit.loadGraphic(mContext.getResources(), R.drawable.pressbuttons2, 0, 0);
        final int buttonsWidth = Float.valueOf(getSprites().gPressButtons.image.getBounds().width() * 0.50f).intValue();
        final int buttonsHeight = buttonsWidth * (getSprites().gPressButtons.image.getBounds().bottom - getSprites().gPressButtons.image.getBounds().top) / getSprites().gPressButtons.image.getBounds().right;
        final int buttonsTop = (screenHeight - buttonsHeight) / 2;
        getSprites().gPressButtons.image.setBounds(10, buttonsTop, 10 + buttonsWidth, buttonsTop + buttonsHeight);
        getSprites().gPressButtons2.image.setBounds(screenWidth - 10 - buttonsWidth, buttonsTop, screenWidth - 10, buttonsTop + buttonsHeight);

        getSprites().gCat = new Graphic[2];
        getSprites().gCat[0] = Renderkit.loadGraphic(mContext.getResources(), R.drawable.kater_0, 0, 0);
        getSprites().gCat[1] = Renderkit.loadGraphic(mContext.getResources(), R.drawable.kater_1, 0, 0);

        getSprites().aCat = new Animation();
        AnimationKit.addAnimation(getSprites().aCat, 0, 200);
        AnimationKit.addAnimation(getSprites().aCat, 1, 200);

        initCatPhysics();

        getSprites().gIconFish = Renderkit.loadGraphic(mContext.getResources(), R.drawable.fishhead, 0, 0);

        ScreenKit.scaleImage(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.09f, 10, 10, getSprites().gIconFish);

        getSprites().rPoints = getSprites().gButton.image.copyBounds();
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.3f, 30, 20, getSprites().rPoints);
        getSprites().rPoints.right -= getSprites().rPoints.width() / 3;
        ScreenKit.moveRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 45, 5, getSprites().rPoints);

        // load fish graphics into memory
//      for(int drawFish = 0; drawFish<GameValues.fishGroups.length-1; drawFish++) {
//          int maxJ = drawFish == 0 ? 8 : 4;
//          if(drawFish == 0) {
//              getSprites().gFishLTR = new Graphic[GameValues.fishGroups[drawFish].length * maxJ/2];
//              getSprites().gFishRTL = new Graphic[GameValues.fishGroups[drawFish].length * maxJ/2];
//          } else if(drawFish == 1) {
//              getSprites().gRareFishLTR = new Graphic[GameValues.fishGroups[drawFish].length * maxJ/2];
//              getSprites().gRareFishRTL = new Graphic[GameValues.fishGroups[drawFish].length * maxJ/2];
//          }
//          
//          for(int s=0,i=0; i<GameValues.fishGroups[drawFish].length; i++) {
//              // determine width of fish to automatically scale all fish
//              final int fishWidth = Float.valueOf(screenWidth * (drawFish == 1 ? GameValues.rareFishes[i].screenWidthScaling : GameValues.fishes[i].screenWidthScaling)).intValue();
//              for(int j=0; j<maxJ/2; j++,s++) {
//                  if(drawFish == 0) {
//                      getSprites().gFishLTR[s] = Renderkit.loadGraphic(mContext.getResources(), GameValues.fishGroups[drawFish][i][j], 0, 0);
//                      getSprites().gFishLTR[s].image.setBounds(0, 0, fishWidth, ((fishWidth * (getSprites().gFishLTR[s].image.getBounds().bottom - getSprites().gFishLTR[s].image.getBounds().top) / getSprites().gFishLTR[s].image.getBounds().right)));
//                  } else if(drawFish == 1) {
//                      getSprites().gRareFishLTR[s] = Renderkit.loadGraphic(mContext.getResources(), GameValues.fishGroups[drawFish][i][j], 0, 0);
//                      getSprites().gRareFishLTR[s].image.setBounds(0, 0, fishWidth, ((fishWidth * (getSprites().gRareFishLTR[s].image.getBounds().bottom - getSprites().gRareFishLTR[s].image.getBounds().top) / getSprites().gRareFishLTR[s].image.getBounds().right)));
//                  }
//              }
//              s-=maxJ/2;
//              for(int j=maxJ/2; j<maxJ; j++,s++) {
//                  if(drawFish == 0) {
//                      getSprites().gFishRTL[s] = Renderkit.loadGraphic(mContext.getResources(), GameValues.fishGroups[drawFish][i][j], 0, 0);
//                      getSprites().gFishRTL[s].image.setBounds(0, 0, fishWidth, ((fishWidth * (getSprites().gFishRTL[s].image.getBounds().bottom - getSprites().gFishRTL[s].image.getBounds().top) / getSprites().gFishRTL[s].image.getBounds().right)));
//                  } else if(drawFish == 1) {
//                      getSprites().gRareFishRTL[s] = Renderkit.loadGraphic(mContext.getResources(), GameValues.fishGroups[drawFish][i][j], 0, 0);
//                      getSprites().gRareFishRTL[s].image.setBounds(0, 0, fishWidth, ((fishWidth * (getSprites().gRareFishRTL[s].image.getBounds().bottom - getSprites().gRareFishRTL[s].image.getBounds().top) / getSprites().gRareFishRTL[s].image.getBounds().right)));
//                  }
//              }
//          }
//      }


        getSprites().gBubbles = new Graphic[7];
        getSprites().gBubbles[0] = Renderkit.loadGraphic(mContext.getResources(), R.drawable.bubbles1, 0, 0);
        getSprites().gBubbles[1] = Renderkit.loadGraphic(mContext.getResources(), R.drawable.bubbles2, 0, 0);
        getSprites().gBubbles[2] = Renderkit.loadGraphic(mContext.getResources(), R.drawable.bubbles3, 0, 0);
        getSprites().gBubbles[3] = Renderkit.loadGraphic(mContext.getResources(), R.drawable.bubbles4, 0, 0);
        getSprites().gBubbles[4] = Renderkit.loadGraphic(mContext.getResources(), R.drawable.bubbles5, 0, 0);
        getSprites().gBubbles[5] = Renderkit.loadGraphic(mContext.getResources(), R.drawable.bubbles6, 0, 0);
        getSprites().gBubbles[6] = Renderkit.loadGraphic(mContext.getResources(), R.drawable.bubbles7, 0, 0);

        getSprites().aBubbles = new Animation();
        AnimationKit.addAnimation(getSprites().aBubbles, 0, 100);
        AnimationKit.addAnimation(getSprites().aBubbles, 1, 100);
        AnimationKit.addAnimation(getSprites().aBubbles, 2, 100);
        AnimationKit.addAnimation(getSprites().aBubbles, 3, 100);
        AnimationKit.addAnimation(getSprites().aBubbles, 4, 100);
        AnimationKit.addAnimation(getSprites().aBubbles, 5, 100);
        AnimationKit.addAnimation(getSprites().aBubbles, 6, 100);

        ScreenKit.scaleImage(screenWidth, realScreenHeight, ScreenPosition.CENTER, 0.06f, 0, 0, getSprites().gBubbles);
        getSprites().aBubbles.rect.set(getSprites().gBubbles[0].image.copyBounds());
    }

    @Override
    public void doUpdateRenderState() {
        final long time = System.currentTimeMillis();

        if (delay > 0l && lastTime > 0l) {
            delay -= time - lastTime;
        }

        switch (mMode) {
            case NONE: {
                // move to initialization
                setMode(GameState.INIT);
            }
            break;
            case INIT: {
                // setup the game
                setMode(GameState.READY);
            }
            break;
            case READY: {
                if (delay <= 0l) {
                    setMode(GameState.PLAY);
                    mGame.started = true;
                }
            }
            break;
            case PLAY: {
                // active gameplay
                // update graphic positions
                updatePhysics();

                // end game
                // cat touches bottom screen
                if (getSprites().aCat != null && getSprites().aCat.rect.bottom >= screenHeight) {
                    setMode(GameState.END);
                }

            }
            break;
            case END: {
                if (!mGame.finished()) {
                    // update statistics
                    updateStatistics();
                }
                // only set ground after statistic update otherwise the client may exit too early
                mGame.onGround = true;
            }
            break;
            default:
                setMode(GameState.NONE);
                break;
        }
        lastTime = time;
    }

    @Override
    public void doDrawRenderer(Canvas canvas) {

        if (getSprites().pBackground != null) {
            canvas.drawRect(getSprites().rBackground, getSprites().pBackground);
        }
        if (getSprites().gBackground != null) {
            getSprites().gBackground.image.draw(canvas);
        }

        // the fixed time for drawing this frame
        final long time = System.currentTimeMillis();

        if (mMode == GameState.NONE || mMode == GameState.INIT || mMode == GameState.READY) {
            drawText(canvas, getSprites().rMessage, mContext.getString(R.string.message_loading), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
        }

        if (mMode == GameState.PLAY) {
//          if(bonus && getSprites().gBonus != null) {
//              getSprites().aBonus.nextFrame(time);
//              getSprites().gBonus[getSprites().aBonus.nextFrame().gReference].image.setBounds(getSprites().aBonus.rect);
//              getSprites().gBonus[getSprites().aBonus.nextFrame().gReference].image.draw(canvas);
//              if(getSprites().aBonus.done) {
//                  bonus = false;
//                  getSprites().aBonus.reset();
//              }
//          }

            if (mSubMode == GameSubState.NONE) {
                if (getSprites().gPressButtons != null) {
                    getSprites().gPressButtons.image.draw(canvas);
                    getSprites().gPressButtons2.image.draw(canvas);
                }
            }

            getSprites().gIconFish.image.draw(canvas);

            renderFishNumber(canvas, standardNumberWidth * 1.1f, standardNumberHeight * 1.1f, mGame.catched);

            if (getSprites().gCat != null) {
                canvas.save();
                getSprites().aCat.rect.offsetTo(getSprites().aCat.coord.x, getSprites().aCat.coord.y);
                getSprites().gCat[getSprites().aCat.nextFrame().gReference].image.setBounds(getSprites().aCat.rect);
                getSprites().gCat[getSprites().aCat.nextFrame().gReference].image.draw(canvas);
                canvas.restore();
            }
            if (mSubMode == GameSubState.NONE) {

                // draw bubbles if timeout passed
                if (bubbleTimeout <= 0l && getSprites().aBubbles != null) {
                    getSprites().aBubbles.nextFrame(time);

                    // calculate bubbles here dynamically as we use a endless loop
                    canvas.save();
                    getSprites().aBubbles.coord.y = bubbleBoundaryTop;
                    getSprites().aBubbles.rect.offsetTo(getSprites().aBubbles.coord.x, getSprites().aBubbles.coord.y);
                    getSprites().gBubbles[getSprites().aBubbles.nextFrame().gReference].image.setBounds(getSprites().aBubbles.rect);
                    getSprites().gBubbles[getSprites().aBubbles.nextFrame().gReference].image.draw(canvas);
                    canvas.restore();

                    bubbleBoundaryTop -= ScreenKit.scaleHeight(35, screenHeight);
                }

                for (FishAnimation a : getSprites().aFish) {
                    canvas.save();
                    Graphic[] g = getSprites().fishGraphics.get(getFishKey(a));
                    if (g != null) {
                        a.rect.set(g[a.nextFrame().gReference].image.copyBounds());
                        a.rect.offsetTo(a.coord.x, a.coord.y);
                        g[a.nextFrame().gReference].image.setBounds(a.rect);
                        g[a.nextFrame().gReference].image.draw(canvas);
                    }
                    canvas.restore();
                }

                choiceBaseDraw(canvas, getSprites().rBtnPause, getSprites().gButtonOverlay, getSprites().gButton, true, false, GameValues.cFilterGreen);
                drawText(canvas, getSprites().rBtnPause, mContext.getString(R.string.menubutton_pause), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
            }
            if (mSubMode == GameSubState.PAUSE) {
                drawText(canvas, getSprites().rMessage, mContext.getString(R.string.message_pause), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));

                choiceBaseDraw(canvas, getSprites().rBtnResume, getSprites().gButtonOverlay, getSprites().gButton, true, false, GameValues.cFilterGreen);
                drawText(canvas, getSprites().rBtnResume, mContext.getString(R.string.menubutton_resume), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));

                choiceBaseDraw(canvas, getSprites().rBtnBack, getSprites().gButtonOverlay, getSprites().gButton, activeButton, Constants.ACTION_HOME, GameValues.cFilterGreen);
                drawText(canvas, getSprites().rBtnBack, mContext.getString(R.string.menubutton_back), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight), GameValues.cFilterRed);
            }

        }

        if (mMode == GameState.END) {
            drawText(canvas, getSprites().rMessage, mContext.getString(R.string.message_finished), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));

            choiceBaseDraw(canvas, getSprites().rBtnBack, getSprites().gButtonOverlay, getSprites().gButton, activeButton, Constants.ACTION_HOME, GameValues.cFilterGreen);
            drawText(canvas, getSprites().rBtnBack, mContext.getString(R.string.menubutton_end), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight), GameValues.cFilterRed);
        }

    }


    @Override
    public void reset() {
    }

}

