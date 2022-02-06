package com.juergenkleck.android.game.fishingcat.storage.dto;

import java.io.Serializable;

import com.juergenkleck.android.appengine.storage.dto.BasicTable;

/**
 * Android app - FishingCat
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class CurrentGame2 extends BasicTable implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6974204243261183587L;

    public int[] currentGame;
    public int[] positionX;
    public int[] positionY;
    public int[] gameCatched;
    public int[] gameRare;
    public boolean bonus;

    public void selfcheck() {
        if (currentGame == null) {
            currentGame = new int[]{-1, -1, -1};
        }
        if (positionX == null) {
            positionX = new int[3];
        }
        if (positionY == null) {
            positionY = new int[3];
        }
        if (gameCatched == null) {
            gameCatched = new int[3];
        }
        if (gameRare == null) {
            gameRare = new int[3];
        }
    }

}
