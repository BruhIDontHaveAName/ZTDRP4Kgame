package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Zombie {
    int x, y, w, h, speed;
    int hp, mhp;
    String type;
    boolean active = true;

    int rows = 1, cols = 4;
    Animation anim;
    TextureRegion[] frames;
    TextureRegion frame;
    float frame_time = 0.2f;

    Zombie(String type,  int x, int y, int speed){
        this.type = type;
        this.x = x;
        this.y = y;
        this.speed = speed;
        hp = 10;
        mhp = hp;
        w = (Tables.zombies.get(type) == null ? Resources.zombie : Tables.zombies.get(type)).getWidth() / cols;
        h = (Tables.zombies.get(type) == null ? Resources.zombie : Tables.zombies.get(type)).getHeight() / rows;
        init_animations();
    }

    void draw(SpriteBatch b){
        frame_time += Gdx.graphics.getDeltaTime();
        frame = (TextureRegion)anim.getKeyFrame(frame_time,true);
        b.draw(frame, x, y);

        b.draw(Resources.red_bar, x, y + h + 1, w, 5);
        b.draw(Resources.green_bar, x, y + h + 1, hp * ((float)w / (float)mhp), 5);
    }


    void update(){
        x -= speed;
        active = x > -50 && hp > 0;
    }

    void init_animations(){
        TextureRegion[][] sheet = TextureRegion.split(Tables.zombies.get(type) == null ? Resources.zombie : Tables.zombies.get(type), w, h);

        frames = new TextureRegion[rows * cols];

        int index = 0;
        for(int r = 0; r < rows; r++)
            for(int c = 0; c < cols; c++)
                frames[index++] = sheet[r][c];

            anim = new Animation(frame_time, frames);
    }

    Rectangle hitbox() { return new Rectangle(x, y, w, h); }
}
