package com.app.repository.impl;

import com.app.entities.Teacher;
import com.app.repository.generic.AbstractGenericRepository;
import com.app.repository.generic.CrudRepository;

import java.util.Optional;

public class TeacherRepository extends AbstractGenericRepository<Teacher, Long> implements CrudRepository<Teacher, Long> {
    public Optional<Teacher> findByEmail(String email) {
        Teacher foundElement;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            foundElement = entityManager.createQuery(
                    "SELECT u from Teacher u WHERE u.email = :email", Teacher.class).
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
