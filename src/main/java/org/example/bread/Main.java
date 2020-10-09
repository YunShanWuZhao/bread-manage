package org.example.bread;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.bread.persist.SqlConnection;

import java.net.URL;

public class Main extends Application {

    public void start(Stage primaryStage) throws Exception {
        SqlConnection.buildSqlSessionFactory();
        URL location = getClass().getResource("/fxml/order.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 900, 600);
        //scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        //scene.setFill(null);
        primaryStage.setScene(scene);
        //primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
