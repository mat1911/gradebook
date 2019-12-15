package com.app.repository.impl;

import com.app.entity.Mark;
import com.app.entity.Student;
import com.app.entity.Subject;
import com.app.repository.generic.AbstractGenericRepository;
import com.app.repository.generic.CrudRepository;

import java.util.Collections;
import java.util.List;

public class MarkRepository extends AbstractGenericRepository<Mark, Long> implements CrudRepository<Mark, Long> {
    public List<Mark> findByStudentAndSubject(Student selectedStudent, Subject selectedSubject) {
        List<Mark> marks;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            marks = entityManager.createQuery(
                    "SELECT m from Mark m WHERE m.student = :selectedStudent AND m.subject = :selectedSubject", Mark.class)
                    .setParameter("selectedStudent", selectedStudent)
                    .setParameter("selectedSubject", selectedSubject)
                    .getResultList();
            entityTransaction.commit();
        }catch (Exception e){
            if(entityTransaction != null)
                entityTransaction.rollback();
            return Collections.emptyList();
        } finally {
            if (entityManager != null)
                entityManager.close();
        }
        return marks;
    }

    public List<Mark> findByStudent(Student selectedStudent) {
        List<Mark> marks;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            marks = entityManager.createQuery(
                    "SELECT m from Mark m WHERE m.student = :selectedStudent", Mark.class)
                    .setParameter("selectedStudent", selectedStudent)
                    .getResultList();
            entityTransaction.commit();
        }catch (Exception e){
            if(entityTransaction != null)
                entityTransaction.rollback();
            return Collections.emptyList();
        } finally {
            if (entityManager != null)
                entityManager.close();
        }
        return marks;
    }
}
