package com.netcracker.sharlan.dao;

import com.netcracker.sharlan.hibernate.utils.DatabaseManager;
import com.netcracker.sharlan.hibernate.utils.PostgreSQLDatabaseManager;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractDao<T extends Serializable> {

    private DatabaseManager dm = PostgreSQLDatabaseManager.getInstance();
    private EntityManager em;
    private Class<T> persistentClass;

    public final void setPersistentClass(Class<T> beanToSet) {
        this.persistentClass = beanToSet;
    }

    protected final EntityManager getEntityManager() {
        return em;
    }

    public T findOneById(long id) {
        begin();
        T foundEntity = em.find(persistentClass, id);
        commit();
        return foundEntity;
    }

    public List<T> findAll() {
        begin();
        List<T> foundEntities = em.createQuery("SELECT e FROM " + persistentClass.getSimpleName() + " e", persistentClass).getResultList();
        commit();
        return foundEntities;
    }

    public T create(T entity) {
        begin();
        em.persist(entity);
        commit();
        return entity;
    }

    public T merge(T entity) {
        begin();
        T en = em.merge(entity);
        commit();
        return en;
    }


    public void remove(T entity) {
        begin();
        em.remove(em.merge(entity));
        commit();
    }

    public void begin(){
        em = dm.getEntityManager();
        em.getTransaction().begin();
    }

    public void commit(){
        em.getTransaction().commit();
    }

    public void rollback(){
        em.getTransaction().rollback();
    }


}
