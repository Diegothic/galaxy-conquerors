package com.galaxy.game.level;

import com.galaxy.game.collision.Collider;
import com.galaxy.game.entity.SortingLayer;
import com.galaxy.game.entity.enemy.Enemy;
import com.galaxy.game.entity.player.Player;

public class Level_1 extends GameLevel {

    private final Player player;
    private final Enemy enemy;

    private final int numberOfCollumns = 12;
    private final int numberOfRows = 4;

    private final int spaceDim = 20;
    private final int spaceOffsetLeft = 2;
    private final int borderOffset = 24;

    public Level_1(int width, int height) {
        super(width, height);
        player = new Player();
        enemy = new Enemy(SortingLayer.ENEMIES, 100, 100, "alien/alien_1.png");

    }

    @Override
    protected void initLevel() {
        spawn(player);

        for(int i = 0; i < numberOfRows; i++){
            for(int j = 0; j < numberOfCollumns; j++){
                int y = 200 - i * spaceDim;
                int x = j * spaceDim + spaceOffsetLeft + borderOffset;
                Enemy enemy = new Enemy(SortingLayer.ENEMIES, x, y, "alien/alien_"+(i+1)+".png");
                spawn(enemy);
            }
        }
    }
}
