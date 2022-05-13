package com.galaxy.game.level;

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
//    private String seed = "o2o2o2o2/b3b3b3/g4g5g/rrrrrrrrrrrr";
    private String seed = "rrrrrrrrrrrr//bbbbbbbbbbbb/rgborgborgbo";
    public Level_1(int width, int height) {
        super(width, height);
        player = new Player();
        ship = new Ship(SortingLayer.SHIP);
        parseSeed();
    }

    @Override
    protected void initLevel() {
        spawn(player);
        spawn(ship);
    }

    private void parseSeed(){
        int x = 0;
        int y = 0;
        for(int i = 0; i < seed.length(); i++){
            char point = seed.charAt(i);
            if(point == 'o' || point == 'b' || point == 'g' || point == 'r'){
                int posX = x * enemySpace + borderOffset + enemySpaceOffset;
                int posY = 190 - y * enemySpace;
                Enemy enemy;
                switch (point){
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
            } else if(point == '/'){
                y++;
                x = 0;
            }else if(point == ' '){
                x++;
            }else if((Integer.parseInt(String.valueOf(point))) % 1 == 0){
                int offset = Integer.parseInt(String.valueOf(point));
                if (x + offset <= numberOfCollumns) {
                    x += offset;
                }
            }
        }
    }
}
