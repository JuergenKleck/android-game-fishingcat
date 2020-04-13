package info.simplyapps.game.fishingcat.rendering;

import info.simplyapps.game.fishingcat.Constants;
import info.simplyapps.game.fishingcat.Constants.RenderMode;
import info.simplyapps.game.fishingcat.Constants.SubRenderMode;
import info.simplyapps.game.fishingcat.R;
import info.simplyapps.game.fishingcat.engine.GameValues;
import info.simplyapps.game.fishingcat.rendering.kits.AnimationKit;
import info.simplyapps.game.fishingcat.rendering.kits.Renderkit;
import info.simplyapps.game.fishingcat.sprites.HomeViewSprites;
import info.simplyapps.gameengine.EngineConstants;
import info.simplyapps.gameengine.rendering.kits.ScreenKit;
import info.simplyapps.gameengine.rendering.kits.ScreenKit.ScreenPosition;
import info.simplyapps.gameengine.rendering.objects.Animation;
import info.simplyapps.gameengine.rendering.objects.Graphic;

import java.util.Properties;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.media.MediaPlayer;
import android.view.MotionEvent;

public class Home1Renderer extends FishRendererTemplate {

    long lastTime = 0l;
    public RenderMode mRenderMode;
    public SubRenderMode mSubRenderMode;
    int bubbleBoundaryTop;
    long bubbleTimeout;

	public Home1Renderer(Context context, Properties p) {
		super(context, p);
        mRenderMode = RenderMode.HOME;
        mSubRenderMode = SubRenderMode.NONE;
	}

	public HomeViewSprites getSprites() {
		return HomeViewSprites.class.cast(super.sprites);
	}

	private MediaPlayer getMediaPlayer() {
	    return getScreen().getMediaPlayer();
	}
	
	@Override
	public void doStart() {
	    getScreen().assignMediaPlayer(mContext, R.raw.sea);
	    if(getMediaPlayer() != null  && !getMediaPlayer().isPlaying()) {
	        getMediaPlayer().start();
	    }
	}

	@Override
	public void pause() {
        if(getMediaPlayer() != null) {
            getMediaPlayer().pause();
        }
	}

