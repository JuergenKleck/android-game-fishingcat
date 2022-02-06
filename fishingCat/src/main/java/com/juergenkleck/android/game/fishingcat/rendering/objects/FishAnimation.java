package com.juergenkleck.android.game.fishingcat.rendering.objects;

/**
 * Android app - FishingCat
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class FishAnimation extends com.juergenkleck.android.gameengine.rendering.objects.Animation {

    public int fishNumber;
    public boolean rareFish;
    public boolean sharkFish;
    public long fishSpeed;
    // updated value
    public long moveTime;
    public boolean catched;
    // the click event
    public boolean init;
    // what happens on click
    public int clickAction;

    public FishAnimation() {
        super();
    }

}
