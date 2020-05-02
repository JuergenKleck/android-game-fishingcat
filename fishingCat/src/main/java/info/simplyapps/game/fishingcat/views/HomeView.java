package info.simplyapps.game.fishingcat.views;

import android.content.Context;
import android.util.AttributeSet;

import java.util.Properties;

import info.simplyapps.game.fishingcat.Constants;
import info.simplyapps.game.fishingcat.engine.GameValues;
import info.simplyapps.game.fishingcat.rendering.Home1Renderer;
import info.simplyapps.game.fishingcat.rendering.Home2Renderer;
import info.simplyapps.game.fishingcat.storage.StoreDataNew;
import info.simplyapps.gameengine.EngineConstants;
import info.simplyapps.gameengine.RenderingSystem;
import info.simplyapps.gameengine.rendering.GenericViewTemplate;

public class HomeView extends GenericViewTemplate {

    public HomeView(Context context) {
        super(context);
    }

    public HomeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void createThread(Context context) {
        if (GameValues.GAMESYSTEM_ISLAND == StoreDataNew.getInstance().inventory.gameSystem) {
            setBasicEngine(new Home2Renderer(getContext(), getEngineProperties()));
        } else {
            setBasicEngine(new Home1Renderer(getContext(), getEngineProperties()));
        }
    }

    @Override
    public boolean isDragable() {
        return true;
    }

    @Override
    public String getNameSpace() {
        return Constants.PREFERENCE_NS;
    }

    public Properties getEngineProperties() {
        Properties p = new Properties();
        p.put(EngineConstants.GameProperties.RENDERING_SYSTEM, RenderingSystem.DEFAULT);
        p.put(EngineConstants.GameProperties.SCREEN_SCALE, 0);//getScreenView().getScreenScaleValue());
        p.put(EngineConstants.GameProperties.LEVEL, 0);
        p.put(EngineConstants.GameProperties.SPACE_LR, Constants.spaceLR);
        p.put(EngineConstants.GameProperties.SPACE_TB, Constants.spaceTB);
        return p;
    }

}
