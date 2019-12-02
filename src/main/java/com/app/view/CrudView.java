package com.app.view;

import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public interface CrudView<T> {

    default void addObjectToTable(TableView<T> objectsTable, T objectToAdd) {

        objectsTable.getItems().add(objectToAdd);
        objectsTable.refresh();
    }

    default void removeObjectFromTable(TableView<T> objectsTable, T objectToAdd){

        objectsTable.getItems().remove(objectToAdd);
        objectsTable.refresh();
    }

    default void setObjectsInTable(TableView<T> objectsTable, FilteredList<T> filteredObjects){

        objectsTable.getItems().clear();
        objectsTable.getItems().addAll(filteredObjects);
        objectsTable.refresh();
    }

    void editObjectInTable(TableView<T> objectsTable, T editedObject);

    default void hideErrorMessage(Label errorMessageLabel){

        errorMessageLabel.setVisible(false);
    }

    default void showErrorMessage(Label errorMessageLabel, String errorMessage){

        errorMessageLabel.setText(errorMessage);
        errorMessageLabel.setVisible(true);
    }

    default void showSmallerBackgroundImage(Button button, String imageName){

        String path = "'file:src/main/resources/static/" + imageName + "'";
        button.setStyle("-fx-background-image: url(" + path + "); -fx-background-repeat: stretch; -fx-background-position: center center; -fx-background-size: 15 15; -fx-background-color: transparent;");
    }

    default void showNormalBackgroundImage(Button button, String imageName){

        String path = "'file:src/main/resources/static/" + imageName + "'";
        button.setStyle("-fx-background-image: url(" + path + "); -fx-background-repeat: stretch; -fx-background-position: center center; -fx-background-size: 20 20; -fx-background-color: transparent;");
    }
}
