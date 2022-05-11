package com.galaxy.game.entity.effects;

import com.badlogic.gdx.math.Vector2;
import com.galaxy.game.entity.player.Player;

public class PlayerReloadEffect extends Effect {

    private final Player player;
    private final Vector2 offset;

    public PlayerReloadEffect(Player player, Vector2 offset, float duration) {
        super("player/player_reload_sheet_2.png",
                16, 16, 13,
                duration / 13.0f
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
