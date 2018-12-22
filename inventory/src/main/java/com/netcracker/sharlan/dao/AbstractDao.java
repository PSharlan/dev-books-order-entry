package com.netcracker.sharlan.dao;

import com.netcracker.sharlan.utils.DatabaseManager;
import com.netcracker.sharlan.utils.PostgreSQLDatabaseManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public abstract class AbstractDao<T> {

    private DatabaseManager dm = PostgreSQLDatabaseManager.getInstance();
    private EntityManager em = dm.getEntityManager();
    private Class<T> persistentClass;
    private EntityTransaction transaction;

    AbstractDao(Class<T> persistentClass){
        this.persistentClass = persistentClass;
    }
    protected final EntityManager getEntityManager() {
        return em;
    }

    public T findOneById(long id) {
        transaction = em.getTransaction();
        transaction.begin();
        T t = em.find(persistentClass, id);
        transaction.commit();
        return t;
    }

    public List<T> findAll() {
        transaction = em.getTransaction();
        transaction.begin();
        List<T> list = em.createQuery("SELECT e FROM " + persistentClass.getSimpleName() + " e", persistentClass).getResultList();
        transaction.commit();
        return list;
    }

    public T create(T entity) {
        transaction = em.getTransaction();
        transaction.begin();
        em.persist(entity);
        transaction.commit();
        return entity;
    }

    public T merge(T entity) {
        transaction = em.getTransaction();
        transaction.begin();
        T en = em.merge(entity);
        transaction.commit();
        return en;
    }


    public void remove(T entity) {
        transaction = em.getTransaction();
        transaction.begin();
        em.remove(em.merge(entity));
        transaction.commit();
    }


}
