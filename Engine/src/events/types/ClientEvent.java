package events.types;

import networkthreads.ClientSocket;

public class ClientEvent extends Event {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ClientSocket client;
	private int messageCode;
	private String message = "";

	public ClientEvent(int i, ClientSocket c, int t, String msg) {
		super(i);
		this.client = c;
		this.messageCode = t;
		this.message = msg;
	}

	@Override
	public int instanceOf() {
		return eventType;
	}
	//TODO:needed?
	public int getMessageCode() {
		return messageCode;
	}

	public ClientSocket getClient() {
		return client;
	}

	public String getMessage() {
		return message;
	}


}
