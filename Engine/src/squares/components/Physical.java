package squares.components;

import squares.Square;

/**
 * A Component that defines the physical properties of an object
 * 
 * @author Owner
 *
 */
public class Physical extends Component{
	

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** The Object's physical characteristics */
	private int wid = 0;
	private int hgt = 0;
	
	private int mass = 0;	
	
	/**
	 * 	"Bounciness" coefficient. lowest value determines the transfer ratio 
	 * 	Represented as a ratio calculated from two integers
	 */
	private int restNumer = 1;	//default ratio: 1/2
	private int restDenom = 2;	//evaluates to 50/50 impulse split

	public Physical(int i, int w, int h, int m) {
		super(i);
		this.wid = w;
		this.hgt = h;
		this.mass = m;
		
		// TODO Auto-generated constructor stub
	}
	public Physical(int i, int m) {
		super(i);
		this.wid = Square.BASIC_SQUARE;
		this.hgt = Square.BASIC_SQUARE;
		this.mass = m;
		
		// TODO Auto-generated constructor stub
	}
	
	public Physical(int i) {
		super(i);
		this.wid = Square.BASIC_SQUARE;
		this.hgt = Square.BASIC_SQUARE;
		this.mass = 1;
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public int instanceOf() {
		return componentType;
		
	}


	public int getWid() {
		return wid;
	}

	public int getHgt(){
		return hgt;
	}
	
	public int getMass(){
		return mass;
	}

	public int getRestNumer() {
		return restNumer;
	}

	public int getRestDenom() {
		return restDenom;
	}
	@Override
	public void update(Square s1) {
		//TODO: currently no update logic associated with a physical object
		
	}


}
