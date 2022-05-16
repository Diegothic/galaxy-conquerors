package com.galaxy.game.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.galaxy.game.entity.Background;
import com.galaxy.game.entity.Shield;
import com.galaxy.game.entity.enemy.Enemy;
import com.galaxy.game.entity.player.Player;

public class Level_1 extends GameLevel {

    private final Player player;
    private final int numberOfRows = 4;
    private final int numberOfCollumns = 12;
    private final int enemySpace = 20;
    private final int enemySpaceOffset = 2;
    private final int borderOffset = 24;

    private final float shieldHealth = 100.0f;
    private final float shieldCount = 5;
    private final float shieldFirstX = 85.0f;

    private final Music theme;

    public Level_1(int width, int height) {
        super(width, height);
        theme = Gdx.audio.newMusic(Gdx.files.internal("sounds/main_theme.wav"));
        var background = new Background();
        background.position.set(213.0f, 120.0f);
        spawn(background);
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
        theme.setLooping(true);
        theme.play();
        spawn(player);
        for (int shieldInd = 0; shieldInd < shieldCount; ++shieldInd) {
            var shield = new Shield(shieldHealth);
            shield.position.set(shieldFirstX + shieldInd * 64.0f, 48.0f);
            spawn(shield);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        theme.dispose();
    }
}
