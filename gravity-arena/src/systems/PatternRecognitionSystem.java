package systems;

import java.util.Arrays;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.ImmutableBag;
import components.Pattern;
import components.Shape;

public class PatternRecognitionSystem extends EntityProcessingSystem {

	@Mapper ComponentMapper<Shape> shm;
	@Mapper ComponentMapper<Pattern> ptm;
	
	@SuppressWarnings("unchecked")
	public PatternRecognitionSystem() {
		super(Aspect.getAspectForAll(Pattern.class));
	}

	@Override
	protected void process(Entity e) {
		ImmutableBag<Entity> shapes = world.getManager(GroupManager.class).getEntities("SHAPE");
		Pattern pattern = ptm.get(e);
		
		if(shapes.size() > 0){
			Shape shape = shm.get(shapes.get(0));
			
			if(Arrays.deepEquals(shape.grid, pattern.pattern)){
				pattern.recognized = true;
			} else {
				pattern.recognized = false;
			}
		} else {
			pattern.recognized = false;
		}
	}

}
