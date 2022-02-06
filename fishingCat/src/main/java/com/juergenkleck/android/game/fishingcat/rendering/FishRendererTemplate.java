package com.juergenkleck.android.game.fishingcat.rendering;

import android.content.Context;

import java.util.Properties;

import com.juergenkleck.android.game.fishingcat.Constants;
import com.juergenkleck.android.game.fishingcat.screens.HomeScreen;
import com.juergenkleck.android.gameengine.rendering.GenericRendererTemplate;

/**
 * Android app - FishingCat
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
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
