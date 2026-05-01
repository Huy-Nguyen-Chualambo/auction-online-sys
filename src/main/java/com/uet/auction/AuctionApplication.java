package com.uet.auction;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class AuctionApplication extends Application {

    private static ConfigurableApplicationContext springContext;

    public static void main(String[] args) {
        // System.setProperty("java.awt.headless", "false"); // Not needed if we use JavaFX only
        launch(args);
    }

    @Override
    public void init() {
        springContext = SpringApplication.run(AuctionApplication.class);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/uet/auction/view/login.fxml"));
        // Set controller factory to use Spring beans
        fxmlLoader.setControllerFactory(springContext::getBean);
        
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Hệ thống Đấu giá Trực tuyến - UET");
        stage.setScene(scene);
        stage.show();
    }

    public static void showLoginWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(AuctionApplication.class.getResource("/com/uet/auction/view/login.fxml"));
            fxmlLoader.setControllerFactory(springContext::getBean);
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Hệ thống Đấu giá Trực tuyến - UET (New Window)");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        springContext.close();
        Platform.exit();
    }
    
    public static ConfigurableApplicationContext getSpringContext() {
        return springContext;
    }
}