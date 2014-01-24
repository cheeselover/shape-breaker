package components;

import java.util.Random;

import com.artemis.Component;

public class Pattern extends Component {
	
	public static String[] patterns = {
		"1/0/1/0/1 1/1/1/1/1 0/0/0/0/0 1/1/1/1/1 1/0/1/0/1",
		"1/0/0/0/1 1/1/0/1/1 0/0/1/0/0 1/0/1/0/1 1/0/1/0/1",
		"1/0/1/0/1 0/0/1/0/0 1/1/1/1/1 0/0/1/0/0 1/0/1/0/1",
		"1/0/1/0/1 0/1/1/1/0 1/1/1/1/1 0/1/1/1/0 1/0/1/0/1",
		"0/0/1/0/0 0/1/1/1/0 1/1/1/1/1 0/1/1/1/0 1/1/1/1/1",
		"0/0/1/0/0 1/1/1/1/1 1/1/1/1/1 0/0/1/0/0 1/1/0/1/1",
		"0/1/1/1/0 0/0/0/0/0 0/1/0/1/0 0/0/0/0/0 1/1/0/1/1",
		"1/0/1/0/1 1/0/0/0/1 1/1/0/1/1 0/1/0/1/0 0/1/0/1/0",
		"0/0/1/0/0 0/0/0/0/0 0/0/1/0/0 0/0/0/0/0 0/1/1/1/0",
		"0/0/1/0/0 0/0/0/0/0 0/0/0/0/0 1/1/1/1/1 1/0/1/0/1"
	};
	
	public static int currentPattern = -1;
	
	public boolean[][] pattern;
	public boolean recognized;
	
	public Pattern(){
		this.pattern = new boolean[5][5];
		this.recognized = false;
		getNextPattern();
	}
	
	public void getNextPattern(){
		if(currentPattern < 10){
			currentPattern++;
		} else {
			currentPattern = 0;
		}
		
		String[] line = patterns[currentPattern].split(" ");
		for(int y = 0; y < pattern.length; y++){
			for(int x = 0; x < pattern.length; x++){
				String[] tmp = line[y].split("/");
				pattern[y][x] = (tmp[x].equals("1")) ? true : false;
			}
		}
	}
	
	public static void shufflePatterns(){
		Random r = new Random();
		for(int i = patterns.length - 1; i > 0; i--){
			int index = r.nextInt(i + 1);
			String s = patterns[index];
			patterns[index] = patterns[i];
			patterns[i] = s;
		}
	}
	
}
