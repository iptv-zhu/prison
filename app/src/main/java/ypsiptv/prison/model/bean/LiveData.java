package ypsiptv.prison.model.bean;

import java.io.Serializable;
import java.util.List;

public class LiveData implements Serializable {
    private List<LiveList> liveList;

    private List<Parts> parts;

    public void setLiveList(List<LiveList> liveList) {
        this.liveList = liveList;
    }

    public List<LiveList> getLiveList() {
        return this.liveList;
    }

    public void setParts(List<Parts> parts) {
        this.parts = parts;
    }

    public List<Parts> getParts() {
        return this.parts;
    }

}