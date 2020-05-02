package info.simplyapps.game.fishingcat.rendering;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MotionEvent;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import info.simplyapps.appengine.storage.dto.Configuration;
import info.simplyapps.appengine.storage.dto.Extensions;
import info.simplyapps.game.fishingcat.Constants;
import info.simplyapps.game.fishingcat.R;
import info.simplyapps.game.fishingcat.SystemHelper;
import info.simplyapps.game.fishingcat.engine.GameHelper;
import info.simplyapps.game.fishingcat.engine.GameValues;
import info.simplyapps.game.fishingcat.rendering.kits.AnimationKit;
import info.simplyapps.game.fishingcat.rendering.kits.Renderkit;
import info.simplyapps.game.fishingcat.rendering.objects.FishAnimation;
import info.simplyapps.game.fishingcat.sprites.GameViewSpritesMode1;
import info.simplyapps.game.fishingcat.storage.DBDriver;
import info.simplyapps.game.fishingcat.storage.StoreDataNew;
import info.simplyapps.game.fishingcat.system.Game1;
import info.simplyapps.game.fishingcat.system.GameRound;
import info.simplyapps.gameengine.EngineConstants;
import info.simplyapps.gameengine.engine.GameEngine;
import info.simplyapps.gameengine.rendering.kits.ScreenKit;
import info.simplyapps.gameengine.rendering.kits.ScreenKit.ScreenPosition;
import info.simplyapps.gameengine.rendering.objects.Animation;
import info.simplyapps.gameengine.rendering.objects.Graphic;
import info.simplyapps.gameengine.system.BasicGame;
import info.simplyapps.gameengine.system.GameState;
import info.simplyapps.gameengine.system.GameSubState;

public class GameModeClassicSingle extends FishRendererTemplate implements GameEngine {

    /**
     * The state of the game. One of READY, RUNNING, PAUSE, LOSE, or WIN
     */
    private GameState mMode;
    private GameSubState mSubMode;

    private static Random RND = new Random();

    final float standardNumberWidth = 0.05f;
    final float standardNumberHeight = 1.20f;

    private Game1 mGame;

    private boolean hasShark;
    private boolean hasRare;
    private long delay = 0l;
    private long lastTime;

    int difficulty;
    int hasSilverReward;
    int hasBronzeReward;
    int hasGoldReward;

    boolean bonus;
    int mRounds;
    boolean mCatMode;

    int[] mExtensions;
    long createTimeout;

    ActivityManager activityManager;
    MemoryInfo memoryInfo;

    public GameModeClassicSingle(Context context, Properties p) {
        super(context, p);

        try {
            activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
            memoryInfo = new ActivityManager.MemoryInfo();
        } catch (Exception e) {
            // ignore
        }
    }

    public GameViewSpritesMode1 getSprites() {
        return GameViewSpritesMode1.class.cast(super.sprites);
    }

    private void createGame() {
        synchronized (StoreDataNew.getInstance()) {
            hasBronzeReward = -1;
            hasSilverReward = -1;
            hasGoldReward = -1;
            difficulty = StoreDataNew.getInstance().gameData1.difficulty;
            if (GameHelper.hasBronze()) {
                if (StoreDataNew.getInstance().gameData1.rewards[GameValues.GAMEMODE_EASY][0]) {
                    hasBronzeReward += 1;
                }
                if (StoreDataNew.getInstance().gameData1.rewards[GameValues.GAMEMODE_MEDIUM][0]) {
                    hasBronzeReward += 1;
                }
                if (StoreDataNew.getInstance().gameData1.rewards[GameValues.GAMEMODE_HARD][0]) {
                    hasBronzeReward += 1;
                }
            }
            if (GameHelper.hasSilver()) {
                if (StoreDataNew.getInstance().gameData1.rewards[GameValues.GAMEMODE_EASY][1]) {
                    hasSilverReward += 1;
                }
                if (StoreDataNew.getInstance().gameData1.rewards[GameValues.GAMEMODE_MEDIUM][1]) {
                    hasSilverReward += 1;
                }
                if (StoreDataNew.getInstance().gameData1.rewards[GameValues.GAMEMODE_HARD][1]) {
                    hasSilverReward += 1;
                }
            }
            if (GameHelper.hasGold()) {
                if (StoreDataNew.getInstance().gameData1.rewards[GameValues.GAMEMODE_EASY][2]) {
                    hasGoldReward += 1;
                }
                if (StoreDataNew.getInstance().gameData1.rewards[GameValues.GAMEMODE_MEDIUM][2]) {
                    hasGoldReward += 1;
                }
                if (StoreDataNew.getInstance().gameData1.rewards[GameValues.GAMEMODE_HARD][2]) {
                    hasGoldReward += 1;
                }
            }
        }
        mGame = new Game1(new GameRound[]{
                new GameRound(0, GameValues.m1timePerRound[difficulty][0] + (hasSilverReward >= 0 ? Float.valueOf(GameValues.m1timePerRound[difficulty][0] * GameValues.m1rewardSilverMoreTime[hasSilverReward]).longValue() : 0)),
                new GameRound(1, GameValues.m1timePerRound[difficulty][1] + (hasSilverReward >= 0 ? Float.valueOf(GameValues.m1timePerRound[difficulty][1] * GameValues.m1rewardSilverMoreTime[hasSilverReward]).longValue() : 0)),
                new GameRound(2, GameValues.m1timePerRound[difficulty][2] + (hasSilverReward >= 0 ? Float.valueOf(GameValues.m1timePerRound[difficulty][2] * GameValues.m1rewardSilverMoreTime[hasSilverReward]).longValue() : 0)),
                new GameRound(3, GameValues.m1timePerRound[difficulty][3] + (hasSilverReward >= 0 ? Float.valueOf(GameValues.m1timePerRound[difficulty][3] * GameValues.m1rewardSilverMoreTime[hasSilverReward]).longValue() : 0)),
                new GameRound(4, GameValues.m1timePerRound[difficulty][4] + (hasSilverReward >= 0 ? Float.valueOf(GameValues.m1timePerRound[difficulty][4] * GameValues.m1rewardSilverMoreTime[hasSilverReward]).longValue() : 0)),
                new GameRound(5, GameValues.m1timePerRound[difficulty][5] + (hasSilverReward >= 0 ? Float.valueOf(GameValues.m1timePerRound[difficulty][5] * GameValues.m1rewardSilverMoreTime[hasSilverReward]).longValue() : 0)),
                new GameRound(6, GameValues.m1timePerRound[difficulty][6] + (hasSilverReward >= 0 ? Float.valueOf(GameValues.m1timePerRound[difficulty][6] * GameValues.m1rewardSilverMoreTime[hasSilverReward]).longValue() : 0)),
                new GameRound(7, GameValues.m1timePerRound[difficulty][7] + (hasSilverReward >= 0 ? Float.valueOf(GameValues.m1timePerRound[difficulty][7] * GameValues.m1rewardSilverMoreTime[hasSilverReward]).longValue() : 0)),
                new GameRound(8, GameValues.m1timePerRound[difficulty][8] + (hasSilverReward >= 0 ? Float.valueOf(GameValues.m1timePerRound[difficulty][8] * GameValues.m1rewardSilverMoreTime[hasSilverReward]).longValue() : 0))});
        delay = GameValues.gameRoundDelay;
        mGame.lives = GameValues.m1totalLives;
        if (hasBronzeReward >= 0) {
            mGame.immunity = GameValues.m1rewardBronzeSharkImmunity[hasBronzeReward];
        }
    }

