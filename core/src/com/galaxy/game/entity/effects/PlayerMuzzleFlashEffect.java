package com.galaxy.game.entity.effects;

import com.badlogic.gdx.math.Vector2;
import com.galaxy.game.entity.player.Player;

public class PlayerMuzzleFlashEffect extends Effect {

    private final Player player;
    private final Vector2 offset;

    public PlayerMuzzleFlashEffect(Player player, Vector2 offset) {
        super("player/player_projectile_shoot_sheet.png",
                16, 16, 6,
                1.0f / 24.0f
        );
        this.player = player;
        this.offset = new Vector2();
        this.offset.set(offset);
    }

    @Override
    public void onUpdate(float delta) {
        super.onUpdate(delta);
        position.set(
                player.position.x + offset.x,
                player.position.y + offset.y
        );
    }
}
