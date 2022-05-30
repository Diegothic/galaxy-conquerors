package com.galaxy.game.level;

import com.galaxy.game.entity.Background;
import com.galaxy.game.entity.Shield;
import com.galaxy.game.entity.SortingLayer;
import com.galaxy.game.entity.enemy.Enemy;
import com.galaxy.game.entity.player.Player;
import com.galaxy.game.entity.ship.Ship;

import java.util.List;

public class Level_1 extends GameLevel {

    private static final String SEED = "oorrrrrrrroo//bbbbbbbbbbbb/rgborgborgbo";

    public Level_1(int width, int height) {
        super(width, height);
    }

    @Override
    protected void initLevel() {
        var background = new Background();
        background.position.set(213.0f, 120.0f);
        spawn(background);
        var player = new Player();
        var ship = new Ship(SortingLayer.SHIP);
        spawn(player);
        spawn(ship);

        SeedParser seedParser = new SeedParser(SEED);
        List<Enemy> enemiesToSpawn = seedParser.parse();
        getGameMode().setAliveEnemies(seedParser.getEnemyCount());
        for (var enemy : enemiesToSpawn) {
            spawn(enemy);
        }

        float shieldHealth = 100.0f;
        float shieldCount = 5;
        float shieldFirstX = 85.0f;
        for (int shieldInd = 0; shieldInd < shieldCount; ++shieldInd) {
            var shield = new Shield(shieldHealth);
            shield.position.set(shieldFirstX + shieldInd * 64.0f, 48.0f);
            spawn(shield);
        }
    }

    @Override
    protected void endLevel() {
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
