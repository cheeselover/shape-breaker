package components;

import com.artemis.Component;

public class Shape extends Component {

	public boolean[][] grid;
	public int numBlocks;
	public static float spawnSpeed = -1f;
	public static boolean isTouched = false;
	
	public Shape(){
		this.grid = new boolean[5][5];
		
		for(int y = 0; y < 5; y++){
			for(int x = 0; x < 5; x++){
				grid[y][x] = true;
			}
		}
		
		numBlocks = 25;
	}
	
	public void rotateClockwise(){
		boolean[][] tmp = new boolean[grid.length][grid.length];
		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < grid.length; j++){
				tmp[j][grid.length - i - 1] = grid[i][j];
			}
		}
		
		this.grid = tmp;
	}
	
	public void rotateCounterClockwise(){
		boolean[][] tmp = new boolean[grid.length][grid.length];
		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < grid.length; j++){
				tmp[grid.length - j - 1][i] = grid[i][j];
			}
		}
		
		this.grid = tmp;
	}
	
}
