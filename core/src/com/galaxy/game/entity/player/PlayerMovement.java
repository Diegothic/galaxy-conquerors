package com.galaxy.game.entity.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.galaxy.game.util.FloatUtils;

public class PlayerMovement {
    private enum Direction {
        Left(-1.0f),
        Right(1.0f);

        public float value;

        Direction(float value) {
            this.value = value;
        }
    }

    private static final float ROTATION_SPEED = 50.0f;
    private static final float MOVEMENT_SPEED = 200.0f;

    private final Player player;
    private float speed;
    private float rotation;

    public PlayerMovement(Player player) {
        this.player = player;
        speed = 0.0f;
    }

    public void update(float delta) {
        rotation = player.rotation;

        if (isMoveLeftKeyPressed()) {
            move(delta, Direction.Left, speed >= 0.0f ? 2.0f : 1.0f);
        } else if (isMoveRightKeyPressed()) {
            move(delta, Direction.Right, speed <= 0.0f ? 2.0f : 1.0f);
        } else {
            neutralizeMovement(delta);
        }

        speed = FloatUtils.clamp(speed, -300.0f, 300.0f);
        player.position.x += speed * delta;
        if (player.position.x - player.sprite.getWidth() / 2 <= player.bounds.x
                || player.position.x + player.sprite.getWidth() / 2 >= player.bounds.y) {
            speed *= -0.5f;
        }
        player.position.x = FloatUtils.clamp(player.position.x,
                player.bounds.x + player.sprite.getWidth() / 2,
                player.bounds.y - player.sprite.getWidth() / 2
        );
        player.rotation = FloatUtils.clamp(rotation, -10.0f, 10.0f);
    }

    public void reset() {
        speed = 0.0f;
        rotation = 0.0f;
    }

    private boolean isMoveRightKeyPressed() {
        return Gdx.input.isKeyPressed(Input.Keys.RIGHT)
                || Gdx.input.isKeyPressed(Input.Keys.D);
    }

    private boolean isMoveLeftKeyPressed() {
        return Gdx.input.isKeyPressed(Input.Keys.LEFT)
                || Gdx.input.isKeyPressed(Input.Keys.A);
    }

    private void move(float delta, Direction direction, float speedMultiplier) {
        speed += direction.value * MOVEMENT_SPEED * speedMultiplier * delta;
        rotation -= direction.value * ROTATION_SPEED * delta;
    }

    private void neutralizeMovement(float delta) {
        if (rotation > 1.0f) {
            rotation -= ROTATION_SPEED * delta;
        } else if (rotation < -1.0f) {
            rotation += ROTATION_SPEED * delta;
        } else {
            rotation = 0.0f;
        }
        if (speed > 1.0f) {
            speed -= MOVEMENT_SPEED * 0.5f * delta;
        } else if (speed < -1.0f) {
            speed += MOVEMENT_SPEED * 0.5f * delta;
        } else {
            speed = 0.0f;
        }
    }
}
