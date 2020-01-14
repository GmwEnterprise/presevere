package cn.presevere.next.common;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.*;

/**
 * domain基类，提供通用的domain方法
 *
 * @param <R> 基类泛型
 */
@Slf4j
@MappedSuperclass
public abstract class BaseDomain<R> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object target) {
        if (this == target) {
            return true;
        }
        if (id == null || target == null || !getClass().equals(target.getClass())) {
            return false;
        }
        var that = (BaseDomain) target;
        return id.equals(that.getId());
    }


    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
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
