package com.galaxy.game.entity.effects;

public class EnemyExplosionEffect extends Effect {

    public EnemyExplosionEffect() {
        super("enemy/enemy_explode.png",
                16, 16, 7,
                1.0f / 12.0f
        );
        addSound("sounds/enemy_death.wav", true);
    }
}
