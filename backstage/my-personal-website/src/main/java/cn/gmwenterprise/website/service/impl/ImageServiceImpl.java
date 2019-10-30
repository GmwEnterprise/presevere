package cn.gmwenterprise.website.service.impl;

import cn.gmwenterprise.website.service.ImageService;
import cn.gmwenterprise.website.vo.ImageStoreVo;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageInputStreamImpl;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

@Service
public class ImageServiceImpl implements ImageService {

    /**
     * 系统指定图片高度为500px
     */
    private static final int SIMPLE_HEIGHT = 500;

    @Override
    public BufferedImage imageRender(byte[] before, String renderStyle) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(before));
        // 完全渲染，图像不作任何缩减
        if (renderStyle.equals(ImageStoreVo.FULL_RENDERING)) {
            return bufferedImage;
        }

        // 缩放到合适大小（系统指定）
        if (renderStyle.equals(ImageStoreVo.SIMPLE_RENDERING)) {

            int height = bufferedImage.getHeight();

            int scale = SIMPLE_HEIGHT / height;
            return Thumbnails.of(bufferedImage)
                .scale(scale)
                .asBufferedImage();
        }

        return bufferedImage;
    }

    @Override
    public ImageStoreVo imageRender(ImageStoreVo before, String renderStyle) {
        try {
            BufferedImage image = imageRender(before.getImageContent(), renderStyle);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, before.getImageType(), outputStream);
            return new ImageStoreVo() {{
                setImageTitle(before.getImageTitle());
                setImageContent(outputStream.toByteArray());
                setImageType(before.getImageType());
                setContentType(before.getContentType());
                setAddDatetime(before.getAddDatetime());
            }};
            // TODO 没写完
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
