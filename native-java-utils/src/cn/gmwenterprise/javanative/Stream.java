package cn.gmwenterprise.javanative;

import java.util.*;

/**
 * 提供在低版本的jdk1.5环境下类似于java8才有的对于集合的Stream操作。
 * <p>
 * 通过静态方法初始化获取到本类的实例，然后可以进行链式调用以处理集合；
 * 处理完毕后调用<code>toXXX</code>方法获取到处理完毕后的集合
 * </p>
 *
 * @param <E> 集合泛型
 * @author Ganmingwei
 */
public class Stream<E> {

    private Collection<E> collection;

    private Stream(Collection<E> collection) {
        // 不使用公共构造器初始化实例
        this.collection = collection;
    }

    /**
     * 获取Stream实例的唯一方法
     *
     * @param collection 待处理集合
     * @param <E>        泛型
     * @return 初始化成功即返回相应的Stream实例
     * @throws NullPointerException 如果collection为空
     */
    public static <E> Stream<E> of(Collection<E> collection) {
        if (collection == null) {
            throw new NullPointerException("the parameter of Stream.of(param) cannot be null.");
        }
        return new Stream<E>(collection);
    }

    /**
     * 返回List形式的集合
     *
     * @return list
     */
    public List<E> toList() {
        return new ArrayList<E>(collection);
    }

    /**
     * 返回Set形式的集合。返回的过程中会进行去重操作。<br>
     * 本质上是保留顺序中key值相同的最后一个。
     *
     * @return 去重后的集合
     */
    public Set<E> toSet() {
        return new HashSet<E>(collection);
    }

    /**
     * 长度计数
     *
     * @return 结果
     */
    public Integer count() {
        return collection.size();
    }

    //##################### 操作方法

    /**
     * filter
     */
    public Stream<E> filter(Filter<E> filter) {
        ArrayList<E> result = new ArrayList<E>();
        for (E e : collection) {
            if (filter.filter(e)) {
                result.add(e);
            }
        }
        return new Stream<E>(result);
    }

    /**
     * map
     */
    public <T> Stream<T> map(Mapping<E, T> mapping) {
        ArrayList<T> result = new ArrayList<T>();
        for (E e : collection) {
            result.add(mapping.map(e));
        }
        return new Stream<T>(result);
    }

    /**
     * forEach
     */
    public void forEach(Do<E> eDo) {
        for (E e : collection) {
            eDo.d(e);
        }
    }

    //##################### 用到的结构

    /**
     * 用于过滤。传入一个参数，返回布尔结果
     *
     * @param <E>
     */
    public interface Filter<E> {
        boolean filter(E item);
    }

    /**
     * 转换集合。
     *
     * @param <F>
     * @param <T>
     */
    public interface Mapping<F, T> {
        T map(F from);
    }

    public interface Do<E> {
        void d(E item);
    }

    public static void main(String[] args) {
        System.out.println(
            Stream.of(Arrays.asList(1, 2, 3, 4))
                .filter(new Filter<Integer>() {
                    public boolean filter(Integer item) {
                        return item > 1;
                    }
                })
                .map(new Mapping<Integer, Object>() {
                    public Object map(Integer from) {
                        return "Number[" + from + "]";
                    }
                })
                .toList()
        );
    }
}
