package com.app.repository.impl;

import com.app.entities.AdminUser;
import com.app.repository.generic.AbstractGenericRepository;
import com.app.repository.generic.CrudRepository;

import java.util.Optional;

public class AdminUserRepository extends AbstractGenericRepository<AdminUser, Long> implements CrudRepository<AdminUser, Long> {

    public Optional<AdminUser> findByEmail(String email) {
        AdminUser foundElement;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            foundElement = entityManager.createQuery(
                    "SELECT u from AdminUser u WHERE u.email = :email", AdminUser.class).
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
