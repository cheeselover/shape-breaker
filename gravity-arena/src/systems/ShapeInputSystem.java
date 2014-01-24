package systems;

import main.EntityFactory;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

import components.Position;
import components.Shape;

public class ShapeInputSystem extends EntityProcessingSystem implements InputProcessor {

	@Mapper ComponentMapper<Shape> shm;
	@Mapper ComponentMapper<Position> pm;
	private Position p;
	
	private Vector3 mouseVector;
	private boolean isTouched = false;
	private OrthographicCamera camera;
	
	@SuppressWarnings("unchecked")
	public ShapeInputSystem(OrthographicCamera camera) {
		super(Aspect.getAspectForAll(Shape.class));
		this.camera = camera;
	}
	
	@Override
	protected void process(Entity e) {
		mouseVector = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(mouseVector);
		
		Shape s = shm.get(e);
		p = pm.get(e);
		
		if(isTouched && mouseVector.x >= 0 && mouseVector.x <= 60 && mouseVector.y >= 0 && mouseVector.y <= 60){
			isTouched = false;
			s.rotateCounterClockwise();
			resetBlockPositions(s);
		} else if(isTouched && mouseVector.x >= 420 && mouseVector.x <= 480 && mouseVector.y >= 0 && mouseVector.y <= 60){
			isTouched = false;
			s.rotateClockwise();
			resetBlockPositions(s);
		} else if(isTouched && mouseVector.x >= p.x && mouseVector.x <= p.x + 5*32 && mouseVector.y >= p.y && mouseVector.y <= p.y + 5*32){
			Shape.isTouched = true;
			isTouched = false;
		}
	}
	
	private void resetBlockPositions(Shape shape){
		ImmutableBag<Entity> entities = world.getManager(GroupManager.class).getEntities("BLOCKS");
		for(int i = 0; i < entities.size(); i++){
			entities.get(i).deleteFromWorld();
		}
		for(int y = 0; y < shape.grid.length; y++){
			for(int x = 0; x < shape.grid.length; x++){
				if(shape.grid[y][x]){
					EntityFactory.createBlock(world, x, y, x*32 + p.x, (4-y) * 32 + p.y, 0, Shape.spawnSpeed, "block").addToWorld();
				}
			}
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Input.Keys.UP) p.y++;
		else if(keycode == Input.Keys.DOWN) p.y--;
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
		Shape.isTouched = false;
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