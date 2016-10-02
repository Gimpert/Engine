package events.handlers;

import squares.Square;
import squares.components.Component;
import squares.components.Movable;
import squares.components.Physical;
import events.types.CollisionEvent;
import events.types.Event;

public class CollisionHandler extends EventHandler {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CollisionHandler(int i) {
		super(i);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onEvent(Event e) {

		// TODO	1. get collision resolution targets and relevant components
		CollisionEvent cEvent = (CollisionEvent) e;
		Square s1 = cEvent.getS1();
		int x1 = s1.getCurX();
		int y1= s1.getCurY();
		
		Physical physical1 = (Physical) s1.getComponent(Component.PHYSICAL_ID);
		int m1 = physical1.getMass();
		int w1 = physical1.getWid();
		int h1 = physical1.getHgt();
		
		Movable movable1 = (Movable) s1.getComponent(Component.MOVABLE_ID);
		int vx1 = movable1.getVelX();
		int vy1 = movable1.getVelY();
		int impX = 0;
		int impY = 0;
		
		int penX = 0; 
		int penY = 0;
		
		int bounce = 2;
		
		Square s2 = cEvent.getS2();
		Physical physical2 = (Physical) s2.getComponent(Component.PHYSICAL_ID);
		int x2 = s2.getCurX();
		int y2 = s2.getCurY();
		int m2 = physical2.getMass();
		int w2 = physical2.getWid();
		int h2 = physical2.getHgt();
		Movable movable2;
		
		//		2. determine if second object is affected of the objects are affected

		//		3. call update on affected collision components
		//check the normal vector of vx1 and vy1
		//value of zero has no effect on the overall impulse or velocity adjustment
		if(vx1 >0){
			penX = vx1 + x1 + w1 - x2;
			//calculate the number of pixels overlap caused
			impX = -(vx1+(vx1 / bounce));
			//calculate the reactive force
		}else if ( vx1 < 0){

			penX = vx1 + x1 - (x2 + w2);
			impX = -(vx1+(vx1 / bounce));
		}

		if(vy1 >0){
			penY = vy1 + y1 + h1 - y2;
			impY = -(vy1+(vy1 / bounce));
		}else if ( vy1 < 0){

			penY = vy1 + y1 - (y2 + h2);
			impY = -(vy1+(vy1 / bounce));
		}

		//adjust velocity to prevent overlap in either axis
		movable1.setVelX(vx1 - penX);
		movable1.setVelY(vy1 - penY);
		
		//S1 may have mass 0 but still be movable. If it's mass is > 0,
		//adjust inertia to simulate collision resolution in the next step
		if(m1 > 0){
		movable1.setInertiaX(impX);
		movable1.setInertiaY(impY);
		}
		//if the second object has 0 mass, it is immovable and therefore resolution is
		//irrelevant. Otherwise, adjust inertia
		if(m2 > 0){
			movable2 = (Movable) s2.getComponent(Component.MOVABLE_ID);
			movable2.setInertiaX(-impX);
			movable2.setInertiaY(-impY);
		}

		

	}

	@Override
	public int instanceOf() {
		return eventType;
	}

}
