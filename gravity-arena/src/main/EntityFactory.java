package main;

import java.util.Random;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import components.Acceleration;
import components.Block;
import components.Bounds;
import components.Expires;
import components.Gun;
import components.Highscore;
import components.Pattern;
import components.Player;
import components.Position;
import components.Shape;
import components.Sprite;
import components.Sprite.Layer;
import components.Velocity;

public class EntityFactory {

	private static Random r = new Random();
	
	public static Entity createBlock(World world, int xIndex, int yIndex, float x, float y, float vx, float vy, String name){
		Entity e = world.createEntity();
		
		e.addComponent(new Position(x, y));
		e.addComponent(new Block(xIndex, yIndex));
		e.addComponent(new Velocity(vx, vy));
		e.addComponent(new Acceleration(0, 0));
		e.addComponent(new Bounds(16, 16));
		e.addComponent(new Sprite(name, Layer.BLOCKS));
		e.addComponent(new Expires(false, false, true, false));
		
		world.getManager(GroupManager.class).add(e, "BLOCKS");
		
		return e;
	}
	
	public static Entity createDestroyGun(World world, float x, float y, String orientation){
		Entity e = world.createEntity();
		
		e.addComponent(new Gun(orientation, false));
		e.addComponent(new Position(x, y));
		e.addComponent(new Velocity(0, 0));
		e.addComponent(new Bounds(16, 16));
		
		Sprite sprite = new Sprite(orientation + "_gun", Layer.GUNS);
		sprite.r = 0;
		sprite.g = 1;
		sprite.b = 0;
		e.addComponent(sprite);
		
		world.getManager(GroupManager.class).add(e, "GUNS");
		
		return e;
	}
	
	public static Entity createRepairGun(World world, float x, float y, String orientation){
		Entity e = world.createEntity();
		
		e.addComponent(new Gun(orientation, true));
		e.addComponent(new Position(x, y));
		e.addComponent(new Velocity(0, 0));
		e.addComponent(new Bounds(16, 16));
		
		Sprite sprite = new Sprite(orientation + "_gun", Layer.GUNS);
		sprite.r = 0;
		sprite.g = 0;
		sprite.b = 1;
		e.addComponent(sprite);
		
		world.getManager(GroupManager.class).add(e, "GUNS");
		
		return e;
	}
	
	public static Entity createParticle(World world, float x, float y, float vx, float vy, float ax, float ay){
		Entity e = world.createEntity();
		
		e.addComponent(new Position(x, y));
		e.addComponent(new Bounds(1.5f, 1.5f));
		e.addComponent(new Velocity(vx, vy));
		e.addComponent(new Acceleration(ax, ay));
		e.addComponent(new Expires(true, true, true, true));
		
		e.addComponent(new Sprite("particle", Layer.PARTICLES));
		
		world.getManager(GroupManager.class).add(e, "PARTICLES");
		
		return e;
	}
	
	public static Entity createDestroyBullet(World world, float x, float y, float vx, float vy, float ax, float ay){
		Entity e = world.createEntity();
		
		e.addComponent(new Position(x, y));
		e.addComponent(new Bounds(8, 24));
		e.addComponent(new Velocity(vx, vy));
		e.addComponent(new Acceleration(ax, ay));
		e.addComponent(new Sprite("bullet", Layer.PARTICLES));
		e.addComponent(new Expires());
		
		world.getManager(GroupManager.class).add(e, "D_BULLETS");
		
		return e;
	}
	
	public static Entity createRepairBullet(World world, float x, float y, float vx, float vy, float ax, float ay){
		Entity e = world.createEntity();
		
		e.addComponent(new Position(x, y));
		e.addComponent(new Bounds(8, 24));
		e.addComponent(new Velocity(vx, vy));
		e.addComponent(new Acceleration(ax, ay));
		e.addComponent(new Sprite("bullet", Layer.PARTICLES));
		e.addComponent(new Expires());
		
		world.getManager(GroupManager.class).add(e, "R_BULLETS");
		
		return e;
	}
	
	public static Entity[] createSmallExplosion(World world, float x, float y){
		Entity[] entities = new Entity[30];
		
		for(int i = 0; i < 30; i += 2){
			entities[i] = createParticle(world, x, y, r.nextFloat() * 8, r.nextFloat() * 16, 0, -4);
			entities[i+1] = createParticle(world, x, y, r.nextFloat() * -8, r.nextFloat() * 16, 0, -4);
		}
		
		return entities;
	}
	
