package com.rawgames.skybouncer.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by sebbe on 2017-06-30.
 */
public class StaticPlatform extends Obsticle {

    Sprite spr;
    int target;

    public StaticPlatform(float x, float y, Texture texture){
        position =  new Vector2(x, y);
        rectangle = new Vector2(64, 8);
        velocity = new Vector2(0 , 0);

        spr = new Sprite(texture);
        target = com.rawgames.skybouncer.Game.WIDTHT * (int)(Math.random()*2);
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
