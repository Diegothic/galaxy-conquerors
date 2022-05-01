package com.galaxy.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.galaxy.game.GalaxyConquerors;
import com.galaxy.game.player.Player;

public class DevScreen implements Screen {

    final GalaxyConquerors game;

    OrthographicCamera camera;
    Texture shipTex;
    Texture gunTex;
    Texture gun2Tex;
    Texture alienTex;
    Texture alien2Tex;
    Texture alien3Tex;
    Texture alien4Tex;
    Texture bigShipTex;

    Texture flamesTex;
    TextureRegion[] flamesAnimFrames;
    Animation<TextureRegion> flamesAnim;
    float elapsedTime;

    Player player;

    public DevScreen(GalaxyConquerors game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 426, 240);

        shipTex = new Texture(Gdx.files.internal("player/ship.png"));
        gunTex = new Texture(Gdx.files.internal("player/gun_1.png"));
        gun2Tex = new Texture(Gdx.files.internal("player/gun_2.png"));
        alienTex = new Texture(Gdx.files.internal("alien/alien_1.png"));
        alien2Tex = new Texture(Gdx.files.internal("alien/alien_2.png"));
        alien3Tex = new Texture(Gdx.files.internal("alien/alien_3.png"));
        alien4Tex = new Texture(Gdx.files.internal("alien/alien_4.png"));
        bigShipTex = new Texture(Gdx.files.internal("other/big_ship.png"));

        flamesTex = new Texture(Gdx.files.internal("player/ship_flames_sheet.png"));
        TextureRegion[][] tempFrames = TextureRegion.split(flamesTex, 16, 16);
        flamesAnimFrames = new TextureRegion[4];
        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 1; j++) {
                flamesAnimFrames[index++] = tempFrames[j][i];
            }
        }
        flamesAnim = new Animation<TextureRegion>(1f / 12f, flamesAnimFrames);

        player = new Player();
        player.position.set(213, 16);
        player.setBounds(new Vector2(0, 426));
        player.setRespawnPosition(new Vector2(213, 16));
    }

    @Override
    public void show() {
        player.respawn();
    }

    @Override
    public void render(float delta) {
        player.update(delta);

        elapsedTime += delta;
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

//        game.batch.draw(flamesAnim.getKeyFrame(elapsedTime,true),213 - 8, 24 - 8);

//        game.batch.draw(gunTex, 213 - 8, 24 - 8 + 9, 16, 16);
//        game.batch.draw(shipTex, 213 - 8, 24 - 8, 16, 16);
//
//        game.batch.draw(gun2Tex, 213 - 8 + 32, 24 - 8 + 9, 16, 16);
//        game.batch.draw(shipTex, 213 - 8 + 32, 24 - 8, 16, 16);

        for (int i = 0; i < 12; i++) {
            game.batch.draw(alienTex, i * 24 + 88 - 8, 40 - 8 + 64, 16, 16);
            game.batch.draw(alien2Tex, i * 24 + 88 - 8, 64 - 8 + 64, 16, 16);
            game.batch.draw(alien3Tex, i * 24 + 88 - 8, 88 - 8 + 64, 16, 16);
            game.batch.draw(alien4Tex, i * 24 + 88 - 8, 112 - 8 + 64, 16, 16);
        }

        game.batch.draw(bigShipTex, 213 - 8, 152 - 8 + 64, 32, 16);


        player.render(game.batch);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        shipTex.dispose();
        gunTex.dispose();
        gun2Tex.dispose();
        alienTex.dispose();
        alien2Tex.dispose();
        alien3Tex.dispose();
        alien4Tex.dispose();
        bigShipTex.dispose();
        flamesTex.dispose();

        player.dispose();
    }
}
