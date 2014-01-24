package components;

import com.artemis.Component;

public class Block extends Component {

	public int xIndex, yIndex;
	public static boolean canBreak = true;
	
	public Block(int xIndex, int yIndex){
		this.xIndex = xIndex;
		this.yIndex = yIndex;
	}
	
}
