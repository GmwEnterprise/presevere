package com.example.pmq.generator;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public final class QRCodeGenerator {

    /**
     * 二维码默认宽度
     */
    private static final int DEFAULT_QRCODE_WIDTH = 200;
    /**
     * 二维码默认高度
     */
    private static final int DEFAULT_QRCODE_HEIGHT = 200;
    /**
     * 二维码图片默认类型
     */
    private static final String DEFAULT_QRCODE_IMAGE_TYPE = "jpeg";

    public static String bytesToBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 将内容转换为二维码
     *
     * @param content 字符串内容
     */
    public static ByteArrayOutputStream content2OutputStream(String content) throws WriterException, IOException {
        Map<EncodeHintType, Object> hints = new HashMap<>(4);
        // 指定编码
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 指定二维码纠错等级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 指定二维码边的空度，非负数
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(
                content,
                BarcodeFormat.QR_CODE,
                DEFAULT_QRCODE_WIDTH,
                DEFAULT_QRCODE_HEIGHT,
                hints);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, DEFAULT_QRCODE_IMAGE_TYPE, outputStream);
        return outputStream;
    }

    public static byte[] content2Bytes(String content) throws IOException, WriterException {
        return content2OutputStream(content).toByteArray();
    }

    public static void main(String[] args) throws WriterException, IOException {
        // 测试用例

        String content = "https://www.gamersky.com/";

        Map<EncodeHintType, Object> hints = new HashMap<>();
        // 指定编码
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 指定二维码纠错等级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 指定二维码边的空度，非负数
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(
                content,
                BarcodeFormat.QR_CODE,
                DEFAULT_QRCODE_WIDTH,
                DEFAULT_QRCODE_HEIGHT,
                hints);
        MatrixToImageWriter.writeToPath(bitMatrix, DEFAULT_QRCODE_IMAGE_TYPE, new File("D:\\zxing.jpg").toPath());

        // 参考web：

        // https://www.cnblogs.com/rick168/p/5679830.html
        // https://www.cnblogs.com/huanzi-qch/p/10097791.html
        // https://gitee.com/philfan/zxing
    }
}
