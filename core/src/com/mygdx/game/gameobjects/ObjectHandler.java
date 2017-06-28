package com.mygdx.game.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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

    public ObjectHandler(){
        // constructor
        obsticles = new ArrayList<Obsticle>();
        playerImg = new Texture("test.png");
        player = new Player(playerImg);
    }

    public void update(){
        // uppdaterar player objektet
        player.update();

        // uppdaterar varje hinder
        for (Obsticle obsticle : obsticles){
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

    public void restart(){
        // resets position
        player.start();
    }

    public void dispose(){
        playerImg.dispose();
    }

}
