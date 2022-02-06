package com.juergenkleck.android.game.fishingcat.rendering.objects;

/**
 * Android app - FishingCat
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class Fish {

    public long speedMin;
    public long speedMax;
    public float screenWidthScaling;

    public Fish(long speedMin, long speedMax, float screenWidthScaling) {
        this.speedMin = speedMin;
        this.speedMax = speedMax;
        this.screenWidthScaling = screenWidthScaling;
    }

}
