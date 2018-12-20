package com.netcracker.sharlan.dao;

import com.netcracker.sharlan.bean.BaseEntity;
import com.netcracker.sharlan.hibernate.utils.DatabaseManager;
import com.netcracker.sharlan.hibernate.utils.PostgreSQLDatabaseManager;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractDao<T> {

    private DatabaseManager dm = PostgreSQLDatabaseManager.getInstance();
    private EntityManager em = dm.getEntityManager();
    private Class<T> persistentClass;

    public final void setPersistentClass(Class<T> beanToSet) {
        this.persistentClass = beanToSet;
    }

    protected final EntityManager getEntityManager() {
        return em;
    }

    public T findOneById(long id) {
        em.getTransaction().begin();
        T foundEntity = em.find(persistentClass, id);
        em.getTransaction().commit();
        return foundEntity;
    }

    public List<T> findAll() {
        em.getTransaction().begin();
        List<T> foundEntities = em.createQuery("SELECT e FROM " + persistentClass.getSimpleName() + " e", persistentClass).getResultList();
        em.getTransaction().commit();
        return foundEntities;
    }

    public T create(T entity) {
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
        return entity;
    }

    public T merge(T entity) {
        em.getTransaction().begin();
        T en = em.merge(entity);
        em.getTransaction().commit();
        return en;
    }


    public void remove(T entity) {
        em.getTransaction().begin();
        em.remove(em.merge(entity));
        em.getTransaction().commit();
    }

}
