package model;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import ws.WSEndPoint;

@Startup
@Singleton
public class Data {
	@EJB
	private WSEndPoint ws;
	
	private static ArrayList<AgentType> types = new ArrayList<>();
	
	private static HashMap<AID, IAgent> agents = new HashMap<>();
	
	static{
		
		
		AgentType a = new AgentType();
		a.setModule("abc");
		a.setName("test");
		types.add(a);
		AID a1 = new AID();
		a1.setName("prvi");
		agents.put(a1,new Agent(new AID("A",null,null)));
		agents.put(new AID(), new Agent(new AID("B",null,null)));
	}

	public ArrayList<AgentType> getTypes() {
		return types;
	}

	public void setTypes(ArrayList<AgentType> tipovi) {
		this.types = tipovi;
	}


	public HashMap<AID, IAgent> getAgents() {
		return agents;
	}

	public void setAgents(HashMap<AID, IAgent> agenti) {
		this.agents = agenti;
	}
	
	
	
}
