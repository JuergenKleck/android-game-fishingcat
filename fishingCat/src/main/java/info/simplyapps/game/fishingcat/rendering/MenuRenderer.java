package info.simplyapps.game.fishingcat.rendering;

import info.simplyapps.game.fishingcat.Constants;
import info.simplyapps.game.fishingcat.Constants.RenderMode;
import info.simplyapps.game.fishingcat.Constants.SubRenderMode;
import info.simplyapps.game.fishingcat.SystemHelper;
import info.simplyapps.game.fishingcat.R;
import info.simplyapps.game.fishingcat.engine.GameValues;
import info.simplyapps.game.fishingcat.rendering.kits.Renderkit;
import info.simplyapps.game.fishingcat.sprites.HomeViewSprites;
import info.simplyapps.gameengine.EngineConstants;
import info.simplyapps.gameengine.rendering.kits.ScreenKit;
import info.simplyapps.gameengine.rendering.kits.ScreenKit.ScreenPosition;

import java.util.Properties;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.view.MotionEvent;

public class MenuRenderer extends FishRendererTemplate {

    long lastTime = 0l;
    public RenderMode mRenderMode;
    public SubRenderMode mSubRenderMode;
    boolean mHasLWP;
    
    public MenuRenderer(Context context, Properties p) {
        super(context, p);
        mRenderMode = RenderMode.MENU;
        mSubRenderMode = SubRenderMode.SELECT;
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
        if(delayedAction == EngineConstants.ACTION_NONE) {
            // determine button click
        
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_MOVE:
                    // move
                    break;
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_UP:
                    if(containsClick(getSprites().rBtnSettings, event.getX(), event.getY())) {
                        delayedActionHandler(Constants.ACTION_SETTINGS, Constants.ACTION_SETTINGS);
                    }
                    if(containsClick(getSprites().rBtnStatistics, event.getX(), event.getY())) {
                        delayedActionHandler(Constants.ACTION_STATISTIC, Constants.ACTION_STATISTIC);
                    }
                    if(containsClick(getSprites().rBtnRewards, event.getX(), event.getY())) {
                        delayedActionHandler(Constants.ACTION_REWARDS, Constants.ACTION_REWARDS);
                    }
                    if(containsClick(getSprites().rBtnTrophies, event.getX(), event.getY())) {
                        delayedActionHandler(Constants.ACTION_TROPHIES, Constants.ACTION_TROPHIES);
                    }
                    if(containsClick(getSprites().rBtnLWP, event.getX(), event.getY()) && !mHasLWP) {
                        delayedActionHandler(Constants.ACTION_LWP, Constants.ACTION_LWP);
                    }
                    if(containsClick(getSprites().rBtnBack, event.getX(), event.getY())) {
                        delayedActionHandler(Constants.ACTION_HOME, Constants.ACTION_HOME);
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
        
        if(getSprites().gBackground != null) {
            getSprites().gBackground.image.setBounds(0,0,screenWidth,screenHeight);
            getSprites().gBackground.image.draw(canvas);
        }
        if(getSprites().pBackground != null) {
            canvas.drawRect(getSprites().rBackground, getSprites().pBackground); 
        }

        drawText(canvas, getSprites().rMsgMenu, mContext.getString(R.string.message_menu), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));

        if(Constants.RenderMode.MENU == mRenderMode) {

            choiceBaseDraw(canvas, getSprites().rBtnSettings, getSprites().gButtonOverlay, getSprites().gButton, activeButton, Constants.ACTION_SETTINGS, GameValues.cFilterBlue);
            choiceBaseDraw(canvas, getSprites().rBtnStatistics, getSprites().gButtonOverlay, getSprites().gButton, activeButton, Constants.ACTION_STATISTIC, GameValues.cFilterBlue);
            choiceBaseDraw(canvas, getSprites().rBtnTrophies, getSprites().gButtonOverlay, getSprites().gButton, activeButton, Constants.ACTION_TROPHIES, GameValues.cFilterBlue);
            choiceBaseDraw(canvas, getSprites().rBtnRewards, getSprites().gButtonOverlay, getSprites().gButton, activeButton, Constants.ACTION_REWARDS, GameValues.cFilterBlue);
            if(!mHasLWP) {
                choiceBaseDraw(canvas, getSprites().rBtnLWP, getSprites().gButtonOverlay, getSprites().gButton, activeButton, Constants.ACTION_LWP, GameValues.cFilterBlue);
            }

            drawText(canvas, getSprites().rBtnSettings, mContext.getString(R.string.menubutton_settings), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
            drawText(canvas, getSprites().rBtnStatistics, mContext.getString(R.string.menubutton_statistic), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
            drawText(canvas, getSprites().rBtnTrophies, mContext.getString(R.string.menubutton_trophies), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
            drawText(canvas, getSprites().rBtnRewards, mContext.getString(R.string.menubutton_rewards), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
            if(!mHasLWP) {
                drawText(canvas, getSprites().rBtnLWP, mContext.getString(R.string.menubutton_lwp), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
            }

        }
        
        choiceBaseDraw(canvas, getSprites().rBtnBack, getSprites().gButtonOverlay, getSprites().gButton, activeButton, Constants.ACTION_HOME, GameValues.cFilterGreen);
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

        mHasLWP = !SystemHelper.requireLWP(mContext);
        
        Shader shader = new LinearGradient(0, 0, screenWidth, realScreenHeight, Color.parseColor("#bdcce3"), Color.parseColor("#0598ff"), TileMode.CLAMP);
        getSprites().pBackground = new Paint(); 
        getSprites().pBackground.setShader(shader);
        getSprites().rBackground = new Rect(0, 0, screenWidth, realScreenHeight);
        
        // button backgrounds
        getSprites().gButton = Renderkit.loadButtonGraphic(mContext.getResources(), R.drawable.button_background, 0, 0, EngineConstants.ACTION_NONE);
        getSprites().gButtonOverlay = Renderkit.loadGraphic(mContext.getResources(), R.drawable.button_background, 0, 0);

        getSprites().rBtnBack = getSprites().gButton.image.copyBounds();
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.BOTTOM_LEFT, 0.25f, 50, 25, getSprites().rBtnBack);
        
        getSprites().rMsgMenu = getSprites().gButton.image.copyBounds();
        getSprites().rBtnSettings = getSprites().gButton.image.copyBounds();
        getSprites().rBtnRewards = getSprites().gButton.image.copyBounds();
        getSprites().rBtnStatistics = getSprites().gButton.image.copyBounds();
        getSprites().rBtnTrophies = getSprites().gButton.image.copyBounds();
        getSprites().rBtnLWP = getSprites().gButton.image.copyBounds();
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.CENTER_TOP, 0.4f, 0, 20, getSprites().rMsgMenu);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.34f, 50, 200, getSprites().rBtnSettings);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.34f, 300, 200, getSprites().rBtnStatistics);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.34f, 50, 400, getSprites().rBtnRewards);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.34f, 300, 400, getSprites().rBtnTrophies);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.34f, 300, 600, getSprites().rBtnLWP);

        getSprites().rBtnLWP.bottom -= getSprites().rBtnLWP.height() / 3;
    }
   

    public synchronized void updateRenderMode(RenderMode renderMode) {
        mRenderMode = renderMode;
    }

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
