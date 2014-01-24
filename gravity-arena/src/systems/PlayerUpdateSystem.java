package systems;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;

import main.EntityFactory;
import main.HighscoreScreen;
import main.MenuScreen;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.systems.VoidEntitySystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import components.Highscore;
import components.Pattern;
import components.Player;
import components.Position;
import components.Shape;

public class PlayerUpdateSystem extends VoidEntitySystem {

	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Pattern> ptm;
	@Mapper ComponentMapper<Player> plm;
	
	private Game game;
	
	public PlayerUpdateSystem(Game game){
		this.game = game;
	}

	@Override
	protected void processSystem() {
		GroupManager manager = world.getManager(GroupManager.class); 
		ImmutableBag<Entity> shapes = manager.getEntities("SHAPE");
		final Player pl = plm.get(manager.getEntities("PLAYER").get(0));
		
		if(shapes.size() > 0){
			Entity shape = shapes.get(0);
			Position shapePos = pm.get(shape);
			
			if(shapePos.y < 0){
				if(pl.lives > 1){
					pl.lives--;
					manager.getEntities("HEARTS").get(pl.lives).deleteFromWorld();
				} else {
					String[] temp = new String[2];
					temp[0] = MenuScreen.playerName;
					temp[1] = String.valueOf(pl.score);
					
					Highscore.scoreTable.add(temp);
					Collections.sort(Highscore.scoreTable, new Comparator<String[]>(){
						@Override
						public int compare(String[] o1, String[] o2) {
							int i1 = Integer.parseInt(o1[1]), i2 = Integer.parseInt(o2[1]);
							if(i1 > i2){
								return -1;
							} else if(i1 < i2){
								return 1;
							} else {
								return 0;
							}
						}
					});
					
					try {
						BufferedWriter out = new BufferedWriter(new FileWriter(Gdx.files.local("scores.txt").file()));
						for(int i = 0; i < Highscore.scoreTable.size(); i++){
							out.write(String.format("%s,%s\n", Highscore.scoreTable.get(i)[0], Highscore.scoreTable.get(i)[1]));
						}
						out.close();
					} catch(IOException ioe){
					}
					
					game.setScreen(new HighscoreScreen(game, true));
				}
			} else {
				ImmutableBag<Entity> patterns = manager.getEntities("PATTERNS");
				
				for(int i = 0; i < patterns.size(); i++){
					Pattern pt = ptm.get(patterns.get(i));
					
					if(pt.recognized && Shape.isTouched){
						Shape.isTouched = false;
						
						new Timer().scheduleTask(new Task(){
							@Override
							public void run() {
								pl.score += 5;
							}
						}, 0, 0.01f, 100 + (int) (shapePos.y/2));
						
						for(Entity e : EntityFactory.createRadialExplosion(world, shapePos.x + 32*2.5f, shapePos.y + 32*2.5f)){
							e.addToWorld();
						}
						
						shape.deleteFromWorld();
						
						ImmutableBag<Entity> blocks = manager.getEntities("BLOCKS");
						for(int z = 0; z < blocks.size(); z++){
							blocks.get(z).deleteFromWorld();
						}
						
						patterns.get(i).deleteFromWorld();
						EntityFactory.createPattern(world, i*90 + 83, 88).addToWorld();
						
						break;
					}
				}
			}
		}
	}

}
