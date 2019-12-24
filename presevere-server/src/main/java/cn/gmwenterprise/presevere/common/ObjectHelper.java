package cn.gmwenterprise.presevere.common;

import java.io.*;

public final class ObjectHelper {

    /**
     * 深拷贝，使用序列化为对象字节流实现
     *
     * @param origin 源对象
     * @param <T>    实现了{@link Serializable}接口的类型
     * @return 拷贝后的对象
     */
    public static <T extends Serializable> T deepCopy(T origin) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(origin);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (T) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}
