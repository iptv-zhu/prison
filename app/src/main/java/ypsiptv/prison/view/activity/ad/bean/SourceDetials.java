package ypsiptv.prison.view.activity.ad.bean;

import java.io.Serializable;
import java.util.List;

public class SourceDetials implements Serializable {
	private Integer id;
	private Integer mipId;
	private Integer sourceId;
	private String path;
	private List<String> subFiles;
	public List<String> getSubFiles() {
		return subFiles;
	}

	public void setSubFiles(List<String> subFiles) {
		this.subFiles = subFiles;
	}

	private SourceFile file;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMipId() {
		return mipId;
	}

	public void setMipId(Integer mipId) {
		this.mipId = mipId;
	}

	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	public SourceFile getFile() {
		return file;
	}

	public void setFile(SourceFile file) {
		this.file = file;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