	public static Entity[] createRadialExplosion(World world, float x, float y){
		Entity[] entities = new Entity[180];
		
		for(int i = 0; i < 180; i += 4){
			entities[i] = createParticle(world, x, y, Math.max(3, (float) Math.cos(Math.toRadians(i)) * r.nextFloat() * 8), Math.max(3, (float) Math.sin(Math.toRadians(i)) * 8 * r.nextFloat()), 0, 0);
			entities[i+1] = createParticle(world, x, y, Math.min(-3, (float) Math.cos(Math.toRadians(i)) * r.nextFloat() * -8), Math.min(-3, (float) Math.sin(Math.toRadians(i)) * -8 * r.nextFloat()), 0, 0);
			entities[i+2] = createParticle(world, x, y, Math.max(3, (float) Math.cos(Math.toRadians(i)) * r.nextFloat() * 8), Math.min(-3, (float) Math.sin(Math.toRadians(i)) * -8 * r.nextFloat()), 0, 0);
			entities[i+3] = createParticle(world, x, y, Math.min(-3, (float) Math.cos(Math.toRadians(i)) * r.nextFloat() * -8), Math.max(3, (float) Math.sin(Math.toRadians(i)) * 8 * r.nextFloat()), 0, 0);
		}
		
		return entities;
	}
	
	public static Entity createShape(World world, float x, float y){
		Entity e = world.createEntity();
		
		Shape shape = new Shape();
		
		for(int yy = 0; yy < 5; yy++){
			for(int xx = 0; xx < 5; xx++){
				if(shape.grid[yy][xx]){
					EntityFactory.createBlock(world, xx, 4 - yy, xx*32 + x, yy*32 + y, 0, Shape.spawnSpeed, "block").addToWorld();
				}
			}
		}
		
		e.addComponent(shape);
		e.addComponent(new Position(x, y));
		e.addComponent(new Velocity(0, Shape.spawnSpeed));
		e.addComponent(new Expires(false, false, true, false));
		
		world.getManager(GroupManager.class).add(e, "SHAPE");
		
		return e;
	}
	
	public static Entity createButton(World world, float x, float y, String name){
		Entity e = world.createEntity();
		
		e.addComponent(new Sprite(name, Layer.BACKGROUND));
		e.addComponent(new Position(x, y));
		
		world.getManager(GroupManager.class).add(e, "BUTTONS");
		
		return e;
	}
	
	public static Entity createBackground(World world, float x, float y){
		Entity e = world.createEntity();
		
		e.addComponent(new Sprite("bg", Layer.BACKGROUND));
		e.addComponent(new Position(x, y));
		
		world.getManager(GroupManager.class).add(e, "BUTTONS");
		
		return e;
	}
	
	public static Entity createPattern(World world, float x, float y){
		Entity e = world.createEntity();
		
		e.addComponent(new Pattern());
		e.addComponent(new Position(x, y));
		
		world.getManager(GroupManager.class).add(e, "PATTERNS");
		
		return e;
	}
	
	public static Entity createPlayer(World world){
		Entity e = world.createEntity();
		
		Player player = new Player(); 
		e.addComponent(player);
		
		for(int i = 0; i < player.lives; i++){
			EntityFactory.createHeart(world, i*25 + 353, 22).addToWorld();
		}
		
		world.getManager(GroupManager.class).add(e, "PLAYER");
		
		return e;
	}
	
	public static Entity createHeart(World world, float x, float y){
		Entity e = world.createEntity();
		
		e.addComponent(new Sprite("heart", Layer.BACKGROUND));
		e.addComponent(new Position(x, y));
		
		world.getManager(GroupManager.class).add(e, "HEARTS");
		
		return e;
	}
	
	public static Entity createHighscore(World world, float y, int position, String playerName, int score){
		Entity e = world.createEntity();
		
		e.addComponent(new Highscore(y, position, playerName, score));
		e.addComponent(new Position(70, y));
		e.addComponent(new Velocity(0, 0));
		
		world.getManager(GroupManager.class).add(e, "HIGHSCORES");
		
		return e;
	}
	
}
