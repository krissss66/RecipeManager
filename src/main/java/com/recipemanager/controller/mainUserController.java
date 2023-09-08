package com.recipemanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.recipemanager.entity.ingredients;
import com.recipemanager.entity.recipe;
import com.recipemanager.entity.user;
import com.recipemanager.service.ingredientService;
import com.recipemanager.service.recipeService;
import com.recipemanager.service.userService;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.recipemanager.RecipeManagerApplication.loadFxml;

//
// Class: mainUserController
//
// Description:
// MainUserController is a controller class responsible for managing user interactions,
// searching and displaying recipes, generate recipes based on the ingredients, and changing user password
//


@Controller
public class mainUserController {


    @FXML
    private TextField search_ingredients;
    @FXML
    public Label welcomeHeader;

    @FXML
    public HBox user_recipe_form;
    @Qualifier("recipeServiceImpl")
    @Autowired
    private recipeService recipeS;

    @Qualifier("ingredientServiceImpl")
    @Autowired
    private ingredientService ingredientS;

    @Qualifier("userServiceImpl")
    @Autowired
    private userService userS;

    @FXML
    private TextField search_recipe;

    @FXML
    private VBox user_ingredient_form;

    @FXML
    private AnchorPane generate_pane;
    @FXML
    private AnchorPane discover_pane;


    @FXML
    private VBox show_recipe_form;
    @FXML
    private AnchorPane show_recipe_pane;

    @FXML
    private TextField current_password;
    @FXML
    private TextField new_password;
    @FXML
    private TextField confirm_password;
    @FXML
    private AnchorPane change_password_pane;


    public List<String> userIngredientsList = new ArrayList<String>();



    public void initialize(){
        loadRecipe();
    } // initialize the main user page and load recipes initially

