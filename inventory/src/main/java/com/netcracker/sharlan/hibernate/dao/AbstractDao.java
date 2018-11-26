package com.netcracker.sharlan.hibernate.dao;

import com.netcracker.sharlan.hibernate.utils.DatabaseManager;
import com.netcracker.sharlan.hibernate.utils.PostgreSQLDatabaseManager;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractDao<T extends Serializable> {

    private DatabaseManager dm = new PostgreSQLDatabaseManager();
    private EntityManager em;
    private Class<T> persistentClass;

    public final void setPersistentClass(Class<T> beanToSet) {
        this.persistentClass = beanToSet;
    }

    protected final EntityManager getEntityManager() {
        return em;
    }

    public T findOneById(int id) {
        return (T) em.find(persistentClass, id);
    }

    public List<T> findAll() {
        System.out.println("simple name: " + persistentClass.getSimpleName());
        return em.createQuery("SELECT e FROM " + persistentClass.getSimpleName() + " e").getResultList();
    }

    public T create(T entity) {
        em.persist(entity);
        return (T) entity;
    }

    public T merge(T entity) {
        return (T) em.merge(entity);
    }


    public void remove(T entity) {
        em.remove(entity);
    }

    public void begin(){
        em = dm.getEntityManager();
        em.getTransaction().begin();
    }

    public void commit(){
        em.getTransaction().commit();
        em.close();
        em.getEntityManagerFactory().close();
    }

    public void rollBack(){
        //commit();
        em.getTransaction().rollback();
    }


}
