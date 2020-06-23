package com.github.mrag.rpc.serialization;

/**
 * @author Gmw
 */
public interface Serialization<E> {

    E serialize(Object in) throws SerializeException;

    <T> T deserialize(E out, Class<T> type) throws SerializeException;
}
