package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private double x,y;
    private int mode = 0;
    @FXML
    Pane titledPane;
    @FXML
    Pane ParentPane;
    @FXML
    Pane contentArea;
    @FXML
    Button logIn;
    @FXML
    Button theme1;
    @FXML
    Button theme2;
    @FXML
    Button logOut;
    @FXML
    Button next;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void draged(MouseEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }
    @FXML
    public void pressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }
    @FXML
    public void close(MouseEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
    @FXML
    public void minimize(MouseEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void logIn(ActionEvent actionEvent) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("mainSample.fxml"));
        stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(login);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void logOut(ActionEvent actionEvent) throws IOException {
        Parent logout = FXMLLoader.load(getClass().getResource("Login.fxml"));
        stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(logout);
        stage.setScene(scene);
        stage.show();
    }

    public void Next(ActionEvent actionEvent) throws IOException {
        Parent next = FXMLLoader.load(getClass().getResource("mainSample.fxml"));
        stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(next);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void switchTheme1(ActionEvent actionEvent) throws IOException {
        ParentPane.getStylesheets().add("stylesheet/darkmode.css");
        ParentPane.getStylesheets().remove("stylesheet/lightmode.css");
    }

    public void switchTheme2(ActionEvent actionEvent) throws IOException {
        ParentPane.getStylesheets().add("stylesheet/lightmode.css");
        ParentPane.getStylesheets().remove("stylesheet/darkmode.css");
    }

}
