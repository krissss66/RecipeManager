package com.recipemanager.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.recipemanager.entity.*;
import com.recipemanager.service.ingredientService;
import com.recipemanager.service.recipeService;
import com.recipemanager.service.userService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import javafx.stage.Stage;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static com.recipemanager.RecipeManagerApplication.loadFxml;

//
// Class: mainController
//
// Description:
// mainController is a controller class responsible for administrator's main page, which includes adding, deleting, updating users
// ingredients and recipes.
//

@Controller
public class mainController {
    public TextField recipe_name_txt;
    public TextField recipe_description_txt;
    public TextArea recipe_instruction_txt;
    public TextField recipe_category_txt;


    @FXML
    private Button logout_btn;
    @FXML
    private AnchorPane main_form;
    @FXML
    private TableColumn<recipe, Long> recipe_ID;
    @FXML
    private Button recipe_addBtn;
    @FXML
    private Button recipe_btn;
    @FXML
    private TableColumn<recipe, Long> recipe_category;
    @FXML
    private Button recipe_deleteBtn;
    @FXML
    private TableColumn<recipe, String> recipe_description;
    @FXML
    private AnchorPane recipe_form;
    @FXML
    private AnchorPane user_form;
    @FXML
    private ImageView recipe_imageView;
    @FXML
    private Button recipe_importBtn;
    @FXML
    private TableColumn<recipe, String> recipe_ingredient;
    @FXML
    private TableColumn<recipe, String> recipe_insctruction;
    @FXML
    private TableColumn<recipe, String> recipe_name;
    @FXML
    private TableView<recipe> recipe_tableview;
    @FXML
    private TableView<ingredients> ingredients_tableview;
    @FXML
    private TableColumn<ingredients, Long> ingredients_id_txt;
    @FXML
    private TableColumn<ingredients, String> ingredients_name_txt;

    @FXML
    private TextField ingredients_insert_name;

    @FXML
    private TextField username_insert;
    @FXML
    private TextField password_insert;
    @FXML
    private TextField email_insert;
    @FXML
    private TextField phoneNumber_insert;
    @FXML
    private TextField gender_insert;

    public TableColumn<user, String> username_txt;
    public TableColumn<user, String> password_txt;
    public TableColumn<user, String> email_txt;
    public TableColumn<user, String> phonenumber_txt;
    public TableColumn<user, String> gender_txt;
    @FXML
    private TableView<user> user_tableview;
    @FXML
    private Button recipe_updateBtn;
    @FXML
    private ComboBox<String> ingredients_insert;
    private Image image;

    @FXML
    private AnchorPane ingredient_form;

    @Qualifier("userServiceImpl")
    @Autowired
    private userService userS;
    @Qualifier("recipeServiceImpl")
    @Autowired
    private recipeService recipeS;

    @Qualifier("ingredientServiceImpl")
    @Autowired
    private ingredientService ingredientS;


    private String selectedRecipeName;

    private String selectedIngredientName;

    private String selectedUsername;

    public mainController() {
    }


    //show ingredients in the combo box of recipe organization page
    public void ingredientList(){
        List<String> ingredientL = new ArrayList<>();
        List<ingredients> userBeanList = ingredientS.list();
        userBeanList.forEach(ingredient -> ingredientL.add(ingredient.getIngredientName()));          //get all the ingredients from the database

        ObservableList ingredientList = FXCollections.observableArrayList(ingredientL);
        ingredients_insert.setItems(ingredientList);
    }



    public ObservableList<recipe> getRecipeList(){
        ObservableList<recipe> recipeList = FXCollections.observableArrayList();
        recipeList.addAll(recipeS.list());
        return recipeList;
    }

    public ObservableList<ingredients> getIngredientList(){
        ObservableList<ingredients> ingList = FXCollections.observableArrayList();
        ingList.addAll(ingredientS.list());
        return ingList;
    }

    public ObservableList<user> getUserList(){
        ObservableList<user> userList = FXCollections.observableArrayList();
        userList.addAll(userS.list());
        return userList;
    }

    //show user data in the table view
    public void showUserData(){
        ObservableList<user> userBeanList = getUserList();
        username_txt.setCellValueFactory(new PropertyValueFactory<>("username"));
        password_txt.setCellValueFactory(new PropertyValueFactory<>("password"));
        email_txt.setCellValueFactory(new PropertyValueFactory<>("email"));
        gender_txt.setCellValueFactory(new PropertyValueFactory<>("gender"));
        phonenumber_txt.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        user_tableview.setItems(userBeanList);
    }

