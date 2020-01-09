package cn.presevere.next.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * domain基类，提供通用的domain方法
 *
 * @param <R> 基类泛型
 */
public abstract class BaseDomain<R> implements Serializable {
    protected static final Logger log = LoggerFactory.getLogger(BaseDomain.class);

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 自身深拷贝
     *
     * @return 成功拷贝返回拷贝后的对象；失败则返回null，同时打印失败日志
     */
    public R deepCopy() {
        try {
            try (
                var byteOut = new ByteArrayOutputStream();
                var objOut = new ObjectOutputStream(byteOut)) {
                objOut.writeObject(this);

                try (
                    var byteIn = new ByteArrayInputStream(byteOut.toByteArray());
                    var objIn = new ObjectInputStream(byteIn)) {
                    return ((R) objIn.readObject());
                }
            }
        } catch (Exception e) {
            log.error("Error in domain deep copy. ", e);
            return null;
        }
    }
}
