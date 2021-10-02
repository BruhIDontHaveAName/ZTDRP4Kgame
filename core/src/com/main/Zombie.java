package com.main;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Zombie {
    int x, y, w, h, speed;
    String type;

    Zombie(String type,  int x, int y, int speed){
        this.type = type;
        this.x = x;
        this.y = y;
        this.speed = speed;

        w = Resources.zombie.getWidth();
        h = Resources.zombie.getHeight();
    }

    void draw(SpriteBatch b){
        b.draw(Resources.zombie, x, y);
    }

    void update(){
        x -= speed;
    }
}
