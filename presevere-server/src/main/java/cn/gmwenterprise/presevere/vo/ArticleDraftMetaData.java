package cn.gmwenterprise.presevere.vo;

import cn.gmwenterprise.presevere.domain.ArticleDraft;
import cn.gmwenterprise.presevere.domain.SysUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArticleDraftMetaData extends ArticleDraft {
    private SysUser writerObject;
    private List<String> tagList;

    @Override
    public void setTags(String tags) {
        super.setTags(tags);
        if (tags != null && !"".equals(tags)) {
            tagList = new ArrayList<>(Arrays.asList(tags.split(",")));
        }
    }

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
