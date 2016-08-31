package cn.foodslab.image;

/**
 * Created by Pengwei Ding on 2016-07-31 09:36.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class ImageEntity {
    private String imageId;
    private String filePath;
    private int level;
    private int queue;
    private int status;
    private String trunkId;
    private String createTime;
    private String updateTime;

    public ImageEntity() {
        super();
    }

    public ImageEntity(String imageId, String filePath, int level, int queue, int status, String trunkId, String createTime, String updateTime) {
        this.imageId = imageId;
        this.filePath = filePath;
        this.level = level;
        this.queue = queue;
        this.status = status;
        this.trunkId = trunkId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public String getTrunkId() {
        return trunkId;
    }

    public void setTrunkId(String trunkId) {
        this.trunkId = trunkId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
