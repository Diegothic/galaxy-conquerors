package com.galaxy.game.entity.effects;

public class ExplosionEffect extends Effect {

    public ExplosionEffect() {
        super("other/explosion_sheet.png",
                16, 16, 8,
                1.0f / 12.0f
        );
        addSound("sounds/explosion.wav", true);
    }
}
