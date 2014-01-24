package main;

import com.badlogic.gdx.Game;

public class GravityArena extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen(this));
	}

}
