package cn.gmwenterprise.presevere.common;

import java.io.*;

public final class ObjectHelper {

    /**
     * 深拷贝，使用序列化为对象字节流实现
     *
     * @param origin 源对象
     * @return 拷贝后的对象
     */
    public static Serializable deepCopy(Serializable origin) throws IOException {
        try (
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
        ) {
            objOut.writeObject(origin);
            try (
                ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
                ObjectInputStream objIn = new ObjectInputStream(byteIn);
            ) {
                return ((Serializable) objIn.readObject());
            }
        } catch (Exception e) {
            return null;
        }
    }
}
