package com.app.repository.impl;

import com.app.entity.Admin;
import com.app.repository.generic.AbstractGenericRepository;
import com.app.repository.generic.CrudRepository;

import java.util.Optional;

public class AdminRepository extends AbstractGenericRepository<Admin, Long> implements CrudRepository<Admin, Long> {

    public Optional<Admin> findByEmail(String email) {
        Admin foundElement;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            foundElement = entityManager.createQuery(
                    "SELECT u from Admin u WHERE u.email = :email", Admin.class).
                    setParameter("email", email).getSingleResult();
            entityTransaction.commit();
        }catch (Exception e){
            if(entityTransaction != null)
                entityTransaction.rollback();
            return Optional.empty();

        } finally {
            if (entityManager != null)
                entityManager.close();
        }
        return Optional.of(foundElement);
    }
}
