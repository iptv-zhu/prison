package ypsiptv.prison.view.activity.ad.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class InsertAd implements Serializable {
	
	private long start;
	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	private long end;
	
	
	public List<MipTrDetial> getMipTrDetials() {
		return mipTrDetials;
	}

	public void setMipTrDetials(List<MipTrDetial> mipTrDetials) {
		this.mipTrDetials = mipTrDetials;
	}

	private List<MipTrDetial> mipTrDetials;

	private Date endTime;

	private int id;

	private String liveAdd;

	private List<MipdList> mipdList;

	public List<SourceDetials> getSourceDetials() {
		return sourceDetials;
	}

	public void setSourceDetials(List<SourceDetials> sourceDetials) {
		this.sourceDetials = sourceDetials;
	}

	private List<SourceDetials> sourceDetials;

	private int msgType;

	private String pptPath;

	private Date startTime;

	private int status;

	private String targetAgent;

	private String title;

	private int type;

	 

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void setLiveAdd(String liveAdd) {
		this.liveAdd = liveAdd;
	}

	public String getLiveAdd() {
		return this.liveAdd;
	}

	public void setMipdList(List<MipdList> mipdList) {
		this.mipdList = mipdList;
	}

	public List<MipdList> getMipdList() {
		return this.mipdList;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public int getMsgType() {
		return this.msgType;
	}

	public void setPptPath(String pptPath) {
		this.pptPath = pptPath;
	}

	public String getPptPath() {
		return this.pptPath;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return this.status;
	}

	public void setTargetAgent(String targetAgent) {
		this.targetAgent = targetAgent;
	}

	public String getTargetAgent() {
		return this.targetAgent;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return this.type;
	}

}
