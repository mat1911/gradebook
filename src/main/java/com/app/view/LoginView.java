package com.app.view;

import javafx.scene.control.Label;

public class LoginView {
    public void hideLabel(Label label) {
        label.setOpacity(0.0);
    }

    public void showLabel(Label label) {
        label.setOpacity(1.0);
    }
}
