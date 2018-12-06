package ypsiptv.prison.view.activity.ad.bean;

import java.io.Serializable;

public class ProgramList implements Serializable {
	private int id;

	private String name;

	private int rank;

	private int rankId;

	private String remark;

	private String serverMark;

	private String zipFile;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getRank() {
		return this.rank;
	}

	public void setRankId(int rankId) {
		this.rankId = rankId;
	}

	public int getRankId() {
		return this.rankId;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setServerMark(String serverMark) {
		this.serverMark = serverMark;
	}

	public String getServerMark() {
		return this.serverMark;
	}

	public void setZipFile(String zipFile) {
		this.zipFile = zipFile;
	}

	public String getZipFile() {
		return this.zipFile;
	}

}