    // load the recipe list based on the ingredients the user inserted
    public void loadRecipeList(){
        ObservableList<recipe> recipeList = getRecipeList();                  // get all recipes from database
        show_recipe_form.getChildren().clear();                        // clear the recipe list form
        ObservableList<recipe> filteredList = FXCollections.observableArrayList();              // create a new observable list to store filtered recipes
        recipeList.forEach(recipe ->{
            String[] recipeIngre = recipe.getIngredients().split(" ");    // get the ingredients of the recipe

            for (String ingre : recipeIngre){
                if (userIngredientsList.contains(ingre)){                        // if the user has the ingredient, then add the recipe to the filtered list
                    filteredList.add(recipe);
                    return;
                }
            }
                                   // if the recipe has all the ingredients, then add the recipe to the filtered list
        });

        filteredList.forEach(recipe -> {                        // for each recipe in the filtered list, create a new anchor pane to display the recipe
            AnchorPane pane = new AnchorPane();
            pane.setPrefHeight(200);
            pane.setPrefWidth(762);
            byte[] imageByte = recipe.getImage();
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            BufferedImage bImage2 = null;
            try {
                bImage2 = ImageIO.read(bis);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Image image = SwingFXUtils.toFXImage(bImage2, null);
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitHeight(154);
            imageView.setFitWidth(252);
            imageView.setLayoutX(23);
            imageView.setLayoutY(22);
            pane.getChildren().add(imageView);
            Label label = new Label();
            label.setText(recipe.getRecipeName());
            label.setLayoutX(329);
            label.setLayoutY(33);
            pane.getChildren().add(label);
            Label label1 = new Label();
            label1.setText(recipe.getDescription());
            label1.setLayoutX(329);
            label1.setLayoutY(84);
            pane.getChildren().add(label1);
            pane.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 5px; -fx-border-radius: 10; -fx-border-width: 5px; -fx-cursor: hand; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 10, 0, 0, 0);");
            pane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                try {
                    loadRecipeDetails(recipe);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            show_recipe_form.getChildren().add(pane);                           // add the anchor pane to the recipe list form

        });
    }
    // load the generation result based on the ingredients the user inserted
    public void createRecipeList() {
        discover_pane.setVisible(false);
        generate_pane.setVisible(false);
        show_recipe_pane.setVisible(true);
        loadRecipeList();

    }


    // load the recipe list in the discover page
    public void loadRecipe(){
        user_recipe_form.getChildren().clear();
        HBox.setMargin(user_recipe_form, new Insets(10, 10, 10, 10));       // set the margin of the recipe list form
        ObservableList<recipe> userBeanList = getRecipeList();
        userBeanList.forEach(recipeData -> {                               // for each recipe in the recipe list, create a new anchor pane to display the recipe
            AnchorPane pane = new AnchorPane();
            pane.setPrefHeight(168);
            pane.setPrefWidth(226);
            byte[] imageByte = recipeData.getImage();                            // get the image of the recipe
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);         // convert the image to byte array
            BufferedImage bImage2 = null;
            try {
                bImage2 = ImageIO.read(bis);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Image image = SwingFXUtils.toFXImage(bImage2, null);
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitHeight(133);
            imageView.setFitWidth(225);
            imageView.setLayoutX(0);
            imageView.setLayoutY(0);
            pane.getChildren().add(imageView);                                   // add the image to the anchor pane
            Label label = new Label();
            label.setText(recipeData.getRecipeName());                         // get the name of the recipe
            label.setLayoutX(14);
            label.setLayoutY(137);
            pane.getChildren().add(label);                                   // add the name to the anchor pane
            pane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {            // add the event handler to the anchor pane
                try {
                    loadRecipeDetails(recipeData);                           // load the recipe details page
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            pane.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 5px; -fx-border-radius: 10; -fx-border-width: 5px; -fx-cursor: hand; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 10, 0, 0, 0);");

            user_recipe_form.getChildren().add(pane);
            HBox.setMargin(pane, new Insets(10, 10, 10, 10));

        });

    }

    // load the recipe details page
    public void loadRecipeDetails(recipe re) throws IOException {
        Popup popup = new Popup();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/recipe.fxml"));           // load the recipe details page
        popup.getContent().add((Parent)loader.load());                            // add the recipe details page to the popup
        popup.show(welcomeHeader.getScene().getWindow());                        // show the popup
        recipeController controller = loader.getController();                      // get the controller of the recipe details page
        controller.recipe_name.setText(re.getRecipeName());                      // set the name of the recipe
        controller.recipe_name.setStyle("-fx-pref-width: 200");        // set the style of the name
        controller.recipe_description.setText(re.getDescription());               // set the description of the recipe
        controller.recipe_instruction.setText(re.getInstruction());                // set the instruction of the recipe
        controller.recipe_imageview.setImage(new Image(new ByteArrayInputStream(re.getImage())));               // set the image of the recipe

    }


    public ObservableList<recipe> getRecipeList(){
        ObservableList<recipe> recipeList = FXCollections.observableArrayList();
        recipeList.addAll(recipeS.list());
        return recipeList;
    }


    // load the recipe list based on the search result of the user insert in the discover page
    public void searchRecipe() {
        String search = search_recipe.getText();                // get the search result
        List<recipe> recipeList = recipeS.list();                 // get the recipe list
        List<recipe> searchList = new ArrayList<>();               // create a new list to store the search result
        for (recipe recipe : recipeList) {                             // for each recipe in the recipe list, check if the recipe name contains the search result
            String recipeName = recipe.getRecipeName().toLowerCase();
            search = search.toLowerCase();
            if (recipeName.contains(search)) {
                searchList.add(recipe);
            }
        }
        user_recipe_form.getChildren().clear();
        searchList.forEach(recipeData -> {                          // for each recipe in the search result, create a new anchor pane to display the recipe
            AnchorPane pane = new AnchorPane();
            pane.setPrefHeight(168);
            pane.setPrefWidth(226);
            byte[] imageByte = recipeData.getImage();
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            BufferedImage bImage2 = null;
            try {
                bImage2 = ImageIO.read(bis);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Image image = SwingFXUtils.toFXImage(bImage2, null);
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitHeight(133);
            imageView.setFitWidth(225);
            imageView.setLayoutX(0);
            imageView.setLayoutY(0);
            pane.getChildren().add(imageView);
            Label label = new Label();
            label.setText(recipeData.getRecipeName());
            label.setLayoutX(14);
            label.setLayoutY(137);
            pane.getChildren().add(label);
            pane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                try {
                    loadRecipeDetails(recipeData);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            pane.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 5px; -fx-border-radius: 10; -fx-border-width: 5px; -fx-cursor: hand; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 10, 0, 0, 0);");
            user_recipe_form.getChildren().add(pane);
            HBox.setMargin(pane, new Insets(10, 10, 10, 10));

        });

    }

    //back to the login page
    public void signOutUser() {
        try {
            welcomeHeader.getScene().getWindow().hide();              // hide the view
            Stage stage = new Stage();
            stage.setScene(new Scene(loadFxml("/login.fxml").load()));               // load the login page
            stage.show();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // add the ingredients in the search bar to the list of ingredients that prepare for the recipe generation
    public void addIngredients() {
        String search = search_ingredients.getText();                       // get the search result
        List<ingredients> ingredientList = ingredientS.list();                    // get the ingredient list
        ingredients ingredient = new ingredients();
        if(search.equals("")){                                               // if the search result is empty, show the error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Please enter an ingredient");
            alert.showAndWait();
        }
        for (ingredients ingredients : ingredientList) {                           // for each ingredient in the ingredient list, check if the ingredient name equals to the search result
            String ingredientName = ingredients.getIngredientName().toLowerCase();
            search = search.toLowerCase();
            if (ingredientName.equals(search)) {
                ingredient = ingredients;
            }

        }
        if(ingredient.getIngredientName() == null){                               // if the ingredient name is null, show the error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Ingredient not found");
            alert.showAndWait();
        }else{
            AnchorPane pane = new AnchorPane();                            // create a new anchor pane to display the ingredient
            pane.setPrefHeight(70);
            pane.setPrefWidth(760);
            Label label = new Label();
            label.setText(ingredient.getIngredientName());
            label.setLayoutX(37);
            label.setLayoutY(18);
            pane.getChildren().add(label);
            Button button = new Button();
            button.setLayoutX(643);
            button.setLayoutY(15);
            button.setPrefHeight(23);
            button.setPrefWidth(35);
            button.setStyle("-fx-background-color: #b1c1c6; -fx-background-radius: 150; -fx-border-width: 1px; -fx-cursor: hand; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 10, 0, 0, 0);");
            button.setOnAction(event -> {                                        // when the user click the button, remove the ingredient from the list
                user_ingredient_form.getChildren().remove(pane);
                userIngredientsList.remove(label.getText());
            });
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("18");
            icon.setLayoutX(654);
            icon.setLayoutY(35);
            pane.getChildren().add(button);
            pane.getChildren().add(icon);
            pane.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 5px; -fx-border-radius: 10; -fx-border-width: 5px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 10, 0, 0, 0);");
            user_ingredient_form.getChildren().add(pane);
            userIngredientsList.add(ingredient.getIngredientName());                          // add the ingredient to the list
            VBox.setMargin(pane, new Insets(10, 0, 10, 0));

        }



    }



    // show the generated recipe pane
    public void onClickedGenerate() {
        discover_pane.setVisible(false);
        generate_pane.setVisible(true);
        show_recipe_pane.setVisible(false);
        change_password_pane.setVisible(false);
        user_ingredient_form.getChildren().clear();


    }

    // show the discover recipe pane
    public void onClicledDiscover() {
        discover_pane.setVisible(true);
        generate_pane.setVisible(false);
        show_recipe_pane.setVisible(false);
        change_password_pane.setVisible(false);
        loadRecipe();

    }

    // back to the recipe generation pane
    public void backBtnClicked() {
        discover_pane.setVisible(false);
        generate_pane.setVisible(true);
        show_recipe_pane.setVisible(false);
        change_password_pane.setVisible(false);
    }

    // show the change password pane
    public void onClickedChange() {
        change_password_pane.setVisible(true);
        show_recipe_pane.setVisible(false);
        generate_pane.setVisible(false);
        discover_pane.setVisible(false);
    }

    // reset the password
    public void resetPassword() {
        String password = new_password.getText();                      // get the new password
        String confirmPassword = confirm_password.getText();           // get the confirm password
        String oldPassword = current_password.getText();              // get the current password
        Scene scene = welcomeHeader.getScene();
        String username = scene.getUserData().toString();                    // get the username of the current user
        LambdaQueryWrapper<user> queryWrapper = new LambdaQueryWrapper<user>();
        queryWrapper.eq(user::getUsername, username);
        user us = userS.getOne(queryWrapper);                           // get the user object
        if(oldPassword.equals(us.getPassword())){                           // check if the current password is correct
            if(password.equals(confirmPassword)){                           // check if the new password and confirm password are the same
                us.setPassword(password);
                userS.updateById(us);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Success");
                alert.setContentText("Password changed successfully");
                alert.showAndWait();
                change_password_pane.setVisible(false);
                show_recipe_pane.setVisible(false);
                generate_pane.setVisible(false);
                discover_pane.setVisible(true);
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);              // show the error message if the new password and confirm password are not the same
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Passwords do not match");
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);           // show the error message if the current password is incorrect
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Incorrect password");
            alert.showAndWait();
        }

    }
}
