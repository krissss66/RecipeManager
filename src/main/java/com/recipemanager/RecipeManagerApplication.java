package com.recipemanager;
import com.recipemanager.view.MainView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
//---------------------------------------------------------------------------
//
// Recipe Manager Application
// A Spring Boot and JavaFX combined project for managing recipes
//
// Author: Haoming Chen
// Date: 5/2/2023
// Class: met cs622
// Issues: None known
//
// Description:
// This class initializes and launches the Spring Boot application, and sets
// up JavaFX user interface components. It also provides a method for loading
// FXML resources.
//
//---------------------------------------------------------------------------


@SpringBootApplication
@MapperScan("com.recipemanager.mapper")
public class RecipeManagerApplication extends Application {


    private static ApplicationContext appplicationContext;
    ///////////////////////////////////////////////////////////////////
    /// loadFxml (Load FXML resources) ///
    /// Input : FXML path ///
    /// Output: FXMLLoader instance ///
    /// Returns FXMLLoader with the specified FXML path and controller factory ///
    /// ///
    ///////////////////////////////////////////////////////////////////
    public static FXMLLoader loadFxml(String fxmlPath){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(MainView.class.getResource(fxmlPath));
        fxmlLoader.setControllerFactory(appplicationContext::getBean);
        return fxmlLoader;
    }


    public static void main(String[] args) {
        appplicationContext = SpringApplication.run(RecipeManagerApplication.class, args);
        Application.launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(loadFxml("/login.fxml").load()));
        stage.show();

    }
}
