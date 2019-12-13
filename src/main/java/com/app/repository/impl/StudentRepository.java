package com.app.repository.impl;

import com.app.entity.Student;
import com.app.entity.StudentGroup;
import com.app.repository.generic.AbstractGenericRepository;
import com.app.repository.generic.CrudRepository;

import java.util.Collections;
import java.util.List;

public class StudentRepository extends AbstractGenericRepository<Student, Long> implements CrudRepository<Student, Long> {
    public List<Student> findByGroup(StudentGroup studentGroup) {
        List<Student> students;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            students = entityManager.createQuery(
                    "SELECT s from Student s WHERE s.group = :studentGroup", Student.class).
                    setParameter("studentGroup", studentGroup).getResultList();
            entityTransaction.commit();
        }catch (Exception e){
            if(entityTransaction != null)
                entityTransaction.rollback();
            return Collections.emptyList();
        } finally {
            if (entityManager != null)
                entityManager.close();
        }
        return students;
    }
}
