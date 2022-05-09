package com.galaxy.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Font {

    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    public BitmapFont font;
    public Font(int size){
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/invasion2000.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = size;
        fontParameter.borderColor = Color.BLACK;
        fontParameter.color = Color.WHITE;
        fontParameter.borderWidth = 5;
        font = fontGenerator.generateFont(fontParameter);
    }
}
