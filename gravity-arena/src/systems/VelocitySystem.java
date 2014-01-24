package systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import components.Position;
import components.Velocity;

public class VelocitySystem extends EntityProcessingSystem {

	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Velocity> vm;
	
	@SuppressWarnings("unchecked")
	public VelocitySystem() {
		super(Aspect.getAspectForAll(Position.class, Velocity.class));
	}

	@Override
	protected void process(Entity e) {
		Position p = pm.get(e);
		Velocity v = vm.get(e);
		
		p.x += v.vx * world.delta;
		p.y += v.vy * world.delta;
	}
	
}
