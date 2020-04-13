package info.simplyapps.game.fishingcat.sprites;

import android.graphics.Paint;
import android.graphics.Rect;

import info.simplyapps.game.fishingcat.rendering.objects.FishAnimation;
import info.simplyapps.gameengine.rendering.objects.Animation;
import info.simplyapps.gameengine.rendering.objects.Graphic;
import info.simplyapps.gameengine.sprites.ViewSprites;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameViewSpritesMode2 implements ViewSprites {
	
    // main
    public Graphic gBackground;
    public Paint pBackground;
    public Rect rBackground;
    
    // generic buttons
    public Graphic gButton;
    public Graphic gButtonOverlay;

    public Rect rBtnPause;
    public Rect rBtnResume;
    public Rect rBtnBack;

	public Graphic[] gCat;
	public Animation aCat;

	public Rect rMessage;

	public Graphic gIconFish;

	public Graphic gPressButtons;
	public Graphic gPressButtons2;

	public Rect rPoints;

	public Graphic[] gBubbles;
	public Animation aBubbles;

	public Map<String,Graphic[]> fishGraphics;
	public List<FishAnimation> aFish;
	   
    @Override
    public void init() {
    }
    @Override
    public void clean() {
        gBackground = null;
        gCat = null;
        if(aFish != null) {
            aFish.clear();
        }
        aFish = new ArrayList<FishAnimation>();
        fishGraphics = new HashMap<String,Graphic[]>();
    }

}
