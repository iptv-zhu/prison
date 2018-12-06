package ypsiptv.prison.view.activity.ad.bean;

import java.io.Serializable;
import java.util.Date;

public class MsgData implements Serializable {


    public int getFontsize() {
        return fontsize;
    }

    public void setFontsize(int fontsize) {
        this.fontsize = fontsize;
    }

    private int fontsize;

    private String admin;

    private String agent;

    private String content;

    private Date createtime;

    private Date endtime;

    private String hasPower;

    private int id;

    private int rank;

    private int rankId;

    private int roll_speed;

    private Date starttime;

    private int status;

    private String targetAgent;

    private String title;

    private int type;

    private String userTypeId;


    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getAdmin() {
        return this.admin;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getAgent() {
        return this.agent;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getCreatetime() {
        return this.createtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Date getEndtime() {
        return this.endtime;
    }

    public void setHasPower(String hasPower) {
        this.hasPower = hasPower;
    }

    public String getHasPower() {
        return this.hasPower;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return this.rank;
    }

    public void setRankId(int rankId) {
        this.rankId = rankId;
    }

    public int getRankId() {
        return this.rankId;
    }

    public void setRoll_speed(int roll_speed) {
        this.roll_speed = roll_speed;
    }

    public int getRoll_speed() {
        return this.roll_speed;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getStarttime() {
        return this.starttime;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }

    public void setTargetAgent(String targetAgent) {
        this.targetAgent = targetAgent;
    }

    public String getTargetAgent() {
        return this.targetAgent;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }

    public void setUserTypeId(String userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getUserTypeId() {
        return this.userTypeId;
    }
}
