package com.app.controller;

import com.app.service.generic.ActionPerformer;
import com.app.service.generic.CrudService;
import com.app.utility.BackgroundTask;
import com.app.view.CrudView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

public class CrudController<T>{

    @FXML
    protected TextField searchField;

    @FXML
    protected Label errorMessage;

    @FXML
    protected Button addButton;

    @FXML
    protected Button removeButton;

    @FXML
    protected Button editButton;

    @FXML
    protected Button addOkButton;

    @FXML
    protected Button removeOkButton;

    @FXML
    protected Button editOkButton;

    @FXML
    protected GridPane userInput;

    private TableView<T> objectsTable;

    private ObservableList<T> allObjects;

    private CrudService<T> crudService;

    private CrudView<T> crudView;


    public void initFields(TableView<T> objectsTable, ObservableList<T> allObjects, CrudService<T> crudService, CrudView<T> crudView) {
        this.objectsTable = objectsTable;
        this.allObjects = allObjects;
        this.crudService = crudService;
        this.crudView = crudView;
    }

    @FXML
    protected void addNewObject(){

        performAction(() -> {

            T addedObject = crudService
                    .addObjectToDatabase(getFieldsForAdding());

            allObjects.add(addedObject);

            Platform.runLater(() -> crudView.addObjectToTable(objectsTable, addedObject));

        }, "Sprawdź poprawność pól!");
    }

    @FXML
    protected void removeObject(){

        performAction(() -> {

            T removedObject = crudService
                    .removeObjectFromDatabase(getId());

            allObjects.remove(removedObject);

            Platform.runLater(() -> crudView.removeObjectFromTable(objectsTable, removedObject));

        }, "Sprawdź poprawność pól!");
    }

    @FXML
    protected void editObject(){

        performAction(() -> {

            T foundObject = getObjectFromTable();

            T editedObject = crudService
                    .editObject(foundObject, getFieldsForEditing());

            allObjects.remove(foundObject);
            allObjects.add(editedObject);

            Platform.runLater(() -> crudView.editObjectInTable(objectsTable, editedObject));

        }, "Sprawdź poprawność pól!");
    }

    @FXML
    protected void searchForObject(){

        performAction(() -> {

            FilteredList<T> filteredObjects = crudService
                    .filterObjects(allObjects, searchField.getText());

            Platform.runLater(() -> crudView.setObjectsInTable(objectsTable, filteredObjects));

        }, "");

    }

    @FXML
    protected void setSmallerAddBackgroundImage(){

        setSmallerBackgroundImage(addButton, "plus.png");
    }

    @FXML
    protected void setSmallerRemoveBackgroundImage(){

        setSmallerBackgroundImage(removeButton, "minus.png");
    }

    @FXML
    protected void setSmallerEditBackgroundImage(){

        setSmallerBackgroundImage(editButton, "pencil.png");
    }

    @FXML
    protected void setNormalAddBackgroundImage(){

        setNormalBackgroundImage(addButton, "plus.png");
    }

    @FXML
    protected void setNormalRemoveBackgroundImage(){

        setNormalBackgroundImage(removeButton, "minus.png");
    }

    @FXML
    protected void setNormalEditBackgroundImage(){

        setNormalBackgroundImage(editButton, "pencil.png");
    }


    protected void performAction(ActionPerformer actionPerformer, String errorMess){

        BackgroundTask backgroundTask = new BackgroundTask(() -> {
            Platform.runLater(() -> crudView.hideErrorMessage(errorMessage));
            try {
                actionPerformer.perform();

            } catch (Exception e) {
                Platform.runLater(() -> crudView.showErrorMessage(errorMessage, errorMess));
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        });
        backgroundTask.execute();
    }

    private T getObjectFromTable(){

       return objectsTable.getItems()
                    .stream()
                    .filter(obj -> {
                        try {

                            Field tClass = obj.getClass().getDeclaredField("id");
                            tClass.setAccessible(true);

                            return tClass.get(obj).toString().equals(getId());

                        } catch (Exception e) {
                            Platform.runLater(() -> crudView.showErrorMessage(errorMessage, "Nie znaleziono podanego id!"));
                            System.out.println("No object with such id found!");
                        }

                        return false;
                    }).findFirst()
                    .orElseThrow(() -> new IllegalStateException("No object with such id found!"));
    }

    protected ObservableList<TextField> getFieldsForAdding(){

        return FXCollections.observableArrayList(userInput
                .getChildren()
                .stream()
                .filter(node -> node instanceof TextField && !node.getId().equals("idField"))
                .map(node -> (TextField) node)
                .collect(Collectors.toList()));
    }

    protected String getId(){

         return userInput
                .getChildren()
                .stream()
                .filter(node -> node instanceof TextField && node.getId().equals("idField"))
                 .map(txtField -> ((TextField) txtField).getText())
                 .findFirst()
                 .orElseThrow(() -> new IllegalStateException("Id field is not found!"));
    }

    protected ObservableList<TextField> getFieldsForEditing(){

        return FXCollections.observableArrayList(userInput
                .getChildren()
                .stream()
                .filter(node -> node instanceof TextField)
                .map(node -> (TextField) node)
                .collect(Collectors.toList()));
    }

    protected void changeAccessibilityForButtons(Button[] toDisableButtons, Button[] toEnableButtons){
        crudView.changeButtonsAccessibility(toDisableButtons, toEnableButtons);
    }

    protected void changeAccessibilityForFields(TextField[] toDisableFields, TextField[] toEnableFields){
        crudView.changeFieldsAccessibility(toDisableFields, toEnableFields);
    }

    private void setSmallerBackgroundImage(Button button, String imageName){

        crudView.showSmallerBackgroundImage(button, imageName);
    }

    private void setNormalBackgroundImage(Button button, String imageName){

        crudView.showNormalBackgroundImage(button, imageName);
    }

}
