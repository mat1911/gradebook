package com.app.service.generic;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TextField;

import java.util.List;


public interface CrudService<T> {

    T addObjectToDatabase(ObservableList<TextField> fieldsWithObjectData);

    T removeObjectFromDatabase(String id);

    FilteredList<T> filterObjects(ObservableList<T> allObjects, String filterInput);

    T editObject(T object, ObservableList<TextField> fields);

    List<T> getAllObjects();
}
