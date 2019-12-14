package com.app.repository.impl;

import com.app.entity.Lesson;
import com.app.entity.StudentGroup;
import com.app.entity.Teacher;
import com.app.repository.generic.AbstractGenericRepository;
import com.app.repository.generic.CrudRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class LessonRepository extends AbstractGenericRepository<Lesson, Long> implements CrudRepository<Lesson, Long> {

    public List<Lesson> findAllByDateAndGroup(LocalDate date, StudentGroup group){

        List<Lesson> foundLessons;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();

            foundLessons = entityManager
                    .createQuery("SELECT u from Lesson u WHERE u.group.id = :id AND u.date = :date", Lesson.class)
                    .setParameter("id", group.getId())
                    .setParameter("date", date)
                    .getResultList();

            entityTransaction.commit();
        }catch (Exception e){

            if(entityTransaction != null)
                entityTransaction.rollback();

            return Collections.EMPTY_LIST;

        } finally {
            if (entityManager != null)
                entityManager.close();
        }
        return foundLessons;

    }

    public Optional<Lesson> findByDateAndGroupAndLessonNumber(LocalDate date, StudentGroup group, Long lessonNumber){

        Lesson foundLesson;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();

            foundLesson = entityManager
                    .createQuery("SELECT u from Lesson u WHERE u.group.id = :id " +
                            "AND u.date = :date AND u.lessonNumber = :number", Lesson.class)
                    .setParameter("id", group.getId())
                    .setParameter("date", date)
                    .setParameter("number", lessonNumber)
                    .getSingleResult();

            entityTransaction.commit();
        }catch (Exception e){

            if(entityTransaction != null)
                entityTransaction.rollback();

            return Optional.empty();

        } finally {
            if (entityManager != null)
                entityManager.close();
        }
        return Optional.of(foundLesson);

    }

    public List<Lesson> findByTeacher(Teacher loggedTeacher) {
        List<Lesson> lessons;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            lessons = entityManager.createQuery(
                    "SELECT l from Lesson l WHERE l.teacher = :teacher", Lesson.class).
                    setParameter("teacher", loggedTeacher).getResultList();
            entityTransaction.commit();
        }catch (Exception e){
            if(entityTransaction != null)
                entityTransaction.rollback();
            return Collections.emptyList();
        } finally {
            if (entityManager != null)
                entityManager.close();
        }

        return lessons;
    }

    public List<Lesson> findByGroup(StudentGroup group) {
        List<Lesson> lessons;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            lessons = entityManager.createQuery(
                    "SELECT l from Lesson l WHERE l.group = :group", Lesson.class).
                    setParameter("group", group).getResultList();
            entityTransaction.commit();
        }catch (Exception e){
            if(entityTransaction != null)
                entityTransaction.rollback();
            return Collections.emptyList();
        } finally {
            if (entityManager != null)
                entityManager.close();
        }
        return lessons;
    }
}