    //show ingredient data in the table view
    public void showIngredientData(){
        ObservableList<ingredients> ingList = getIngredientList();
        ingredients_id_txt.setCellValueFactory(new PropertyValueFactory<>("ingredientId"));
        ingredients_name_txt.setCellValueFactory(new PropertyValueFactory<>("ingredientName"));
        ingredients_tableview.setItems(ingList);
    }
    //show recipe data in the table view
    public void showRecipeData(){
        ObservableList<recipe> userBeanList = getRecipeList();
        ObservableList<ingredients> ingList = getIngredientList();
        recipe_ID.setCellValueFactory(new PropertyValueFactory<>("recipeId"));
        recipe_name.setCellValueFactory(new PropertyValueFactory<>("recipeName"));
        recipe_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        recipe_insctruction.setCellValueFactory(new PropertyValueFactory<>("instruction"));
        recipe_category.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        recipe_ingredient.setCellValueFactory(new PropertyValueFactory<>("ingredients"));
        recipe_tableview.setItems(userBeanList);
        ingredientList();
    }


    //initialize the page
    public void initialize() {
        showRecipeData();
        showIngredientData();
        showUserData();
    }


    //log out and back to login page
    @FXML
    private void logout() {
        try {
            logout_btn.getScene().getWindow().hide();
            Stage stage = new Stage();
            stage.setScene(new Scene(loadFxml("/login.fxml").load()));
            stage.show();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

      //add a new ingredient in recipe
    public void addIngredients() throws IOException {

        String ingre =ingredients_insert.getSelectionModel().getSelectedItem();              //get the selected ingredient from the combo box
        if(ingre.isEmpty()){                                                        //if the ingredient is empty, show error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please select an ingredient");
            alert.showAndWait();
        }else if(recipe_tableview.getSelectionModel().getSelectedItem().equals(null)){                      //if the recipe is empty, show error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please select a recipe");
            alert.showAndWait();
        }else{
            LambdaQueryWrapper<ingredients> queryWrapper = new LambdaQueryWrapper<ingredients>();
            queryWrapper.eq(ingredients::getIngredientName, ingre);
            ingredients ing = ingredientS.getOne(queryWrapper);
            recipe recipe = recipe_tableview.getSelectionModel().getSelectedItem();                            //get the selected recipe from the table view
            String ingreList = recipe.getIngredients();                                          //get the ingredients of the selected recipe
            if(ingreList == null){
                recipe.setIngredients(ing.getIngredientName());                                  //if the ingredient list is empty, add the ingredient to the list directly
            }else{
                recipe.setIngredients(ing.getIngredientName()+" "+recipe.getIngredients());            //if the ingredient list is not empty, add the ingredient to the list with a space
            }
            recipeS.updateById(recipe);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Ingredient added to "+recipe.getRecipeName());                   //show success message
            alert.showAndWait();
            showRecipeData();



        }

    }

    // add a new recipe
    public void addRecipe() throws IOException {

        if(recipe_name_txt.getText().isEmpty()||recipe_description_txt.getText().isEmpty()||recipe_instruction_txt.getText().isEmpty()||recipe_category_txt.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill all fields");                //if any field is empty, show error message
            alert.showAndWait();
        }else{
            recipe recipe = new recipe();
            recipe.setRecipeName(recipe_name_txt.getText());                       //get the input data from the text field
            recipe.setDescription(recipe_description_txt.getText());
            recipe.setInstruction(recipe_instruction_txt.getText());
            recipe.setCategoryId(Long.parseLong(recipe_category_txt.getText()));
            BufferedImage bImage = SwingFXUtils.fromFXImage(recipe_imageView.getImage(), null);
            ByteArrayOutputStream s = new ByteArrayOutputStream();
            ImageIO.write(bImage, "png", s);
            byte[] res  = s.toByteArray();
            s.close();
            recipe.setImage(res);
            recipeS.save(recipe);                                        //save the recipe
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Recipe added successfully");                 //show success message
            alert.showAndWait();
            showRecipeData();
            clearContext();


        }
    }

    // select a recipe and show the data in the text field
    public void selectedData() throws IOException {
        recipe recipeData = recipe_tableview.getSelectionModel().getSelectedItem();                    //get the selected recipe from the table view
        int index = recipe_tableview.getSelectionModel().getSelectedIndex();                      //get the index of the selected recipe
        if(index <= -1){                                                         //if the index is -1, return
            return;
        }
        recipe_name_txt.setText(recipeData.getRecipeName());                               //set the data to the text field
        recipe_description_txt.setText(recipeData.getDescription());
        recipe_instruction_txt.setText(recipeData.getInstruction());
        recipe_category_txt.setText(String.valueOf(recipeData.getCategoryId()));
        byte[] imageByte = recipeData.getImage();                                           //get the image from the database
        ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
        BufferedImage bImage2 = ImageIO.read(bis);
        Image image = SwingFXUtils.toFXImage(bImage2, null);
        recipe_imageView.setImage(image);
        selectedRecipeName = recipeData.getRecipeName();
    }

    // select a ingredient and show the data in the text field
    public void selectedIngredient() throws IOException {
        ingredients ingData = ingredients_tableview.getSelectionModel().getSelectedItem();
        int index = ingredients_tableview.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }
        ingredients_insert_name.setText(ingData.getIngredientName());
        selectedIngredientName = ingData.getIngredientName();
    }
    //select a user and show the data in the text field
    public void selectedUser() throws IOException {
        user userData = user_tableview.getSelectionModel().getSelectedItem();
        int index = user_tableview.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }
        username_insert.setText(userData.getUsername());
        email_insert.setText(userData.getEmail());
        password_insert.setText(userData.getPassword());
        gender_insert.setText(userData.getGender());
        phoneNumber_insert.setText(userData.getPhoneNumber());
        selectedUsername = userData.getUsername();

    }


