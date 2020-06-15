package model;

public interface IAgent {

	void handleMessage(ACLPoruka poruka);
	void init(AID aid);
	
}
