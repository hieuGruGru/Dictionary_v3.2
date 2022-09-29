package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.File;

public class MainWindow extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Image image = new Image(new File("D:\\java\\Dictionary\\Dictionary_v3.2\\src\\resource\\teebee-removebg.png").toURI().toString());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Từ điển Anh - Việt");
        stage.getIcons().add(image);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void run() {
        launch();
    }
}