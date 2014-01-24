package systems;

import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class MenuRenderSystem extends VoidEntitySystem {

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private TextureAtlas atlas;
	
	public MenuRenderSystem(OrthographicCamera camera){
		this.camera = camera;
	}
	
	@Override
	protected void processSystem(){
		batch.draw(atlas.findRegion("logo"), 31.5f, 440);
		batch.draw(atlas.findRegion("play_button"), 55, 240);
		batch.draw(atlas.findRegion("highscores_button"), 55, 100);
	}
	
	@Override
	protected void initialize(){
		this.batch = new SpriteBatch();
		atlas = new TextureAtlas(Gdx.files.internal("resources/textures/pack.atlas"), Gdx.files.internal("resources/textures"));
	}
	
	@Override
	protected void begin(){
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
	}
	
	@Override
	protected void end(){
		batch.end();
	}
	
}
