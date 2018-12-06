package ypsiptv.prison.model.bean;

import android.telecom.Call;

import java.io.Serializable;
import java.util.List;

public class VolData  implements Serializable {
    private List<Details> details;

    private int id;

    private String key;

    private String name;

    private String targetAgent;

    private int type;

    private String val;

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

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setTargetAgent(String targetAgent) {
        this.targetAgent = targetAgent;
    }

    public String getTargetAgent() {
        return this.targetAgent;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getVal() {
        return this.val;
    }

}