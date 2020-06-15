package model;

import java.io.Serializable;

public class AgentskiCentar implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5011412853274340741L;
	private String alias;
	private String address;

	public AgentskiCentar() {
		super();
	}

	public AgentskiCentar(String alias, String address) {
		super();
		this.alias = alias;
		this.address = address;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "AgentskiCentar [alias=" + alias + ", address=" + address + "]";
	}

}
