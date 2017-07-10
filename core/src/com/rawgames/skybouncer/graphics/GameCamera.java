package com.rawgames.skybouncer.graphics;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by sebastianjohansson on 2017-06-28.
 */
public class GameCamera {

    OrthographicCamera cam;
    public Matrix4 projection;

    int camWidth;
    int camHeight;

    public float x;
    public float y;

    public float realY;

    public GameCamera(int viewWidth, int viewHeight){
        cam = new OrthographicCamera(viewWidth, viewHeight);
        projection = cam.combined;
        cam.update();

        camWidth = viewWidth;
        camHeight = viewHeight;

        realY = 0;

        setPosition(0, 0);
        cam.update();
        x = 0;
        y = 0;
    }

    public void update(){
        // uppdaterar skit
        cam.update();
        realY = cam.position.y;
    }

    public void setPosition(float x, float y){
        // ändrar position av kamera
        this.x = x;
        this.y = y;
        cam.position.set(x + (camWidth/2), y + (camHeight/2), 0);
        cam.update();
    }

    public void translate(float x, float y){
        cam.translate(x, y);
        this.x = cam.position.x - camWidth/2;
        this.y = cam.position.y - camHeight/2;
    }

    public Vector3 unproject(Vector3 vec){
        cam.unproject(vec);
        return vec;
    }

}
