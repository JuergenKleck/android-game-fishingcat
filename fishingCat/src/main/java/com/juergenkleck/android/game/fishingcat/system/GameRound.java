package com.juergenkleck.android.game.fishingcat.system;

/**
 * Android app - FishingCat
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class GameRound extends com.juergenkleck.android.gameengine.system.GameRound {

    public int catched;
    public int rare;

    public GameRound(int catched, int rare, int round, long time) {
        super(round, time, 0);
        this.catched = catched;
        this.rare = rare;
    }

    public GameRound(int round, long time) {
        super(round, time, 0);
        this.catched = 0;
        this.rare = 0;
    }

    public GameRound() {
        super(0, 0, 0);
        this.catched = 0;
        this.rare = 0;
    }
}
