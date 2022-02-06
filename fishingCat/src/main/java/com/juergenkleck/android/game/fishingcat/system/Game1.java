package com.juergenkleck.android.game.fishingcat.system;

import com.juergenkleck.android.gameengine.system.BasicGame;

/**
 * Android app - FishingCat
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class Game1 extends BasicGame {

    public GameRound[] rounds;
    public int currentRound;
    public int lives;
    public int immunity;
    public boolean lifeLost;

    public Game1(GameRound[] rounds) {
        this.rounds = rounds;
        this.currentRound = 0;
    }

    public GameRound getCurrentRound() {
        return rounds.length > currentRound ? rounds[currentRound] : null;
    }

    public GameRound getNextRound() {
        currentRound++;
        return rounds.length > currentRound ? rounds[currentRound] : null;
    }

    public boolean finished() {
        return currentRound >= rounds.length || lives <= 0;
    }

    public boolean gameWon() {
        return currentRound >= rounds.length && lives > 0;
    }

    public boolean hasGame() {
        return currentRound >= 0 && currentRound < rounds.length;
    }

}
