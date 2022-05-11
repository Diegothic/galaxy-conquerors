package com.galaxy.game.level;

import com.galaxy.game.entity.enemy.Enemy;
import com.galaxy.game.entity.player.Player;

public class Level_1 extends GameLevel {

    private final Player player;
    private final int numberOfRows = 4;
    private final int numberOfCollumns = 12;
    private final int enemySpace = 20;
    private final int enemySpaceOffset = 2;
    private final int borderOffset = 24;

    public Level_1(int width, int height) {
        super(width, height);
        player = new Player();
        for (int i = 0; i < numberOfCollumns; i++) {
            for (int j = 0; j < numberOfRows; j++) {
                int x = i * enemySpace + borderOffset + enemySpaceOffset;
                int y = 200 - j * enemySpace;
                Enemy enemy = new Enemy(x, y, "alien/alien_" + (j + 1) + ".png");
                spawn(enemy);
            }
        }
    }

    @Override
    protected void initLevel() {
        spawn(player);
    }
}
