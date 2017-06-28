package com.mygdx.game.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Game;

/**
 * Created by sebbe on 2017-06-28.
 */
public class Player {

    //Sprite
    Sprite spr;

    // vectorer
    public Vector2 position;
    public Vector2 velocity;

    float grav;

    public Player(Texture playerTexture){
        //vectorer och andra variables
        position = new Vector2(Game.WIDTHT/2,Game.HEIGHT/3);
        velocity = new Vector2(0,0);
        grav = .25f;

        // sprite
        spr = new Sprite(playerTexture);
    }

    public void update(){
        // gravity
        if (Game.GameStarted){
            velocity.y -= grav;
        }

        if (Gdx.input.justTouched()){
            Game.GameStarted = true;
        }

        // addera hastighet till position
        position.add(velocity);

        // fixar sprite position
        spr.setPosition(position.x - 32, position.y - 32); // subtraherar halva spriten så att x och y värden hamnar i mitten av bilden
    }

    public void draw(SpriteBatch batch){
        spr.draw(batch);
    }

}
