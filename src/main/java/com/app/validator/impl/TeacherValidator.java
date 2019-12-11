package com.app.validator.impl;

import com.app.entity.Teacher;
import com.app.validator.generic.AbstractValidator;
import com.app.validator.generic.Validator;
import org.apache.commons.validator.EmailValidator;

import java.util.Map;

public class TeacherValidator extends AbstractValidator<Teacher> implements Validator<Teacher> {

    @Override
    public Map<String, String> validate(Teacher teacher){

        errors.clear();

        if(teacher == null){
            System.out.println("TR = null");
        }

        if(teacher.getId() == null || !isIdValid(teacher.getId().toString())){
            errors.put("id", "Id is not valid");
        }

        if(teacher.getName().isEmpty() || !isNameValid(teacher.getName())){
            errors.put("name", "Name is not valid");
        }

        if(teacher.getSurname().isEmpty() || !isSurnameValid(teacher.getSurname())){
            errors.put("surname", "Surname is not valid");
        }

        if(teacher.getEmail().isEmpty() || !isEmailValid(teacher.getEmail())){
            errors.put("email", "Email is not valid");
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

    private boolean isEmailValid(String email){
        EmailValidator emailValidator = EmailValidator.getInstance();
        return emailValidator.isValid(email);
    }
}
