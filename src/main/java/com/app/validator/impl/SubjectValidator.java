package com.app.validator.impl;

import com.app.entity.Subject;
import com.app.validator.generic.AbstractValidator;
import com.app.validator.generic.Validator;

import java.util.Map;

public class SubjectValidator extends AbstractValidator<Subject> implements Validator<Subject> {

    @Override
    public Map<String, String> validate(Subject subject){

        errors.clear();

        if(subject.getId() == null || !isIdValid(subject.getId().toString())){
            errors.put("id", "Id is not valid");
        }

        if(subject.getName() == null || !isNameValid(subject.getName())){
            errors.put("name", "Name is not valid");
        }

        return errors;
    }

    @Override
    public boolean isIdValid(String id) { return id.matches("[0-9]+");}


    public boolean isNameValid(String id) { return id.matches("([A-Za-z]+[0-9]* *)+");}

}
