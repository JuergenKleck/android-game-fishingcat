package com.juergenkleck.android.game.fishingcat.rendering;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.view.MotionEvent;

import java.util.Properties;

import com.juergenkleck.android.game.fishingcat.Constants;
import com.juergenkleck.android.game.fishingcat.R;
import com.juergenkleck.android.game.fishingcat.engine.GameValues;
import com.juergenkleck.android.game.fishingcat.rendering.kits.Renderkit;
import com.juergenkleck.android.game.fishingcat.sprites.HomeViewSprites;
import com.juergenkleck.android.game.fishingcat.storage.StoreDataNew;
import com.juergenkleck.android.gameengine.EngineConstants;
import com.juergenkleck.android.gameengine.rendering.kits.ScreenKit;
import com.juergenkleck.android.gameengine.rendering.kits.ScreenKit.ScreenPosition;

/**
 * Android app - FishingCat
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class StatisticRenderer extends FishRendererTemplate {

    long lastTime = 0l;

    int totalFish;
    int totalRareFish;
    int totalGames;
    int totalLivesLost;
    int lastGameFish;
    int lastGameRareFish;
    int lastGameLivesLost;

    public StatisticRenderer(Context context, Properties p) {
        super(context, p);
    }

    public HomeViewSprites getSprites() {
        return HomeViewSprites.class.cast(super.sprites);
    }

    @Override
    public void doStart() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void unpause() {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (delayedAction == EngineConstants.ACTION_NONE) {
            // determine button click

            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_MOVE:
                    // move
                    break;
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_UP:
                    if (containsClick(getSprites().rBtnBack, event.getX(), event.getY())) {
                        delayedActionHandler(EngineConstants.ACTION_OPTIONS, EngineConstants.ACTION_OPTIONS);
                    }
                    break;
            }

        }

        return true;
    }

    @Override
    public void doUpdateRenderState() {
        final long time = System.currentTimeMillis();

        lastTime = time;
    }

    @Override
    public void doDrawRenderer(Canvas canvas) {

        if (getSprites().gBackground != null) {
            getSprites().gBackground.image.setBounds(0, 0, screenWidth, screenHeight);
            getSprites().gBackground.image.draw(canvas);
        }
        if (getSprites().pBackground != null) {
            canvas.drawRect(getSprites().rBackground, getSprites().pBackground);
        }

        drawText(canvas, getSprites().rMsgGameTotal, mContext.getString(R.string.statistic_game_total), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
        drawText(canvas, getSprites().rMsgTotalFish, mContext.getString(R.string.statistic_total_fish), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
        drawTextUnbounded(canvas, getSprites().rValueTotalFish, Integer.toString(totalFish), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
        drawText(canvas, getSprites().rMsgTotalRareFish, mContext.getString(R.string.statistic_total_rare_fish), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
        drawTextUnbounded(canvas, getSprites().rValueTotalRareFish, Integer.toString(totalRareFish), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
        drawText(canvas, getSprites().rMsgTotalGames, mContext.getString(R.string.statistic_total_games), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
        drawTextUnbounded(canvas, getSprites().rValueTotalGames, Integer.toString(totalGames), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));


        drawText(canvas, getSprites().rMsgGameLast, mContext.getString(R.string.statistic_game_best), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
        drawText(canvas, getSprites().rMsgLastGameFish, mContext.getString(R.string.statistic_lastgame_fish), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
        drawTextUnbounded(canvas, getSprites().rValueLastGameFish, Integer.toString(lastGameFish), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
        drawText(canvas, getSprites().rMsgLastGameRareFish, mContext.getString(R.string.statistic_lastgame_rare_fish), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
        drawTextUnbounded(canvas, getSprites().rValueLastGameRareFish, Integer.toString(lastGameRareFish), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));

        if (StoreDataNew.getInstance() != null) {
            if (GameValues.GAMESYSTEM_CLASSIC == StoreDataNew.getInstance().inventory.gameSystem) {
                drawText(canvas, getSprites().rMsgTotalLivesLost, mContext.getString(R.string.statistic_total_lives_lost), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
                drawTextUnbounded(canvas, getSprites().rValueTotalLivesLost, Integer.toString(totalLivesLost), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
                drawText(canvas, getSprites().rMsgLastGameLivesLost, mContext.getString(R.string.statistic_lastgame_lives_lost), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
                drawTextUnbounded(canvas, getSprites().rValueLastGameLivesLost, Integer.toString(lastGameLivesLost), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
            }
        }


        choiceBaseDraw(canvas, getSprites().rBtnBack, getSprites().gButtonOverlay, getSprites().gButton, activeButton, EngineConstants.ACTION_OPTIONS, GameValues.cFilterGreen);
        drawText(canvas, getSprites().rBtnBack, mContext.getString(R.string.menubutton_back), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
    }


    @Override
    public void restoreGameState() {
    }

    @Override
    public void saveGameState() {
    }

    @Override
    public void doInitThread(long time) {
        super.sprites = new HomeViewSprites();

        Shader shader = new LinearGradient(0, 0, screenWidth, realScreenHeight, Color.parseColor("#bdcce3"), Color.parseColor("#0598ff"), TileMode.CLAMP);
        getSprites().pBackground = new Paint();
        getSprites().pBackground.setShader(shader);
        getSprites().rBackground = new Rect(0, 0, screenWidth, realScreenHeight);

        // button backgrounds
        getSprites().gButton = Renderkit.loadButtonGraphic(mContext.getResources(), R.drawable.button_background, 0, 0, EngineConstants.ACTION_NONE);
        getSprites().gButtonOverlay = Renderkit.loadGraphic(mContext.getResources(), R.drawable.button_background, 0, 0);

        getSprites().rBtnBack = getSprites().gButton.image.copyBounds();
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.BOTTOM_LEFT, 0.25f, 50, 25, getSprites().rBtnBack);

        getSprites().rMsgGameTotal = getSprites().gButton.image.copyBounds();
        getSprites().rMsgTotalFish = getSprites().gButton.image.copyBounds();
        getSprites().rValueTotalFish = getSprites().gButton.image.copyBounds();
        getSprites().rMsgTotalRareFish = getSprites().gButton.image.copyBounds();
        getSprites().rValueTotalRareFish = getSprites().gButton.image.copyBounds();
        getSprites().rMsgTotalGames = getSprites().gButton.image.copyBounds();
        getSprites().rValueTotalGames = getSprites().gButton.image.copyBounds();
        getSprites().rMsgTotalLivesLost = getSprites().gButton.image.copyBounds();
        getSprites().rValueTotalLivesLost = getSprites().gButton.image.copyBounds();
        getSprites().rMsgGameLast = getSprites().gButton.image.copyBounds();
        getSprites().rMsgLastGameFish = getSprites().gButton.image.copyBounds();
        getSprites().rValueLastGameFish = getSprites().gButton.image.copyBounds();
        getSprites().rMsgLastGameRareFish = getSprites().gButton.image.copyBounds();
        getSprites().rValueLastGameRareFish = getSprites().gButton.image.copyBounds();
        getSprites().rMsgLastGameLivesLost = getSprites().gButton.image.copyBounds();
        getSprites().rValueLastGameLivesLost = getSprites().gButton.image.copyBounds();

        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.4f, 0, 20, getSprites().rMsgGameTotal);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.25f, 20, 200, getSprites().rMsgTotalFish);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.23f, 140, 200, getSprites().rValueTotalFish);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.25f, 20, 360, getSprites().rMsgTotalRareFish);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.23f, 140, 360, getSprites().rValueTotalRareFish);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.25f, 20, 520, getSprites().rMsgTotalGames);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.23f, 140, 520, getSprites().rValueTotalGames);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.25f, 20, 680, getSprites().rMsgTotalLivesLost);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.23f, 140, 680, getSprites().rValueTotalLivesLost);

        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.4f, 280, 20, getSprites().rMsgGameLast);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.25f, 280, 200, getSprites().rMsgLastGameFish);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.23f, 410, 200, getSprites().rValueLastGameFish);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.25f, 280, 360, getSprites().rMsgLastGameRareFish);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.23f, 410, 360, getSprites().rValueLastGameRareFish);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.25f, 280, 520, getSprites().rMsgLastGameLivesLost);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.23f, 410, 520, getSprites().rValueLastGameLivesLost);


        if (StoreDataNew.getInstance() != null) {
            if (GameValues.GAMESYSTEM_ISLAND == StoreDataNew.getInstance().inventory.gameSystem) {
                totalFish = StoreDataNew.getInstance().gameData2.totalFish[StoreDataNew.getInstance().gameData2.difficulty];
                totalRareFish = StoreDataNew.getInstance().gameData2.totalRareFish[StoreDataNew.getInstance().gameData2.difficulty];
                totalGames = StoreDataNew.getInstance().gameData2.totalGames[StoreDataNew.getInstance().gameData2.difficulty];
                lastGameFish = StoreDataNew.getInstance().gameData2.lastGameFish[StoreDataNew.getInstance().gameData2.difficulty];
                lastGameRareFish = StoreDataNew.getInstance().gameData2.lastGameRareFish[StoreDataNew.getInstance().gameData2.difficulty];
            } else {
                totalFish = StoreDataNew.getInstance().gameData1.totalFish[StoreDataNew.getInstance().gameData1.difficulty];
                totalRareFish = StoreDataNew.getInstance().gameData1.totalRareFish[StoreDataNew.getInstance().gameData1.difficulty];
                totalGames = StoreDataNew.getInstance().gameData1.totalGames[StoreDataNew.getInstance().gameData1.difficulty];
                totalLivesLost = StoreDataNew.getInstance().gameData1.totalLivesLost[StoreDataNew.getInstance().gameData1.difficulty];
                lastGameFish = StoreDataNew.getInstance().gameData1.lastGameFish[StoreDataNew.getInstance().gameData1.difficulty];
                lastGameRareFish = StoreDataNew.getInstance().gameData1.lastGameRareFish[StoreDataNew.getInstance().gameData1.difficulty];
                lastGameLivesLost = StoreDataNew.getInstance().gameData1.lastGameLivesLost[StoreDataNew.getInstance().gameData1.difficulty];
            }
        }

    }

    @Override
    public void reset() {
        // TODO Auto-generated method stub

    }

}
