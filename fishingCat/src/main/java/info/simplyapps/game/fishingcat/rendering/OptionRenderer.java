package info.simplyapps.game.fishingcat.rendering;

import info.simplyapps.appengine.storage.dto.Configuration;
import info.simplyapps.game.fishingcat.Constants;
import info.simplyapps.game.fishingcat.SystemHelper;
import info.simplyapps.game.fishingcat.R;
import info.simplyapps.game.fishingcat.engine.GameHelper;
import info.simplyapps.game.fishingcat.engine.GameValues;
import info.simplyapps.game.fishingcat.rendering.kits.Renderkit;
import info.simplyapps.game.fishingcat.sprites.HomeViewSprites;
import info.simplyapps.game.fishingcat.storage.DBDriver;
import info.simplyapps.game.fishingcat.storage.StoreDataNew;
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
import android.widget.Toast;

public class OptionRenderer extends FishRendererTemplate {

    long lastTime = 0l;
    
    public OptionRenderer(Context context, Properties p) {
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
        if(delayedAction == EngineConstants.ACTION_NONE) {
            // determine button click
        
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_MOVE:
                    // move
                    break;
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_UP:
                    if(containsClick(getSprites().rBtnMusic, event.getX(), event.getY())) {
                        Configuration c = SystemHelper.getConfiguration(Constants.CONFIG_MUSIC, Constants.DEFAULT_CONFIG_MUSIC);
                        c.value = Boolean.toString(!Boolean.valueOf(c.value).booleanValue());
                        if(DBDriver.getInstance().store(c)) {
                            SystemHelper.setConfiguration(c);
                        }
                    }
                    if(containsClick(getSprites().rBtnVibration, event.getX(), event.getY())) {
                        Configuration c = SystemHelper.getConfiguration(Constants.CONFIG_VIBRATION, Constants.DEFAULT_CONFIG_VIBRATION);
                        c.value = Boolean.toString(!Boolean.valueOf(c.value).booleanValue());
                        if(DBDriver.getInstance().store(c)) {
                            SystemHelper.setConfiguration(c);
                        }
                    }
                    if(containsClick(getSprites().rBtnDifficultyEasy, event.getX(), event.getY())) {
                        if(isGameActive()) {
                            Toast.makeText(mContext, R.string.change_while_playing, Toast.LENGTH_LONG).show();
                        } else {
                            if(GameValues.GAMESYSTEM_ISLAND == StoreDataNew.getInstance().inventory.gameSystem) {
                                StoreDataNew.getInstance().gameData2.difficulty = GameValues.GAMEMODE_EASY;
                                DBDriver.getInstance().store(StoreDataNew.getInstance().gameData2);
                            } else {
                                StoreDataNew.getInstance().gameData1.difficulty = GameValues.GAMEMODE_EASY;
                                DBDriver.getInstance().store(StoreDataNew.getInstance().gameData1);
                            }
                        }
                    }
                    if(containsClick(getSprites().rBtnDifficultyMedium, event.getX(), event.getY())) {
                        if(isGameActive()) {
                            Toast.makeText(mContext, R.string.change_while_playing, Toast.LENGTH_LONG).show();
                        } else {
                            if(GameValues.GAMESYSTEM_ISLAND == StoreDataNew.getInstance().inventory.gameSystem) {
                                StoreDataNew.getInstance().gameData2.difficulty = GameValues.GAMEMODE_MEDIUM;
                                DBDriver.getInstance().store(StoreDataNew.getInstance().gameData2);
                            } else {
                                StoreDataNew.getInstance().gameData1.difficulty = GameValues.GAMEMODE_MEDIUM;
                                DBDriver.getInstance().store(StoreDataNew.getInstance().gameData1);
                            }
                        }
                    }
                    if(containsClick(getSprites().rBtnDifficultyHard, event.getX(), event.getY())) {
                        if(isGameActive()) {
                            Toast.makeText(mContext, R.string.change_while_playing, Toast.LENGTH_LONG).show();
                        } else {
                            if(GameValues.GAMESYSTEM_ISLAND == StoreDataNew.getInstance().inventory.gameSystem) {
                                StoreDataNew.getInstance().gameData2.difficulty = GameValues.GAMEMODE_HARD;
                                DBDriver.getInstance().store(StoreDataNew.getInstance().gameData2);
                            } else {
                                StoreDataNew.getInstance().gameData1.difficulty = GameValues.GAMEMODE_HARD;
                                DBDriver.getInstance().store(StoreDataNew.getInstance().gameData1);
                            }
                        }
                    }
                    // only for the basic game
                    if(GameValues.GAMESYSTEM_CLASSIC == StoreDataNew.getInstance().inventory.gameSystem) {
                        if(containsClick(getSprites().rBtnCatMode, event.getX(), event.getY())) {
                            if(isGameActive()) {
                                Toast.makeText(mContext, R.string.change_while_playing, Toast.LENGTH_LONG).show();
                            } else {
                                StoreDataNew.getInstance().inventory.catMode = !StoreDataNew.getInstance().inventory.catMode;
                                DBDriver.getInstance().store(StoreDataNew.getInstance().inventory);
                            }
                        }
                        if(containsClick(getSprites().rBtnResetGame, event.getX(), event.getY())) {
                            if(GameValues.GAMESYSTEM_CLASSIC == StoreDataNew.getInstance().inventory.gameSystem) {
                                StoreDataNew.getInstance().currentGame1.currentGame[StoreDataNew.getInstance().gameData1.difficulty] = -1;
                                DBDriver.getInstance().store(StoreDataNew.getInstance().currentGame1);
                            }
                            else if(GameValues.GAMESYSTEM_ISLAND == StoreDataNew.getInstance().inventory.gameSystem) {
                                StoreDataNew.getInstance().currentGame2.currentGame[StoreDataNew.getInstance().gameData2.difficulty] = -1;
                                DBDriver.getInstance().store(StoreDataNew.getInstance().currentGame2);
                            }
                            Toast.makeText(mContext, R.string.reset_complete, Toast.LENGTH_SHORT).show();
                        }
                    }
                    if(containsClick(getSprites().rBtnBack, event.getX(), event.getY())) {
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
        
        if(getSprites().gBackground != null) {
            getSprites().gBackground.image.setBounds(0,0,screenWidth,screenHeight);
            getSprites().gBackground.image.draw(canvas);
        }
        if(getSprites().pBackground != null) {
            canvas.drawRect(getSprites().rBackground, getSprites().pBackground); 
        }

        drawText(canvas, getSprites().rMsgOptions, mContext.getString(R.string.message_options), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));

        int difficulty = (GameValues.GAMESYSTEM_ISLAND == StoreDataNew.getInstance().inventory.gameSystem ? StoreDataNew.getInstance().gameData2.difficulty : StoreDataNew.getInstance().gameData1.difficulty);

        Configuration cVib = SystemHelper.getConfiguration(Constants.CONFIG_VIBRATION, Constants.DEFAULT_CONFIG_VIBRATION);
        Configuration cMus = SystemHelper.getConfiguration(Constants.CONFIG_MUSIC, Constants.DEFAULT_CONFIG_MUSIC);

        drawText(canvas, getSprites().rMsgMusic, mContext.getString(R.string.options_music), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
        choiceDraw(canvas, getSprites().rBtnMusic, getSprites().gButtonOverlay, getSprites().gButtonOverlay, Boolean.valueOf(cMus.value).booleanValue(), true, Boolean.valueOf(cMus.value).booleanValue(), false, GameValues.cFilterGreen, GameValues.cFilterRed);
        if(Boolean.valueOf(cMus.value).booleanValue()) {
            drawText(canvas, getSprites().rBtnMusic, mContext.getString(R.string.on), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
        } else {
            drawText(canvas, getSprites().rBtnMusic, mContext.getString(R.string.off), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
        }

        drawText(canvas, getSprites().rMsgVibration, mContext.getString(R.string.options_vibration), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
        choiceDraw(canvas, getSprites().rBtnVibration, getSprites().gButtonOverlay, getSprites().gButtonOverlay, Boolean.valueOf(cVib.value).booleanValue(), true, Boolean.valueOf(cVib.value).booleanValue(), false, GameValues.cFilterGreen, GameValues.cFilterRed);
        if(Boolean.valueOf(cVib.value).booleanValue()) {
            drawText(canvas, getSprites().rBtnVibration, mContext.getString(R.string.on), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
        } else {
            drawText(canvas, getSprites().rBtnVibration, mContext.getString(R.string.off), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
        }

        drawText(canvas, getSprites().rMsgDifficulty, mContext.getString(R.string.options_difficulty), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
        choiceDraw(canvas, getSprites().rBtnDifficultyEasy, getSprites().gButtonOverlay, getSprites().gButtonOverlay, difficulty, GameValues.GAMEMODE_EASY, difficulty, -1, GameValues.cFilterGreen, GameValues.cFilterRed);
        drawText(canvas, getSprites().rBtnDifficultyEasy, mContext.getString(R.string.difficulty_easy), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
        choiceDraw(canvas, getSprites().rBtnDifficultyMedium, getSprites().gButtonOverlay, getSprites().gButtonOverlay, difficulty, GameValues.GAMEMODE_MEDIUM, difficulty, -1, GameValues.cFilterGreen, GameValues.cFilterRed);
        drawText(canvas, getSprites().rBtnDifficultyMedium, mContext.getString(R.string.difficulty_medium), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
        choiceDraw(canvas, getSprites().rBtnDifficultyHard, getSprites().gButtonOverlay, getSprites().gButtonOverlay, difficulty, GameValues.GAMEMODE_HARD, difficulty, -1, GameValues.cFilterGreen, GameValues.cFilterRed);
        drawText(canvas, getSprites().rBtnDifficultyHard, mContext.getString(R.string.difficulty_hard), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
        
        if(GameValues.GAMESYSTEM_CLASSIC == StoreDataNew.getInstance().inventory.gameSystem) {
            choiceBaseDraw(canvas, getSprites().rBtnResetGame, getSprites().gButtonOverlay, getSprites().gButton, true, false, GameValues.cFilterGreen);
            drawText(canvas, getSprites().rBtnResetGame, mContext.getString(R.string.option_reset), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));

            drawText(canvas, getSprites().rMsgCatMode, mContext.getString(R.string.menubutton_cat_mode), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
            choiceDraw(canvas, getSprites().rBtnCatMode, getSprites().gButtonOverlay, getSprites().gButtonOverlay, StoreDataNew.getInstance().inventory.catMode, true, StoreDataNew.getInstance().inventory.catMode, false, GameValues.cFilterGreen, GameValues.cFilterRed);
            if(StoreDataNew.getInstance().inventory.catMode) {
                drawText(canvas, getSprites().rBtnCatMode, mContext.getString(R.string.on), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
            } else {
                drawText(canvas, getSprites().rBtnCatMode, mContext.getString(R.string.off), ScreenKit.scaleWidth(Constants.spaceLR, screenWidth), ScreenKit.scaleHeight(Constants.spaceTB, screenHeight));
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
        
        
        getSprites().rMsgOptions = getSprites().gButton.image.copyBounds();
        getSprites().rMsgMusic = getSprites().gButton.image.copyBounds();
        getSprites().rBtnMusic = getSprites().gButton.image.copyBounds();
        getSprites().rMsgVibration = getSprites().gButton.image.copyBounds();
        getSprites().rBtnVibration = getSprites().gButton.image.copyBounds();
        getSprites().rMsgDifficulty = getSprites().gButton.image.copyBounds();
        getSprites().rBtnDifficultyEasy = getSprites().gButton.image.copyBounds();
        getSprites().rBtnDifficultyMedium = getSprites().gButton.image.copyBounds();
        getSprites().rBtnDifficultyHard = getSprites().gButton.image.copyBounds();
        getSprites().rBtnResetGame = getSprites().gButton.image.copyBounds();
        getSprites().rMsgCatMode = getSprites().gButton.image.copyBounds();
        getSprites().rBtnCatMode = getSprites().gButton.image.copyBounds();
        
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.CENTER_TOP, 0.4f, 0, 20, getSprites().rMsgOptions);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.3f, 0, 190, getSprites().rMsgMusic);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.25f, 220, 200, getSprites().rBtnMusic);

        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.3f, 0, 320, getSprites().rMsgVibration);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.25f, 220, 330, getSprites().rBtnVibration);

        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.3f, 320, 190, getSprites().rMsgDifficulty);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.25f, 330, 330, getSprites().rBtnDifficultyEasy);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.25f, 330, 450, getSprites().rBtnDifficultyMedium);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.25f, 330, 570, getSprites().rBtnDifficultyHard);
        
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.4f, 0, 460, getSprites().rMsgCatMode);
        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.TOP_LEFT, 0.25f, 220, 460, getSprites().rBtnCatMode);

        ScreenKit.scaleRect(screenWidth, screenHeight, ScreenPosition.BOTTOM_LEFT, 0.5f, 200, 20, getSprites().rBtnResetGame);

        getSprites().rBtnMusic.right -= getSprites().rBtnMusic.width() / 2;
        getSprites().rBtnVibration.right -= getSprites().rBtnVibration.width() / 2;

        getSprites().rBtnResetGame.bottom -= getSprites().rBtnResetGame.height() / 3;

        getSprites().rMsgCatMode.bottom -= getSprites().rMsgCatMode.height() / 3;
        getSprites().rBtnCatMode.right -= getSprites().rBtnCatMode.width() / 2;
        
    }
    
 
    private boolean isGameActive() {
        boolean active = false;
        if(GameValues.GAMESYSTEM_ISLAND == StoreDataNew.getInstance().inventory.gameSystem) {
            // we do not restore games in island mode
            active = false;
        } else {
            StoreDataNew.getInstance().currentGame1.selfcheck();
            StoreDataNew.getInstance().gameData1.selfcheck();
            int difficulty = StoreDataNew.getInstance().gameData1.difficulty;
            int gameRoundLength = GameValues.m1totalRounds;
            int currentGame = StoreDataNew.getInstance() != null ? StoreDataNew.getInstance().currentGame1.currentGame[difficulty] : 0;
            int gameCatched = StoreDataNew.getInstance() != null && StoreDataNew.getInstance().currentGame1.gameCatched[difficulty] != null ? StoreDataNew.getInstance().currentGame1.gameCatched[difficulty].length : 0;
            if (currentGame >= 0 && currentGame < gameRoundLength 
                    && gameCatched == gameRoundLength
                    /*&& StoreDataNew.getInstance().currentLives > 0*/) {
                // continue
                active = true;
            }
            
        }
        return active;
    }

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
    
}
