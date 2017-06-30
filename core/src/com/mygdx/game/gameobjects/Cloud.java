package com.mygdx.game.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Game;

/**
 * Created by sebastianjohansson on 2017-06-30.
 */
public class Cloud {

    Vector2 position;
    Vector2 velocity;

    Sprite spr;

    public Cloud(float x, float y, float xVel, Texture texture){
        position = new Vector2(x, y);
        velocity = new Vector2(xVel, 0);
        spr = new Sprite(texture);
    }

    public void update(){

        // move to left side if outside screen
        if (position.x > Game.WIDTHT + 200) position.x = -200;

        // lägg till hastighet
        position.add(velocity);
        // ändra sprite position
        spr.setPosition(position.x, position.y);
    }

    public void draw(SpriteBatch batch){
        // låla moln
        spr.draw(batch);
    }

}
