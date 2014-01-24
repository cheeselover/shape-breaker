package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import systems.HighscoreInputSystem;
import systems.HighscoreRenderSystem;
import systems.VelocitySystem;

import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import components.Highscore;

public class HighscoreScreen implements Screen {

	private HighscoreRenderSystem hsRenderSystem;
	
	private OrthographicCamera camera;
	private World world;
	private Game game;
	
	public HighscoreScreen(Game game, boolean died){
		camera = new OrthographicCamera();
		this.game = game;
		camera.setToOrtho(false, 480, 640);
		
		world = new World();
		world.setManager(new GroupManager());
		
		hsRenderSystem = world.setSystem(new HighscoreRenderSystem(camera, died), true);
		world.setSystem(new HighscoreInputSystem(camera, game));
		world.setSystem(new VelocitySystem());
		
		world.initialize();
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(Gdx.files.local("scores.txt").file()));
			for(int i = 0; i < Highscore.scoreTable.size(); i++){
				String[] line = in.readLine().split(",");
				EntityFactory.createHighscore(world, 560 - i*30, i+1, line[0], Integer.parseInt(line[1])).addToWorld();
			}
			in.close();
		} catch(IOException ioe){
		}
		
	}
	
	@Override
	public void render(float delta) {
		delta *= 8;
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    Gdx.gl20.glClearColor(110/255f, 110/255f, 110/255f, 1);
	    camera.update();
	    world.setDelta(delta);
	    world.process();
	    hsRenderSystem.process();
	    Gdx.graphics.setTitle("Shape Breaker | Highscores");
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		
	}

}