package systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import components.Highscore;
import components.Position;

public class HighscoreRenderSystem extends EntitySystem {

	@Mapper ComponentMapper<Highscore> hm;
	@Mapper ComponentMapper<Position> pm;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private TextureAtlas atlas;
	private BitmapFont font;
	private boolean died;
	
	@SuppressWarnings("unchecked")
	public HighscoreRenderSystem(OrthographicCamera camera, boolean died){
		super(Aspect.getAspectForAll(Highscore.class));
		this.camera = camera;
		this.died = died;
	}
	
	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		for(int i = 0; i < entities.size(); i++){
			Entity e = entities.get(i);
			Position p = pm.get(e);
			
			process(entities.get(i), p.x, p.y);
		}
		
		batch.draw(atlas.findRegion("bg"), 50, 0);
		batch.draw(atlas.findRegion("menu"), 144.5f, 55);
		batch.draw(atlas.findRegion((died) ? "gameover" : "highscores"), 70, 563);
		batch.draw(atlas.findRegion("arrow-down"), 0, 0);
		batch.draw(atlas.findRegion("arrow-up"), 427, 0);
		
	}
	
	protected void process(Entity e, float x, float y){
		Highscore h = hm.get(e);
		
		font.draw(batch, String.format("#%-5d     %s%10d", h.position, h.playerName, h.score), x, y);
	}
	
	@Override
	protected void initialize(){
		batch = new SpriteBatch();
		atlas = new TextureAtlas(Gdx.files.internal("resources/textures/pack.atlas"), Gdx.files.internal("resources/textures"));
		font = new BitmapFont(Gdx.files.internal("resources/fonts/mono.fnt"));		
		font.setUseIntegerPositions(false);
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

	@Override
	protected boolean checkProcessing() {
		return true;
	}

}
