package components;

import com.artemis.Component;

public class Expires extends Component {

	public boolean top, right, bottom, left;
	
	public Expires(){
		this.top = true;
		this.right = true;
		this.bottom = true;
		this.left = true;
	}
	
	public Expires(boolean top, boolean right, boolean bottom, boolean left){
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.left = left;
	}
	
}
