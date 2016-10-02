package etc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.math.*;

import processing.core.PApplet;
import squares.Square;
/**
 * A singleton class that handles updating the positions of any objects that are currently
 * moving in each step.
 * It also currently handles any physical collisions that this movement may result in.
 * @author Corey Glasson
 * TODO: repurpose: move all basic logic/specifics to physical component, have components
 * communicate through EventHandler
 */
public class MovementHandlerOld implements Serializable{

	private static MovementHandlerOld inst = null;
	public static final int GRAVITY_COEFFICIENT = 3;
	public static final int ACCEL_COEFFICIENT = 4;
	PApplet parent;
	private MovementHandlerOld(PApplet p){
		this.parent = p;
	}
	
	public static MovementHandlerOld getMovementHandler(PApplet p){
		if( inst == null){
			inst = new MovementHandlerOld(p);
		}
		return inst;
	}
	/**
	 * updates game object list by resolving and naturalizing the inertia of any movable object.
	 * This provides the velocity, which is then used to update the object's position on the list
	 * @param objList
	 * @param board
	 */
	public void moveSquares(ArrayList<Square> objList){
		Square collision;
		//TODO: movement needs to be calculated 
		
		// iterate through list of objects.
		for(Square s1 : objList){
			//if object can move
			if((s1.getMass() != 0)){
				// resolve inertia and naturalize velocity
				accel(s1);
				// Check if object's velocity causes a collision
				collision = checkCollision(s1, objList);
				//if it does, resolve the collision
				if(collision != null){
					resolveCollision(s1, collision);
				}
				//resolve any movement
				s1.setCurX(s1.getCurX() + s1.getVelX());
				s1.setCurY(s1.getCurY() + s1.getVelY());
			}

		}
		Collections.sort(objList);
		
	}



/**
 * 
 * @param s1 A moving square that needs a collision check 
 * @param board The gameboard to check collision on;
 * @return the first square that s1 will collide with, else null
 */
	public Square checkCollision(Square s1, ArrayList<Square> objList){
		Square collision = null;
		int x1 = s1.getCurX() + s1.getVelX();
		int y1 = s1.getCurY() + s1.getVelY();
		int w1 = s1.getWid();
		int h1 = s1.getHgt();
		
		int x2;
		int y2;
		int w2;
		int h2;
		for( Square s2 : objList){
			if( !s1.getObjID().equals(s2.getObjID()) ){
				x2 = s2.getCurX();
				y2 = s2.getCurY();
				w2 = s2.getWid();
				h2 = s2.getHgt();
				if(  (x1 < x2 + w2) &&
					(x1 + w1 > x2) &&
					(y1 < y2 + h2) &&
					(y1 + h1 > y2)){
					collision = s2;
				}
			}
			if(collision != null){
				break;
			}
		}
		return collision;
		
	}
	/**
	 * Calculates the adjustments needed to avoid overlap and resolve collisions
	 * as such does not consider the second square moving
	 * if the second square is movable it will be affected, and it's inertia
	 * will reflect upon it's velocity resulting from the collision when it's
	 * movement is calculated
	 * @param s1 The square that triggered a collision event
	 * @param s2 The square s1 will collide with
	 */
	public void resolveCollision(Square s1, Square s2){
		int x1 = s1.getCurX();
		int vx1 = s1.getVelX();
		int y1= s1.getCurY();
		int vy1 = s1.getVelY();
		//int m1 = s1.getMass();
		int w1 = s1.getWid();
		int h1 = s1.getHgt();
		
		
		int x2 = s2.getCurX();
		int y2 = s2.getCurY();
		int m2 = s2.getMass();
		int w2 = s2.getWid();
		int h2 = s2.getHgt();
		
		int ximp = 0;
		int yimp = 0;
		
		int xpen = 0;
		int ypen = 0;

		int bounce = 2;
		
		//check the normal vector of vx1 and vy1
		//value of zero has no effect on the overall impulse or velocity adjustment
		if(vx1 >0){
			xpen = vx1 + x1 + w1 - x2;
			//calculate the number of pixels overlap caused
			ximp = -(vx1+(vx1 / bounce));
			//calculate the reactive force
		}else if ( vx1 < 0){

			xpen = vx1 + x1 - (x2 + w2);
			ximp = -(vx1+(vx1 / bounce));
		}
		
		if(vy1 >0){
			ypen = vy1 + y1 + h1 - y2;
			yimp = -(vy1+(vy1 / bounce));
		}else if ( vy1 < 0){

			ypen = vy1 + y1 - (y2 + h2);
			yimp = -(vy1+(vy1 / bounce));
		}
		
		//adjust velocity to prevent overlap
		s1.setVelX(vx1 - xpen);
		//adjust inertia to simulate collision resolution in the next step
		s1.setInertiaX(ximp);
		
		s1.setVelY(vy1 - ypen);
		s1.setInertiaY(yimp);
		//if the second object has 0 mass, it is immovable and therefore is unaffected
		if(m2 > 0){
			s2.setInertiaX(-ximp);
			s2.setInertiaY(-yimp);
		}
		
		

		
	}

	
	public void accel(Square s1) {
		int maxv = s1.getMaxVel();
		int velx = s1.getVelX();
		int vely = s1.getVelY();
		int inertiax = s1.getInertiaX();
		int inertiay = s1.getInertiaY();
		int friction = s1.getFriction();
		

		

		//if square has nonzero y inertia, try to accelerate, then apply gravity
			if(inertiay > 0 && Math.abs(vely) < maxv){
				//if positive inertia
				if(inertiay > ACCEL_COEFFICIENT){
					//if inertia is greater than max acceleration coefficient
					//apply acceleration
					s1.setVelY(vely + ACCEL_COEFFICIENT);
					//and adjust inertia accordingly, then apply gravity
					s1.setInertiaY(-ACCEL_COEFFICIENT - GRAVITY_COEFFICIENT);
				}else{
					//else only apply current inertia
					s1.setVelY(vely + inertiay);
					s1.setInertiaY(-inertiay - GRAVITY_COEFFICIENT);
				}
				
			}else if (inertiay < 0 && Math.abs(vely) < maxv){
				//else negative y inertia
				if(inertiay < -ACCEL_COEFFICIENT){
					s1.setVelY(vely - ACCEL_COEFFICIENT);
					s1.setInertiaY(ACCEL_COEFFICIENT - GRAVITY_COEFFICIENT);
				}else{
					s1.setVelY(vely + inertiay);
					s1.setInertiaY(-inertiay - GRAVITY_COEFFICIENT);
				}
			}
		
			

			//if object has 0 horizontal inertia and 0 vertical velocity
			if( inertiax == 0 && vely == 0 ){
				if (velx > 0){
					//and it has positive x velocity
					//apply friction
					s1.setInertiaX(-s1.getFriction());
				}else if(velx < 0){
					//and it has negative x velocity...
					s1.setInertiaX(s1.getFriction());
					
				}
				//else if if max velocity is not reached, 
			}else if( inertiax > 0 && Math.abs(velx) < maxv){
				//and object has positive x inertia
				if(inertiay > ACCEL_COEFFICIENT){
					//if inertia is greater than max acceleration coefficient
					//apply acceleration
					s1.setVelX(velx + ACCEL_COEFFICIENT);
					//and adjust inertia accordingly
					s1.setInertiaX(-ACCEL_COEFFICIENT);
				}else{
					//else only apply current inertia...
					s1.setVelX(velx + inertiax);
					s1.setInertiaX(-inertiax);
				}
				
			}else if(inertiax < 0 && Math.abs(velx) < maxv){
				//and object has negative x inertia..
				if(inertiax < -ACCEL_COEFFICIENT){
					s1.setVelX(velx - ACCEL_COEFFICIENT);
					s1.setInertiaX(ACCEL_COEFFICIENT);
				}else{
					s1.setVelX(velx + inertiax);
					s1.setInertiaX(-inertiax);
				}
			}
			
		
	}
	
}

