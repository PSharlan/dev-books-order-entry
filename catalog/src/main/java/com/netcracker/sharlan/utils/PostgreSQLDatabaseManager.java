package com.netcracker.sharlan.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PostgreSQLDatabaseManager implements DatabaseManager{

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.
            createEntityManagerFactory("catalogPersistenceUnit");

    private EntityManager entityManager;

    private PostgreSQLDatabaseManager() {
        entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    private static class Holder{
        private static final DatabaseManager INSTANCE = new PostgreSQLDatabaseManager();
    }

    public static DatabaseManager getInstance(){
        return PostgreSQLDatabaseManager.Holder.INSTANCE;
    }

}
