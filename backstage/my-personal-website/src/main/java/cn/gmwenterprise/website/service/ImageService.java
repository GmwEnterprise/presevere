package cn.gmwenterprise.website.service;

import cn.gmwenterprise.website.vo.ImageStoreVo;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface ImageService {

    /**
     * 渲染二进制图片
     *
     * @param before      原始图片
     * @param renderStyle 渲染风格
     * @return 渲染后图片
     */
    BufferedImage imageRender(byte[] before, String renderStyle) throws IOException;

    ImageStoreVo imageRender(ImageStoreVo before, String renderStyle);
}
