package systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import components.Expires;
import components.Position;

public class ExpirationSystem extends EntityProcessingSystem {

	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Expires> em;
	
	@SuppressWarnings("unchecked")
	public ExpirationSystem() {
		super(Aspect.getAspectForAll(Expires.class));
	}

	@Override
	protected void process(Entity e) {
		Position p = pm.get(e);
		Expires ex = em.get(e);
		
		if((p.x < 0 && ex.left) || (p.x > Gdx.graphics.getWidth() && ex.right) || (p.y < 0 && ex.bottom) || (p.y > Gdx.graphics.getHeight() && ex.top)){
			e.deleteFromWorld();
		}
	}

}
