package com.app.validator.impl;

import com.app.entity.Lesson;
import com.app.validator.generic.AbstractValidator;
import com.app.validator.generic.Validator;

import java.util.Map;

public class LessonValidator extends AbstractValidator<Lesson> implements Validator<Lesson> {

    @Override
    public Map<String, String> validate(Lesson lesson) {

        errors.clear();

        if(lesson.getGroup() == null){
            errors.put("group", "Group is not valid!");
        }

        if(lesson.getDate() == null){
            errors.put("date", "Date is not valid!");
        }

        if(lesson.getLessonNumber() == null){
            errors.put("lessonNumber", "Lesson number is not valid!");
        }

        if(lesson.getTeacher() == null){
            errors.put("teacher", "Teacher is not valid!");
        }

        if(lesson.getSubject() == null){
            errors.put("subject", "Subject is not valid!");
        }

        return errors;
    }

    @Override
    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    @Override
    public boolean isIdValid(String id) {
        return id.matches("[0-9]+");
    }

}
