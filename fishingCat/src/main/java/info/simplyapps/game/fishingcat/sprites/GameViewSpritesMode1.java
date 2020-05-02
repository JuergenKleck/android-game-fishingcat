package info.simplyapps.game.fishingcat.sprites;

import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.simplyapps.game.fishingcat.rendering.objects.FishAnimation;
import info.simplyapps.gameengine.rendering.objects.Animation;
import info.simplyapps.gameengine.rendering.objects.Graphic;
import info.simplyapps.gameengine.sprites.ViewSprites;

public class GameViewSpritesMode1 implements ViewSprites {

    // main
    public Graphic gBackground;
    public Graphic gBackground2;
    public Paint pBackground;
    public Rect rBackground;

    // generic buttons
    public Graphic gButton;
    public Graphic gButtonOverlay;

    public Rect rBtnExtensionLife;
    public Rect rBtnExtensionTime;
    public Rect rBtnPause;
    public Rect rBtnResume;
    public Rect rBtnBack;

    public Graphic[] gCat;
    public Animation aCatEyes;
    public Animation aClicked;
    public long clickedTime;

    public Rect rMessage;
    public Rect rMessage2;

    public Graphic gIconCat;
    public Graphic gIconFish;

    public Rect rPoints;
    public Rect rTime;
    public Rect rLives;

    public Graphic[] gClickAnim;
    public Graphic[] gCatEyesAnim;

    public Map<String, Graphic[]> fishGraphics;
    public List<FishAnimation> aFish;


    @Override
    public void init() {
    }

    @Override
    public void clean() {
        gBackground = null;
//		gBlack = null;
        gCat = null;
        aCatEyes = null;
        aClicked = null;
        if (aFish != null) {
            aFish.clear();
        }
        aFish = new ArrayList<FishAnimation>();
        fishGraphics = new HashMap<String, Graphic[]>();
    }

}
