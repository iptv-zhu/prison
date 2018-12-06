package ypsiptv.prison.model.bean;

import java.io.Serializable;

public class ResData implements Serializable {

	private String admin;

	private int check;

	private int dir;

	private int dlCount;

	private int fileType;

	private String hasPower;

	private int id;

	private String intro;

	private String name;

	private String path;

	private int playCount;

	private int rank;

	private String server;

	private int serverId;

	private ResType type;

	private String uploadDate;

	private int uploadUser;

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getAdmin() {
		return this.admin;
	}

	public void setCheck(int check) {
		this.check = check;
	}

	public int getCheck() {
		return this.check;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	public int getDir() {
		return this.dir;
	}

	public void setDlCount(int dlCount) {
		this.dlCount = dlCount;
	}

	public int getDlCount() {
		return this.dlCount;
	}

	public void setFileType(int fileType) {
		this.fileType = fileType;
	}

	public int getFileType() {
		return this.fileType;
	}

	public void setHasPower(String hasPower) {
		this.hasPower = hasPower;
	}

	public String getHasPower() {
		return this.hasPower;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getIntro() {
		return this.intro;
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

	public void setPlayCount(int playCount) {
		this.playCount = playCount;
	}

	public int getPlayCount() {
		return this.playCount;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getRank() {
		return this.rank;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getServer() {
		return this.server;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public int getServerId() {
		return this.serverId;
	}

	public void setType(ResType type) {
		this.type = type;
	}

	public ResType getType() {
		return this.type;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getUploadDate() {
		return this.uploadDate;
	}

	public void setUploadUser(int uploadUser) {
		this.uploadUser = uploadUser;
	}

	public int getUploadUser() {
		return this.uploadUser;
	}

}
