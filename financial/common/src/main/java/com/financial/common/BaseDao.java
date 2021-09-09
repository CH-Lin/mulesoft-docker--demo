package com.financial.common;

public interface BaseDao<T> {

    String JNDI_FRAGMENT = "domain/BaseDaoImpl";

    void rollback();

    void removeObject(T object);

    void persistObject(T object);

    void mergeObject(T object);

    Object findObject(Class<?> t, Object id);

    void refreshObject(T object);

    boolean contains(Object id);

    Object getReferenceObject(Class<?> t, Object id);

}
