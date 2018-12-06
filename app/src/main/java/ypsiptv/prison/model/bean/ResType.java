package ypsiptv.prison.model.bean;

import java.io.Serializable;

public class ResType implements Serializable {
	private int id;

	private String type;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}
}
