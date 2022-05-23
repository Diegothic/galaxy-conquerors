package com.galaxy.game.level;

import com.galaxy.game.entity.enemy.Enemy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SeedParser {

    private final static int NUM_OF_COLUMNS = 12;
    private final static int ENEMY_WIDTH = 20;
    private final static int ENEMY_WIDTH_OFFSET = 2;
    private final static int BORDER_OFFSET = 24;

    private final String seed;

    public SeedParser(String seed) {
        this.seed = seed;
    }

    public List<Enemy> parse() {
        var enemiesToSpawn = new ArrayList<Enemy>();
        int x = 0;
        int y = 0;
        int baseY = 190;
        Map<Character, String> enemyTypes = Map.of(
                'o', "alien/alien_1.png",
                'b', "alien/alien_2.png",
                'g', "alien/alien_3.png",
                'r', "alien/alien_4.png"
        );
        for (Character ch : seed.toCharArray()) {
            if (enemyTypes.containsKey(ch)) {
                int posX = x * ENEMY_WIDTH + BORDER_OFFSET + ENEMY_WIDTH_OFFSET;
                int posY = baseY - y * ENEMY_WIDTH;
                Enemy enemy = new Enemy(posX, posY, enemyTypes.get(ch));
                enemiesToSpawn.add(enemy);
                x++;
            } else if (ch.equals('/')) {
                y++;
                x = 0;
            } else if (ch.equals(' ')) {
                x++;
            } else if (Character.isDigit(ch)) {
                int offset = Character.getNumericValue(ch);
                if (x + offset <= NUM_OF_COLUMNS) {
                    x += offset;
                }
            }
        }
        return enemiesToSpawn;
    }
}