    private void initCatPhysics() {

        // set cat
        final int catWidth = Float.valueOf(screenWidth * 0.25f).intValue();
        final int catHeigth = catWidth * (getSprites().gCat[0].image.getBounds().bottom - getSprites().gCat[0].image.getBounds().top) / getSprites().gCat[0].image.getBounds().right;

        final int screenHeightCatStart = catHeigth / 3 * -1;
        final int screenWidthCatStart = screenWidth / 2 - catWidth / 2;

        getSprites().gCat[0].image.setBounds(screenWidthCatStart, screenHeightCatStart, screenWidthCatStart + catWidth, screenHeightCatStart + catHeigth);

        getSprites().aCatEyes.rect = getSprites().gCat[0].image.copyBounds();
        getSprites().aCatEyes.coord.x = getSprites().gCat[0].image.getBounds().left;
        getSprites().aCatEyes.coord.y = getSprites().gCat[0].image.getBounds().top;
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
            // determine width of fish to automatically scale all fish
            final int fishWidth = Float.valueOf(screenWidth * (drawFish > 1 ? GameValues.m1shark[i].screenWidthScaling : drawFish == 1 ? GameValues.m1rareFishes[i].screenWidthScaling : GameValues.m1fishes[i].screenWidthScaling)).intValue();
            int j = fa.ltr ? 0 : maxJ / 2;
            int max = fa.ltr ? maxJ / 2 : maxJ;
            for (int s = 0; j < max; j++, s++) {
                g[s] = Renderkit.loadGraphic(mContext.getResources(), GameValues.fishGroups[drawFish][i][j], 0, 0);
                g[s].image.setBounds(0, 0, fishWidth, ((fishWidth * (g[s].image.getBounds().bottom - g[s].image.getBounds().top) / g[s].image.getBounds().right)));
            }
            getSprites().fishGraphics.put(getFishKey(fa), g);
        }
    }

    /**
     * Create fishes Assume 80% of the screenheight are available for fishes to
     * start the swimming ltr and rtl
     *
     * @param count
     * @param time
     */
    private void createFishes(int count, long time) {

        if (createTimeout > time) {
            return;
        }

        long avail = 99999999999l;
        boolean lowMem = false;
        /*
http://developer.android.com/training/articles/memory.html#CheckHowMuchMemory
         */

        // animations
        for (int i = 0; i < count; i++) {
            if (i >= GameValues.m1maxFishesAtOnce) {
                createTimeout = time + GameValues.m1fishCreateTimeout;
                break;
            }

            if (activityManager != null && memoryInfo != null) {
                try {
                    activityManager.getMemoryInfo(memoryInfo);
                    avail = memoryInfo.availMem;
                } catch (Exception e) {
                    avail = 99999999999l;
                }
            }
            // below 1 megabyte
            lowMem = avail < 1048576l;

            boolean ltr = RND.nextBoolean();
            // only one rare per screen
            boolean isRare = !mCatMode && !hasRare ? RND.nextFloat() < GameValues.m1chanceRareFishPerRound[difficulty][mGame.currentRound] : false;
            // only one shark per screen
            boolean isShark = !mCatMode && !hasShark ? RND.nextFloat() < GameValues.m1chanceSharkPerRound[difficulty][mGame.currentRound] : false;
            int fish = isShark ? GameValues.m1shark.length - 1 : isRare ? RND.nextInt(GameValues.m1rareFishes.length) : RND.nextInt(GameValues.m1fishes.length);

            if (lowMem) {
                // scan for regular fish
                if (!isRare && !isShark) {
                    for (FishAnimation a : getSprites().aFish) {
                        if (!a.rareFish && !a.sharkFish) {
                            fish = a.fishNumber;
                            break;
                        }
                    }
                }
            }


            int rand = RND.nextInt(GameValues.highestAnimationSpeed);
            if (rand < GameValues.lowestAnimationSpeed) {
                rand = GameValues.lowestAnimationSpeed;
            }
            if (isRare) {
                rand = GameValues.rareAnimationSpeed;
            }
            int fishSpeed = RND.nextInt(Long.valueOf(isShark ? GameValues.m1shark[fish].speedMax : isRare ? GameValues.m1rareFishes[fish].speedMax : GameValues.m1fishes[fish].speedMax).intValue());
            while (fishSpeed < Long.valueOf(isShark ? GameValues.m1shark[fish].speedMin : isRare ? GameValues.m1rareFishes[fish].speedMin : GameValues.m1fishes[fish].speedMin).intValue()) {
                fishSpeed = RND.nextInt(Long.valueOf(isShark ? GameValues.m1shark[fish].speedMax : isRare ? GameValues.m1rareFishes[fish].speedMax : GameValues.m1fishes[fish].speedMax).intValue());
            }
            // update speed with round increasement
            fishSpeed = Float.valueOf(fishSpeed - (GameValues.m1speedIncreasePerRound[difficulty][mGame.currentRound] * fishSpeed)).intValue();

            if (hasGoldReward >= 0 && !isShark) {
                fishSpeed = fishSpeed - Float.valueOf(fishSpeed * GameValues.m1rewardGoldSlowerFishes[hasGoldReward]).intValue();
            }

            FishAnimation fa = new FishAnimation();
            getSprites().aFish.add(fa);
            fa.fishSpeed = Integer.valueOf(fishSpeed).longValue();
            fa.moveTime = time + fa.fishSpeed;
            fa.ltr = ltr;
            if (isShark) {
                fa.sharkFish = isShark;
            } else if (isRare) {
                fa.rareFish = isRare;
            }
            if (isShark) {
                hasShark = true;
                AnimationKit.addFishAnimation(fa, 0, rand, fish);
                AnimationKit.addFishAnimation(fa, 1, rand, fish);
            } else if (isRare) {
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
            calculateStartPosition(fa);
        }

    }

    public void calculateStartPosition(FishAnimation a) {

        // determine starting position
        float f;
        if (!mCatMode) {
            while ((f = RND.nextFloat()) < 0.30f) {
                // skip float determination until it is above 30 percent -
                // this automatically inverts the starting point to skip the top
                // screen space for the cat
                continue;
            }
        } else {
            while ((f = RND.nextFloat()) < 0.10f) {
                // skip float determination until it is above 10 percent -
                // this automatically inverts the starting point to skip the top
                // screen space for fishes
                continue;
            }
        }
        Graphic[] g = getSprites().fishGraphics.get(getFishKey(a));
//		final int fishWidth = a.sharkFish ? getSprites().gSharkFishLTR[a.fishNumber].image.getBounds().width() : a.rareFish ? getSprites().gRareFishLTR[a.fishNumber].image.getBounds().width() : getSprites().gFishLTR[a.fishNumber].image.getBounds().width();
//		final int fishHeight = a.sharkFish ? getSprites().gSharkFishLTR[a.fishNumber].image.getBounds().height() : a.rareFish ? getSprites().gRareFishLTR[a.fishNumber].image.getBounds().height() : getSprites().gFishLTR[a.fishNumber].image.getBounds().height();
        final int fishWidth = g[0].image.getBounds().width();
        final int fishHeight = g[0].image.getBounds().height();

        int screenHeightStart = Float.valueOf(screenHeight * f).intValue();
        int screenWidthStart = a.ltr ? 0 - fishWidth : screenWidth;
        if (screenHeightStart + fishHeight > screenHeight) {
            screenHeightStart -= fishHeight;
        }

        if (a.sharkFish) {
            // Step 1 - start at top
//			screenHeightStart = -1 * (fishHeight/3);
        } else if (a.rareFish) {
            // start at bottom only
            screenHeightStart = screenHeight - ScreenKit.scaleHeight(30, screenHeight) - fishHeight;
        }

        // only set the target coords as the rect is already sized to the scaling factor
        a.coord.x = screenWidthStart;
        a.coord.y = screenHeightStart;
    }

    public synchronized BasicGame getGame() {
        return mGame;
    }

    private MediaPlayer getMediaPlayer() {
        return getScreen().getMediaPlayer();
    }


    /**
     * Starts the game, setting parameters for the current difficulty.
     */
    public void doStart() {
        if (mMode == GameState.NONE) {
            setMode(GameState.INIT);
        }

        musicStart();
    }

    /**
     * Pauses the physics update & animation.
     */
    public synchronized void pause() {
        if (mMode == GameState.END) {
            updateStatistics();
        }
        if (mMode == GameState.PLAY) {
            musicStop();
            setSubMode(GameSubState.PAUSE);
        }
    }

    private void musicStop() {
        if (getMediaPlayer() != null) {
            getMediaPlayer().pause();
        }
    }

    private void musicStart() {
        getScreen().assignMediaPlayer(mContext, R.raw.music2);
        if (getMediaPlayer() != null && !getMediaPlayer().isPlaying()) {
            getMediaPlayer().start();
        }
    }

    /**
     * Resumes from a pause.
     */
    public synchronized void unpause() {
        musicStart();
        //set state back to running
        delay = GameValues.gamePauseDelay;
        lastTime = System.currentTimeMillis();
        setSubMode(GameSubState.NONE);
    }

    public synchronized void restoreGameState() {
        synchronized (StoreDataNew.getInstance()) {
            StoreDataNew.getInstance().currentGame1.selfcheck();
            StoreDataNew.getInstance().gameData1.selfcheck();

            int gameRoundLength = mGame != null && mGame.rounds != null ? mGame.rounds.length : 0;
            int currentGame = StoreDataNew.getInstance() != null ? StoreDataNew.getInstance().currentGame1.currentGame[difficulty] : 0;
            int gameCatched = StoreDataNew.getInstance() != null && StoreDataNew.getInstance().currentGame1.gameCatched[difficulty] != null ? StoreDataNew.getInstance().currentGame1.gameCatched[difficulty].length : 0;
            if (currentGame >= 0 && currentGame < gameRoundLength
                    && gameCatched == gameRoundLength) {
                mGame.currentRound = StoreDataNew.getInstance().currentGame1.currentGame[difficulty];
                mGame.lives = StoreDataNew.getInstance().currentGame1.currentLives[difficulty];
                bonus = StoreDataNew.getInstance().currentGame1.bonus;
                int i = 0;
                for (GameRound round : mGame.rounds) {
                    round.catched = StoreDataNew.getInstance().currentGame1.gameCatched[difficulty][i];
                    round.rare = StoreDataNew.getInstance().currentGame1.gameRare[difficulty][i];
                    round.round = StoreDataNew.getInstance().currentGame1.gameRound[difficulty][i];
                    if (i > mGame.currentRound) {
                        // reinvoke game time for future rounds
                        round.time = GameValues.m1timePerRound[difficulty][i] + (hasSilverReward >= 0 ? Float.valueOf(GameValues.m1timePerRound[difficulty][i] * GameValues.m1rewardSilverMoreTime[hasSilverReward]).longValue() : 0);
                    } else {
                        round.time = StoreDataNew.getInstance().currentGame1.gameTime[difficulty][i];
                    }
                    i++;
                }

                if (mGame.lives <= 0) {
                    setMode(GameState.END);
                } else {
                    delay = GameValues.gameRoundDelay;
                }

            } else {
                // we are coming from the HOME button and restored the game
                // or we are just resuming the game after the game has been finished
                StoreDataNew.getInstance().gameData1.totalGames[difficulty] += 1;
                createGame();
            }
        }
    }

    public synchronized void saveGameState() {
        synchronized (StoreDataNew.getInstance()) {
            final boolean finished = mGame.finished();
            // always update current data
            StoreDataNew.getInstance().currentGame1.selfcheck();
            StoreDataNew.getInstance().currentGame1.currentLives[difficulty] = mGame.lives;
            StoreDataNew.getInstance().currentGame1.gameCatched[difficulty] = new int[mGame.rounds.length];
            StoreDataNew.getInstance().currentGame1.gameRare[difficulty] = new int[mGame.rounds.length];
            StoreDataNew.getInstance().currentGame1.gameRound[difficulty] = new int[mGame.rounds.length];
            StoreDataNew.getInstance().currentGame1.gameTime[difficulty] = new long[mGame.rounds.length];
            StoreDataNew.getInstance().currentGame1.bonus = bonus;

            for (int i = 0; i < mGame.rounds.length; i++) {
                StoreDataNew.getInstance().currentGame1.gameCatched[difficulty][i] = mGame.rounds[i].catched;
                StoreDataNew.getInstance().currentGame1.gameRare[difficulty][i] = mGame.rounds[i].rare;
                StoreDataNew.getInstance().currentGame1.gameRound[difficulty][i] = mGame.rounds[i].round;
                StoreDataNew.getInstance().currentGame1.gameTime[difficulty][i] = mGame.rounds[i].time;
            }
            if (!finished) {
                StoreDataNew.getInstance().currentGame1.currentGame[difficulty] = mGame.currentRound;
            } else {
                mGame.currentRound = -1;
                StoreDataNew.getInstance().currentGame1.currentGame[difficulty] = -1;
            }

            if (mGame.gameWon() && mGame.finished()) {
                GameHelper.checkTrophiesGameMode1(StoreDataNew.getInstance());
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
        setSubMode(GameSubState.PAUSE);
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
            musicStop();
            saveGameState();
        }
        return map;
    }

    private void updateStatistics() {
        synchronized (StoreDataNew.getInstance()) {
            StoreDataNew.getInstance().currentGame1.selfcheck();
            StoreDataNew.getInstance().gameData1.selfcheck();

            StoreDataNew.getInstance().currentGame1.currentGame[difficulty] = -1;
            StoreDataNew.getInstance().gameData1.totalLivesLost[difficulty] += GameValues.m1totalLives - mGame.lives;
            int catched = 0;
            int rare = 0;
            for (GameRound round : mGame.rounds) {
                catched += round.catched;
                rare += round.rare;
            }
            if (mGame.gameWon() && mGame.finished()) {
                StoreDataNew.getInstance().gameData1.fishesInLastGame[difficulty] = StoreDataNew.getInstance().gameData1.lastGameFish[difficulty] + StoreDataNew.getInstance().gameData1.lastGameRareFish[difficulty];
                StoreDataNew.getInstance().gameData1.totalFish[difficulty] += catched;
                StoreDataNew.getInstance().gameData1.totalRareFish[difficulty] += rare;
            } else if (mGame.finished() && !mGame.gameWon()) {
                StoreDataNew.getInstance().gameData1.totalGamesLost[difficulty] += 1;
            }
            StoreDataNew.getInstance().gameData1.lastGameFish[difficulty] = catched;
            StoreDataNew.getInstance().gameData1.lastGameRareFish[difficulty] = rare;
            StoreDataNew.getInstance().gameData1.lastGameLivesLost[difficulty] = GameValues.m1totalLives - mGame.lives;
            if (!mCatMode && mGame.lives == GameValues.m1totalLives) {
                StoreDataNew.getInstance().gameData1.gamesWithoutLivesLost[difficulty] += 1;
            } else {
                StoreDataNew.getInstance().gameData1.gamesWithoutLivesLost[difficulty] = 0;
            }
            if (mGame.lives == 1) {
                StoreDataNew.getInstance().gameData1.gamesWithOneLive[difficulty] += 1;
            } else {
                StoreDataNew.getInstance().gameData1.gamesWithOneLive[difficulty] = 0;
            }
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

        // the fixed time for drawing this frame
        final long time = System.currentTimeMillis();

        if (mMode == GameState.PLAY) {
            if (mSubMode == GameSubState.NONE) {

                // use down event as we do not drag
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        getSprites().aClicked.reset();
                        getSprites().aClicked.rect.set(
                                Float.valueOf(event.getX()).intValue() - getSprites().aClicked.rect.width() / 2,
                                Float.valueOf(event.getY()).intValue() - getSprites().aClicked.rect.height() / 2,
                                Float.valueOf(event.getX()).intValue() + getSprites().aClicked.rect.right - getSprites().aClicked.rect.left - getSprites().aClicked.rect.width() / 2,
                                Float.valueOf(event.getY()).intValue() + getSprites().aClicked.rect.bottom - getSprites().aClicked.rect.top - getSprites().aClicked.rect.height() / 2);
                        getSprites().aClicked.coord.x = getSprites().aClicked.rect.left;
                        getSprites().aClicked.coord.y = getSprites().aClicked.rect.top;
                        getSprites().clickedTime = time + GameValues.m1clickDelay;

                        for (FishAnimation a : getSprites().aFish) {
                            if (containsClick(a.rect, event.getX(), event.getY())) {
                                if (a.sharkFish) {
                                    if (containsClick(a.rect, event.getX(), event.getY(), 2.5f, 10.0f)) {
                                        // we cannot catch the shark
                                        mGame.lifeLost = true;
                                        setMode(GameState.END);
                                    }
                                } else if (!a.catched) {
                                    // we catched the fish, count it
                                    a.catched = true;
                                    mGame.getCurrentRound().catched += 1;
                                    if (a.rareFish) {
                                        mGame.getCurrentRound().rare += 1;
                                    }

                                    a.frameTime = time + GameValues.m1clickDelay;

                                    // only allow one fish to be catched per click
                                    break;
                                }
                            }
                        }
                        if (containsClick(getSprites().rBtnPause, event.getX(), event.getY())) {
                            setSubMode(GameSubState.PAUSE);
                            blocked = true;
                        }
                        break;
                }
            }
        } else if (mMode == GameState.END && !mGame.gameWon()) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    if (containsClick(getSprites().rBtnExtensionLife, event.getX(), event.getY()) && mExtensions[Constants.EXTENSION_NUMBER_LIFE] > 0) {
                        triggerExtension(Constants.EXTENSION_ITEM_LIFE, Constants.EXTENSION_NUMBER_LIFE);
                    }
                    if (containsClick(getSprites().rBtnResume, event.getX(), event.getY()) && mGame.lives > 0) {
                        setSubMode(GameSubState.NONE);
                    }
                    if (containsClick(getSprites().rBtnBack, event.getX(), event.getY())) {
                        delayedActionHandler(Constants.ACTION_HOME, Constants.ACTION_HOME);
                    }
                    break;
            }
        } else if (mMode == GameState.END && mGame.gameWon()) {
            delayedActionHandler(Constants.ACTION_HOME, Constants.ACTION_HOME);
        }

        if (!blocked && mSubMode == GameSubState.PAUSE) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    if (containsClick(getSprites().rBtnResume, event.getX(), event.getY())) {
                        setSubMode(GameSubState.NONE);
                        musicStart();
                    }
                    if (containsClick(getSprites().rBtnExtensionLife, event.getX(), event.getY())) {
                        triggerExtension(Constants.EXTENSION_ITEM_LIFE, Constants.EXTENSION_NUMBER_LIFE);
                    }
                    if (containsClick(getSprites().rBtnExtensionTime, event.getX(), event.getY())) {
                        triggerExtension(Constants.EXTENSION_ITEM_TIME, Constants.EXTENSION_NUMBER_TIME);
                    }
                    if (containsClick(getSprites().rBtnBack, event.getX(), event.getY())) {
                        delayedActionHandler(Constants.ACTION_HOME, Constants.ACTION_HOME);
                    }
                    break;
            }
        }


        return true;
    }


    private void triggerExtension(String extName, int extNumber) {
        Extensions ext = SystemHelper.getExtensions(extName);
        if (ext.amount > -1) {
            ext.amount -= 1;
            SystemHelper.setExtensions(ext);
            mExtensions[extNumber] -= 1;
            DBDriver.getInstance().store(ext);

            switch (extNumber) {
                // apply immediate effects
                case Constants.EXTENSION_NUMBER_TIME:
                    mGame.getCurrentRound().time += GameValues.EXPANSION_TIME_ADD;
                    break;
                case Constants.EXTENSION_NUMBER_LIFE:
                    mGame.lives = GameValues.m1totalLives;
                    break;
            }
        }
    }

    private void clearSharks() {
        List<Animation> removals = new ArrayList<Animation>();
        for (FishAnimation a : getSprites().aFish) {
            if (a.sharkFish) {
                removals.add(a);
            }
        }
        if (removals.size() > 0) {
            getSprites().aFish.removeAll(removals);
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

                hasRare = false;
                hasShark = false;
                // check fishes here
                for (FishAnimation a : getSprites().aFish) {
                    // check if rare fish is available
                    if (a.rareFish) {
                        hasRare = true;
                    }
                    // check if shark fish is available
                    if (a.sharkFish) {
                        hasShark = true;
                    }
                }

                // create missing fishes
                if (GameValues.m1fishesPerRound[mGame.getCurrentRound().round] - getSprites().aFish.size() > 0) {
                    createFishes(GameValues.m1fishesPerRound[mGame.getCurrentRound().round] - getSprites().aFish.size(), time);
                }

                // clicked - fish swipe paw animation
                if (getSprites().clickedTime > time) {
                    // draw clicked image if it is visible in the time frame
                    getSprites().aClicked.nextFrame(time);
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
                    removals.clear();
                }

                // cat animation
                if (getSprites().aCatEyes != null && getSprites().aCatEyes.frameTime <= time) {
                    final int frame = getSprites().aCatEyes.frame;
                    getSprites().aCatEyes.nextFrame(time);
                    if (getSprites().aCatEyes.frame < frame) {
                        getSprites().aCatEyes.frameTime = time + GameValues.m1catEyeDelay;
                        getSprites().aCatEyes.active = false;
                    } else {
                        getSprites().aCatEyes.active = true;
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
                        final int fishMovement = a.ltr ? ScreenKit.scaleWidth(GameValues.m1fishMovement, screenWidth) : ScreenKit.scaleWidth(GameValues.m1fishMovement, screenWidth) * -1;
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

                    // shark catches cat
//				if(!mCatMode && getSprites().gCat != null && a.sharkFish && ((a.ltr && a.rect.right > getSprites().gCat[0].image.getBounds().centerX()) || (!a.ltr && a.rect.left < getSprites().gCat[0].image.getBounds().centerX())) 
//						&& catHiding == 0) {
//					if(a.ltr && a.rect.right - ScreenKit.scaleWidth(100, screenWidth) < getSprites().gCat[0].image.getBounds().centerX()
//							|| !a.ltr && a.rect.left + ScreenKit.scaleWidth(100, screenWidth) > getSprites().gCat[0].image.getBounds().centerX()) {
//						setMode(GameState.END);
//				        mGame.lifeLost = true;
//					}
//				}


                }
                // remove rare and shark fishes
                if (removals.size() > 0) {
                    getSprites().aFish.removeAll(removals);
                    for (FishAnimation fa : removals) {
                        clearFishGraphic(fa);
                    }
                    removals.clear();
                }
            }
        }

    }

    private void renderFishNumber(Canvas canvas, float logoWidth, float logoHeight, int total) {
        drawTextUnbounded(canvas, getSprites().rPoints, Integer.toString(total));
    }

    private void renderLiveNumber(Canvas canvas, float logoWidth, float logoHeight, int total) {
        drawTextUnbounded(canvas, getSprites().rLives, Integer.toString(total));
    }

    public void setBonus(boolean b) {
        bonus = b;
    }

    @Override
    public void doInitThread(long time) {
        super.sprites = new GameViewSpritesMode1();

        mMode = GameState.NONE;
        mSubMode = GameSubState.NONE;

        // button backgrounds
        getSprites().gButton = Renderkit.loadButtonGraphic(mContext.getResources(), R.drawable.button_background, 0, 0, EngineConstants.ACTION_NONE);
        getSprites().gButtonOverlay = Renderkit.loadGraphic(mContext.getResources(), R.drawable.button_background, 0, 0);

        getSprites().gBackground = new Graphic(mContext.getResources().getDrawable(R.drawable.bg_classic_game1));
        getSprites().gBackground.image.setBounds(0, 0, screenWidth, realScreenHeight - realScreenHeight / 100 * 85);

        getSprites().gBackground2 = new Graphic(mContext.getResources().getDrawable(R.drawable.bg_classic_game2));
        getSprites().gBackground2.image.setBounds(0, realScreenHeight - realScreenHeight / 100 * 49, screenWidth, realScreenHeight);

        Shader shader = new LinearGradient(0, 0, 0, realScreenHeight, Color.parseColor("#aabbd7"), Color.parseColor("#69a3c9"), TileMode.CLAMP);
        getSprites().pBackground = new Paint();
        getSprites().pBackground.setShader(shader);
        getSprites().rBackground = new Rect(0, 0, screenWidth, realScreenHeight);


        getSprites().rMessage = getSprites().gButton.image.copyBounds();
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.CENTER, 0.6f, 0, 0, getSprites().rMessage);
        getSprites().rMessage2 = getSprites().gButton.image.copyBounds();
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.CENTER_TOP, 0.5f, 0, 190, getSprites().rMessage2);
        getSprites().rMessage2.bottom -= getSprites().rMessage2.height() / 2;

        getSprites().aFish = new ArrayList<FishAnimation>();
        getSprites().fishGraphics = new HashMap<String, Graphic[]>();

        mCatMode = StoreDataNew.getInstance().inventory.catMode;

        if (mGame == null) {
            createGame();
        }

        getSprites().gClickAnim = new Graphic[3];
        getSprites().gClickAnim[0] = Renderkit.loadGraphic(mContext.getResources(), R.drawable.clicked_m0, 0, 0);
        getSprites().gClickAnim[1] = Renderkit.loadGraphic(mContext.getResources(), R.drawable.clicked_m1, 0, 0);
        getSprites().gClickAnim[2] = Renderkit.loadGraphic(mContext.getResources(), R.drawable.clicked_m2, 0, 0);

        getSprites().aClicked = new Animation();
        AnimationKit.addAnimation(getSprites().aClicked, 0, 100);
        AnimationKit.addAnimation(getSprites().aClicked, 1, 100);
        AnimationKit.addAnimation(getSprites().aClicked, 2, 500);
        getSprites().aClicked.rect.set(getSprites().gClickAnim[0].image.getBounds());
        getSprites().clickedTime = 0;

        if (!mCatMode) {
            getSprites().gCat = new Graphic[1];
            getSprites().gCat[0] = Renderkit.loadGraphic(mContext.getResources(), R.drawable.cat, 0, 0);

            getSprites().gCatEyesAnim = new Graphic[3];
            getSprites().gCatEyesAnim[0] = Renderkit.loadGraphic(mContext.getResources(), R.drawable.cat_eyes1, 0, 0);
            getSprites().gCatEyesAnim[1] = Renderkit.loadGraphic(mContext.getResources(), R.drawable.cat_eyes2, 0, 0);
            getSprites().gCatEyesAnim[2] = Renderkit.loadGraphic(mContext.getResources(), R.drawable.cat_eyes3, 0, 0);

            getSprites().aCatEyes = new Animation();
            AnimationKit.addAnimation(getSprites().aCatEyes, 0, 50);
            AnimationKit.addAnimation(getSprites().aCatEyes, 1, 50);
            AnimationKit.addAnimation(getSprites().aCatEyes, 2, 50);
            AnimationKit.addAnimation(getSprites().aCatEyes, 1, 50);
            AnimationKit.addAnimation(getSprites().aCatEyes, 0, 50);
            getSprites().aCatEyes.frameTime = time + GameValues.m1catEyeDelay;
            getSprites().aCatEyes.active = false;

            initCatPhysics();
        }

        getSprites().gIconCat = Renderkit.loadGraphic(mContext.getResources(), R.drawable.cathead, 0, 0);
        getSprites().gIconFish = Renderkit.loadGraphic(mContext.getResources(), R.drawable.fishhead, 0, 0);

        ScreenKit.scaleImage(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.09f, 10, 10, getSprites().gIconFish);
        ScreenKit.scaleImage(screenWidth, screenHeight, ScreenPosition.TOP_RIGHT, 0.08f, 160, 10, getSprites().gIconCat);

        // collect stored data
        updateExtensions();

        getSprites().rBtnBack = getSprites().gButton.image.copyBounds();
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.BOTTOM_LEFT, 0.30f, 50, 25, getSprites().rBtnBack);

        getSprites().rBtnExtensionLife = getSprites().gButton.image.copyBounds();
        getSprites().rBtnExtensionTime = getSprites().gButton.image.copyBounds();
        getSprites().rBtnPause = getSprites().gButton.image.copyBounds();
        getSprites().rBtnResume = getSprites().gButton.image.copyBounds();
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.BOTTOM_LEFT, 0.4f, 280, 170, getSprites().rBtnExtensionLife);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.BOTTOM_LEFT, 0.4f, 20, 170, getSprites().rBtnExtensionTime);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.3f, 20, 100, getSprites().rBtnPause);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.3f, 20, 100, getSprites().rBtnResume);

        getSprites().rBtnPause.bottom -= getSprites().rBtnPause.height() / 3;
        getSprites().rBtnResume.bottom -= getSprites().rBtnResume.height() / 3;
        getSprites().rBtnExtensionTime.bottom -= getSprites().rBtnExtensionTime.height() / 3;
        getSprites().rBtnExtensionLife.bottom -= getSprites().rBtnExtensionLife.height() / 3;

        getSprites().rPoints = getSprites().gButton.image.copyBounds();
        getSprites().rTime = getSprites().gButton.image.copyBounds();
        getSprites().rLives = getSprites().gButton.image.copyBounds();
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.3f, 0, 0, getSprites().rPoints);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_RIGHT, 0.3f, 0, 0, getSprites().rTime);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_RIGHT, 0.3f, 0, 0, getSprites().rLives);
        getSprites().rPoints.right -= getSprites().rPoints.width() / 3;
        getSprites().rTime.right -= getSprites().rTime.width() / 3;
        getSprites().rLives.right -= getSprites().rLives.width() / 3;
        ScreenKit.moveRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 45, 5, getSprites().rPoints);
        ScreenKit.moveRect(screenWidth, screenHeight, ScreenPosition.TOP_RIGHT, 10, 5, getSprites().rTime);
        ScreenKit.moveRect(screenWidth, screenHeight, ScreenPosition.TOP_RIGHT, 60, 5, getSprites().rLives);

