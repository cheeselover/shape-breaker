package systems;

import main.GameScreen;
import main.HighscoreScreen;

import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class MenuInputSystem extends VoidEntitySystem implements InputProcessor {

	private OrthographicCamera camera;
	private Vector3 mouseVector;
	private Game game;
	
	public MenuInputSystem(OrthographicCamera camera, Game game){
		this.camera = camera;
		this.game = game;
	}
	
	@Override
	protected void processSystem() {
		mouseVector = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(mouseVector);
		
		if(Gdx.input.justTouched() && mouseVector.x > 55 && mouseVector.x < 425){
			if(mouseVector.y > 100 && mouseVector.y < 180){
				game.setScreen(new HighscoreScreen(game, false));
			} else if(mouseVector.y > 240 && mouseVector.y < 320){
				game.setScreen(new GameScreen(game));
			} 
		}
	}
	
	@Override
	protected void initialize(){
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
