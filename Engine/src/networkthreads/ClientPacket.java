/**
 * 
 */
package networkthreads;

import java.io.Serializable;

/**
 * @author Owner
 *
 */
public class ClientPacket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *  list of global constants that maps an integer ID to
	 *  each type of client event that could
	 */
	public static final int CONNECT_ID = 0;
	public static final int DISCONNECT_ID = 1;
	public static final int UPDATE_ID = 2;
	
	private int code;
	private String name;
	private String info;
	
	public ClientPacket(int c, String n, String i){
		this.code = c;
		this.name = n;
		this.info = i;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}