package main;

import java.util.Random;

import systems.AccelerationSystem;
import systems.BackgroundRenderSystem;
import systems.CollisionSystem;
import systems.ExpirationSystem;
import systems.GunInputSystem;
import systems.HUDRenderSystem;
import systems.PatternRecognitionSystem;
import systems.PatternRenderSystem;
import systems.PlayerUpdateSystem;
import systems.ShapeInputSystem;
import systems.ShapeSpawnSystem;
import systems.SpriteRenderSystem;
import systems.VelocitySystem;

import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import components.Pattern;

public class GameScreen implements Screen {

	private SpriteRenderSystem spriteRenderSystem;
	private BackgroundRenderSystem bgRenderSystem;
	private PatternRenderSystem patternRenderSystem;
	private HUDRenderSystem hudRenderSystem;
	
	private OrthographicCamera camera;
	private World world;
	private Game game;
	
	public GameScreen(Game game){
		camera = new OrthographicCamera();
		this.game = game;
		camera.setToOrtho(false, 480, 640);
		
		world = new World();
		world.setManager(new GroupManager());
		
		spriteRenderSystem = world.setSystem(new SpriteRenderSystem(camera), true);
		bgRenderSystem = world.setSystem(new BackgroundRenderSystem(camera), true);
		patternRenderSystem = world.setSystem(new PatternRenderSystem(camera), true);
		hudRenderSystem = world.setSystem(new HUDRenderSystem(camera), true);
		
		world.setSystem(new VelocitySystem());
		world.setSystem(new AccelerationSystem());
		world.setSystem(new CollisionSystem());
		world.setSystem(new ExpirationSystem());
		world.setSystem(new PatternRecognitionSystem());
		world.setSystem(new PlayerUpdateSystem(game));
		world.setSystem(new ShapeSpawnSystem());
		
		InputMultiplexer im = new InputMultiplexer();
		im.addProcessor(world.setSystem(new GunInputSystem(camera)));
		im.addProcessor(world.setSystem(new ShapeInputSystem(camera)));
		Gdx.input.setInputProcessor(im);
		
		world.initialize();
		
		for(int i = 0; i < 10; i += 2){
			EntityFactory.createRepairGun(world, 60, i*50 + 150, "left").addToWorld();
			EntityFactory.createRepairGun(world, 420, i*50 + 150, "right").addToWorld();
			
			EntityFactory.createDestroyGun(world, 60, (i+1) * 50 + 150, "left").addToWorld();
			EntityFactory.createDestroyGun(world, 420, (i+1) * 50 + 150, "right").addToWorld();
		}
		
		EntityFactory.createButton(world, 30, 32, "left_arrow").addToWorld();
		EntityFactory.createButton(world, 450, 32, "right_arrow").addToWorld();
		EntityFactory.createBackground(world, 240, 320).addToWorld();
		
		Pattern.shufflePatterns();
		
		for(int i = 0; i < 4; i++){
			EntityFactory.createPattern(world, i*90 + 83, 88).addToWorld();
		}
		
		EntityFactory.createPlayer(world).addToWorld();
	}
	
	@Override
	public void render(float delta) {
		delta *= 8;
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    Gdx.gl20.glClearColor(110/255f, 110/255f, 110/255f, 1);
	    camera.update();
	    world.setDelta(delta);
	    world.process();
	    bgRenderSystem.process();
	    spriteRenderSystem.process();
	    patternRenderSystem.process();
	    hudRenderSystem.process();
	    Gdx.graphics.setTitle("Shape Breaker");
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