//      // load fish graphics into memory
//      for(int drawFish = 0; drawFish<GameValues.fishGroups.length; drawFish++) {
//          int maxJ = drawFish == 0 ? 8 : 4;
//          if(drawFish == 0) {
//              getSprites().gFishLTR = new Graphic[GameValues.fishGroups[drawFish].length * maxJ/2];
//              getSprites().gFishRTL = new Graphic[GameValues.fishGroups[drawFish].length * maxJ/2];
//          } else if(drawFish == 1) {
//              getSprites().gRareFishLTR = new Graphic[GameValues.fishGroups[drawFish].length * maxJ/2];
//              getSprites().gRareFishRTL = new Graphic[GameValues.fishGroups[drawFish].length * maxJ/2];
//          } else {
//              getSprites().gSharkFishLTR = new Graphic[GameValues.fishGroups[drawFish].length * maxJ/2];
//              getSprites().gSharkFishRTL = new Graphic[GameValues.fishGroups[drawFish].length * maxJ/2];
//          }
//          
//          for(int s=0,i=0; i<GameValues.fishGroups[drawFish].length; i++) {
//              // determine width of fish to automatically scale all fish
//              final int fishWidth = Float.valueOf(screenWidth * (drawFish > 1 ? GameValues.shark[i].screenWidthScaling : drawFish == 1 ? GameValues.rareFishes[i].screenWidthScaling : GameValues.fishes[i].screenWidthScaling)).intValue();
//              for(int j=0; j<maxJ/2; j++,s++) {
//                  if(drawFish == 0) {
//                      getSprites().gFishLTR[s] = Renderkit.loadGraphic(mContext.getResources(), GameValues.fishGroups[drawFish][i][j], 0, 0);
//                      getSprites().gFishLTR[s].image.setBounds(0, 0, fishWidth, ((fishWidth * (getSprites().gFishLTR[s].image.getBounds().bottom - getSprites().gFishLTR[s].image.getBounds().top) / getSprites().gFishLTR[s].image.getBounds().right)));
//                  } else if(drawFish == 1) {
//                      getSprites().gRareFishLTR[s] = Renderkit.loadGraphic(mContext.getResources(), GameValues.fishGroups[drawFish][i][j], 0, 0);
//                      getSprites().gRareFishLTR[s].image.setBounds(0, 0, fishWidth, ((fishWidth * (getSprites().gRareFishLTR[s].image.getBounds().bottom - getSprites().gRareFishLTR[s].image.getBounds().top) / getSprites().gRareFishLTR[s].image.getBounds().right)));
//                  } else {
//                      getSprites().gSharkFishLTR[s] = Renderkit.loadGraphic(mContext.getResources(), GameValues.fishGroups[drawFish][i][j], 0, 0);
//                      getSprites().gSharkFishLTR[s].image.setBounds(0, 0, fishWidth, ((fishWidth * (getSprites().gSharkFishLTR[s].image.getBounds().bottom - getSprites().gSharkFishLTR[s].image.getBounds().top) / getSprites().gSharkFishLTR[s].image.getBounds().right)));
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
//                  } else {
//                      getSprites().gSharkFishRTL[s] = Renderkit.loadGraphic(mContext.getResources(), GameValues.fishGroups[drawFish][i][j], 0, 0);
//                      getSprites().gSharkFishRTL[s].image.setBounds(0, 0, fishWidth, ((fishWidth * (getSprites().gSharkFishRTL[s].image.getBounds().bottom - getSprites().gSharkFishRTL[s].image.getBounds().top) / getSprites().gSharkFishRTL[s].image.getBounds().right)));
//                  }
//              }
//          }
//      }

        mRounds = 0;
    }

    private void updateExtensions() {
        mExtensions = new int[GameValues.extensionName.length];
        int i = 0;
        for (; i < mExtensions.length; i++) {
            Extensions ext = SystemHelper.getExtensions(GameValues.extensionName[i]);
            mExtensions[i] = ext.amount + 1;
        }
    }

    @Override
    public void doUpdateRenderState() {
        final long time = System.currentTimeMillis();

        if (delay > 0 && lastTime > 0) {
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
                // prepare message
                if (delay <= 0) {
                    mGame.lifeLost = false;
                    setMode(GameState.PLAY);
                }
                if (mGame.lives <= 0) {
                    setMode(GameState.END);
                }
            }
            break;
            case PLAY: {
                if (mSubMode == GameSubState.NONE) {
                    // calculate game time
                    if (delay <= 0 && lastTime > 0) {
                        mGame.getCurrentRound().time -= time - lastTime;
                    }
                }

                // update graphic positions
                updatePhysics();

                // end game
                if (mGame.getCurrentRound().time < 0) {
                    setMode(GameState.END);
                }
            }
            break;
            case END: {
                // round time expired - switch to next round
                if (mGame.hasGame() && mGame.getCurrentRound().time < 0) {
                    mRounds++;
                    // trigger next round only
                    mGame.getNextRound();
                    if (mGame.getCurrentRound() != null) {
                        delay = GameValues.gameRoundDelay;
                        setMode(GameState.INIT);
                        // remove all fished to re-create the new round
                        getSprites().aFish.clear();
                        saveGameState(); // save on round switch
                    } else {
//                    setMode(GameState.END);
                        // update statistics
                        updateStatistics();
                    }
//                if(mRounds >= Constants.ROUNDS_PER_AD && getScreen().hasAdReady()) {
//                    mRounds = 0;
                    //                 getScreen().showInterstitialAd();
//                }
                }
                if (mGame.lifeLost) {

                    // shark catched - reduce life
                    Configuration c = SystemHelper.getConfiguration(Constants.CONFIG_VIBRATION, Constants.DEFAULT_CONFIG_VIBRATION);
                    if (Boolean.valueOf(c.value).booleanValue()) {
                        // Get instance of Vibrator from current Context
                        Vibrator v = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
                        // Vibrate for 1000 milliseconds
                        v.vibrate(1000);
                    }

                    delay = GameValues.m1liveLostDelay;
                    if (mGame.immunity > 0) {
                        mGame.immunity -= 1;
                    } else {
                        mGame.lives -= 1;
                    }
                    if (mGame.lives <= 0) {
                        // update statistics
//                  updateStatistics();
                        mGame.lifeLost = false;
                    } else {
                        setMode(GameState.INIT);
                    }
                    clearSharks();
//                mGame.lifeLost = false;
                }

                // after a life has been lost
                if (!mGame.finished() && mGame.lives > 0 && delay <= 0) {
                    setMode(GameState.INIT);
                }
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
        if (!mCatMode && getSprites().gBackground != null) {
            getSprites().gBackground.image.draw(canvas);
        }
        if (getSprites().gBackground2 != null) {
            getSprites().gBackground2.image.draw(canvas);
        }

        if (mMode == GameState.END && mGame.finished() && !mGame.gameWon()) {
            drawText(canvas, getSprites().rMessage, mContext.getString(R.string.message_gameover), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
            drawText(canvas, getSprites().rMessage2, mContext.getString(R.string.message_continue), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));

            if (mExtensions[Constants.EXTENSION_NUMBER_LIFE] > 0) {
                choiceBaseDraw(canvas, getSprites().rBtnExtensionLife, getSprites().gButtonOverlay, getSprites().gButton, true, false, GameValues.cFilterGreen);
                drawText(canvas, getSprites().rBtnExtensionLife, MessageFormat.format(mContext.getString(R.string.menubutton_ext_lives), mExtensions[Constants.EXTENSION_NUMBER_LIFE]), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
            }

            if (mGame != null && mGame.lives > 0) {
                choiceBaseDraw(canvas, getSprites().rBtnResume, getSprites().gButtonOverlay, getSprites().gButton, true, false, GameValues.cFilterGreen);
                drawText(canvas, getSprites().rBtnResume, mContext.getString(R.string.menubutton_resume), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
            }

            choiceBaseDraw(canvas, getSprites().rBtnBack, getSprites().gButtonOverlay, getSprites().gButton, activeButton, Constants.ACTION_HOME, GameValues.cFilterGreen);
            drawText(canvas, getSprites().rBtnBack, mContext.getString(R.string.menubutton_end), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight), GameValues.cFilterRed);
        }
        if (mMode == GameState.END && mGame.gameWon()) {
            drawText(canvas, getSprites().rMessage, mContext.getString(R.string.message_finished), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));

            int totalFish = 0;
            for (GameRound round : mGame.rounds) {
                totalFish += round.catched;
            }

            if (!mCatMode) {
                getSprites().gIconCat.image.draw(canvas);
            }
            getSprites().gIconFish.image.draw(canvas);

            renderFishNumber(canvas, standardNumberWidth * 1.1f, standardNumberHeight * 1.1f, totalFish);
            renderLiveNumber(canvas, standardNumberWidth * 1.1f, standardNumberHeight * 1.1f, mGame.lives);

            choiceBaseDraw(canvas, getSprites().rBtnBack, getSprites().gButtonOverlay, getSprites().gButton, activeButton, Constants.ACTION_HOME, GameValues.cFilterGreen);
            drawText(canvas, getSprites().rBtnBack, mContext.getString(R.string.menubutton_end), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight), GameValues.cFilterRed);
        }

        if ((mMode == GameState.END || mMode == GameState.READY) && mGame.lifeLost) {
            drawText(canvas, getSprites().rMessage, mContext.getString(R.string.message_lifelost), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
        }

        // draw round number
        if (mMode == GameState.READY && mGame.hasGame() && !mGame.lifeLost) {
            drawText(canvas, getSprites().rMessage, mContext.getString(R.string.message_round) + " " + (mGame.getCurrentRound().round + 1), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
        }

//        if(mMode == GameState.INIT || mMode == GameState.NONE) {
//            drawText(canvas, getSprites().rMessage, mContext.getString(R.string.message_ready), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
//        }

        // the fixed time for drawing this frame
        final long time = System.currentTimeMillis();

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

            if (mGame.hasGame() && mGame.getCurrentRound() != null) {

                if (!mCatMode) {
                    getSprites().gIconCat.image.draw(canvas);
                }
                getSprites().gIconFish.image.draw(canvas);

                renderFishNumber(canvas, standardNumberWidth * 1.1f, standardNumberHeight * 1.1f, mGame.getCurrentRound().catched);
                if (!mCatMode) {
                    // Draw Lives
                    renderLiveNumber(canvas, standardNumberWidth * 1.1f, standardNumberHeight * 1.1f, mGame.lives);
                }

                // Draw Time
                long minutes = (mGame.getCurrentRound().time) / 60000;
                long seconds = (mGame.getCurrentRound().time) / 1000;
                if (seconds > 60) {
                    seconds = seconds - (minutes * 60);
                }
                if (seconds == 60) {
                    seconds = 0;
                }
                String strValue = MessageFormat.format("{0}:{1,number,00}", minutes, seconds);
                drawTextUnbounded(canvas, getSprites().rTime, strValue, GameValues.cFilterBlue);
            }

            if (!mCatMode && getSprites().gCat != null) {
                canvas.save();
                // blinking eyes
                if (getSprites().aCatEyes.active) {
                    getSprites().gCatEyesAnim[getSprites().aCatEyes.nextFrame().gReference].image.setBounds(getSprites().aCatEyes.rect);
                    getSprites().gCatEyesAnim[getSprites().aCatEyes.nextFrame().gReference].image.draw(canvas);
                } else {
                    // normal cat
                    getSprites().gCat[0].image.setBounds(getSprites().aCatEyes.rect);
                    getSprites().gCat[0].image.draw(canvas);
                }
                canvas.restore();
            }

            if (mSubMode == GameSubState.NONE) {
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

                if (getSprites().clickedTime > time && getSprites().aClicked != null) {
                    // draw clicked image if it is visible in the time frame
                    canvas.save();
                    getSprites().aClicked.rect.offsetTo(getSprites().aClicked.coord.x, getSprites().aClicked.coord.y);
                    getSprites().gClickAnim[getSprites().aClicked.nextFrame().gReference].image.setBounds(getSprites().aClicked.rect);
                    getSprites().gClickAnim[getSprites().aClicked.nextFrame().gReference].image.draw(canvas);
                    canvas.restore();
                }

                choiceBaseDraw(canvas, getSprites().rBtnPause, getSprites().gButtonOverlay, getSprites().gButton, true, false, GameValues.cFilterGreen);
                drawText(canvas, getSprites().rBtnPause, mContext.getString(R.string.menubutton_pause), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
            }
            if (mSubMode == GameSubState.PAUSE) {
                drawText(canvas, getSprites().rMessage, mContext.getString(R.string.message_pause), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));

                choiceBaseDraw(canvas, getSprites().rBtnExtensionLife, getSprites().gButtonOverlay, getSprites().gButton, true, false, GameValues.cFilterGreen);
                if (mExtensions[Constants.EXTENSION_NUMBER_LIFE] > 0) {
                    drawText(canvas, getSprites().rBtnExtensionLife, MessageFormat.format(mContext.getString(R.string.menubutton_ext_lives), mExtensions[Constants.EXTENSION_NUMBER_LIFE]), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
                } else {
                    drawText(canvas, getSprites().rBtnExtensionLife, MessageFormat.format(mContext.getString(R.string.menubutton_ext_lives), mExtensions[Constants.EXTENSION_NUMBER_LIFE]), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight), GameValues.cFilterRed);
                }

                choiceBaseDraw(canvas, getSprites().rBtnExtensionTime, getSprites().gButtonOverlay, getSprites().gButton, true, false, GameValues.cFilterGreen);
                if (mExtensions[Constants.EXTENSION_NUMBER_TIME] > 0) {
                    drawText(canvas, getSprites().rBtnExtensionTime, MessageFormat.format(mContext.getString(R.string.menubutton_ext_time), mExtensions[Constants.EXTENSION_NUMBER_TIME]), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
                } else {
                    drawText(canvas, getSprites().rBtnExtensionTime, MessageFormat.format(mContext.getString(R.string.menubutton_ext_time), mExtensions[Constants.EXTENSION_NUMBER_TIME]), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight), GameValues.cFilterRed);
                }

                choiceBaseDraw(canvas, getSprites().rBtnResume, getSprites().gButtonOverlay, getSprites().gButton, true, false, GameValues.cFilterGreen);
                drawText(canvas, getSprites().rBtnResume, mContext.getString(R.string.menubutton_resume), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));

                choiceBaseDraw(canvas, getSprites().rBtnBack, getSprites().gButtonOverlay, getSprites().gButton, activeButton, Constants.ACTION_HOME, GameValues.cFilterGreen);
                drawText(canvas, getSprites().rBtnBack, mContext.getString(R.string.menubutton_back), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
            }

        }

    }

    @Override
    public void reset() {
    }

}

