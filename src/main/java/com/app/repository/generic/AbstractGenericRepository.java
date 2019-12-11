package com.app.repository.generic;

import com.app.repository.connection.DbConnection;

import javax.persistence.*;
import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class AbstractGenericRepository<T, ID> implements CrudRepository<T, ID>{

    protected DbConnection dbConnection = DbConnection.getInstance();

    protected Class<T> entityType = (Class<T>) ((ParameterizedType) (super.getClass().getGenericSuperclass())).getActualTypeArguments()[0];

    protected EntityManagerFactory entityManagerFactory = dbConnection.getEntityManagerFactory();
    protected EntityManager entityManager;
    protected EntityTransaction entityTransaction;


    @Override
    public Optional<T> add(T entity){
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction = entityManager.getTransaction();

            entityTransaction.begin();
            entityManager.persist(entity);
            entityTransaction.commit();

        }catch (Exception e){

            e.printStackTrace();

            if(entityTransaction != null){
                entityTransaction.rollback();
            }
           return Optional.empty();

        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        return Optional.of(entity);
    }

    @Override
    public Optional<T> update(T entity){

        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction = entityManager.getTransaction();

            entityTransaction.begin();
            entityManager.merge(entity);
            entityTransaction.commit();

        }catch (Exception e){

            if(entityTransaction != null){
                entityTransaction.rollback();
            }
                return Optional.empty();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        return Optional.of(entity);
    }

    @Override
    public Optional<T> findOne(ID id) {

        T foundElement;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction = entityManager.getTransaction();

            entityTransaction.begin();
            foundElement = entityManager.find(entityType, id);
            entityTransaction.commit();

        }catch (Exception e){

            if(entityTransaction != null){
                entityTransaction.rollback();
            }

            return Optional.empty();

        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        return Optional.of(foundElement);
    }

    @Override
    public List<T> findAll() {

        List<T> elements;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction = entityManager.getTransaction();

            entityTransaction.begin();
            elements = entityManager
                    .createQuery("select x from " + entityType.getName() + " x", entityType)
                    .getResultList();
            entityTransaction.commit();

        }catch (Exception e){

            if(entityTransaction != null){
                entityTransaction.rollback();
            }

            return Collections.emptyList();

        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        return elements;
    }

    @Override
    public Optional<T> delete(ID id) {

        T elementToRemove;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction = entityManager.getTransaction();

            entityTransaction.begin();
            elementToRemove = entityManager.find(entityType, id);
            entityManager.remove(elementToRemove);
            entityTransaction.commit();

        }catch (Exception e){

            if(entityTransaction != null){
                entityTransaction.rollback();
            }

            return Optional.empty();

        }finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        return Optional.of(elementToRemove);

    }

    @Override
    public boolean deleteAll() {

        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction = entityManager.getTransaction();

            Query query = entityManager.createQuery("delete from " + entityType.getName());

            entityTransaction.begin();
            query.executeUpdate();
            entityTransaction.commit();

        }catch (Exception e){

            if(entityTransaction != null){
                entityTransaction.rollback();
            }

            return false;

        }finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        return true;
    }


}
