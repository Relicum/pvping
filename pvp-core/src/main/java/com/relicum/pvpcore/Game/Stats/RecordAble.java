package com.relicum.pvpcore.Game.Stats;

/**
 * Name: RecordAble.java Created: 15 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public interface RecordAble<K, V> {

    K getKey();

    void setKey(K k);

    V getValue(K k);

    void setValue(K k, V v);


}