    //import image from local file
    public void importImg(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();                //create a file chooser
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("open image file","*.jpg","*.png"));          //set the file type
        File file = fileChooser.showOpenDialog(main_form.getScene().getWindow());            //open the file chooser
        if(file != null){
            image = new Image(file.toURI().toString(),191,122,false,true);           //get the image from the file
            recipe_imageView.setImage(image);                     //set the image to the image view

        }
    }

    // clear the text field and image view of recipe
    public void clearContext() throws IOException {
        recipe_name_txt.clear();
        recipe_description_txt.clear();
        recipe_instruction_txt.clear();
        recipe_category_txt.clear();
        File file = ResourceUtils.getFile("classpath:default-image-300x169.png");
        Image image = new Image(file.toURI().toString(),191,122,false,true);
        recipe_imageView.setImage(image);

    }

    public void clearIngredientContext() throws IOException {
        ingredients_insert_name.clear();
    }

    //update the recipe data
    public void updateRecipe() throws IOException {

        if(recipe_name_txt.getText().isEmpty()||recipe_description_txt.getText().isEmpty()||recipe_instruction_txt.getText().isEmpty()||recipe_category_txt.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);                  //if any field is empty, show error message
            alert.setHeaderText(null);
            alert.setContentText("Please fill all fields");
            alert.showAndWait();
        }else{
            String rName = selectedRecipeName;
            LambdaQueryWrapper<recipe> queryWrapper = new LambdaQueryWrapper<recipe>();
            queryWrapper.eq(recipe::getRecipeName, rName);
            recipe rec = recipeS.getOne(queryWrapper);                                //get the selected recipe
            recipe recipe = new recipe();
            recipe.setRecipeId(rec.getRecipeId());                          //set the data to the recipe object
            recipe.setRecipeName(recipe_name_txt.getText());
            recipe.setDescription(recipe_description_txt.getText());
            recipe.setInstruction(recipe_instruction_txt.getText());
            recipe.setCategoryId(Long.parseLong(recipe_category_txt.getText()));
            BufferedImage bImage = SwingFXUtils.fromFXImage(recipe_imageView.getImage(), null);
            ByteArrayOutputStream s = new ByteArrayOutputStream();
            ImageIO.write(bImage, "png", s);
            byte[] res  = s.toByteArray();
            s.close();
            recipe.setImage(res);
            recipeS.updateById(recipe);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Recipe updated successfully");
            alert.showAndWait();
            showRecipeData();                              //show the updated data in the table view
            clearContext();                                //clear the text field and image view
        }

    }

    //delete the recipe data
    public void deleteRecipe() throws IOException {

        if(recipe_name_txt.getText().isEmpty()||recipe_description_txt.getText().isEmpty()||recipe_instruction_txt.getText().isEmpty()||recipe_category_txt.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please select a recipe");              //if no recipe is selected, show error message
            alert.showAndWait();
        }else{
            String rName = selectedRecipeName;
            LambdaQueryWrapper<recipe> queryWrapper = new LambdaQueryWrapper<recipe>();
            queryWrapper.eq(recipe::getRecipeName, rName);
            recipe rec = recipeS.getOne(queryWrapper);
            recipeS.removeById(rec.getRecipeId());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Recipe deleted successfully");
            alert.showAndWait();
            showRecipeData();                                     //delete the recipe and show the recipe data again
            clearContext();                                         //clear the text field and image view
        }
    }

    //show recipe data page
    public void onClickedRecipeBtn() {
        recipe_form.setVisible(true);
        ingredient_form.setVisible(false);
        user_form.setVisible(false);
        showRecipeData();
    }
    //show ingredients data page
    public void onClickedIngredientBtn() {
        ingredient_form.setVisible(true);
        recipe_form.setVisible(false);
        user_form.setVisible(false);
        showIngredientData();

    }
    //add ingredient data
    public void addIngredient() throws IOException {
        if(ingredients_name_txt.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill all fields");        //if any field is empty, show error message
            alert.showAndWait();
        }else{
            ingredients ingredient = new ingredients();
            ingredient.setIngredientName(ingredients_insert_name.getText());          //set the data to the ingredient object
            ingredientS.save(ingredient);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Ingredient added successfully");
            alert.showAndWait();
            showIngredientData();
            clearIngredientContext();
        }

    }
    //delete ingredient data
    public void deleteIngredient() throws IOException {
         if(ingredients_insert_name.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please select an ingredient");                  //if no ingredient is selected, show error message
            alert.showAndWait();
        }else{
            String iName = selectedIngredientName;
            LambdaQueryWrapper<ingredients> queryWrapper = new LambdaQueryWrapper<ingredients>();
            queryWrapper.eq(ingredients::getIngredientName, iName);
            ingredients ing = ingredientS.getOne(queryWrapper);                          //get the selected ingredient
            ingredientS.removeById(ing.getIngredientId());                              //delete the ingredient
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Ingredient deleted successfully");
            alert.showAndWait();
            showIngredientData();
            clearIngredientContext();
        }
    }
    //add user data
    public void addUser() {
        if(username_insert.getText().isEmpty() || password_insert.getText().isEmpty() || phoneNumber_insert.getText().isEmpty()|| email_insert.getText().isEmpty()|| email_insert.getText().isEmpty()|| gender_insert.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill all fields");
            alert.showAndWait();
        }else{
            user user = new user();
            user.setUsername(username_insert.getText());             //set the data to the user object
            user.setPassword(password_insert.getText());
            user.setPhoneNumber(phoneNumber_insert.getText());
            user.setEmail(email_insert.getText());
            user.setGender(gender_insert.getText());
            userS.save(user);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("User added successfully");
            alert.showAndWait();
            showUserData();
            clearUserContext();

        }

    }
    //delete user data
    public void deleteUser() {
        if(username_insert.getText().isEmpty() || password_insert.getText().isEmpty() || phoneNumber_insert.getText().isEmpty()|| email_insert.getText().isEmpty()|| email_insert.getText().isEmpty()|| gender_insert.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill all fields");
            alert.showAndWait();
        }else{
            String uName = selectedUsername;
            LambdaQueryWrapper<user> queryWrapper = new LambdaQueryWrapper<user>();
            queryWrapper.eq(user::getUsername, uName);
            user us = userS.getOne(queryWrapper);
            userS.removeById(us.getId());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("User deleted successfully");
            alert.showAndWait();
            showUserData();                                   //show user data page after deleting
            clearUserContext();
        }

    }
    //update user data
    public void updateUser() {
        if(username_insert.getText().isEmpty() || password_insert.getText().isEmpty() || phoneNumber_insert.getText().isEmpty()|| email_insert.getText().isEmpty()|| email_insert.getText().isEmpty()|| gender_insert.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill all fields");
            alert.showAndWait();
        }else{
            String uName = selectedUsername;
            LambdaQueryWrapper<user> queryWrapper = new LambdaQueryWrapper<user>();
            queryWrapper.eq(user::getUsername, uName);
            user us = userS.getOne(queryWrapper);                         //get the selected user
            us.setUsername(username_insert.getText());                   //set the data to the user object
            us.setPassword(password_insert.getText());
            us.setPhoneNumber(phoneNumber_insert.getText());
            us.setGender(gender_insert.getText());
            us.setEmail(email_insert.getText());
            userS.updateById(us);                                            //update the user
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("User updated successfully");
            alert.showAndWait();
            showUserData();                                          //show the updated data in the table
            clearUserContext();                                     //clear the fields
        }

    }

    public void clearUserContext() {
        username_insert.clear();
        password_insert.clear();
        email_insert.clear();
        gender_insert.clear();
        phoneNumber_insert.clear();

    }

    public void onCickedUser() {
        user_form.setVisible(true);
        recipe_form.setVisible(false);
        ingredient_form.setVisible(false);
        showUserData();
    }
}
