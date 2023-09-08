package com.recipemanager.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.recipemanager.entity.user;
import com.recipemanager.service.userService;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.io.IOException;
import java.util.regex.Pattern;
import static com.recipemanager.RecipeManagerApplication.loadFxml;
import static com.recipemanager.RecipeManagerApplication.main;

//
// Class: loginController
//
// Description:
//  loginController is a controller class responsible for login and register
//
//
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
    private Button createBtn;

    @FXML
    private Button alreadyHaveBtn;
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

    // handle login button click event and check which page to open
    public void loginButtonClicked() throws IOException {
        String uname = this.username.getText();              // Get the username
        String pword = this.password.getText();               // Get the password
        if(uname.isEmpty()||pword.isEmpty()){                 // Check if the username or password is empty
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter username and password");
            alert.showAndWait();

        }else{
        LambdaQueryWrapper<user > queryWrapper = new LambdaQueryWrapper<user>();
        queryWrapper.eq(user::getUsername, uname);
        user user = userService.getOne(queryWrapper);                  // Get the user from the database
       if(user != null && user.getPassword().equals(pword)){             // Check if the user is not null and the password is correct
           if(user.getUsername().equals("admin")){                    // Check if the user is admin

               openMainView(user,"/main.fxml");                 // Open the admin main page

           }else{
                openMainView(user,"/userMain.fxml");             // Open the user main page
           }
       } else {
        alerLoginError();                          // Alert the user that the login failed
       }
    }
    }
    private void alerLoginError() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Login failed");             // Alert the user that the login failed
        alert.showAndWait();
    }

    // open admin main page or user main page based on the user type
    private void openMainView(user user,String path) throws IOException {


        Stage mainStage = new Stage();
        FXMLLoader loader = loadFxml(path);                   // Load the fxml file

        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        String userN = user.getUsername();
        scene.setUserData(userN);
        if(path.equals("/userMain.fxml")){                       // Check if the user is admin
            mainUserController controller = loader.getController();
            controller.welcomeHeader.setText("Welcome " + user.getUsername());             // Set the welcome header
        }
        mainStage.setScene(scene);
        mainStage.show();
        Window window = username.getScene().getWindow();
        if(window instanceof Stage){
            ((Stage) window).close();
        }
    }

    // handle create button click event and play the slider
    public void createBtnClicked(ActionEvent actionEvent) {
        TranslateTransition slider = new TranslateTransition();              // Create a slider
        if(actionEvent.getSource() == createBtn){                           // Check if the source is create button

            slider.setNode(signUpPane);
            slider.setToX(-300);
            slider.setDuration(Duration.seconds(.5));
            slider.setOnFinished(event -> {                                    // Set the event when the slider is finished
                alreadyHaveBtn.setVisible(true);                          // Set the already have button visible
                createBtn.setVisible(false);                        // Set the create button invisible
            });
            slider.play();                                               // Play the slider
        }else if (actionEvent.getSource() == alreadyHaveBtn){
            slider.setNode(signUpPane);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));
            slider.setOnFinished(event -> {
                alreadyHaveBtn.setVisible(false);
                createBtn.setVisible(true);
            });
            slider.play();
        }

    }


    // handle sign up button click event and check if the user is already registered
    public void ClicedSignupBtn() {

        String username = registerUserName.getText();                   // Get the username from the text field
        String password = regPassword.getText();                       // Get the password from the text field
        String phone = phoneNumber.getText();
        String userGender = gender.getText();
        String userEmail = email.getText();
        String email_Pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";              // Email pattern, check if the email is valid
        String password_Pattern = ".*[A-Z].*";                                // Password pattern, check at least one uppercase letter
        Pattern emailPattern = Pattern.compile(email_Pattern);
        Pattern passwordPattern = Pattern.compile(password_Pattern);
        if(username.isEmpty()||password.isEmpty()||phone.isEmpty()||userGender.isEmpty()||userEmail.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill all the fields");                // Alert the user to fill all the fields
            alert.showAndWait();
        }else{
            if(!emailPattern.matcher(userEmail).matches()){                      // Check if the email is valid based on the pattern
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid email");        // Alert the user to enter a valid email address
                alert.showAndWait();
                return;
            }
            if(!passwordPattern.matcher(password).matches()){                           // Check if the password is valid based on the pattern
                Alert alert = new Alert(Alert.AlertType.ERROR, "Password must contain at least one uppercase letter");        // Alert the user to enter a valid password
                alert.showAndWait();
                return;
            }
            user us = new user();
            us.setUsername(username);                               // Set the username
            us.setPassword(password);
            us.setPhoneNumber(phone);
            us.setGender(userGender);
            us.setEmail(userEmail);

            LambdaQueryWrapper<user > queryWrapper = new LambdaQueryWrapper<user>();
            queryWrapper.eq(user::getUsername, username);
            user user = userService.getOne(queryWrapper);                                  // Get the user from the database based on the username
            if(user != null){                                               // Check if the user is not null
                Alert alert = new Alert(Alert.AlertType.ERROR, "Username already exists");             // Alert the user that the username already exists
                alert.showAndWait();
                return;
            }

            userService.save(us);                                        // Save the user to the database
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "User created successfully");
            alert.showAndWait();
            registerUserName.setText("");                              // Clear the text fields
            regPassword.setText("");
            phoneNumber.setText("");
            gender.setText("");
            email.setText("");
            TranslateTransition slider = new TranslateTransition();
            slider.setNode(signUpPane);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));
            slider.setOnFinished(event -> {
                alreadyHaveBtn.setVisible(false);
                createBtn.setVisible(true);
            });
            slider.play();                         // Play the slider to go back to the login page



        }





    }
}
