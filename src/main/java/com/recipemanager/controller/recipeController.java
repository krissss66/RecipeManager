package com.recipemanager.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.springframework.stereotype.Controller;

//
// Class: recipeController
//
// Description:
//  recipeController is a controller class responsible for closing the recipe detail window
//
//


@Controller
public class recipeController {

    @FXML
    public Label recipe_name;

    @FXML
    public ImageView recipe_imageview;
    @FXML
    public TextArea recipe_description;
    @FXML
    public TextArea recipe_instruction;

    public void closeDetailWindow() {
        recipe_name.getScene().getWindow().hide();                    // Close the window


    }
}
