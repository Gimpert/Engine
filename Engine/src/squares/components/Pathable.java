package squares.components;

import squares.Square;

public class Pathable extends Component{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** List of pathing modes*/
	//horizontal
	public static final int HORIZONTAL_MODE = 0;
	//vertical
	public static final int VERTICAL_MODE = 1;

	//The origin of the patrol route
	private int[] origin;
	//The range of boundaries
	private int bounds;
	//
	private int mode;


	public Pathable(int i, int[] o, int b, int m) {
		super(i);
		this.origin = o;
		this.bounds = b;
		this.mode = m;
		setUpdateable(true);
	}



	@Override
	public int instanceOf() {
		return this.componentType;
	}



	@Override
	public void update(Square s1) {
		// TODO Auto-generated method stub
		Movable moveComponent = (Movable) s1.getComponent(MOVABLE_ID);
		int vel;
		switch (this.mode) {
		case HORIZONTAL_MODE:
			if (s1.getCurX() > bounds || s1.getCurX() < -bounds)
			{
				vel = -(moveComponent.getVelX() * 2);
				moveComponent.setInertiaX(vel);
			}
		case VERTICAL_MODE:
			
			break;

		default:
			break;
		}
		
	}



	public int[] getOrigin() {
		return origin;
	}



	public void setOrigin(int[] origin) {
		this.origin = origin;
	}



	public int getBounds() {
		return bounds;
	}



	public void setBounds(int bounds) {
		this.bounds = bounds;
	}



	public int getMode() {
		return mode;
	}



	public void setMode(int mode) {
		this.mode = mode;
	}

}
