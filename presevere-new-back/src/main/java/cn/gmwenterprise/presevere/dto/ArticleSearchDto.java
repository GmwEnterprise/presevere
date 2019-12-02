package cn.gmwenterprise.presevere.dto;

public class ArticleSearchDto {
    private Integer startPage;
    private Integer countByPage;
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
}
