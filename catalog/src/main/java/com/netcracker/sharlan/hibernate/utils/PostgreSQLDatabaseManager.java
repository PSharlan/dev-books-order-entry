package com.netcracker.sharlan.hibernate.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PostgreSQLDatabaseManager implements DatabaseManager{

    @Override
    public EntityManager getEntityManager() {

        EntityManagerFactory entityManagerFactory = Persistence.
                                createEntityManagerFactory("persistence");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        return entityManager;
    }

}
