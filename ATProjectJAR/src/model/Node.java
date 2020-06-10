package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ws.rs.Path;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;


@Singleton
@Startup
@Path("/")
public class Node {
	private String currentIp;
	private String masterIp = null;
	
	@PostConstruct
	public void init() {
		BufferedReader br = null;
		java.nio.file.Path p = Paths.get(".").toAbsolutePath().normalize();
		System.out.println("path: "+p.toString());
		String line = "";

		   
		try {
			br = new BufferedReader(new FileReader(p.toString()+"\\config.txt"));
			
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
			    masterIp = sb.toString();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
   
		masterIp = masterIp.substring(2, masterIp.length()-2);
		System.out.println("MasterIP : " + masterIp);
		
		InetAddress ip = null ;
      
		try {
		    ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
		    e.printStackTrace();
		}
		currentIp = ip.toString();
		String[] split = currentIp.split("/");
		currentIp=split[1];
		String[] split2 = currentIp.split("/n");
		currentIp=split2[0];
		System.out.println("CurrentIP : " + currentIp);
		notifyMaster(currentIp);
		
	}
	public void notifyMaster(String connection) {
		//– nov ne-master cvor kontaktira master cvor koji ga registruje
		ResteasyClient client = new ResteasyClientBuilder().build();
		AgentskiCentar a = new AgentskiCentar("8080",connection);
		ResteasyWebTarget rtarget = client.target("http://localhost:8080/ATProjectWAR/rest/node/register");
		//Response response = rtarget.request(MediaType.APPLICATION_JSON).post(Entity.entity(a, MediaType.APPLICATION_JSON));
		
		
		
	}

}
