package info.simplyapps.game.fishingcat.rendering;

import android.content.Context;

import java.util.Properties;

import info.simplyapps.game.fishingcat.Constants;
import info.simplyapps.game.fishingcat.screens.HomeScreen;
import info.simplyapps.gameengine.rendering.GenericRendererTemplate;

public abstract class FishRendererTemplate extends GenericRendererTemplate {

    public FishRendererTemplate(Context context, Properties p) {
        super(context, p);
    }

    @Override
    public float getCharSpacing() {
        return Constants.CHAR_SPACING;
    }

    public HomeScreen getScreen() {
        return HomeScreen.class.cast(mContext);
    }

    public boolean logEnabled() {
        return false;
    }

}
