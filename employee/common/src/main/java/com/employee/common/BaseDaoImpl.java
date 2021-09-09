package com.employee.common;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class BaseDaoImpl<T> implements BaseDao<T> {

    // Override objects provided for unit testing--not for use with production code
    public static EntityManager entityManagerOverride;

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public void rollback() {
        getEntityManager().getTransaction().rollback();
    }

    @Override
    public void persistObject(T object) {
        getEntityManager().persist(object);
    }

    @Override
    public void mergeObject(T object) {
        getEntityManager().merge(object);
    }

    @Override
    public Object findObject(Class<?> t, Object id) {
        return getEntityManager().find(t, id);
    }

    @Override
    public void removeObject(T object) {
        getEntityManager().remove(object);
    }

    @Override
    public void refreshObject(T object) {
        getEntityManager().refresh(object);
    }

    @Override
    public Object getReferenceObject(Class<?> t, Object id) {
        return getEntityManager().getReference(t, id);
    }

    @Override
    public boolean contains(Object id) {
        return getEntityManager().contains(id);
    }

    protected EntityManager getEntityManager() {
        return null == entityManagerOverride ? entityManager : entityManagerOverride;
    }

}
