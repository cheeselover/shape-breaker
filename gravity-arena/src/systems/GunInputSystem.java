package systems;

import main.EntityFactory;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import components.Bounds;
import components.Gun;
import components.Position;

public class GunInputSystem extends EntityProcessingSystem implements InputProcessor {

	@Mapper ComponentMapper<Gun> gm;
	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Bounds> bm;
	
	private Vector3 mouseVector;
	private OrthographicCamera camera;
	
	@SuppressWarnings("unchecked")
	public GunInputSystem(OrthographicCamera camera) {
		super(Aspect.getAspectForAll(Gun.class));
		this.camera = camera;
	}
	
	@Override
	protected void process(Entity e) {
		mouseVector = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		Position p = pm.get(e);
		Bounds b = bm.get(e);
		
		camera.unproject(mouseVector);
		
		final Gun g = gm.get(e);
		
		if(Gdx.input.justTouched()){			
			if(mouseVector.x >= p.x - b.bx && mouseVector.x <= p.x + b.bx && mouseVector.y >= p.y - b.by && mouseVector.y <= p.y + b.by){
				world.getSystem(SpriteRenderSystem.class).resetName(e, g.orientation + "_gun_selected");
				
				boolean orientation = g.orientation.equals("left"); // if left gun, then true, else false
				if(g.canShoot){
					if(g.isRepair){
						EntityFactory.createRepairBullet(world, (orientation) ? p.x + 36 : p.x - 36, p.y, (orientation) ? 1 : -1, 0, (orientation) ? 25 : -25, 0).addToWorld();
					} else {
						EntityFactory.createDestroyBullet(world, (orientation) ? p.x + 36 : p.x - 36, p.y, (orientation) ? 1 : -1, 0, (orientation) ? 25 : -25, 0).addToWorld();
					}
					
					g.canShoot = false;
					
					new Timer().scheduleTask(new Task(){
						@Override
						public void run() {
							g.canShoot = true;
						}
					}, 0.75f);
				}
			}
		} else if(g.canShoot){
			world.getSystem(SpriteRenderSystem.class).resetName(e, g.orientation + "_gun");
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
