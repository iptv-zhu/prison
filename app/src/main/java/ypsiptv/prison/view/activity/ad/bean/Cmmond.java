package ypsiptv.prison.view.activity.ad.bean;

import java.io.Serializable;

public class Cmmond implements Serializable {
	private int agentLevel = 10;

	private int command;

	private String details[];

	private int id;

	// private News news;

	private String path;

	private int source_type;

	private int type;

	public void setAgentLevel(int agentLevel) {
		this.agentLevel = agentLevel;
	}

	public int getAgentLevel() {
		return this.agentLevel;
	}

	public void setCommand(int command) {
		this.command = command;
	}

	public int getCommand() {
		return this.command;
	}

	public void setDetails(String[] details) {
		this.details = details;
	}

	public String[] getDetails() {
		return this.details;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	// public void setNews(News news) {
	// this.news = news;
	// }
	//
	// public News getNews() {
	// return this.news;
	// }

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return this.path;
	}

	public void setSource_type(int source_type) {
		this.source_type = source_type;
	}

	public int getSource_type() {
		return this.source_type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return this.type;
	}
}
