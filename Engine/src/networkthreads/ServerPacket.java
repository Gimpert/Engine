package networkthreads;

import java.io.Serializable;

import squares.GameBoard;

public class ServerPacket implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2186021879326439105L;
	private int code;
	private GameBoard gameboard;
	
	public ServerPacket(int code, GameBoard gameBoard){
		this.setCode(code);
		this.setGameboard(gameBoard);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public GameBoard getGameboard() {
		return gameboard;
	}

	public void setGameboard(GameBoard gameboard) {
		this.gameboard = gameboard;
	}




}
