package cn.foodslab.back;

/**
 * Created by Pengwei Ding on 2016-07-28 11:10.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 管理界面菜单
 */
public class FrameMenuEntity {
    private String menuId;
    private String menuLabel;
    private String levelId;
    private String levelLabel;
    private int menuOrder;
    private String menuCall;
    private String createTime;
    private String updateTime;

    public FrameMenuEntity() {

    }

    public FrameMenuEntity(String menuId, String menuLabel, String levelId, String levelLabel, int menuOrder, String menuCall, String createTime, String updateTime) {
        this.menuId = menuId;
        this.menuLabel = menuLabel;
        this.levelId = levelId;
        this.levelLabel = levelLabel;
        this.menuOrder = menuOrder;
        this.menuCall = menuCall;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuLabel() {
        return menuLabel;
    }

    public void setMenuLabel(String menuLabel) {
        this.menuLabel = menuLabel;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getLevelLabel() {
        return levelLabel;
    }

    public void setLevelLabel(String levelLabel) {
        this.levelLabel = levelLabel;
    }

    public int getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(int menuOrder) {
        this.menuOrder = menuOrder;
    }

    public String getMenuCall() {
        return menuCall;
    }

    public void setMenuCall(String menuCall) {
        this.menuCall = menuCall;
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

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
