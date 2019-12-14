package com.app.repository.impl;

import com.app.entity.Remark;
import com.app.entity.Student;
import com.app.repository.generic.AbstractGenericRepository;
import com.app.repository.generic.CrudRepository;

import java.util.Collections;
import java.util.List;

public class RemarkRepository extends AbstractGenericRepository<Remark, Long> implements CrudRepository<Remark, Long>{
    public List<Remark> findByStudent(Student selectedStudent) {
        List<Remark> remarks;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            remarks = entityManager.createQuery(
                    "SELECT r from Remark r WHERE r.student = :selectedStudent", Remark.class)
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
        return remarks;
    }
}
