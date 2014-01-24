package components;

import java.util.ArrayList;
import java.util.List;

import com.artemis.Component;

public class Highscore extends Component {

	public int position;
	public String playerName;
	public int score;
	public float originalY;
	
	public static List<String[]> scoreTable = new ArrayList<>();
	
	public Highscore(float originalY, int position, String playerName, int score){
		this.playerName = playerName;
		this.score = score;
		this.position = position;
		this.originalY = originalY;
	}
	
}
