package ypsiptv.prison.model.bean;

import java.util.List;

public class PowerData {
    private List<Details> details;

    private int id;

    private int operate;

    private String targetAgent;

    private String time;

    private int type;

    public void setDetails(List<Details> details) {
        this.details = details;
    }

    public List<Details> getDetails() {
        return this.details;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setOperate(int operate) {
        this.operate = operate;
    }

    public int getOperate() {
        return this.operate;
    }

    public void setTargetAgent(String targetAgent) {
        this.targetAgent = targetAgent;
    }

    public String getTargetAgent() {
        return this.targetAgent;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return this.time;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }

}