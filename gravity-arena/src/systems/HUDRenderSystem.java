package systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import components.Player;

public class HUDRenderSystem extends EntityProcessingSystem {

	@Mapper ComponentMapper<Player> scm;
	
	private BitmapFont font;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	
	@SuppressWarnings("unchecked")
	public HUDRenderSystem(OrthographicCamera camera) {
		super(Aspect.getAspectForAll(Player.class));
		this.camera = camera;
	}

	@Override
	protected void process(Entity e) {
		batch.setColor(1, 1, 1, 1);
		font.draw(batch, String.format("SCORE:  %d", scm.get(e).score), 70, 29);
	}
	
	@Override
	protected void initialize() {
		batch = new SpriteBatch();
		font = new BitmapFont();		
		font.setUseIntegerPositions(false);
	}

	@Override
	protected void begin() {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
	}
	
	@Override
	protected void end() {
		batch.end();
	}

}
