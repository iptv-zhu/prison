package ypsiptv.prison.view.activity.ad.bean;

import java.io.Serializable;

public class MipdList implements Serializable {
//	private List<String> subFiles;
//
//	public List<String> getSubFiles() {
//		return subFiles;
//	}
//
//	public void setSubFiles(List<String> subFiles) {
//		this.subFiles = subFiles;
//	}

	private int id;

	private int msgInsertPlayId;

	private String name;

	private String path;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void setMsgInsertPlayId(int msgInsertPlayId) {
		this.msgInsertPlayId = msgInsertPlayId;
	}

	public int getMsgInsertPlayId() {
		return this.msgInsertPlayId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return this.path;
	}

}
