package ypsiptv.prison.view.activity.ad.bean;

import java.io.Serializable;

public class MipTrDetial implements Serializable {
 
		private Integer id;
		private Integer mipId;
		private Integer trId;
		private String path;
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
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
		public Integer getTrId() {
			return trId;
		}
		public void setTrId(Integer trId) {
			this.trId = trId;
		}
	 
	}

