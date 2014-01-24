package components;

import com.artemis.Component;

public class Sprite extends Component {

	public enum Layer {
		DEFAULT,
		BLOCKS,
		BACKGROUND,
		GUNS,
		PARTICLES;
		
		public int getLayerId(){
			return ordinal();
		}
	}
	
	public String name;
	public float r = 1, g = 1, b = 1, a = 1;
	public float scaleX = 1, scaleY = 1;
	public float rotation;
	public Layer layer = Layer.DEFAULT;
	
	public Sprite(String name, Layer layer){
		this.name = name;
		this.layer = layer;
	}
	
	public Sprite(String name){
		this(name, Layer.DEFAULT);
	}
	
}
