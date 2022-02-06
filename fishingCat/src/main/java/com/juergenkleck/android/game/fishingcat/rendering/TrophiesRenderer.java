package com.juergenkleck.android.game.fishingcat.rendering;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.juergenkleck.android.game.fishingcat.SystemHelper;
import com.juergenkleck.android.game.fishingcat.engine.GameValues;
import com.juergenkleck.android.game.fishingcat.rendering.kits.Renderkit;
import com.juergenkleck.android.game.fishingcat.sprites.HomeViewSprites;
import com.juergenkleck.android.game.fishingcat.storage.StoreDataNew;
import com.juergenkleck.android.gameengine.EngineConstants;
import com.juergenkleck.android.gameengine.rendering.kits.ScreenKit;
import com.juergenkleck.android.gameengine.rendering.kits.ScreenKit.ScreenPosition;
import com.juergenkleck.android.gameengine.rendering.objects.Graphic;

/**
 * Android app - FishingCat
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class TrophiesRenderer extends FishRendererTemplate {

    long lastTime = 0l;

    int mSelection;
    int mTotalTrophies;
    int mMod = 0;
    int helpId;

    public TrophiesRenderer(Context context, Properties p) {
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
                    if (containsClick(getSprites().rBtnTrophiesBronze, event.getX(), event.getY())) {
                        mSelection = 0;
                        mMod = 0;
                    }
                    if (containsClick(getSprites().rBtnTrophiesSilver, event.getX(), event.getY())) {
                        mSelection = 1;
                        mMod = mTotalTrophies;
                    }
                    if (containsClick(getSprites().rBtnTrophiesGold, event.getX(), event.getY())) {
                        mSelection = 2;
                        mMod = mTotalTrophies * 2;
                    }
                    if (containsClick(getSprites().rBtnBack, event.getX(), event.getY())) {
                        delayedActionHandler(EngineConstants.ACTION_OPTIONS, EngineConstants.ACTION_OPTIONS);
                    }
                    if (containsClick(getSprites().rBtnShowHelp, event.getX(), event.getY())) {
                        showHelp();
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

        drawText(canvas, getSprites().rMsgTrophies, mContext.getString(R.string.message_trophies), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));

        choiceBaseDraw(canvas, getSprites().rBtnTrophiesBronze, getSprites().gButtonOverlay, getSprites().gButton, mSelection, 0, GameValues.cFilterGreen);
        drawText(canvas, getSprites().rBtnTrophiesBronze, mContext.getString(R.string.message_trophy_bronze), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
        choiceBaseDraw(canvas, getSprites().rBtnTrophiesSilver, getSprites().gButtonOverlay, getSprites().gButton, mSelection, 1, GameValues.cFilterGreen);
        drawText(canvas, getSprites().rBtnTrophiesSilver, mContext.getString(R.string.message_trophy_silver), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
        choiceBaseDraw(canvas, getSprites().rBtnTrophiesGold, getSprites().gButtonOverlay, getSprites().gButton, mSelection, 2, GameValues.cFilterGreen);
        drawText(canvas, getSprites().rBtnTrophiesGold, mContext.getString(R.string.message_trophy_gold), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));

        for (int i = 0; i < mTotalTrophies; i++) {
            canvas.save();
            Rect bounds = getSprites().mTrophiesCategory[SystemHelper.getTrophy(i + mMod, false, false)].image.copyBounds();
            bounds.offsetTo(getSprites().rMsgTrophyNumber[i].left - scaleWidth(30), getSprites().rMsgTrophyNumber[i].top - scaleHeight(5));
            getSprites().mTrophiesCategory[SystemHelper.getTrophy(i + mMod, false, false)].image.setBounds(bounds);
            getSprites().mTrophiesCategory[SystemHelper.getTrophy(i + mMod, false, false)].image.draw(canvas);

            bounds = getSprites().mTrophiesLogo[SystemHelper.getTrophy(i + mMod, false, true)].image.copyBounds();
            bounds.offsetTo(getSprites().rMsgTrophyNumber[i].left - scaleWidth(25), getSprites().rMsgTrophyNumber[i].top + scaleHeight(13));
            getSprites().mTrophiesLogo[SystemHelper.getTrophy(i + mMod, false, true)].image.setBounds(bounds);
            getSprites().mTrophiesLogo[SystemHelper.getTrophy(i + mMod, false, true)].image.draw(canvas);

            canvas.restore();
            drawText(canvas, getSprites().rMsgTrophyNumber[i], mContext.getString(SystemHelper.getTrophy(i + mMod, true, false)), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));

            if (GameValues.GAMESYSTEM_ISLAND == StoreDataNew.getInstance().inventory.gameSystem) {
                if (StoreDataNew.getInstance().gameData2.trophies != null && StoreDataNew.getInstance().gameData2.trophies[StoreDataNew.getInstance().gameData2.difficulty].length > i + mMod && !StoreDataNew.getInstance().gameData2.trophies[StoreDataNew.getInstance().gameData2.difficulty][i + mMod]) {
                    getSprites().gNotYet.image.setBounds(getSprites().mTrophiesCategory[SystemHelper.getTrophy(i + mMod, false, false)].image.copyBounds());
                    getSprites().gNotYet.image.draw(canvas);
                }
            } else {
                if (StoreDataNew.getInstance().gameData1.trophies != null && StoreDataNew.getInstance().gameData1.trophies[StoreDataNew.getInstance().gameData1.difficulty].length > i + mMod && !StoreDataNew.getInstance().gameData1.trophies[StoreDataNew.getInstance().gameData1.difficulty][i + mMod]) {
                    getSprites().gNotYet.image.setBounds(getSprites().mTrophiesCategory[SystemHelper.getTrophy(i + mMod, false, false)].image.copyBounds());
                    getSprites().gNotYet.image.draw(canvas);
                }
            }
        }


        choiceBaseDraw(canvas, getSprites().rBtnShowHelp, getSprites().gButtonOverlay, getSprites().gButton, true, false, GameValues.cFilterGreen);
        drawText(canvas, getSprites().rBtnShowHelp, mContext.getString(R.string.menubutton_show_help), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));

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

        getSprites().rBtnShowHelp = getSprites().gButton.image.copyBounds();
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.BOTTOM_RIGHT, 0.30f, 50, 25, getSprites().rBtnShowHelp);

        mTotalTrophies = GameValues.GAMESYSTEM_ISLAND == StoreDataNew.getInstance().inventory.gameSystem ? 6 : 9;

        getSprites().rMsgTrophies = getSprites().gButton.image.copyBounds();
        getSprites().rBtnTrophiesBronze = getSprites().gButton.image.copyBounds();
        getSprites().rBtnTrophiesSilver = getSprites().gButton.image.copyBounds();
        getSprites().rBtnTrophiesGold = getSprites().gButton.image.copyBounds();
        getSprites().rMsgTrophyNumber = new Rect[mTotalTrophies];
        for (int i = 0; i < mTotalTrophies; i++) {
            getSprites().rMsgTrophyNumber[i] = getSprites().gButton.image.copyBounds();
        }
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.CENTER_TOP, 0.4f, 0, 20, getSprites().rMsgTrophies);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.25f, 30, 190, getSprites().rBtnTrophiesBronze);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.25f, 200, 190, getSprites().rBtnTrophiesSilver);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.25f, 370, 190, getSprites().rBtnTrophiesGold);

        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.25f, 50, 320, getSprites().rMsgTrophyNumber[0]);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.25f, 225, 320, getSprites().rMsgTrophyNumber[1]);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.25f, 400, 320, getSprites().rMsgTrophyNumber[2]);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.25f, 50, 470, getSprites().rMsgTrophyNumber[3]);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.25f, 225, 470, getSprites().rMsgTrophyNumber[4]);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.25f, 400, 470, getSprites().rMsgTrophyNumber[5]);
        if (GameValues.GAMESYSTEM_ISLAND == StoreDataNew.getInstance().inventory.gameSystem) {
        } else {
            ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.25f, 50, 620, getSprites().rMsgTrophyNumber[6]);
            ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.25f, 225, 620, getSprites().rMsgTrophyNumber[7]);
            ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.25f, 400, 620, getSprites().rMsgTrophyNumber[8]);
        }

        getSprites().rBtnTrophiesBronze.bottom -= getSprites().rBtnTrophiesBronze.height() / 5;
        getSprites().rBtnTrophiesSilver.bottom -= getSprites().rBtnTrophiesSilver.height() / 5;
        getSprites().rBtnTrophiesGold.bottom -= getSprites().rBtnTrophiesGold.height() / 5;

        getSprites().mTrophiesLogo = new Graphic[3];
        getSprites().mTrophiesLogo[0] = Renderkit.loadGraphic(mContext.getResources(), R.drawable.trophy_fish, 0, 0);
        getSprites().mTrophiesLogo[1] = Renderkit.loadGraphic(mContext.getResources(), R.drawable.trophy_cat, 0, 0);
        getSprites().mTrophiesLogo[2] = Renderkit.loadGraphic(mContext.getResources(), R.drawable.trophy_cup, 0, 0);

        getSprites().mTrophiesCategory = new Graphic[3];
        getSprites().mTrophiesCategory[0] = Renderkit.loadGraphic(mContext.getResources(), R.drawable.trophy_bronze, 0, 0);
        getSprites().mTrophiesCategory[1] = Renderkit.loadGraphic(mContext.getResources(), R.drawable.trophy_silver, 0, 0);
        getSprites().mTrophiesCategory[2] = Renderkit.loadGraphic(mContext.getResources(), R.drawable.trophy_gold, 0, 0);

        ScreenKit.scaleImage(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.30f, 0, 0, getSprites().mTrophiesCategory);
        ScreenKit.scaleImage(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.05f, 0, 0, getSprites().mTrophiesLogo);

        mSelection = 0;
        mMod = 0;

        getSprites().gNotYet = Renderkit.loadGraphic(mContext.getResources(), R.drawable.trophy_not_yet, 0, 0);
    }

    private void showHelp() {
        int max = Constants.m1HelpIds.length;
        if (GameValues.GAMESYSTEM_ISLAND == StoreDataNew.getInstance().inventory.gameSystem) {
            max = Constants.m2HelpIds.length;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder
//             .setCancelable(false)
                .setPositiveButton(R.string.btn_help_close, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        if (helpId > 0) {
            builder.setNeutralButton(R.string.btn_help_back, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    helpId--;
                    showHelp();
                }
            });
        }
        if (helpId < max - 1) {
            builder.setNegativeButton(R.string.btn_help_next, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    helpId++;
                    showHelp();
                }
            });
        }

        if (GameValues.GAMESYSTEM_ISLAND == StoreDataNew.getInstance().inventory.gameSystem) {
            builder.setMessage(Constants.m2HelpIds[helpId]);
        } else {
            builder.setMessage(Constants.m1HelpIds[helpId]);
        }

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void reset() {
        // TODO Auto-generated method stub

    }

}
