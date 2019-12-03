package com.app.validator;

import com.app.entity.Teacher;
import com.app.repository.impl.TeacherRepository;
import org.apache.commons.validator.EmailValidator;

import java.util.HashMap;
import java.util.Map;

public class TeacherValidator {

    private Map<String, String> errors = new HashMap<>();
    private TeacherRepository teacherRepository;

    public TeacherValidator(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public boolean hasErrors(){
        return !errors.isEmpty();
    }

    public Map<String, String> validate(Teacher teacher){

        errors.clear();

        if(teacher.getId() == null || !isIdValid(teacher.getId())){
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

    public boolean isIdValid(String id) { return id.matches("[0-9]+");}

    private boolean isIdValid(Long id){
        return teacherRepository.findOne(id).isPresent();
    }

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
