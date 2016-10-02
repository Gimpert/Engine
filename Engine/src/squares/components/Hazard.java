package squares.components;

import squares.Square;

public class Hazard extends Component{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int hazardType;
	public Hazard(int i, int h) {
		super(i);
		this.hazardType = h;
		// TODO Auto-generated constructor stub
	}

	@Override
	public int instanceOf() {
		return componentType;
	}

	public int getHazardType() {
		return hazardType;
	}

	@Override
	public void update(Square s1) {
		// TODO Auto-generated method stub
		
	}



}
