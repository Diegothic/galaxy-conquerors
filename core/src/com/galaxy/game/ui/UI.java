package com.galaxy.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.galaxy.game.graphics.AnimatedSprite;
import com.galaxy.game.level.GameLevel;

public class UI {

    private final float width;
    private final float height;
    private final GameLevel level;

    private final AnimatedSprite hearthFullSprite;
    private final Texture hearthEmptyTexture;
    private final Sprite hearthEmptySprite;

    public UI(float width, float height, GameLevel level) {
        this.width = width;
        this.height = height;
        this.level = level;

        hearthFullSprite = new AnimatedSprite("other/hearth_full_sheet.png",
                16, 16, 7,
                1.0f / 12.0f
        );
        hearthFullSprite.setLooping(true);

        hearthEmptyTexture = new Texture(Gdx.files.internal("other/hearth_empty.png"));
        hearthEmptySprite = new Sprite(hearthEmptyTexture);
    }

    public void update(float delta) {
        hearthFullSprite.step(delta);
    }

    public void render(SpriteBatch batch) {
        int maxLives = level.getGameMode().getMaxLives();
        int emptyLives = maxLives - level.getGameMode().getLives();
        for (int i = 0; i < maxLives; ++i) {
            float spriteX = width - maxLives * 20.0f - 4.0f + i * 20.0f;
            float spriteY = height - 24.0f;
            if (i < emptyLives) {
                hearthEmptySprite.setPosition(spriteX, spriteY);
                hearthEmptySprite.draw(batch);
            } else {
                hearthFullSprite.setPosition(spriteX, spriteY);
                hearthFullSprite.draw(batch);
            }
        }
    }

    public void dispose() {
        hearthFullSprite.dispose();
        hearthEmptyTexture.dispose();
    }
}
