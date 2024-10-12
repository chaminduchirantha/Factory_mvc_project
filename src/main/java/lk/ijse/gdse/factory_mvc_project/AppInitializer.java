package lk.ijse.gdse.factory_mvc_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class AppInitializer extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppInitializer.class.getResource("/View/HomePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 998,670);
        stage.setTitle("Garment Factory Management System");

        Image image = new Image(getClass().getResourceAsStream("/image/app_icon.jpg"));
        stage.getIcons().add(image);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}