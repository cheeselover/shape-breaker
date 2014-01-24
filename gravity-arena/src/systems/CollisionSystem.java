package systems;

import main.EntityFactory;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import components.Block;
import components.Bounds;
import components.Gun;
import components.Position;
import components.Shape;
import components.Velocity;

public class CollisionSystem extends EntitySystem {

	@Mapper ComponentMapper<Bounds> bm;
	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Velocity> vm;
	@Mapper ComponentMapper<Shape> shm;
	@Mapper ComponentMapper<Block> blm;
	@Mapper ComponentMapper<Gun> gm;
	
	private Bag<CollisionPair> collisionPairs;
	
	private class CollisionPair {
		
		private ImmutableBag<Entity> entitiesA;
		private ImmutableBag<Entity> entitiesB;
		private CollisionHandler handler;
		
		public CollisionPair(String group1, String group2, CollisionHandler handler){
			entitiesA = world.getManager(GroupManager.class).getEntities(group1);
			entitiesB = world.getManager(GroupManager.class).getEntities(group2);
			this.handler = handler;
		}
		
		public void checkForCollisions(){
			for(int i = 0; i < entitiesA.size(); i++){
				for(int j = 0; j < entitiesB.size(); j++){
					Entity a = entitiesA.get(i);
					Entity b = entitiesB.get(j);
					if(collisionExists(a, b)){
						handler.handleCollision(a, b);
					}
				}
			}
		}
		
		private boolean collisionExists(Entity a, Entity b){
			Position p1 = pm.get(a);
			Position p2 = pm.get(b);
			
			Bounds b1 = bm.get(a);
			Bounds b2 = bm.get(b);
			
			return !((p1.x > p2.x + b2.bx) || (p1.x + b1.bx < p2.x) || (p1.y > p2.y + b2.by) || (p1.y + b1.by < p2.y));
		}
		
	}
	
	private interface CollisionHandler {
		
		public void handleCollision(Entity a, Entity b);
		
	}
	
	@SuppressWarnings("unchecked")
	public CollisionSystem() {
		super(Aspect.getAspectForAll(Bounds.class, Position.class));
	}
	
	@Override
	public void initialize(){
		collisionPairs = new Bag<>();
		
		collisionPairs.add(new CollisionPair("D_BULLETS", "BLOCKS", new CollisionHandler(){
			@Override
			public void handleCollision(Entity bullet, Entity block){
				if(Block.canBreak){
					Block.canBreak = false;
					bullet.deleteFromWorld();
					
					Position p = pm.get(block);
					for(Entity entity : EntityFactory.createSmallExplosion(world, p.x, p.y)){
						entity.addToWorld();
					}
					
					Entity shape = world.getManager(GroupManager.class).getEntities("SHAPE").get(0);
					
					Shape s = shm.get(shape);
					int y = blm.get(block).yIndex, x = blm.get(block).xIndex;
					s.grid[y][x] = false;
					
					block.deleteFromWorld();
					
					Timer timer = new Timer();
					timer.scheduleTask(new Task(){
						@Override
						public void run() {
							Block.canBreak = true;
						}
					}, 0.1f);
				}
			}
		}));
		
		collisionPairs.add(new CollisionPair("R_BULLETS", "BLOCKS", new CollisionHandler(){
			@Override
			public void handleCollision(Entity bullet, Entity block){
				if(Block.canBreak){
					Block.canBreak = false;
					bullet.deleteFromWorld();
					
					Position p = pm.get(block);
					
					Entity shape = world.getManager(GroupManager.class).getEntities("SHAPE").get(0);
					
					Shape s = shm.get(shape);
					int y = blm.get(block).yIndex, x = blm.get(block).xIndex;
					
					if(vm.get(bullet).vx < 0 && x < 4){
						s.grid[y][x + 1] = true;
						EntityFactory.createBlock(world, x+1, y, p.x + 32, p.y, 0, Shape.spawnSpeed, "block").addToWorld();
						for(Entity entity : EntityFactory.createSmallExplosion(world, p.x + 32, p.y)){
							entity.addToWorld();
						}
					} else if(vm.get(bullet).vx > 0 && x > 0){
						s.grid[y][x - 1] = true;
						EntityFactory.createBlock(world, x-1, y, p.x - 32, p.y, 0, Shape.spawnSpeed, "block").addToWorld();
						for(Entity entity : EntityFactory.createSmallExplosion(world, p.x - 32, p.y)){
							entity.addToWorld();
						}
					}
					
					Timer timer = new Timer();
					timer.scheduleTask(new Task(){
						@Override
						public void run() {
							Block.canBreak = true;
						}
					}, 0.1f);
				}
			}
		}));
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		for(int i = 0; i < collisionPairs.size(); i++){
			collisionPairs.get(i).checkForCollisions();
		}
	}

}
