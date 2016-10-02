package squares.components;

import squares.Square;

/**
 * Defines a component that affords visibility as a colored square
 * @author Owner
 *
 */
public class Visible extends Component {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int[] RED_COLORS = {255, 0 , 0};
	public static final int[] BLUE_COLORS = {0, 0, 255};
	public static final int[] YELLOW_COLORS = {255, 255, 0};
	public static final int[] GREEN_COLORS = {0, 255, 0};
	public static final int[] PURPLE_COLORS = {85, 26, 139};
	public static final int[] BLACK_COLORS = {0, 0, 0};
		
	private int[] backColor = {255,255,255};	// default values are white
	private int[] borderColor = {255,255,255};	
	
	/**
	 * Constructor for the Visibility component of a filled, bordered Visible Object
	 * @param i
	 * @param backColor
	 * @param borderColor
	 */

	public Visible(int i, int[] backColor, int[] borderColor) {
		super(i);
		for(int j = 0; j < 2; j++){
			this.backColor[j] = backColor[j];
		}
		for(int j = 0; j < 2; j++){
			this.borderColor[j] = borderColor[j];
		}
	}
	
	/**
	 * Constructor for a non-filled object
	 * @param i
	 * @param borderColor
	 */
	public Visible(int i, int[] color){
		super(i);

		for(int j = 0; j < 2; j++){
			this.borderColor[j] = color[j];
		}


	}
	
	public Visible(int i, String color) {
		super(i);
		color = color.toLowerCase();
		
		if(color.equals("red")){
			for(int j = 0; j < 2; j++){
				this.borderColor[j] = RED_COLORS[j];
				this.backColor[j] = RED_COLORS[j];
			}
		}else if(color.equals("blue")){
			for(int j = 0; j < 2; j++){
				this.borderColor[j] = BLUE_COLORS[j];
				this.backColor[j] = BLUE_COLORS[j];
			}
			
		}else if(color.equals("yellow")){
			for(int j = 0; j < 2; j++){
				this.borderColor[j] = YELLOW_COLORS[j];
				this.backColor[j] = YELLOW_COLORS[j];
			}
			
		}else if(color.equals("green")){
			for(int j = 0; j < 2;j++){
				this.borderColor[j] = GREEN_COLORS[j];
				this.backColor[j] = GREEN_COLORS[j];
			}
			
		}
	}
	
	@Override
	public int instanceOf() {
		return this.componentType;

	}


	
	public int[] getBackground(){
		return this.backColor;
	}
	
	public int[] getBorder(){
		return this.borderColor;
	}

	@Override
	public void update(Square s1) {
		// TODO Auto-generated method stub
		
	}
}
