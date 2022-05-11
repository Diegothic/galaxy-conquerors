package com.galaxy.game.entity.effects;

import com.badlogic.gdx.math.Vector2;
import com.galaxy.game.entity.player.Player;

public class PlayerReloadEffect extends Effect {

    public PlayerReloadEffect(Player player, Vector2 offset, float duration) {
        super("player/player_reload_sheet_2.png",
                16, 16, 13,
                duration / 13.0f
        );
        attachTo(player, offset);
    }
}
