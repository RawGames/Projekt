package com.mygdx.game.graphics;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;

/**
 * Created by sebastianjohansson on 2017-06-28.
 */
public class GameCamera {

    OrthographicCamera cam;
    public Matrix4 projection;

    public GameCamera(int viewWidth, int viewHeight){
        cam = new OrthographicCamera(viewWidth, viewHeight);
        projection = cam.combined;
        cam.update();
    }

    public void update(){
        cam.update();
    }

    public void setPosition(float x, float y){
        cam.translate(x, y);
    }



}
