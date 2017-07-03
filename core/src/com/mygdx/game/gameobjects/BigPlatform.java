package com.mygdx.game.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Game;

/**
 * Created by sebastianjohansson on 2017-07-03.
 */
public class BigPlatform extends Obsticle {

    Sprite spr;
    int target;

    public BigPlatform(float x, float y, Texture texture){
        position =  new Vector2(x, y);
        rectangle = new Vector2(96, 8);
        velocity = new Vector2(0 , 0);

        spr = new Sprite(texture);
        target = Game.WIDTHT * (int)(Math.random()*2);
    }

    public void update(){

        // sätter positionen korrekt
        spr.setPosition(position.x-rectangle.x/2, position.y-rectangle.y/2);
    }

    public void draw(SpriteBatch batch){

        //Målar skiiten
        spr.draw(batch);

    }

}
