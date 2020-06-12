package rest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import model.AID;
import model.Agent;
import model.AgentType;
import model.AgentskiCentar;
import model.Data;
import model.IAgent;
import model.Performative;
import model.Spyder;
import ws.WSEndPoint;

@LocalBean
@Path("")
public class Rest {
	@EJB
	Data database;

	@EJB
	WSEndPoint ws;
	
	@GET
	@Path("/search/{brand}/{priceFrom}/{priceTo}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<String> search(@PathParam(value = "brand") String brand, @PathParam(value = "priceFrom") String p1,
			@PathParam(value = "priceTo") String p2) {
		Spyder spider = new Spyder();
		String url = "https://www.polovniautomobili.com/auto-oglasi/pretraga?brand="+brand+"&price_from="+p1+"&price_to="+p2;
		System.out.println(url);
		List<String> prices = new ArrayList<String>();
		prices = spider.search(url, "audi");
		return (ArrayList<String>) prices;
	}

	// GET/messages – dobavi listu performativa
	@GET
	@Path("/messages")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Performative> getPerformative() {
		ArrayList<Performative> temp = new ArrayList<Performative>();
		for (Performative p : Performative.values()) {
			temp.add(p);
		}
		return temp;
	}

	// GET /agents/classes – dobavi listu svih tipova agenata na sistemu
	@GET
	@Path("/agents/classes")
	@Produces(MediaType.APPLICATION_JSON)
	public List<AgentType> getTipovi() {
		System.out.println("TIPOVI---" + database.getTypes());
		return database.getTypes();
	}

	// GET /agents/running – dobavi sve pokrenute agente sa sistema
	@GET
	@Path("/agents/running")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Agent> getAgents() {

		return new ArrayList<>(database.getAgents().values());
	}

	// DELETE /agents/running/{aid} – zaustavi odredjenog agenta
	@DELETE
	@Path("/agents/running/{aid}")
	public void stopAgent(@PathParam("aid") String aid) {
		System.out.println("Treba da obrisem "+aid);
		HashMap<String, Agent> agenti = database.getAgents();
		
		for (String a : agenti.keySet()) {
			System.out.println(agenti.get(a).getId().getHost().getAddress());
			if (agenti.get(a).getId().getHost().getAddress().contains(aid)) {
				System.out.println("Brisem");				
				database.getAgents().remove(a);
				break;
			}
		}
		ObjectMapper mapper = new ObjectMapper();
		String msg=null;
		try {
			msg = mapper.writeValueAsString(database.getAgents().values());
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ws.echoTextMessage(msg);
		
	}

	// PUT /agents/running/{type}/{name} – pokreni agenta odredenog tipa sa zadatim
	// imenom;
	@PUT
	@Path("/agents/running/{type}/{name}")
	public void startAgent(@PathParam("type") String type, @PathParam("name") String name) {
		
		System.out.println("Primam novi running cvor");
		String currentIp = "";
		BufferedReader br = null;
		java.nio.file.Path p = Paths.get(".").toAbsolutePath().normalize();
		String line = "";
		
		try {
			br = new BufferedReader(new FileReader(p.toString() + "\\config.txt"));

			StringBuilder sb = new StringBuilder();
			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				try {
					line = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			currentIp = sb.toString();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		currentIp = currentIp.substring(2, currentIp.length() - 2);
		AgentskiCentar host = new AgentskiCentar(currentIp, "8080");

		AID aid = new AID(name, host, new AgentType(type, null));
		Agent agent = new Agent(aid);
		database.getAgents().put(aid.getName(), agent);
		ObjectMapper mapper = new ObjectMapper();
		String msg=null;
		try {
			msg = mapper.writeValueAsString(database.getAgents().values());
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ws.echoTextMessage(msg);
		
	 
	    for(AgentskiCentar at : database.getAgentskiCentri()) {
			if(at.getAddress().equals(currentIp))
				continue;			
			ResteasyClient client2 = new ResteasyClientBuilder().build();
			ResteasyWebTarget rtarget2 = client2.target(at.getAddress()+"/ATProjectWAR/rest/node/agents/running");
			System.out.println(database.getAgents());
			Response response2 = rtarget2.request(MediaType.APPLICATION_JSON).post(Entity.entity(database.getAgents(),MediaType.APPLICATION_JSON));
		}
	}

}
