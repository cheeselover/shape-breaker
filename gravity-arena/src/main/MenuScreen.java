package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import systems.MenuInputSystem;
import systems.MenuRenderSystem;

import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import components.Highscore;

public class MenuScreen implements Screen {

	private MenuRenderSystem menuRenderSystem;
	
	private OrthographicCamera camera;
	private World world;
	private Game game;
	public static String playerName = "";
	
	public MenuScreen(Game game){
		camera = new OrthographicCamera();
		this.game = game;
		camera.setToOrtho(false, 480, 640);
		
		world = new World();
		world.setManager(new GroupManager());
		
		menuRenderSystem = world.setSystem(new MenuRenderSystem(camera), true);
		world.setSystem(new MenuInputSystem(camera, game));
		
		world.initialize();
		
		if(MenuScreen.playerName.isEmpty()){
			Gdx.input.getTextInput(new TextInputListener(){

				@Override
				public void input(String text) {
					if(text.length() > 10){
						playerName = text.substring(0, 10);
					} else if(text.length() == 10){
						playerName = text;
					} else {
						playerName = text + "          ".substring(text.length());
					}
				}

				@Override
				public void canceled() {
					
				}
				
			}, "Enter your name:", "");
			
			try {
				BufferedReader in = new BufferedReader(new FileReader(Gdx.files.local("scores.txt").file()));
				while(in.ready()){
					String[] line = in.readLine().split(",");
					Highscore.scoreTable.add(line);
				}
				in.close();
			} catch(IOException ioe){
			}
		}
		
	}
	
	@Override
	public void render(float delta) {
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    Gdx.gl.glClearColor(0, 0, 0, 1);
	    camera.update();
	    world.setDelta(delta);
	    world.process();
	    menuRenderSystem.process();
	    Gdx.graphics.setTitle("Shape Breaker | Menu");
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