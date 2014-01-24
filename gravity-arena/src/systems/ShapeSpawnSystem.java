package systems;

import main.EntityFactory;

import com.artemis.managers.GroupManager;
import com.artemis.systems.VoidEntitySystem;
import components.Shape;

public class ShapeSpawnSystem extends VoidEntitySystem {
	
	@Override
	protected void processSystem() {
		if(world.getManager(GroupManager.class).getEntities("SHAPE").size() == 0){
			Shape.spawnSpeed -= 0.5f;
			EntityFactory.createShape(world, 170, 680).addToWorld();
		}
	}

}
