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


@SpringBootApplication
@MapperScan("com.recipemanager.mapper")
public class RecipeManagerApplication extends Application {


    public static FXMLLoader loadFxml(String fxmlPath){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(MainView.class.getResource(fxmlPath));
        fxmlLoader.setControllerFactory(appplicationContext::getBean);
        return fxmlLoader;
    }

    private static ApplicationContext appplicationContext;

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
