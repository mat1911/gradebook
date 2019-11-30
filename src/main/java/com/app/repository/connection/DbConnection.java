package com.app.repository.connection;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DbConnection {

    private static DbConnection ourInstance = new DbConnection();
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("HBN");


    private DbConnection(){ }

    public static DbConnection getInstance() {
        return ourInstance;
    }

    public EntityManagerFactory getEntityManagerFactory(){
        return entityManagerFactory;
    }
}
