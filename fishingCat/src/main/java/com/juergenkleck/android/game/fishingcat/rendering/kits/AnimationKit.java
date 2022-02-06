package com.juergenkleck.android.game.fishingcat.rendering.kits;

import com.juergenkleck.android.game.fishingcat.rendering.objects.FishAnimation;

/**
 * Android app - FishingCat
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class AnimationKit extends com.juergenkleck.android.gameengine.rendering.kits.AnimationKit {

    public static void addFishAnimation(FishAnimation animation, int gReference, int delay, int fishNumber) {
        addAnimation(animation, gReference, delay);
        animation.fishNumber = fishNumber;
    }

}
