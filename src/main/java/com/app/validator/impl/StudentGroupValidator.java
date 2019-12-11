package com.app.validator.impl;

import com.app.entity.StudentGroup;
import com.app.validator.generic.AbstractValidator;
import com.app.validator.generic.Validator;

import java.util.Map;

public class StudentGroupValidator extends AbstractValidator<StudentGroup> implements Validator<StudentGroup> {

    @Override
    public Map<String, String> validate(StudentGroup studentGroup){

        errors.clear();

        if(studentGroup.getId() == null || !isIdValid(studentGroup.getId().toString())){
            errors.put("id", "Id is not valid");
        }

        return errors;
    }

    @Override
    public boolean isIdValid(String id) { return id.matches("[0-9]+");}

}