	@Override
	public void unpause() {
	    getScreen().assignMediaPlayer(mContext, R.raw.sea);
        if(getMediaPlayer() != null && !getMediaPlayer().isPlaying()) {
            getMediaPlayer().start();
        }
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
                    if(Constants.SubRenderMode.NONE == mSubRenderMode) {
                    	if(containsClick(getSprites().rBtnStart, event.getX(), event.getY())) {
                    		delayedActionHandler(EngineConstants.ACTION_START, EngineConstants.ACTION_START);
                    	}
                    	if(containsClick(getSprites().rBtnOptions, event.getX(), event.getY())) {
                    		delayedActionHandler(EngineConstants.ACTION_OPTIONS, EngineConstants.ACTION_OPTIONS);
                    	}
                    	if(containsClick(getSprites().rBtnSwitch, event.getX(), event.getY())) {
                    		delayedActionHandler(Constants.ACTION_SWITCH, Constants.ACTION_SWITCH);
                    	}
                    	if(containsClick(getSprites().rBtnQuit, event.getX(), event.getY())) {
                    		delayedActionHandler(EngineConstants.ACTION_QUIT, EngineConstants.ACTION_QUIT);
                    	}
                    }
                    if(Constants.SubRenderMode.SELECT == mSubRenderMode) {
                    	if(containsClick(getSprites().rBtnStartSingle, event.getX(), event.getY())) {
                    		delayedActionHandler(Constants.ACTION_START_SINGLE, Constants.ACTION_START_SINGLE);
                    	}
                    	if(containsClick(getSprites().rBtnStartTournament, event.getX(), event.getY())) {
                    		delayedActionHandler(Constants.ACTION_START_TOURNAMENT, Constants.ACTION_START_TOURNAMENT);
                    	}
                    }
            break;
            }
            
        }

        return true;
    }
	
	@Override
	public void doUpdateRenderState() {
	    final long time = System.currentTimeMillis();
	    
        if(bubbleTimeout >= 0l && lastTime > 0l) {
            bubbleTimeout -= time - lastTime;
        }
	    
        // bubbles animation
        if(bubbleBoundaryTop <= 0l) {
            bubbleBoundaryTop = realScreenHeight;
            bubbleTimeout = 3000l;
        }
        if(bubbleTimeout <= 0l && getSprites().aBubbles != null) {
            getSprites().aBubbles.nextFrame(time);
            
            // calculate bubbles here dynamically as we use a endless loop
            getSprites().aBubbles.coord.y = bubbleBoundaryTop;
            getSprites().aBubbles.rect.offsetTo(getSprites().aBubbles.coord.x, getSprites().aBubbles.coord.y);
            getSprites().gBubbles[getSprites().aBubbles.nextFrame().gReference].image.setBounds(getSprites().aBubbles.rect);
            
            bubbleBoundaryTop -= ScreenKit.scaleHeight(35, realScreenHeight);
        }

        
	    lastTime = time;
	}

	@Override
	public void doDrawRenderer(Canvas canvas) {
	    
        final long time = System.currentTimeMillis();

        if(getSprites().pBackground != null) {
            canvas.drawRect(getSprites().rBackground, getSprites().pBackground); 
        }
        if(getSprites().gBackground != null) {
            getSprites().gBackground.image.draw(canvas);
        }

        // seacrab animation
        if(getSprites().gSeacrab != null) {
            getSprites().gSeacrab[getSprites().aSeacrab.nextFrame(time).gReference].image.draw(canvas);
        }
        
        // seashell image
        if(getSprites().gSeashell != null) {
            getSprites().gSeashell.image.draw(canvas);
        }
        
        // seagrass image
        if(getSprites().gSeagrass != null) {
            getSprites().gSeagrass.image.draw(canvas);
        }
        
        // seagrass2 animation
        if(getSprites().gSeagrass2 != null) {
            getSprites().gSeagrass2[getSprites().aSeagrass2.nextFrame(time).gReference].image.draw(canvas);
        }
        
        // bubbles animation
        if(bubbleTimeout <= 0l && getSprites().aBubbles != null) {
            getSprites().gBubbles[getSprites().aBubbles.nextFrame().gReference].image.draw(canvas);
        }
        
        if(Constants.SubRenderMode.NONE == mSubRenderMode) {
        	// draw buttons last to overlay the background items
        	choiceBaseDraw(canvas, getSprites().rBtnStart, getSprites().gButtonOverlay, getSprites().gButton, activeButton, EngineConstants.ACTION_START, GameValues.cFilterBlue);
        	choiceBaseDraw(canvas, getSprites().rBtnOptions, getSprites().gButtonOverlay, getSprites().gButton, activeButton, EngineConstants.ACTION_OPTIONS, GameValues.cFilterBlue);
        	choiceBaseDraw(canvas, getSprites().rBtnSwitch, getSprites().gButtonOverlay, getSprites().gButton, activeButton, Constants.ACTION_SWITCH, GameValues.cFilterBlue);
        	choiceBaseDraw(canvas, getSprites().rBtnQuit, getSprites().gButtonOverlay, getSprites().gButton, activeButton, EngineConstants.ACTION_QUIT, GameValues.cFilterBlue);
        	
        	drawText(canvas, getSprites().rBtnStart, mContext.getString(R.string.menubutton_start), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
        	drawText(canvas, getSprites().rBtnOptions, mContext.getString(R.string.menubutton_options), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
        	drawText(canvas, getSprites().rBtnSwitch, mContext.getString(R.string.menubutton_switch_island), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight), GameValues.cFilterBlue);
        	drawText(canvas, getSprites().rBtnQuit, mContext.getString(R.string.menubutton_quit), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight), GameValues.cFilterRed);
        }
        if(Constants.SubRenderMode.SELECT == mSubRenderMode) {
        	// draw buttons last to overlay the background items
        	choiceBaseDraw(canvas, getSprites().rBtnStartTournament, getSprites().gButtonOverlay, getSprites().gButton, activeButton, EngineConstants.ACTION_START, GameValues.cFilterBlue);
        	choiceBaseDraw(canvas, getSprites().rBtnStartSingle, getSprites().gButtonOverlay, getSprites().gButton, activeButton, EngineConstants.ACTION_OPTIONS, GameValues.cFilterBlue);
        	
        	drawText(canvas, getSprites().rBtnStartTournament, mContext.getString(R.string.menubutton_start_tournament), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
        	drawText(canvas, getSprites().rBtnStartSingle, mContext.getString(R.string.menubutton_start_single), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
        }
	}
	

	@Override
	public void restoreGameState() {
	    unpause();
	}

	@Override
	public void saveGameState() {
	}

	@Override
    public void doInitThread(long time) {
		super.sprites = new HomeViewSprites();

        getSprites().gBackground = new Graphic(mContext.getResources().getDrawable(R.drawable.bg_classic_home));
        getSprites().gBackground.image.setBounds(0,realScreenHeight-realScreenHeight/100*43,screenWidth,realScreenHeight);
        
        Shader shader = new LinearGradient(0, 0, 0, realScreenHeight, Color.parseColor("#2dd0f4"), Color.parseColor("#19bff8"), TileMode.CLAMP);
        getSprites().pBackground = new Paint(); 
        getSprites().pBackground.setShader(shader);
        getSprites().rBackground = new Rect(0, 0, screenWidth, realScreenHeight);

        // button backgrounds
        getSprites().gButton = Renderkit.loadButtonGraphic(mContext.getResources(), R.drawable.button_background, 0, 0, EngineConstants.ACTION_NONE);
        getSprites().gButtonOverlay = Renderkit.loadGraphic(mContext.getResources(), R.drawable.button_background, 0, 0);

        getSprites().rBtnStart = getSprites().gButton.image.copyBounds();
        getSprites().rBtnStartTournament = getSprites().gButton.image.copyBounds();
        getSprites().rBtnStartSingle = getSprites().gButton.image.copyBounds();
        getSprites().rBtnOptions = getSprites().gButton.image.copyBounds();
        getSprites().rBtnSwitch = getSprites().gButton.image.copyBounds();
        getSprites().rBtnQuit = getSprites().gButton.image.copyBounds();
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.CENTER_TOP, 0.4f, 0, 130, getSprites().rBtnStart);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.CENTER_TOP, 0.3f, 0, 380, getSprites().rBtnOptions);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.BOTTOM_RIGHT, 0.35f, 50, 225, getSprites().rBtnSwitch);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.BOTTOM_RIGHT, 0.25f, 50, 50, getSprites().rBtnQuit);

        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.CENTER_TOP, 0.4f, 0, 380, getSprites().rBtnStartTournament);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.CENTER_TOP, 0.4f, 0, 130, getSprites().rBtnStartSingle);

        getSprites().rBtnSwitch.bottom -= getSprites().rBtnSwitch.height() / 3;

        // other images
        getSprites().gSeashell = Renderkit.loadGraphic(mContext.getResources(), R.drawable.seashell, 0, 0);
        ScreenKit.scaleImage(screenWidth, realScreenHeight, ScreenPosition.BOTTOM_RIGHT, 0.1f, 120, 25, getSprites().gSeashell);
        
        getSprites().gSeagrass = Renderkit.loadGraphic(mContext.getResources(), R.drawable.seagrass, 0, 0);
        ScreenKit.scaleImage(screenWidth, realScreenHeight, ScreenPosition.BOTTOM_LEFT, 0.1f, 170, 80, getSprites().gSeagrass);
        
        getSprites().gSeacrab = new Graphic[3];
        getSprites().gSeacrab[0] = Renderkit.loadGraphic(mContext.getResources(), R.drawable.seacrab_base_small, ScreenKit.scaleWidth(100, screenWidth), 0);
        getSprites().gSeacrab[1] = Renderkit.loadGraphic(mContext.getResources(), R.drawable.seacrab_move1_small, ScreenKit.scaleWidth(100, screenWidth), 0);
        getSprites().gSeacrab[2] = Renderkit.loadGraphic(mContext.getResources(), R.drawable.seacrab_move2_small, ScreenKit.scaleWidth(100, screenWidth), 0);
        ScreenKit.scaleImage(screenWidth, realScreenHeight, ScreenPosition.BOTTOM_LEFT, 0.2f, 50, 25, getSprites().gSeacrab);

        // animations
        getSprites().aSeacrab = new Animation();
        AnimationKit.addAnimation(getSprites().aSeacrab, 0, 200);
        AnimationKit.addAnimation(getSprites().aSeacrab, 2, 200);
        AnimationKit.addAnimation(getSprites().aSeacrab, 1, 200);
        AnimationKit.addAnimation(getSprites().aSeacrab, 2, 900);
        AnimationKit.addAnimation(getSprites().aSeacrab, 0, 200);
        AnimationKit.addAnimation(getSprites().aSeacrab, 2, 200);
        // calculate bounds
        getSprites().aSeacrab.rect.set(getSprites().gSeacrab[0].image.copyBounds());
        
        getSprites().gSeagrass2 = new Graphic[4];
        getSprites().gSeagrass2[0] = Renderkit.loadGraphic(mContext.getResources(), R.drawable.seagrass2_move0, 0, 0);
        getSprites().gSeagrass2[1] = Renderkit.loadGraphic(mContext.getResources(), R.drawable.seagrass2_move1, 0, 0);
        getSprites().gSeagrass2[2] = Renderkit.loadGraphic(mContext.getResources(), R.drawable.seagrass2_move2, 0, 0);
        getSprites().gSeagrass2[3] = Renderkit.loadGraphic(mContext.getResources(), R.drawable.seagrass2_move3, 0, 0);
        ScreenKit.scaleImage(screenWidth, realScreenHeight, ScreenPosition.BOTTOM_RIGHT, 0.2f, 50, 25, getSprites().gSeagrass2);

        getSprites().aSeagrass2 = new Animation();
        AnimationKit.addAnimation(getSprites().aSeagrass2, 0, 600);
        AnimationKit.addAnimation(getSprites().aSeagrass2, 1, 600);
        AnimationKit.addAnimation(getSprites().aSeagrass2, 2, 600);
        AnimationKit.addAnimation(getSprites().aSeagrass2, 3, 600);
        AnimationKit.addAnimation(getSprites().aSeagrass2, 2, 600);
        AnimationKit.addAnimation(getSprites().aSeagrass2, 1, 600);
        getSprites().aSeagrass2.rect.set(getSprites().gSeagrass2[0].image.copyBounds());
        
        getSprites().gBubbles = new Graphic[7];
        getSprites().gBubbles[0] = Renderkit.loadGraphic(mContext.getResources(), R.drawable.bubbles1, 0, 0);
        getSprites().gBubbles[1] = Renderkit.loadGraphic(mContext.getResources(), R.drawable.bubbles2, 0, 0);
        getSprites().gBubbles[2] = Renderkit.loadGraphic(mContext.getResources(), R.drawable.bubbles3, 0, 0);
        getSprites().gBubbles[3] = Renderkit.loadGraphic(mContext.getResources(), R.drawable.bubbles4, 0, 0);
        getSprites().gBubbles[4] = Renderkit.loadGraphic(mContext.getResources(), R.drawable.bubbles5, 0, 0);
        getSprites().gBubbles[5] = Renderkit.loadGraphic(mContext.getResources(), R.drawable.bubbles6, 0, 0);
        getSprites().gBubbles[6] = Renderkit.loadGraphic(mContext.getResources(), R.drawable.bubbles7, 0, 0);
        ScreenKit.scaleImage(screenWidth, realScreenHeight, ScreenPosition.BOTTOM_LEFT, 0.08f, 50, 25, getSprites().gBubbles);
        
        getSprites().aBubbles = new Animation();
        AnimationKit.addAnimation(getSprites().aBubbles, 0, 100);
        AnimationKit.addAnimation(getSprites().aBubbles, 1, 100);
        AnimationKit.addAnimation(getSprites().aBubbles, 2, 100);
        AnimationKit.addAnimation(getSprites().aBubbles, 3, 100);
        AnimationKit.addAnimation(getSprites().aBubbles, 4, 100);
        AnimationKit.addAnimation(getSprites().aBubbles, 5, 100);
        AnimationKit.addAnimation(getSprites().aBubbles, 6, 100);
        getSprites().aBubbles.rect.set(getSprites().gBubbles[0].image.copyBounds());
        
        
	}
	
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	public void setSubRenderMode(SubRenderMode pSubRenderMode) {
		this.mSubRenderMode = pSubRenderMode;
	}
	
	
}
