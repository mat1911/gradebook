package com.app.service;

import com.app.entity.Teacher;
import com.app.repository.impl.TeacherRepository;
import com.app.validator.TeacherValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class TeacherService implements CrudService<Teacher>{

    private TeacherRepository teacherRepository = new TeacherRepository();
    private TeacherValidator teacherValidator = new TeacherValidator(teacherRepository);

    @Override
    public Teacher addObjectToDatabase(ObservableList<String> fields){

        Teacher teacher = Teacher.builder()
                .name(fields.get(0))
                .surname(fields.get(1))
                .email(fields.get(2))
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

    @Override
    public Teacher removeObjectFromDatabase(String id){

        if(!teacherValidator.isIdValid(id)){
            throw new IllegalArgumentException("Given id is not a number!");
        }

        return teacherRepository.delete(Long.parseLong(id))
                .orElseThrow(() -> new IllegalArgumentException("There is no teacher with such id!"));
    }

    @Override
    public Teacher editObject(Teacher teacher, ObservableList<String> fields){

        if(!fields.get(0).isEmpty()){
            teacher.setName(fields.get(0));
        }

        if(!fields.get(1).isEmpty()){
            teacher.setSurname(fields.get(1));
        }

        if(!fields.get(2).isEmpty()){
            teacher.setEmail(fields.get(2));
        }

        teacherValidator.validate(teacher);

        if(teacherValidator.hasErrors()){
            throw new IllegalArgumentException("Some arguments are not valid!");
        }

        return teacherRepository.update(teacher)
                .orElseThrow(() -> new IllegalStateException("Teacher is not modified!"));
    }

    @Override
    public FilteredList<Teacher> filterObjects(ObservableList<Teacher> teachers, String filterInput){

        if(filterInput.isEmpty()){
            return new FilteredList<>(teachers);
        }

        ObservableList<Teacher> result = FXCollections.observableArrayList(
                teachers.stream()
                .filter(tr ->
                        tr.getName().toLowerCase().contains(filterInput.toLowerCase()) ||
                                tr.getSurname().toLowerCase().contains(filterInput.toLowerCase()) ||
                                tr.getEmail().toLowerCase().contains(filterInput.toLowerCase()) ||
                                tr.getId().toString().contains(filterInput))
                .collect(Collectors.toList()));

        return new FilteredList<>(result);
    }

    public List<Teacher> getAllTeachers(){

        return teacherRepository.findAll();
    }

}
