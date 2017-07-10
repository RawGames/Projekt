package com.rawgames.skybouncer.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by sebbe on 2017-06-29.
 */
public class MovingPlatform extends Obsticle{

    Sprite spr;
    int target;

    public MovingPlatform(float x, float y, Texture texture){
        position =  new Vector2(x, y);
        rectangle = new Vector2(64, 8);
        velocity = new Vector2(0 , 0);

        spr = new Sprite(texture);
        target = com.rawgames.skybouncer.Game.WIDTHT * (int)(Math.random()*2);
    }

    public void update(){

        // change direction
        if (position.x == target){
            target = (target == 0) ? (target = com.rawgames.skybouncer.Game.WIDTHT) : (target = 0);
        }

        // go towards target
        position.x = approach(position.x, target, 1);

        // adding velocity
        position.add(velocity);

        //setting sprite position
        spr.setPosition(position.x-rectangle.x/2, position.y-rectangle.y/2);
    }

    public void draw(SpriteBatch batch){
        // DRAWING SPRITE
        spr.draw(batch);
    }

    float approach(float value, float target, float speed){

        if (value == target) return value;

        if (value < target){
            value += speed;
            if (value >= target) return target;
        } else {
            value -= speed;
            if (value <= target) return target;
        }

        return value;

    }

}
