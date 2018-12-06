package ypsiptv.prison.model.bean;

import java.io.Serializable;
import java.util.List;

public class Res implements Serializable {
	private List<ResData> data;

	private boolean hasNextPage;

	private boolean hasPrePage;

	private int pageNo;

	private int pageSize;

	private int totalPage;

	private int totalRows;

	public void setData(List<ResData> data) {
		this.data = data;
	}

	public List<ResData> getData() {
		return this.data;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public boolean getHasNextPage() {
		return this.hasNextPage;
	}

	public void setHasPrePage(boolean hasPrePage) {
		this.hasPrePage = hasPrePage;
	}

	public boolean getHasPrePage() {
		return this.hasPrePage;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageNo() {
		return this.pageNo;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalPage() {
		return this.totalPage;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public int getTotalRows() {
		return this.totalRows;
	}
}
