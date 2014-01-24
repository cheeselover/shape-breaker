package systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import components.Acceleration;
import components.Velocity;

public class AccelerationSystem extends EntityProcessingSystem {

	@Mapper ComponentMapper<Velocity> vm;
	@Mapper ComponentMapper<Acceleration> am;
	
	@SuppressWarnings("unchecked")
	public AccelerationSystem() {
		super(Aspect.getAspectForAll(Velocity.class, Acceleration.class));
	}

	@Override
	protected void process(Entity e) {
		Velocity v = vm.get(e);
		Acceleration a = am.get(e);
		
		v.vx += a.ax * world.delta;
		v.vy += a.ay * world.delta;
	}
	
}
