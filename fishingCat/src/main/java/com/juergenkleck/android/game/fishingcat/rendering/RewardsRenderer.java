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
public class RewardsRenderer extends FishRendererTemplate {

    long lastTime = 0l;

    String mTextBronze;
    String mTextSilver;
    String mTextGold;
    boolean mHasBronze;
    boolean mHasSilver;
    boolean mHasGold;

    public RewardsRenderer(Context context, Properties p) {
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

        drawText(canvas, getSprites().rMsgRewards, mContext.getString(R.string.message_rewards), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));

        if (getSprites().gRewardBronzeCategory != null) {
            getSprites().gRewardBronzeCategory.image.draw(canvas);
            getSprites().gRewardBronzeLogo.image.draw(canvas);
            drawText(canvas, getSprites().rMsgRewardBronze, mTextBronze, ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight), mHasBronze ? GameValues.cFilterBronze : GameValues.cFilterInactive);
            if (!mHasBronze) {
                getSprites().gNotYet.image.setBounds(getSprites().gRewardBronzeCategory.image.copyBounds());
                getSprites().gNotYet.image.draw(canvas);
            }
        }
        if (getSprites().gRewardSilverCategory != null) {
            getSprites().gRewardSilverCategory.image.draw(canvas);
            getSprites().gRewardSilverLogo.image.draw(canvas);
            drawText(canvas, getSprites().rMsgRewardSilver, mTextSilver, ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight), mHasSilver ? GameValues.cFilterSilver : GameValues.cFilterInactive);
            if (!mHasSilver) {
                getSprites().gNotYet.image.setBounds(getSprites().gRewardSilverCategory.image.copyBounds());
                getSprites().gNotYet.image.draw(canvas);
            }
        }
        if (getSprites().gRewardGoldCategory != null) {
            getSprites().gRewardGoldCategory.image.draw(canvas);
            getSprites().gRewardGoldLogo.image.draw(canvas);
            drawText(canvas, getSprites().rMsgRewardGold, mTextGold, ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight), mHasGold ? GameValues.cFilterGold : GameValues.cFilterInactive);
            if (!mHasGold) {
                getSprites().gNotYet.image.setBounds(getSprites().gRewardGoldCategory.image.copyBounds());
                getSprites().gNotYet.image.draw(canvas);
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

        getSprites().rMsgRewards = getSprites().gButton.image.copyBounds();
        getSprites().rMsgRewardBronze = getSprites().gButton.image.copyBounds();
        getSprites().rMsgRewardSilver = getSprites().gButton.image.copyBounds();
        getSprites().rMsgRewardGold = getSprites().gButton.image.copyBounds();
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.CENTER_TOP, 0.4f, 0, 20, getSprites().rMsgRewards);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.35f, 195, 250, getSprites().rMsgRewardBronze);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.35f, 195, 450, getSprites().rMsgRewardSilver);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.35f, 195, 650, getSprites().rMsgRewardGold);

        getSprites().rMsgRewardBronze.bottom -= getSprites().rMsgRewardBronze.height() / 5;
        getSprites().rMsgRewardSilver.bottom -= getSprites().rMsgRewardSilver.height() / 5;
        getSprites().rMsgRewardGold.bottom -= getSprites().rMsgRewardGold.height() / 5;
        getSprites().gRewardBronzeCategory = Renderkit.loadGraphic(mContext.getResources(), getReward(0, false, false), 0, 0);
        getSprites().gRewardBronzeLogo = Renderkit.loadGraphic(mContext.getResources(), getReward(0, false, true), 0, 0);
        mTextBronze = mContext.getString(getReward(0, true, false));
        ScreenKit.scaleImage(screenWidth, screenHeight, ScreenPosition.CENTER_TOP, 0.4f, 0, 220, getSprites().gRewardBronzeCategory);
        ScreenKit.scaleImage(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.055f, 170, 260, getSprites().gRewardBronzeLogo);

        getSprites().gRewardSilverCategory = Renderkit.loadGraphic(mContext.getResources(), getReward(1, false, false), 0, 0);
        getSprites().gRewardSilverLogo = Renderkit.loadGraphic(mContext.getResources(), getReward(1, false, true), 0, 0);
        mTextSilver = mContext.getString(getReward(1, true, false));
        ScreenKit.scaleImage(screenWidth, screenHeight, ScreenPosition.CENTER_TOP, 0.4f, 0, 420, getSprites().gRewardSilverCategory);
        ScreenKit.scaleImage(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.055f, 170, 460, getSprites().gRewardSilverLogo);

        getSprites().gRewardGoldCategory = Renderkit.loadGraphic(mContext.getResources(), getReward(2, false, false), 0, 0);
        getSprites().gRewardGoldLogo = Renderkit.loadGraphic(mContext.getResources(), getReward(2, false, true), 0, 0);
        mTextGold = mContext.getString(getReward(2, true, false));
        ScreenKit.scaleImage(screenWidth, screenHeight, ScreenPosition.CENTER_TOP, 0.4f, 0, 620, getSprites().gRewardGoldCategory);
        ScreenKit.scaleImage(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.055f, 170, 660, getSprites().gRewardGoldLogo);

        getSprites().gNotYet = Renderkit.loadGraphic(mContext.getResources(), R.drawable.trophy_not_yet, 0, 0);

        if (GameValues.GAMESYSTEM_ISLAND == StoreDataNew.getInstance().inventory.gameSystem) {
            mHasBronze = StoreDataNew.getInstance().gameData2.rewards != null && StoreDataNew.getInstance().gameData2.rewards[StoreDataNew.getInstance().gameData2.difficulty].length > 0 && StoreDataNew.getInstance().gameData2.rewards[StoreDataNew.getInstance().gameData2.difficulty][0];
        } else {
            mHasBronze = StoreDataNew.getInstance().gameData1.rewards != null && StoreDataNew.getInstance().gameData1.rewards[StoreDataNew.getInstance().gameData1.difficulty].length > 0 && StoreDataNew.getInstance().gameData1.rewards[StoreDataNew.getInstance().gameData1.difficulty][0];
        }
        if (GameValues.GAMESYSTEM_ISLAND == StoreDataNew.getInstance().inventory.gameSystem) {
            mHasSilver = StoreDataNew.getInstance().gameData2.rewards != null && StoreDataNew.getInstance().gameData2.rewards[StoreDataNew.getInstance().gameData2.difficulty].length > 1 && StoreDataNew.getInstance().gameData2.rewards[StoreDataNew.getInstance().gameData2.difficulty][1];
        } else {
            mHasSilver = StoreDataNew.getInstance().gameData1.rewards != null && StoreDataNew.getInstance().gameData1.rewards[StoreDataNew.getInstance().gameData1.difficulty].length > 1 && StoreDataNew.getInstance().gameData1.rewards[StoreDataNew.getInstance().gameData1.difficulty][1];
        }
        if (GameValues.GAMESYSTEM_ISLAND == StoreDataNew.getInstance().inventory.gameSystem) {
            mHasGold = StoreDataNew.getInstance().gameData2.rewards != null && StoreDataNew.getInstance().gameData2.rewards[StoreDataNew.getInstance().gameData2.difficulty].length > 2 && StoreDataNew.getInstance().gameData2.rewards[StoreDataNew.getInstance().gameData2.difficulty][2];
        } else {
            mHasGold = StoreDataNew.getInstance().gameData1.rewards != null && StoreDataNew.getInstance().gameData1.rewards[StoreDataNew.getInstance().gameData1.difficulty].length > 2 && StoreDataNew.getInstance().gameData1.rewards[StoreDataNew.getInstance().gameData1.difficulty][2];
        }


    }


    public int getReward(int i, boolean text, boolean logo) {
        if (GameValues.GAMESYSTEM_ISLAND == StoreDataNew.getInstance().inventory.gameSystem) {
            return m2Reward(i, text, logo);
        } else {
            return m1Reward(i, text, logo);
        }
    }

    private int m1Reward(int i, boolean text, boolean logo) {
        switch (i) {
            case 0:
                if (text) return R.string.reward_bronze;
                if (logo) return R.drawable.trophy_fish;
                return R.drawable.trophy_bronze;
            case 1:
                if (text) return R.string.reward_silver;
                if (logo) return R.drawable.reward_time;
                return R.drawable.trophy_silver;
            case 2:
                if (text) return R.string.reward_gold;
                if (logo) return R.drawable.reward_speed;
                return R.drawable.trophy_gold;
            default:
                return R.drawable.trophy_not_yet;
        }
    }

    private int m2Reward(int i, boolean text, boolean logo) {
        switch (i) {
            case 0:
                if (text) return R.string.m2reward_bronze;
                if (logo) return R.drawable.reward_time;
                return R.drawable.trophy_bronze;
            case 1:
                if (text) return R.string.m2reward_silver;
                if (logo) return R.drawable.trophy_fish;
                return R.drawable.trophy_silver;
            case 2:
                if (text) return R.string.m2reward_gold;
                if (logo) return R.drawable.reward_speed;
                return R.drawable.trophy_gold;
            default:
                return R.drawable.trophy_not_yet;
        }
    }

    @Override
    public void reset() {
        // TODO Auto-generated method stub

    }
}
