package ypsiptv.prison.model.bean;

import java.io.Serializable;

public class LogoData  implements Serializable {
    private String bgPath;

    private int id;

    private String logoPath;

    public void setBgPath(String bgPath) {
        this.bgPath = bgPath;
    }

    public String getBgPath() {
        return this.bgPath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getLogoPath() {
        return this.logoPath;
    }

}