package com.github.mrag.rpc.serialization;

/**
 * @author Gmw
 */
public interface Serialization<E> {

    E serialize(Object in) throws SerializeException;

    Object deserialize(E out) throws SerializeException;
}
