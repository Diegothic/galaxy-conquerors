package com.galaxy.game.entity.effects;

import com.badlogic.gdx.math.Vector2;
import com.galaxy.game.entity.player.Player;

public class PlayerMuzzleFlashEffect extends Effect {

    public PlayerMuzzleFlashEffect(Player player, Vector2 offset) {
        super("player/player_projectile_shoot_sheet.png",
                16, 16, 6,
                1.0f / 24.0f
        );
        attachTo(player, offset);
        addSound("sounds/laser_shoot.wav", true);
    }
}
