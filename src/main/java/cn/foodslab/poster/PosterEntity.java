package cn.foodslab.poster;

/**
 * Created by Pengwei Ding on 2016-08-15 15:49.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class PosterEntity {
    private String posterId;
    private int status;
    private int clickable;
    private String href;
    private String start;
    private String end;
    private String pid;
    private String updateTime;
    private String createTime;

    public PosterEntity() {
        super();
    }

    public PosterEntity(String posterId, int status, int clickable, String href, String start, String end, String pid) {
        this.posterId = posterId;
        this.status = status;
        this.clickable = clickable;
        this.href = href;
        this.start = start;
        this.end = end;
        this.pid = pid;
    }

    public String getPosterId() {
        return posterId;
    }

    public void setPosterId(String posterId) {
        this.posterId = posterId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getClickable() {
        return clickable;
    }

    public void setClickable(int clickable) {
        this.clickable = clickable;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
