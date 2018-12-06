package ypsiptv.prison.model.bean;

import java.io.Serializable;

public class UpdateData  implements Serializable {
    private String apkUrl;

    private boolean update;

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public String getApkUrl() {
        return this.apkUrl;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public boolean getUpdate() {
        return this.update;
    }

}