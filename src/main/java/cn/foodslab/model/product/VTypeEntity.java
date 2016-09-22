package cn.foodslab.model.product;

/**
 * Created by Pengwei Ding on 2016-09-22 18:49.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class VTypeEntity {
    private String sessionId;
    private String seriesId;
    private String typeId;
    private String label;
    private String summary;
    private String directions;
    private int queue;
    private int status = -2;

    public VTypeEntity() {
        super();
    }

    public VTypeEntity(String sessionId, String seriesId, String typeId, String label, String summary, String directions, int queue, int status) {
        this.sessionId = sessionId;
        this.seriesId = seriesId;
        this.typeId = typeId;
        this.label = label;
        this.summary = summary;
        this.directions = directions;
        this.queue = queue;
        this.status = status;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public int getQueue() {
        return queue;
    }

    public void setQueue(int queue) {
        this.queue = queue;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
