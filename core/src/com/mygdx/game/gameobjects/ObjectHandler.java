package com.mygdx.game.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Game;

import java.util.ArrayList;

/**
 * Created by sebastianjohansson on 2017-06-28.
 */
public class ObjectHandler {

    // Objekt
    ArrayList<Obsticle> obsticles;
    ArrayList<Cloud> clouds;
    Player player;

    // Textures
    Texture playerImg;
    Texture smallPlatformImg;
    Texture bigPlatformImg;
    Texture cloudImg;

    // button Textures
    Texture muteBtn;
    Texture soundBtn;

    int previousScore;

    public ObjectHandler(){
        // Textures
        playerImg = new Texture("player.png");
        smallPlatformImg = new Texture("smallPlatform.png");
        bigPlatformImg = new Texture("bigPlatform.png");
        cloudImg = new Texture("cloud.png");

        // buttons
        muteBtn = new Texture("muteBtn.png");
        soundBtn = new Texture("soundBtn.png");

        // objects
        player = new Player(playerImg);
        obsticles = new ArrayList<Obsticle>();
        clouds = new ArrayList<Cloud>();

        clouds.add(new Cloud(randomRange(-64, Game.WIDTHT), 200, .2f, cloudImg));
    }

    public void update(){

        player.touch = Gdx.input.justTouched();

        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        Game.cam.unproject(touchPos);

        if (touchPos.x > Game.WIDTHT - 34 && touchPos. x < Game.WIDTHT - 2){
            if (touchPos.y > 2 && touchPos.y < 34 && player.touch){
                Game.sound = !Game.sound;
                player.touch = false;
            }
        }

        // uppdaterar player objektet
        player.update();

        // create platforms
        if (Game.score % 5 == 0){
            if (Game.score > previousScore){
                previousScore = Game.score;
                clouds.add(new Cloud(randomRange(-128, Game.WIDTHT), player.bestY + randomRange(250, 400), randomRange(0.1f, 1), cloudImg));
                clouds.add(new Cloud(randomRange(-128, Game.WIDTHT), player.bestY + randomRange(500, 650), randomRange(0.1f, 1), cloudImg));
                createObsticle();
            }
        }

        // uppdaterar hinder
        for (int i = 0; i < obsticles.size(); i ++){
            if (isColliding(obsticles.get(i))) player.die();
            obsticles.get(i).update();
            if (obsticles.get(i).position.y < player.bestY - 300)
                obsticles.remove(i);
        }

        // uppdaterar moln
        for (int i = 0; i < clouds.size(); i++){
            clouds.get(i).update();
            if (clouds.get(i).position.y < player.bestY - 300)
                clouds.remove(i);
        }
    }

    public void draw(SpriteBatch batch){

        // måla moln
        for(Cloud cloud : clouds){
            cloud.draw(batch);
        }

        // målar player ojektet
        player.draw(batch);

        // målar varje hinder
        for (Obsticle obsticle : obsticles){
            obsticle.draw(batch);
        }

        if (!Game.GameStarted){
            if (!Game.sound)
                batch.draw(muteBtn, Game.WIDTHT - 34, 2);
            else
                batch.draw(soundBtn, Game.WIDTHT - 34, 2);
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

    int irandomRange(float min, float max){
        return (int)(Math.random() * ((max - min) + 1));
    }

    void createObsticle(){

        int chance = irandomRange(0, 3);

        switch (chance){
            case 0:
                obsticles.add(new MovingPlatform(randomRange(0, Game.WIDTHT), player.bestY + 250, smallPlatformImg));
                break;
            case 1:
                obsticles.add(new StaticPlatform(8, player.bestY + 250, smallPlatformImg));
                obsticles.add(new StaticPlatform(Game.WIDTHT-8, player.bestY + 250, smallPlatformImg));
                break;
            case 2:
                obsticles.add(new BigPlatform(16, player.bestY + 250, bigPlatformImg));
                break;
            case 3:
                obsticles.add(new BigPlatform(Game.WIDTHT-16, player.bestY + 250, bigPlatformImg));
                break;
        }

    }

    public void restart(){
        // resets position
        player.start();

        // delete the obsticles
        obsticles.clear();
        clouds.clear();

        clouds.add(new Cloud(randomRange(-64, Game.WIDTHT), randomRange(200, 250), .2f, cloudImg));

        // resets previous score
        previousScore = 0;
    }

    public void dispose(){
        // dispose things
        playerImg.dispose();
        smallPlatformImg.dispose();
        bigPlatformImg.dispose();
        cloudImg.dispose();
        player.dispose();
        soundBtn.dispose();
        muteBtn.dispose();
    }

}
