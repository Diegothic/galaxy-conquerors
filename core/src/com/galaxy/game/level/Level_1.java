package com.galaxy.game.level;

import com.galaxy.game.entity.player.Player;

public class Level_1 extends GameLevel {

    private final Player player;

    public Level_1(int width, int height) {
        super(width, height);
        player = new Player();
    }

    @Override
    protected void initLevel() {
        spawn(player);
    }
}
