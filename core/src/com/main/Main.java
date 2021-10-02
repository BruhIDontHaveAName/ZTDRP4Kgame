package com.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	Zombie zombie;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		zombie = new Zombie("zzz", 526, 300, 1);
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		update();
		batch.begin();
		batch.draw(Resources.bg, 0, 0);
		batch.end();
	}

	void update(){
		tap();
	}

	void tap(){

	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
