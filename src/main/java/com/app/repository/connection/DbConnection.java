package com.app.repository.connection;

import com.app.app.App;

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

    public static void load() {
        ClassLoader classLoader = App.class.getClassLoader();
        try {
            Class aClass = classLoader.loadClass("com.app.repository.connection.DbConnection");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
