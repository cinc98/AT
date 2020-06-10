package rest;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import model.AgentskiCentar;
import model.Data;


@Singleton
@Startup
@Path("/node")
public class NodeRest {
	
	@EJB
	Data database;
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	public void notifyMaster(AgentskiCentar a) {
		System.out.println("Ja sam master i registrujem novi agentski centar");
		database.getAgentskiCentri().add(a);
	}
		
	    
		

}
