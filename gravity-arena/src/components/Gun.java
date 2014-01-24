package components;

import com.artemis.Component;

public class Gun extends Component {

	public boolean canShoot, isRepair;
	public String orientation;
	
	public Gun(String orientation, boolean isRepair){
		this.canShoot = true;
		this.orientation = orientation;
		this.isRepair = isRepair;
	}
	
}
