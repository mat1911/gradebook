package com.app.controller.menu;


import com.app.enums.SubViewType;
import com.app.controller.ViewManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public abstract class MenuController {
    protected ViewManager viewManager = new ViewManager();

    @FXML
    protected GridPane contentPane;

    protected void changeSubView(SubViewType subViewType) {
        Platform.runLater(() -> {
            contentPane.getChildren().clear();
            contentPane.getChildren().add(viewManager.getRoot(subViewType));
        });
    }
}
