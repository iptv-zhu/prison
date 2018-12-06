package ypsiptv.prison.model.bean;

public class VodData {
    private int createUser;

    private int id;

    private String name;

    private String parentId;

    public void setCreateUser(int createUser) {
        this.createUser = createUser;
    }

    public int getCreateUser() {
        return this.createUser;
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

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentId() {
        return this.parentId;
    }

}