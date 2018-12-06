package ypsiptv.prison.model.bean;

import java.io.Serializable;
import java.util.List;

public class WelcomData  implements Serializable {
    private List<AddeList> addeList;

    private int category;

    private int contentType;

    private int id;

    private String name;

    private int position;

    public void setAddeList(List<AddeList> addeList) {
        this.addeList = addeList;
    }

    public List<AddeList> getAddeList() {
        return this.addeList;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getCategory() {
        return this.category;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public int getContentType() {
        return this.contentType;
    }

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

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return this.position;
    }

}