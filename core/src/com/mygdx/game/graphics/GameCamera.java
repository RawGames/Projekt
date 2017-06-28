package com.mygdx.game.graphics;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;

/**
 * Created by sebastianjohansson on 2017-06-28.
 */
public class GameCamera {

    OrthographicCamera cam;
    public Matrix4 projection;

    int camWidth;
    int camHeight;

    public GameCamera(int viewWidth, int viewHeight){
        cam = new OrthographicCamera(viewWidth, viewHeight);
        projection = cam.combined;
        cam.update();

        camWidth = viewWidth;
        camHeight = viewHeight;

        setPosition(0, 0);
    }

    public void update(){
        // uppdaterar skit
        cam.update();
    }

    public void setPosition(float x, float y){
        // ändrar position av kamera
        cam.translate(x + (camWidth/4), y + (camHeight/4));
    }



}
