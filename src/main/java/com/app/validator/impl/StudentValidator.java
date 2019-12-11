package com.app.validator.impl;

import com.app.entity.Student;
import com.app.validator.generic.AbstractValidator;
import com.app.validator.generic.Validator;

import java.util.Map;

public class StudentValidator extends AbstractValidator<Student> implements Validator<Student> {

    @Override
    public Map<String, String> validate(Student student){

        errors.clear();

        if(student.getId() == null || !isIdValid(student.getId().toString())){
            errors.put("id", "Id is not valid");
        }

        if(student.getName().isEmpty() || !isNameValid(student.getName())){
            errors.put("name", "Name is not valid");
        }

        if(student.getSurname().isEmpty() || !isSurnameValid(student.getSurname())){
            errors.put("surname", "Surname is not valid");
        }

        return errors;
    }

    @Override
    public boolean isIdValid(String id) { return id.matches("[0-9]+");}

    private boolean isNameValid(String surname){
        return surname.matches("[A-Za-z]{3,}");
    }

    private boolean isSurnameValid(String name){
        return name.matches("[A-Za-z]{3,}(-[A-Za-z]{3,})?");
    }
}
