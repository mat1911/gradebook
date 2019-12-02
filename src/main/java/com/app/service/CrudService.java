package com.app.service;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public interface CrudService<T> {

    T addObjectToDatabase(ObservableList<String> fields);

    T removeObjectFromDatabase(String id);

    FilteredList<T> filterObjects(ObservableList<T> allObjects, String filterInput);

    T editObject(T object, ObservableList<String> fields);
}
