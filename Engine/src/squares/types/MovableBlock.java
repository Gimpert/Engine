package squares.types;

import processing.core.PApplet;
import squares.Square;

public class MovableBlock extends Square{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MovableBlock(PApplet p, int x, int y, int w, int h, int m,
			int[] backColor, int[] borderColor) {
		super(p, x, y, w, h, m, backColor, borderColor);
	}

}