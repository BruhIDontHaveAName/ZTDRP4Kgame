package com.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Random;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	static String current_type = "ccc";
	static Random r = new Random();

	static ArrayList<Zombie> zombies = new ArrayList<Zombie>();
	static ArrayList<Cannon> cannons = new ArrayList<Cannon>();
	static ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	static ArrayList<Button> buttons = new ArrayList<Button>();
	static ArrayList<Effect> effects = new ArrayList<Effect	>();

	@Override
	public void create() {
		batch = new SpriteBatch();
		setup();
	}


	@Override
	public void render() {
		ScreenUtils.clear(1, 0, 0, 1);
		update();
		batch.begin();
		batch.draw(Resources.bg, 0, 0);
		UI.draw(batch);
		for (Zombie z : zombies) z.draw(batch);
		for (Cannon c : cannons) c.draw(batch);
		for (Bullet b : bullets) b.draw(batch);
		for (Button b : buttons) b.draw(batch);
		for (Effect e : effects) e.draw(batch);
		batch.end();
	}

	void update() {
		//calls
		tap();
		spawn_zombies();


		//LOOP
		for (Zombie z : zombies) z.update();
		for (Cannon c : cannons) c.update();
		for (Bullet b : bullets) b.update();
		for (Button b : buttons) b.update();

		//removal
		cleanup();
	}

	void cleanup() {
		for (Zombie z : zombies) {
			if (!z.active) {
				zombies.remove(z);
				break;
			}
		}
		for(Effect e : effects)  {
			if(!e.active) {
				effects.remove(e);
				break;
			}
		}
	}

	void tap() {
		if (Gdx.input.justTouched()) {
			int x = Gdx.input.getX(), y = Gdx.graphics.getHeight() - Gdx.input.getY();
			effects.add(new Effect("boom", x, y));

			for (Button b : buttons) {
				if (!b.t.hidden && b.t.hitbox().contains(x, y)) return;
				if (b.gethitbox().contains(x, y)) {
					if (b.locked) {
						if (b.t.hidden) {
							hidett();
							b.t.hidden = false;
						} else {
							hidett();
							b.locked = false;
						}
					} else {
						deselect();
						hidett();
						b.selected = true;
						current_type = b.type;
					}
					return;
				}
				if(!b.t.hidden && !b.t.hitbox().contains(x, y)&& !anybutton(x, y)) {b.t.hidden = true; return; }
			}
			for (Cannon c : cannons) if (c.hitbox().contains(x, y)) return;
			if (buildable(x, y)) cannons.add(new Cannon(current_type, x, y));
		}


	}

	void hidett(){ for(Button b : buttons) b.t.hidden = true; }

	boolean anybutton(int x, int y) {
		for (Button b : buttons) if(b.gethitbox().contains(x,y)) return true;
	return false;
	}

	boolean buildable(int x, int y){
		return ((y > 0 && y < 200)||(y > 300 && y < 500)) && x < 1000;
	}

	void deselect(){
		for (Button b : buttons) b.selected = false;
	}

	void setup(){
		Tables.init();
		current_type = "cannon";
		buttons.add(new Button("cannon", 225 + buttons.size() * 75, 525));
		buttons.get(buttons.size() - 1).locked = false;
		buttons.get(buttons.size() - 1).selected = true;
		buttons.add(new Button("fire", 225 + buttons.size() * 75, 525));
		buttons.add(new Button("super", 225 + buttons.size() * 75, 525));
		buttons.add(new Button("double", 225 + buttons.size() * 75, 525));
		buttons.add(new Button("laser", 225 + buttons.size() * 75, 525));

	}

	void spawn_zombies(){
		if (!zombies.isEmpty()) return;
		for(int i = 0; i < 6; i++) zombies.add(new Zombie("riot", 1055 + i * 90,r.nextInt(450), 3));
	}

	// TODO: warning, do not go below here
	@Override
	public void dispose () {
		batch.dispose();
	}
}
