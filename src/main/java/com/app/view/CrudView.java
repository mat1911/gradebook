package com.app.view;

import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public interface CrudView<T> {

    default void addObjectToTable(TableView<T> objectsTable, T objectToAdd) {

        objectsTable.getItems().add(objectToAdd);
        objectsTable.refresh();
    }

    default void removeObjectFromTable(TableView<T> objectsTable, T objectToAdd) {

        objectsTable.getItems().remove(objectToAdd);
        objectsTable.refresh();
    }

    default void setObjectsInTable(TableView<T> objectsTable, FilteredList<T> filteredObjects) {

        objectsTable.getItems().clear();
        objectsTable.getItems().addAll(filteredObjects);
        objectsTable.refresh();
    }

    default void editObjectInTable(TableView<T> objectsTable, T editedObject) {

        try {
            Method getMethodEditedObject = editedObject.getClass().getMethod("getId", null);
            Method getMethodEachObject;

            T t = null;

            for(T singleObject : objectsTable.getItems()){

                getMethodEachObject =  singleObject.getClass().getMethod("getId", null);

                if(getMethodEachObject.invoke(singleObject, null).toString().equals(getMethodEditedObject.invoke(editedObject, null).toString())){
                    t = singleObject;
                }
            }

            if(t == null){
                throw new IllegalStateException("Edited object is not found in the table!");
            }

            Field[] fields = t.getClass().getDeclaredFields();
            Field[] editedFields = editedObject.getClass().getDeclaredFields();

            for(int i = 0; i < fields.length; i++){

                fields[i].setAccessible(true);
                editedFields[i].setAccessible(true);

                if(fields[i].getName().equals(editedFields[i])){
                    fields[i].set(t, editedFields[i].get(editedObject));
                }
            }

            objectsTable.refresh();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    default void hideErrorMessage(Label errorMessageLabel) {

        errorMessageLabel.setVisible(false);
    }

    default void showErrorMessage(Label errorMessageLabel, String errorMessage) {

        errorMessageLabel.setText(errorMessage);
        errorMessageLabel.setVisible(true);
    }

    default void showSmallerBackgroundImage(Button button, String imageName) {

        String path = "'file:src/main/resources/static/" + imageName + "'";
        button.setStyle("-fx-background-image: url(" + path + "); -fx-background-repeat: stretch; -fx-background-position: center center; -fx-background-size: 15 15; -fx-background-color: transparent;");
    }

    default void showNormalBackgroundImage(Button button, String imageName) {

        String path = "'file:src/main/resources/static/" + imageName + "'";
        button.setStyle("-fx-background-image: url(" + path + "); -fx-background-repeat: stretch; -fx-background-position: center center; -fx-background-size: 20 20; -fx-background-color: transparent;");
    }

    default void changeButtonsAccessibility(Button[] toDisableButtons, Button[] toEnableButtons) {

        Arrays.stream(toDisableButtons).forEach(button -> button.setDisable(true));
        Arrays.stream(toEnableButtons).forEach(button -> button.setDisable(false));
    }

    default void changeFieldsAccessibility(TextField[] toDisableFields, TextField[] toEnableFields) {

        Arrays.stream(toDisableFields).forEach(field -> field.setDisable(true));
        Arrays.stream(toEnableFields).forEach(field -> field.setDisable(false));
    }
}
