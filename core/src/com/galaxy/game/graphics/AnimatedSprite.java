package com.galaxy.game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimatedSprite extends Sprite {

    private final Texture texture;
    private final Animation<TextureRegion> animation;
    private float timer;
    private boolean looping;

    public AnimatedSprite(String texturePath, int tileWidth, int tileHeight, int tileCount, float frameDuration) {
        super();
        texture = new Texture(Gdx.files.internal(texturePath));
        setSize(tileWidth, tileHeight);
        setOrigin(tileWidth / 2.0f, tileHeight / 2.0f);
        setTexture(texture);

        var tempFrames = TextureRegion.split(texture, tileWidth, tileHeight);
        var animationFrames = new TextureRegion[tileCount];
        var rowsCount = texture.getHeight() / tileHeight;
        var colCount = texture.getWidth() / tileWidth;
        var index = 0;
        framesLoop:
        for (int row = 0; row < rowsCount; row++) {
            for (int col = 0; col < colCount; col++) {
                animationFrames[index] = tempFrames[row][col];
                index++;
                if (index >= tileCount) {
                    break framesLoop;
                }
            }
        }
        animation = new Animation<>(frameDuration, animationFrames);
        timer = 0.0f;
        looping = false;
    }

    public void step(float delta) {
        timer += delta;
    }

    @Override
    public void draw(Batch batch) {
        super.setRegion(animation.getKeyFrame(timer, looping));
        super.draw(batch);
    }

    public boolean isAnimationFinished() {
        return animation.isAnimationFinished(timer);
    }

    public void resetTimer() {
        timer = 0.0f;
    }

    public void setLooping(boolean looping) {
        this.looping = looping;
    }

    public void dispose() {
        texture.dispose();
    }
}
