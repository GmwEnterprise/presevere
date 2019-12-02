package cn.gmwenterprise.presevere.vo;

import cn.gmwenterprise.presevere.domain.ArticleDraft;
import cn.gmwenterprise.presevere.domain.SysUser;

import java.util.List;

public class ArticleDraftMetaData extends ArticleDraft {
    private SysUser writerObject;
    private List<String> tagList;

    public SysUser getWriterObject() {
        return writerObject;
    }

    public void setWriterObject(SysUser writerObject) {
        this.writerObject = writerObject;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }
}
