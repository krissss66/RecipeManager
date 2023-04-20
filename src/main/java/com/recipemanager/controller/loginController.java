package com.recipemanager.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.recipemanager.entity.user;
import com.recipemanager.service.LoginService;
import com.recipemanager.service.userService;
import com.recipemanager.view.MainView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

import static com.recipemanager.RecipeManagerApplication.loadFxml;


@Controller
public class loginController {

    @Autowired
    private userService userService;

    public TextField username;
    public PasswordField password;
    @FXML
    private AnchorPane signUpPane;
    @FXML
    private AnchorPane registerForm;
    @FXML
    private AnchorPane loginForm;
    @FXML
    private TextField registerUserName;
    @FXML
    private PasswordField regPassword;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField gender;
    @FXML
    private TextField email;


    public void loginButtonClicked() throws IOException {
        String uname = this.username.getText();
        String pword = this.password.getText();
        LambdaQueryWrapper<user> queryWrapper = new LambdaQueryWrapper<user>();
        queryWrapper.eq(user::getUsername, uname);
        user user = userService.getOne(queryWrapper);
       if(user != null && user.getPassword().equals(pword)){
        openMainView();
       } else {
        alerLoginError();
       }




    }
    private void alerLoginError() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Login failed");
        alert.showAndWait();
    }

    private void openMainView() throws IOException {
        Stage mainStage = new Stage();
        mainStage.setScene(new Scene(loadFxml("/main.fxml").load()));
        mainStage.show();
        Window window = username.getScene().getWindow();
        if(window instanceof Stage){
            ((Stage) window).close();
        }

    }
    public void createBtnClicked() {
        loginForm.setVisible(false);
        signUpPane.setVisible(false);
        registerForm.setVisible(true);

    }

    public void ClicedSignupBtn() {
        String username = registerUserName.getText();
        String password = regPassword.getText();
        String phone = phoneNumber.getText();
        String userGender = gender.getText();
        String userEmail = email.getText();
        user user = new user();
        user.setUsername(username);
        user.setPassword(password);
        user.setPhoneNumber(phone);
        user.setGender(userGender);
        user.setEmail(userEmail);
        userService.save(user);
        System.out.println("User created successfully");
    }
}
