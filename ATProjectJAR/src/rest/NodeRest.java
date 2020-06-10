package rest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import model.AID;
import model.AgentType;
import model.AgentskiCentar;
import model.Data;
import model.IAgent;


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
	
	@POST
	@Path("/node")
	@Consumes(MediaType.TEXT_PLAIN)
	public void notifyAll(AgentskiCentar a) {
		System.out.println("Ja sam ne-master cvor i primam informaciju da je novi registrovan");
		database.getAgentskiCentri().add(a);
		return;
	}
	@POST
	@Path("/nodes")
	@Consumes(MediaType.APPLICATION_JSON)
	public void getNoMaster(ArrayList<AgentskiCentar> agents){
		System.out.println("Trenutno sam u novom ne-masteru i primam nove cvorove");
		for(AgentskiCentar a : agents)
			database.getAgentskiCentri().add(a);
		return ;		
	}
	@GET
	@Path("/node")
	public ResponseBuilder turnOffNode() {
		return Response.ok();
	}
	@GET
	@Path("/agents/classes")
	public ArrayList<AgentType> getAgents() {
		System.out.println("Trenutno sam u ne-masteru i vracam sve tipove");
		return database.getTypes();
	}
	@POST
	@Path("/agents/classes")
	@Consumes(MediaType.APPLICATION_JSON)
	public void receiveAgents(ArrayList<AgentType> agents){
		System.out.println("Trenutno sam u novom ne-masteru i primam nove tipove koje mi je master poslao");
		for(AgentType a : agents)
			database.getTypes().add(a);
		return;		
	}
	@POST
	@Path("/agents/running ")
	@Consumes(MediaType.APPLICATION_JSON)
	public void loggedIn(HashMap<AID, IAgent> runningAgents) {
		System.out.println("Sada sam u novom ne-masteru i dobijam spisak pokrenutih agenata");
		database.setAgents(runningAgents);
		return;
	}
	
	@DELETE
	@Path("/node/{alias}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteNode(@PathParam("alias") String alias) {
		System.out.println("Sada sam u ne-master cvoru i primio sam poruku koji je cvor neaktivan");
		for(AgentskiCentar a :database.getAgentskiCentri()) {
			if(a.getAddress() == alias) {
				database.getAgentskiCentri().remove(a);
				break;
			}	
		}
	}
	    
		

}
