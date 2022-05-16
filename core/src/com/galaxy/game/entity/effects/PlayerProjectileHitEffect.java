package com.galaxy.game.entity.effects;

public class PlayerProjectileHitEffect extends Effect {

    public PlayerProjectileHitEffect() {
        super("player/player_projectile_hit_sheet_2.png",
                8, 8, 5,
                1.0f / 24.0f
        );
        addSound("sounds/laser_hit.wav", true);
    }
}
