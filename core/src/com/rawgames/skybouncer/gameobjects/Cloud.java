package com.rawgames.skybouncer.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.rawgames.skybouncer.utils.Timer;

/**
 * Created by sebastianjohansson on 2017-06-30.
 */
public class Cloud {

    Vector2 position;
    Vector2 velocity;

    Sprite spr;

    Timer bounceTimer;

    public Cloud(float x, float y, float xVel, Texture texture){
        position = new Vector2(x, y);
        velocity = new Vector2(xVel, .04f);
        spr = new Sprite(texture);

        bounceTimer = new Timer(75, true);
    }

    public void update(){

        // move to left side if outside screen
        if (position.x > com.rawgames.skybouncer.Game.WIDTHT + 200) position.x = -200;

        if (bounceTimer.checkTimer()){
            velocity.y = -velocity.y;
            bounceTimer.setTime(75);
        }

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
