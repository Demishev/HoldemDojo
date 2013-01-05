package com.nedogeek.holdem.gamingStuff;

import com.nedogeek.holdem.PlayerStatus;

/**
 * User: Konstantin Demishev
 * Date: 05.10.12
 * Time: 12:07
 */
public class Player {
    private final String name;

    private PlayerStatus status;
    private PlayerStatus playerStatus;

    //TODO class is a stub
    public Player(String name) {
        this.name = name;
    }

    public PlayerStatus getStatus() {
        return status;
    }

    public PlayerAction getMove() {
        return null;
    }

    public void setStatus(PlayerStatus status) {
        this.status = status;
    }
}
