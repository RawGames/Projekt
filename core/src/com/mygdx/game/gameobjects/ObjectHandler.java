package com.mygdx.game.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Game;

import java.util.ArrayList;

/**
 * Created by sebastianjohansson on 2017-06-28.
 */
public class ObjectHandler {

    // Objekt
    ArrayList<Obsticle> obsticles;
    Player player;

    // Textures
    Texture playerImg;
    Texture movingPlatformImg;

    int previousScore;

    public ObjectHandler(){
        // Textures
        playerImg = new Texture("player.png");
        movingPlatformImg = new Texture("movingPlatform.png");

        player = new Player(playerImg);
        obsticles = new ArrayList<Obsticle>();
    }

    public void update(){
        // uppdaterar player objektet
        player.update();

        // create platforms
        if (Game.score % 5 == 0){
            if (Game.score > previousScore){
                previousScore = Game.score;
                obsticles.add(new MovingPlatform(randomRange(0, Game.WIDTHT), player.bestY + 250, movingPlatformImg));
            }
        }

        // uppdaterar varje hinder
        for (Obsticle obsticle : obsticles){
            if (isColliding(obsticle)) player.die();
            obsticle.update();
        }
    }

    public void draw(SpriteBatch batch){
        // målar player ojektet
        player.draw(batch);

        // målar varje hinder
        for (Obsticle obsticle : obsticles){
            obsticle.draw(batch);
        }
    }

    // checks if player is colliding with other objects
    boolean isColliding(Obsticle obsticle){
        float closestX = clamp(player.position.x, obsticle.position.x - obsticle.rectangle.x/2, obsticle.position.x + obsticle.rectangle.x/2);
        float closestY = clamp(player.position.y, obsticle.position.y - obsticle.rectangle.y/2, obsticle.position.y + obsticle.rectangle.y/2);

        float distanceX = player.position.x - closestX;
        float distanceY = player.position.y - closestY;

        float distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);
        return distanceSquared < (player.rad * player.rad);
    }

    float clamp(float val, float min, float max){
        return Math.max(min, Math.min(max, val));
    }

    float randomRange(float min, float max){
        float value = (float)Math.random() * (max-min) + min;
        return value;
    }

    public void restart(){
        // resets position
        player.start();

        // delete the obsticles
        obsticles.clear();

        // resets previous score
        previousScore = 0;
    }

    public void dispose(){
        // dispose things
        playerImg.dispose();
        movingPlatformImg.dispose();
    }

}
