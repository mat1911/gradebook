package com.app.service;

import com.app.entity.Teacher;
import com.app.repository.impl.TeacherRepository;
import com.app.validator.TeacherValidator;

import java.util.Map;
import java.util.Optional;


public class TeacherService {

    private TeacherRepository teacherRepository = new TeacherRepository();
    private TeacherValidator teacherValidator = new TeacherValidator(teacherRepository);

    public Teacher addTeacherToDatabase(String name, String surname, String email){

        Teacher teacher = Teacher.builder()
                .name(name)
                .surname(surname)
                .email(email)
                .build();

        Map<String, String> errors =  teacherValidator.validate(teacher);

        errors.entrySet()
                .stream()
                .filter(err -> !err.getKey().equals("id"))
                .findFirst()
                .ifPresent(err -> {
                    throw new IllegalArgumentException("Some arguments are not valid!");
                });

        Teacher result = teacherRepository.add(teacher)
                .orElseThrow(() -> new IllegalStateException("Teacher is not added to database!"));

        return result;
    }

    public Teacher removeTeacherFromDatabase(String id){

        if(!teacherValidator.isIdValid(id)){
            throw new IllegalArgumentException("Given id is not a number!");
        }

        return teacherRepository.delete(Long.parseLong(id))
                .orElseThrow(() -> new IllegalArgumentException("There is no teacher with such id!"));
    }

    public Teacher editTeacher(Teacher teacher, String name, String surname, String email){

        if(!name.isEmpty()){
            teacher.setName(name);
        }

        if(!surname.isEmpty()){
            teacher.setSurname(surname);
        }

        if(!email.isEmpty()){
            teacher.setEmail(email);
        }

        teacherValidator.validate(teacher);

        if(teacherValidator.hasErrors()){
            throw new IllegalArgumentException("Some arguments are not valid!");
        }

        return teacherRepository.update(teacher)
                .orElseThrow(() -> new IllegalStateException("Teacher is not modified!"));
    }

    public Teacher findByEmail(String email) {
        Optional<Teacher> teacher = teacherRepository.findByEmail(email);
        if(teacher.isPresent())
            return teacher.get();
        throw new IllegalArgumentException("No teacher associated with such e-mail!");
    }

}
