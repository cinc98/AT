package model;

public class Agent implements IAgent {

	private AID id;

	public Agent() {
		super();

	}

	public Agent(AID id) {
		super();
		this.id = id;
	}

	public AID getId() {
		return id;
	}

	public void setId(AID id) {
		this.id = id;
	}

	@Override
	public void handleMessage(ACLPoruka poruka) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(AID aid) {
		this.id = aid;
		
	}
	
	@Override
	public AID getAid(){
		return id;
	}

}
