package com.bitris.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class MainController {

    @FXML
    private StackPane mainBackground;

    public void setVista(Node node) { mainBackground.getChildren().setAll(node); }
}