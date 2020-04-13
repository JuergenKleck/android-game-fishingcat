package info.simplyapps.game.fishingcat.rendering;

import info.simplyapps.game.fishingcat.Constants;
import info.simplyapps.game.fishingcat.R;
import info.simplyapps.game.fishingcat.engine.GameValues;
import info.simplyapps.game.fishingcat.screens.HomeScreen;
import info.simplyapps.game.fishingcat.rendering.kits.AnimationKit;
import info.simplyapps.game.fishingcat.rendering.kits.Renderkit;
import info.simplyapps.game.fishingcat.sprites.HomeViewSprites;
import info.simplyapps.game.fishingcat.storage.StoreDataNew;
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
import android.view.MotionEvent;

public class Home2Renderer extends FishRendererTemplate {

    long lastTime = 0l;
    boolean playSequence = false;
    int initialX;
    int initialY;

    public Home2Renderer(Context context, Properties p) {
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
        if (playSequence) {
            getSprites().aCat.reset();
            getSprites().aCat.coord.x = initialX;
            getSprites().aCat.coord.y = initialY;
            playSequence = false;
        }
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
                    if (containsClick(getSprites().rBtnStart, event.getX(), event.getY())) {
                        delayedActionHandler(EngineConstants.ACTION_START, EngineConstants.ACTION_START);
                        if (StoreDataNew.getInstance().inventory.gameSystem == GameValues.GAMESYSTEM_ISLAND && delayedAction == EngineConstants.ACTION_CONTINUE) {
                            playSequence = true;
                        }
                    }
                    if (containsClick(getSprites().rBtnOptions, event.getX(), event.getY())) {
                        delayedActionHandler(EngineConstants.ACTION_OPTIONS, EngineConstants.ACTION_OPTIONS);
                    }
                    if (containsClick(getSprites().rBtnSwitch, event.getX(), event.getY())) {
                        delayedActionHandler(Constants.ACTION_SWITCH, Constants.ACTION_SWITCH);
                    }
                    if (containsClick(getSprites().rBtnQuit, event.getX(), event.getY())) {
                        delayedActionHandler(EngineConstants.ACTION_QUIT, EngineConstants.ACTION_QUIT);
                    }
                    break;
            }

        }

        return true;
    }

    @Override
    public void doUpdateRenderState() {
        final long time = System.currentTimeMillis();

        if (playSequence) {
            getSprites().aCat.nextFrame(time);

            switch (getSprites().aCat.frame) {
                case 0:
                    break;
                case 1:
                    getSprites().aCat.coord.x += ScreenKit.scaleWidth(10, screenWidth);
                    getSprites().aCat.coord.y -= ScreenKit.scaleHeight(20, screenHeight);
                    break;
                case 2:
                    getSprites().aCat.coord.x += ScreenKit.scaleWidth(10, screenWidth);
                    getSprites().aCat.coord.y += ScreenKit.scaleHeight(30, screenHeight);
                    break;
                case 3:
                    getSprites().aCat.coord.y += ScreenKit.scaleHeight(50, screenHeight);
                    if (getSprites().aCat.coord.y + getSprites().aCat.rect.height() > screenHeight) {
                        getSprites().aCat.coord.y = screenHeight - getSprites().aCat.rect.height();
                    }
                    break;
                default:
                    break;
            }
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

        canvas.save();
        getSprites().aCat.rect.offsetTo(getSprites().aCat.coord.x, getSprites().aCat.coord.y);
        getSprites().gCat[getSprites().aCat.nextFrame().gReference].image.setBounds(getSprites().aCat.rect);
        getSprites().gCat[getSprites().aCat.nextFrame().gReference].image.draw(canvas);
        canvas.restore();

        // draw buttons last to overlay the background items
        choiceBaseDraw(canvas, getSprites().rBtnStart, getSprites().gButtonOverlay, getSprites().gButton, activeButton, EngineConstants.ACTION_START, GameValues.cFilterBlue);
        choiceBaseDraw(canvas, getSprites().rBtnOptions, getSprites().gButtonOverlay, getSprites().gButton, activeButton, EngineConstants.ACTION_OPTIONS, GameValues.cFilterBlue);
        choiceBaseDraw(canvas, getSprites().rBtnSwitch, getSprites().gButtonOverlay, getSprites().gButton, activeButton, Constants.ACTION_SWITCH, GameValues.cFilterBlue);
        choiceBaseDraw(canvas, getSprites().rBtnQuit, getSprites().gButtonOverlay, getSprites().gButton, activeButton, EngineConstants.ACTION_QUIT, GameValues.cFilterRed);

        drawText(canvas, getSprites().rBtnStart, mContext.getString(R.string.menubutton_start), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
        drawText(canvas, getSprites().rBtnOptions, mContext.getString(R.string.menubutton_options), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
        drawText(canvas, getSprites().rBtnSwitch, mContext.getString(R.string.menubutton_switch_classic), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight), GameValues.cFilterBlue);
        drawText(canvas, getSprites().rBtnQuit, mContext.getString(R.string.menubutton_quit), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight), GameValues.cFilterRed);
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

        getSprites().gBackground = new Graphic(mContext.getResources().getDrawable(R.drawable.bg_island_home));
        getSprites().gBackground.image.setBounds(0, realScreenHeight - realScreenHeight / 100 * 65, screenWidth, realScreenHeight);

        Shader shader = new LinearGradient(0, 0, 0, realScreenHeight, Color.parseColor("#005aa2"), Color.parseColor("#88c1d2"), TileMode.CLAMP);
        getSprites().pBackground = new Paint();
        getSprites().pBackground.setShader(shader);
        getSprites().rBackground = new Rect(0, 0, screenWidth, realScreenHeight);

        // button backgrounds
        getSprites().gButton = Renderkit.loadButtonGraphic(mContext.getResources(), R.drawable.button_background, 0, 0, EngineConstants.ACTION_NONE);
        getSprites().gButtonOverlay = Renderkit.loadGraphic(mContext.getResources(), R.drawable.button_background, 0, 0);

        getSprites().rBtnStart = getSprites().gButton.image.copyBounds();
        getSprites().rBtnOptions = getSprites().gButton.image.copyBounds();
        getSprites().rBtnSwitch = getSprites().gButton.image.copyBounds();
        getSprites().rBtnQuit = getSprites().gButton.image.copyBounds();
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.CENTER_TOP, 0.4f, 0, 130, getSprites().rBtnStart);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.CENTER_TOP, 0.3f, 0, 380, getSprites().rBtnOptions);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.BOTTOM_RIGHT, 0.35f, 50, 225, getSprites().rBtnSwitch);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.BOTTOM_RIGHT, 0.25f, 50, 50, getSprites().rBtnQuit);

        getSprites().rBtnSwitch.bottom -= getSprites().rBtnSwitch.height() / 3;

        getSprites().gCat = new Graphic[1];
        getSprites().gCat[0] = Renderkit.loadGraphic(mContext.getResources(), R.drawable.kater, 0, 0);
        ScreenKit.scaleImage(screenWidth, realScreenHeight, ScreenPosition.BOTTOM_LEFT, 0.06f, 100, 185, getSprites().gCat);

        getSprites().aCat = new Animation(true);
        AnimationKit.addAnimation(getSprites().aCat, 0, 100);
        AnimationKit.addAnimation(getSprites().aCat, 0, 100);
        AnimationKit.addAnimation(getSprites().aCat, 0, 100);
        AnimationKit.addAnimation(getSprites().aCat, 0, 100);

        getSprites().aCat.rect.set(getSprites().gCat[0].image.copyBounds());
        getSprites().aCat.coord.x = getSprites().aCat.rect.left;
        getSprites().aCat.coord.y = getSprites().aCat.rect.top;
        initialX = getSprites().aCat.coord.x;
        initialY = getSprites().aCat.coord.y;

    }

    @Override
    public void reset() {
        // TODO Auto-generated method stub

    }

}
