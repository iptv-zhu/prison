package ypsiptv.prison.view.activity.ad.bean;

import java.io.Serializable;

/**
 * Created by zhu on 2017/10/11.
 */

public class VodDetails implements Serializable {
    private int id;

    private String name;

    private String filePath;

    private int sourceId;

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

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public int getSourceId() {
        return this.sourceId;
    }

}
