package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class help_1 {

    @FXML
    private ImageView back_pane;

    @FXML
    void space(MouseEvent event) {
        back_pane.setDisable(false);
    }
}
