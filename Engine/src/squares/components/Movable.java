package squares.components;

import java.util.ArrayList;

import squares.GameBoard;
import squares.Square;

public class Movable extends Component {
	
	/** World factors for motion normalization*/
	//TODO: fix this so it's not hardcoded
	public static final int GRAVITY_COEFFICIENT = 4;
	public static final int ACCEL_COEFFICIENT = 4;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Motion Characteristics */
	private int velX = 0;
	private int velY = 0;
	private int inertiaX = 0;
	private int inertiaY = 0;

	
	/** drag and normalization*/
	private int maxVel = 24;
	/** base rate at which horizontal velocity decays when object is not airborne*/
	private int friction = 1;
	
	public Movable(int i) {
		super(i);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int instanceOf() {
		return componentType;
	}


//	public void update(Square s1) {
//		// TODO add resolution logic for updating a moving object
//		Square collision;
//		ArrayList<Square> objList = GameBoard.getObjList();
//		// resolve inertia and naturalize velocity
//		
//		
//	}
	
	/**
	 * Resolve Inertia into velocity
	 * Naturalize velocity
	 * @param s1
	 */
	@Override
	public void update( Square s1){
		Movable moveComponent = (Movable) s1.getComponent(Component.MOVABLE_ID);
		int maxV = moveComponent.getMaxVel();
		int velX = moveComponent.getVelX();
		int velY = moveComponent.getVelY();
		int inertiaX = moveComponent.getInertiaY();
		int friction = moveComponent.getFriction();
		
		//if square has nonzero y inertia, try to accelerate, then apply gravity
		if(inertiaY > 0 && Math.abs(velY) < maxV){
			//if positive inertia
			if(inertiaY > ACCEL_COEFFICIENT){
				//if inertia is greater than max acceleration coefficient
				//apply acceleration
				moveComponent.setVelY(velY + ACCEL_COEFFICIENT);
				//and adjust inertia accordingly, then apply gravity
				moveComponent.setInertiaY(-ACCEL_COEFFICIENT - GRAVITY_COEFFICIENT);
			}else{
				//else only apply current inertia
				moveComponent.setVelY(velY + inertiaY);
				moveComponent.setInertiaY(-inertiaY - GRAVITY_COEFFICIENT);
			}
		}else if (inertiaY < 0 && Math.abs(velY) < maxV){
			//else negative y inertia
			if(inertiaY < -ACCEL_COEFFICIENT){
				moveComponent.setVelY(velY - ACCEL_COEFFICIENT);
				moveComponent.setInertiaY(ACCEL_COEFFICIENT - GRAVITY_COEFFICIENT);
			}else{
				moveComponent.setVelY(velY + inertiaY);
				moveComponent.setInertiaY(-inertiaY - GRAVITY_COEFFICIENT);
			}
		}
		//if object has 0 horizontal inertia and 0 vertical velocity
		if( inertiaX == 0 && velY == 0 ){
			if (velX > 0){
				//and it has positive x velocity
				//apply friction
				moveComponent.setInertiaX(-moveComponent.getFriction());
			}else if(velX < 0){
				//and it has negative x velocity...
				moveComponent.setInertiaX(moveComponent.getFriction());
				
			}
			//else if if max velocity is not reached, 
		}else if( inertiaX > 0 && Math.abs(velX) < maxV){
			//and object has positive x inertia
			if(inertiaY > ACCEL_COEFFICIENT){
				//if inertia is greater than max acceleration coefficient
				//apply acceleration
				moveComponent.setVelX(velX + ACCEL_COEFFICIENT);
				//and adjust inertia accordingly
				moveComponent.setInertiaX(-ACCEL_COEFFICIENT);
			}else{
				//else only apply current inertia...
				moveComponent.setVelX(velX + inertiaX);
				moveComponent.setInertiaX(-inertiaX);
			}
			
		}else if(inertiaX < 0 && Math.abs(velX) < maxV){
			//and object has negative x inertia..
			if(inertiaX < -ACCEL_COEFFICIENT){
				moveComponent.setVelX(velX - ACCEL_COEFFICIENT);
				moveComponent.setInertiaX(ACCEL_COEFFICIENT);
			}else{
				moveComponent.setVelX(velX + inertiaX);
				moveComponent.setInertiaX(-inertiaX);
			}
		}
		//resolve any movement
		s1.setCurX(s1.getCurX() + this.velX);
		s1.setCurY(s1.getCurY() + this.velY);
	}
	
	public Square checkCollision(Square s1) {
		Square collision = null;
		Physical physical = (Physical) s1.getComponent(Component.PHYSICAL_ID);
		Movable movable = (Movable) s1.getComponent(Component.MOVABLE_ID);
		int x1 = s1.getCurX() + movable.getVelX();
		int y1 = s1.getCurY() + movable.getVelY();
		int w1 = physical.getWid();
		int h1 = physical.getHgt();

		//information for collision check against a second object

		int x2;
		int y2;
		int w2;
		int h2;
		Physical physical2;
		//Movable movable2;
		//TODO: current collision resolution does not take into account collisions
		//		with moving objects that have not yet had their update step
		//		not important for simplicity's sake
		//For now:	use priority of updates and sorting to avoid most cases of this.
		ArrayList<Square> objList = GameBoard.getGameBoard().getObjList();
		for(Square s2: objList)
		{
			if(s2.isEnabled() && s2.isInstanceOf(Component.PHYSICAL_ID) &&
					!s1.getObjID().equals(s2.getObjID()) )
			{	//check to see if s2
				//is active, has a physical representation, and is not s1
				//checking against itself

				physical2 = (Physical) s2.getComponent(Component.PHYSICAL_ID);
				x2 = s2.getCurX();
				y2 = s2.getCurY();
				w2 = physical2.getWid();
				h2 = physical2.getHgt();
				x2 = s2.getCurX();
				y2 = s2.getCurY();
				if(  (x1 < x2 + w2) &&
						(x1 + w1 > x2) &&
						(y1 < y2 + h2) &&
						(y1 + h1 > y2))
				{
					collision = s2;
				}
				if(collision != null){
					break;
				}
			}
		}
		return collision;
		
//		return collision;
		
	}
	
	public void stopMotion(){
		this.velX = 0;
		this.velY = 0;
		this.inertiaX = 0;
		this.inertiaY = 0;
	}


	public int getVelX() {
		return velX;
	}

	public int getVelY() {
		return velY;
	}
	
	public void setVelX(int v){
		//TODO:
	}
	
	public void setVelY(int v){
		//TODO:
	}
	
	public int getInertiaY() {
		return inertiaY;
	}

	public int getInertiaX() {
		return inertiaX;
	}
	
	public int getMaxVel() {
		return maxVel;
	}
	
	public int getFriction() {
		return friction;
	}
	
	public void setInertiaY(int inertiaY) {
		this.inertiaY += inertiaY;
	}
	
	public void setInertiaX(int inertiaX) {
		this.inertiaX += inertiaX;
	}

}
