package com.mygdx.game.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Game;
import com.mygdx.game.utils.Timer;

/**
 * Created by sebbe on 2017-06-28.
 */
public class Player {

    //Sprite
    Sprite spr;

    // Jump sound
    Sound jumpSnd;
    Sound hitSnd;
    Sound deathSnd;

    // vectorer
    public Vector2 position;
    public Vector2 velocity;

    // Timers
    Timer startoverTimer;
    Timer cameraShake;

    public boolean dead;

    float grav;
    public int rad;
    float bestY;

    public void start(){
        //vectorer och andra variables
        position = new Vector2(Game.WIDTHT/2,Game.HEIGHT/3);
        velocity = new Vector2(0,0);
        bestY = position.y;
        grav = .20f;

        startoverTimer = new Timer(150, false);
        cameraShake = new Timer (20, false);

        // Sounds
        jumpSnd = Gdx.audio.newSound(Gdx.files.internal("sounds/jump.wav"));
        hitSnd = Gdx.audio.newSound(Gdx.files.internal("sounds/hitSound.wav"));
        deathSnd = Gdx.audio.newSound(Gdx.files.internal("sounds/deathSound.wav"));

        dead = false;
    }

    public Player(Texture playerTexture){
        // startpositioner
        start();
        // radious
        rad = playerTexture.getWidth()/2;
        // sprite
        spr = new Sprite(playerTexture);
    }

    public void update(){
        // gravity
        if (Game.GameStarted){
            velocity.y -= grav;
        }

        // kollar efter touch
        if (Gdx.input.justTouched() && !dead){
            Game.GameStarted = true;

            // tar position och fixar till skit med matte
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            Game.cam.unproject(touchPos);

            float angle = (float)Math.atan2(touchPos.x - position.x, touchPos.y - position.y);
            velocity.set((float)Math.sin(angle)*-6, (float)Math.cos(angle)*-6);

            // play sound
            jumpSnd.play();
        }

        // new best y
        if (position.y > bestY && !dead) bestY = position.y;

        // dör om man nuddar botten
        if (position.y < Game.cam.y+rad){
            // startar om om man nuddar botten
            die();
        }
        // Dör om man nuddar sidorna
        if (position.x < rad || position.x > Game.WIDTHT-rad){
            // startar om om man nuddar sidorna
            velocity.x = -velocity.x;
            die();
        }

        // shake camera if dead
        if (dead && !cameraShake.checkTimerContinue()){
            Game.cam.setPosition(randomRange(-3, 3), Game.cam.y);
        } else if (dead && cameraShake.checkTimerStill()) {
            deathSnd.play();
            Game.cam.setPosition(0, Game.cam.y);
        }

        // starts over if timer is 0
        if (startoverTimer.checkTimer()) Game.restart();

        // flyttar kameran om positionen är över halva skärmen
        if (position.y > Game.HEIGHT/2)
            Game.cam.translate(0, (bestY - Game.WIDTHT/2 - Game.cam.y)*0.05f);

        // uppdaterar poängen
        Game.score = (int)((bestY - Game.HEIGHT/3)/100);

        // addera hastighet till position
        position.add(velocity);

        // fixar sprite position
        spr.setPosition(position.x - rad, position.y - rad); // subtraherar halva spriten så att x och y värden hamnar i mitten av bilden
    }

    public void draw(SpriteBatch batch){
        // målar
        spr.draw(batch);
    }

    public void die(){
        if (!dead) {
            startoverTimer.timerStart();
            cameraShake.timerStart();
            hitSnd.play();
            if (!dead) velocity.y = 4;
            dead = true;
        }
    }

    float randomRange(float min, float max){
        float value = (float)Math.random() * (max-min) + min;
        return value;
    }

}
