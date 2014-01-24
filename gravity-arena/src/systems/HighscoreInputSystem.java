package systems;

import main.MenuScreen;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import components.Highscore;
import components.Position;
import components.Velocity;

public class HighscoreInputSystem extends EntityProcessingSystem implements InputProcessor {

	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Velocity> vm;
	@Mapper ComponentMapper<Highscore> hm;
	
	private OrthographicCamera camera;
	private Vector3 mouseVector;
	private Game game;
	private boolean isTouched = false;
	
	@SuppressWarnings("unchecked")
	public HighscoreInputSystem(OrthographicCamera camera, Game game){
		super(Aspect.getAspectForAll(Highscore.class));
		this.camera = camera;
		this.game = game;
	}
	
	@Override
	protected void process(Entity e) {
		mouseVector = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(mouseVector);
		
		if(Gdx.input.justTouched() && mouseVector.x > 20 && mouseVector.x < 360 && mouseVector.y > 50 && mouseVector.y < 100){
			game.setScreen(new MenuScreen(game));
		} else if(isTouched && mouseVector.y > 0 && mouseVector.y < 55){
			if(mouseVector.x > 0 && mouseVector.x < 55){
				vm.get(e).vy = -8;
			} else if(mouseVector.x > 425 && mouseVector.x < 480){
				vm.get(e).vy = 8;
			}
		} else {
			vm.get(e).vy = 0;
		}
	}
	
	@Override
	protected void initialize(){
		Gdx.input.setInputProcessor(this);
		mouseVector = new Vector3();
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
		isTouched = true;
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		isTouched = false;
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
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
