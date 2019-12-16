package cn.gmwenterprise.presevere.vo;

import cn.gmwenterprise.presevere.domain.ArticleMetadata;
import cn.gmwenterprise.presevere.domain.SysUser;

public class ArticleMetadataVo extends ArticleMetadata {
    private SysUser writerObject;

    public SysUser getWriterObject() {
        return writerObject;
    }

    public void setWriterObject(SysUser writerObject) {
        this.writerObject = writerObject;
    }
}
