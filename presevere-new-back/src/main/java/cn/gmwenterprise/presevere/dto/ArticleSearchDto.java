package cn.gmwenterprise.presevere.dto;

public class ArticleSearchDto {
    // 分页参数, 用于分页插件
    private Integer startPage;
    private Integer countByPage;

    // 当前用户主键, 用于查询
    private Integer currentUserId;

    // 查询条件
    private Boolean self;
    private String title;
    private String writerName;

    public Integer getStartPage() {
        return startPage;
    }

    public void setStartPage(Integer startPage) {
        this.startPage = startPage;
    }

    public Integer getCountByPage() {
        return countByPage;
    }

    public void setCountByPage(Integer countByPage) {
        this.countByPage = countByPage;
    }

    public Boolean getSelf() {
        return self;
    }

    public void setSelf(Boolean self) {
        this.self = self;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriterName() {
        return writerName;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }

    public Integer getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(Integer currentUserId) {
        this.currentUserId = currentUserId;
    }
}
