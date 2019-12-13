package com.app.service.generic;

import com.app.repository.generic.CrudRepository;
import com.app.validator.generic.Validator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.mindrot.jbcrypt.BCrypt;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractCrudService<T> implements CrudService<T> {

    protected Class<T> serviceType = (Class<T>) ((ParameterizedType) (super.getClass().getGenericSuperclass())).getActualTypeArguments()[0];

    protected Validator<T> validator;
    protected CrudRepository<T, Long> crudRepository;

    public AbstractCrudService(Validator<T> validator, CrudRepository<T, Long> crudRepository) {
        this.validator = validator;
        this.crudRepository = crudRepository;
    }

    @Override
    public T addObjectToDatabase(ObservableList<TextField> fieldsWithObjectData) {

        T t = null;
        Field[] decFields = serviceType.getDeclaredFields();

        try {

            t = serviceType.newInstance();

            for (int i = 0; i < fieldsWithObjectData.size(); i++) {

                for (int j = 0; j < decFields.length; j++) {

                    if (fieldsWithObjectData.get(i).getId().equals(decFields[j].getName() + "Field")) {

                        decFields[j].setAccessible(true);

                        if (!fieldsWithObjectData.get(i).getText().isEmpty() && fieldsWithObjectData.get(i) instanceof PasswordField) {
                            decFields[j].set(t, BCrypt.hashpw(fieldsWithObjectData.get(i).getText(), BCrypt.gensalt()));
                        } else {
                            decFields[j].set(t, fieldsWithObjectData.get(i).getText());
                        }

                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> errors = validator.validate(t);

        errors.entrySet()
                .stream()
                .filter(err -> !err.getKey().equals("id"))
                .findFirst()
                .ifPresent(err -> {
                    throw new IllegalArgumentException("Some arguments are not valid!");
                });

        return crudRepository.add(t)
                .orElseThrow(() -> new IllegalStateException("Object is not added to database!"));
    }

    @Override
    public T findOne(String id) {
        if (!validator.isIdValid(id)) {
            throw new IllegalArgumentException("Given id is not a number!");
        }

        Optional<T> element = crudRepository.findOne(Long.parseLong(id));
        if(element.isPresent())
            return element.get();
        else throw new IllegalArgumentException("There is no object with such id!");
    }

    @Override
    public T removeObjectFromDatabase(String id) {

        if (!validator.isIdValid(id)) {
            throw new IllegalArgumentException("Given id is not a number!");
        }

        return crudRepository.delete(Long.parseLong(id))
                .orElseThrow(() -> new IllegalArgumentException("There is no object with such id!"));
    }

    @Override
    public FilteredList<T> filterObjects(ObservableList<T> allObjects, String filterInput) {

        if (filterInput.isEmpty()) {
            return new FilteredList<>(allObjects);
        }

        ObservableList<T> result = FXCollections.observableArrayList(
                allObjects.stream()
                        .filter(tr -> checkIfObjectDataContainsFilterInput(tr, filterInput))
                        .collect(Collectors.toList()));

        return new FilteredList<>(result);
    }

    @Override
    public T editObject(T t, ObservableList<TextField> fieldsWithObjectData) {

        Field[] decFields;
        Method getIdMethod;

        try {

            decFields = serviceType.getDeclaredFields();
            getIdMethod = serviceType.getMethod("getId", null);

            for (int i = 0; i < decFields.length; i++) {
                for (int j = 0; j < fieldsWithObjectData.size(); j++) {

                    if (fieldsWithObjectData.get(j).getId().equals(decFields[i].getName() + "Field")
                            && !fieldsWithObjectData.get(j).getText().isEmpty()
                            && decFields[i].getType().getSimpleName().equals("String")) {

                        decFields[j].setAccessible(true);

                        if (!fieldsWithObjectData.get(i).getText().isEmpty() && fieldsWithObjectData.get(i) instanceof PasswordField) {
                            decFields[j].set(t, BCrypt.hashpw(fieldsWithObjectData.get(i).getText(), BCrypt.gensalt()));
                        } else {
                            decFields[j].set(t, fieldsWithObjectData.get(i).getText());
                        }

                    }
                }
            }

            validator.validate(t);

            if (validator.hasErrors() || crudRepository.findOne(Long.parseLong(getIdMethod.invoke(t).toString())).isEmpty()) {
                throw new IllegalArgumentException("Some arguments are not valid!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return crudRepository.update(t)
                .orElseThrow(() -> new IllegalStateException("Object is not modified!"));
    }

    @Override
    public List<T> getAllObjects() {
        return crudRepository.findAll();
    }

    private boolean checkIfObjectDataContainsFilterInput(T t, String filterInput) {

        Field[] decFields = serviceType.getDeclaredFields();

        return Arrays.stream(decFields)
                .anyMatch(field -> {
                    field.setAccessible(true);

                    try {
                        return field.get(t).toString().toLowerCase().contains(filterInput.toLowerCase());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                    return false;
                });
    }
}

