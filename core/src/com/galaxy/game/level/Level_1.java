package com.galaxy.game.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.galaxy.game.entity.Background;
import com.galaxy.game.entity.Shield;
import com.galaxy.game.entity.SortingLayer;
import com.galaxy.game.entity.enemy.Enemy;
import com.galaxy.game.entity.player.Player;
import com.galaxy.game.entity.ship.Ship;

public class Level_1 extends GameLevel {

    private final Player player;
    private final Ship ship;
    private final int numberOfRows = 4;
    private final int numberOfCollumns = 12;
    private final int enemySpace = 20;
    private final int enemySpaceOffset = 2;
    private final int borderOffset = 24;

    private final float shieldHealth = 100.0f;
    private final float shieldCount = 5;
    private final float shieldFirstX = 85.0f;

    private String seed = "rrrrrrrrrrrr//bbbbbbbbbbbb/rgborgborgbo";

    private final Music theme;

    public Level_1(int width, int height) {
        super(width, height);
        theme = Gdx.audio.newMusic(Gdx.files.internal("sounds/main_theme.wav"));
        var background = new Background();
        background.position.set(213.0f, 120.0f);
        spawn(background);
        player = new Player();
        ship = new Ship(SortingLayer.SHIP);
        parseSeed();
    }

    @Override
    protected void initLevel() {
        theme.setLooping(true);
        theme.play();
        spawn(player);
        spawn(ship);
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

    private void parseSeed() {
        int x = 0;
        int y = 0;
        for (int i = 0; i < seed.length(); i++) {
            char point = seed.charAt(i);
            if (point == 'o' || point == 'b' || point == 'g' || point == 'r') {
                int posX = x * enemySpace + borderOffset + enemySpaceOffset;
                int posY = 190 - y * enemySpace;
                Enemy enemy;
                switch (point) {
                    case 'o':
                        enemy = new Enemy(posX, posY, "alien/alien_1.png");
                        break;
                    case 'b':
                        enemy = new Enemy(posX, posY, "alien/alien_2.png");
                        break;
                    case 'g':
                        enemy = new Enemy(posX, posY, "alien/alien_3.png");
                        break;
                    case 'r':
                        enemy = new Enemy(posX, posY, "alien/alien_4.png");
                        break;
                    default:
                        enemy = null;
                }
                spawn(enemy);
                x++;
            } else if (point == '/') {
                y++;
                x = 0;
            } else if (point == ' ') {
                x++;
            } else if ((Integer.parseInt(String.valueOf(point))) % 1 == 0) {
                int offset = Integer.parseInt(String.valueOf(point));
                if (x + offset <= numberOfCollumns) {
                    x += offset;
                }
            }
        }
    }
}
