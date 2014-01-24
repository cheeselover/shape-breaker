package systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import components.Pattern;
import components.Position;

public class PatternRenderSystem extends EntityProcessingSystem {

	@Mapper ComponentMapper<Pattern> ptm;
	@Mapper ComponentMapper<Position> pm;
	
	private ShapeRenderer renderer;
	private OrthographicCamera camera;
	
	@SuppressWarnings("unchecked")
	public PatternRenderSystem(OrthographicCamera camera) {
		super(Aspect.getAspectForAll(Pattern.class));
		this.camera = camera;
	}

	@Override
	protected void process(Entity e) {
		Pattern pattern = ptm.get(e);
		Position position = pm.get(e);
		
		renderer.setProjectionMatrix(camera.combined);
		renderer.begin(ShapeType.Filled);
		
		if(pattern.recognized){
			renderer.setColor(70/255f, 105/255f, 0, 1);
			renderer.rect(position.x - 4, position.y - 41, 50, 50);
		}
		
		renderer.setColor(238/255f, 1, 209/255f, 1);
		for(int y = 0; y < pattern.pattern.length; y++){
			for(int x = 0; x < pattern.pattern.length; x++){
				if(pattern.pattern[y][x]) renderer.rect(position.x + 9*x, position.y - 9*y, 6, 6);
			}
		}
		
		renderer.end();
	}
	
	@Override
	protected void initialize() {
		renderer = new ShapeRenderer();
	}

}
