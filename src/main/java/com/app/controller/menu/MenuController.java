package com.app.controller.menu;

import com.app.app.AppContext;
import com.app.controller.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class MenuController {
    protected AppContext appContext = AppContext.getInstance();
    protected ViewManager viewManager = new ViewManager();

    @FXML
    protected GridPane contentPane;

    public void initialize() {
        appContext.setContentPane(contentPane);
    }
}
