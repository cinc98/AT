package model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;

public class ACLPoruka implements Serializable{

	private Performative performative;
	private AID sender;
	private AID[] receivers;
	private AID replyTo;
	private String content;
	private Object contentObj;
	private HashMap<String, Object> userArgs;
	private String language;
	private String encoding;
	private String ontology;
	private String protocol;
	private String conversationId;
	private String replyWith;
	private String inReplyTo;
	private Long replyBy;

	public ACLPoruka() {
		super();
	}

	public ACLPoruka(Performative performative, AID sender, AID[] receivers, AID replyTo, String content,
			Object contentObj, HashMap<String, Object> userArgs, String language, String encoding, String ontology,
			String protocol, String conversationId, String replyWith, String inReplyTo, Long replyBy) {
		super();
		this.performative = performative;
		this.sender = sender;
		this.receivers = receivers;
		this.replyTo = replyTo;
		this.content = content;
		this.contentObj = contentObj;
		this.userArgs = userArgs;
		this.language = language;
		this.encoding = encoding;
		this.ontology = ontology;
		this.protocol = protocol;
		this.conversationId = conversationId;
		this.replyWith = replyWith;
		this.inReplyTo = inReplyTo;
		this.replyBy = replyBy;
	}
	public ACLPoruka(ACLPoruka copy, int reciver) {
		this.setSender(copy.getSender());
		this.setReceivers(new AID[] { copy.getReceivers()[reciver] });
		this.setContent(copy.getContent());
		this.setContentObj(copy.getContentObj());
		this.setConversationId(copy.getConversationId());
		this.setPerformative(copy.getPerformative());
		this.setProtocol(copy.getProtocol());
		this.setEncoding(copy.getEncoding());
		this.setReplyTo(copy.getReplyTo());
		this.setUserArgs(copy.getUserArgs());;
	}

	public Performative getPerformative() {
		return performative;
	}

	public void setPerformative(Performative performative) {
		this.performative = performative;
	}

	public AID getSender() {
		return sender;
	}

	public void setSender(AID sender) {
		this.sender = sender;
	}

	public AID[] getReceivers() {
		return receivers;
	}

	public void setReceivers(AID[] receivers) {
		this.receivers = receivers;
	}

	public AID getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(AID replyTo) {
		this.replyTo = replyTo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Object getContentObj() {
		return contentObj;
	}

	public void setContentObj(Object contentObj) {
		this.contentObj = contentObj;
	}

	public HashMap<String, Object> getUserArgs() {
		return userArgs;
	}

	public void setUserArgs(HashMap<String, Object> userArgs) {
		this.userArgs = userArgs;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getOntology() {
		return ontology;
	}

	public void setOntology(String ontology) {
		this.ontology = ontology;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getConversationId() {
		return conversationId;
	}

	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}

	public String getReplyWith() {
		return replyWith;
	}

	public void setReplyWith(String replyWith) {
		this.replyWith = replyWith;
	}

	public String getInReplyTo() {
		return inReplyTo;
	}

	public void setInReplyTo(String inReplyTo) {
		this.inReplyTo = inReplyTo;
	}

	public Long getReplyBy() {
		return replyBy;
	}

	public void setReplyBy(Long replyBy) {
		this.replyBy = replyBy;
	}

	@Override
	public String toString() {
		return "ACLPoruka [performative=" + performative + ", sender=" + sender + ", receivers="
				+ Arrays.toString(receivers) + ", replyTo=" + replyTo + ", content=" + content + ", contentObj="
				+ contentObj + ", userArgs=" + userArgs + ", language=" + language + ", encoding=" + encoding
				+ ", ontology=" + ontology + ", protocol=" + protocol + ", conversationId=" + conversationId
				+ ", replyWith=" + replyWith + ", inReplyTo=" + inReplyTo + ", replyBy=" + replyBy + "]";
	}

}
