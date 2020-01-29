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
import java.math.BigDecimal;

@Service
public class ImageServiceImpl implements ImageService {

    /**
     * 系统指定图片高度为500px
     */
    private static final int SIMPLE_HEIGHT = 300;

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
            if (height <= SIMPLE_HEIGHT) {
                return bufferedImage;
            }
            BigDecimal simpleHeight = new BigDecimal(SIMPLE_HEIGHT);
            BigDecimal bigDecimalHeight = new BigDecimal(height);
            double scaleValue = (double) SIMPLE_HEIGHT / height;
            return Thumbnails.of(bufferedImage)
                .scale(scaleValue)
                .asBufferedImage();
        }

        // 其他情况暂不处理
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
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
