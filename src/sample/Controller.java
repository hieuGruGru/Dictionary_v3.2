package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;

public class Controller {
    private static Stage stage;
    private static Scene scene;
    private double x,y;
    Mode themeMode = new Mode(0);
    Mode languageMode = new Mode(1);
    @FXML
    Pane titledPane;
    @FXML
    Pane ParentPane;
    @FXML
    Pane contentArea;
    @FXML
    Button logIn;
    @FXML
    Button logOut;
    @FXML
    Button next;
    @FXML
    TextField usernameField = new TextField();
    @FXML
    TextField notification = new TextField();
    @FXML
    PasswordField passwordField;
    @FXML
    ImageView ImageMode;
    @FXML
    Label languageLabel;
    @FXML
    Label voiceLabel;




    public void init(Stage stage) {

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

    public void login(ActionEvent actionEvent) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        int flag = ConnectToSQLServer.login(username, password);
        if (flag == 2) {
            Parent login = FXMLLoader.load(getClass().getResource("Option.fxml"));
            stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(login);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
        } else {
            if (flag == 1) {
                notification.setText("M???t kh???u kh??ng ch??nh x??c");
            } else {
                notification.setText("T??n ????ng nh???p kh??ng ch??nh x??c");
            }
        }
        usernameField.clear();
        passwordField.clear();
    }

    public void logOut(ActionEvent actionEvent) throws IOException {
        Parent logout = FXMLLoader.load(getClass().getResource("Login.fxml"));
        stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(logout);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void Next(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainSample.fxml"));
        Parent mainNode = loader.load();
        mainController mainController = loader.getController();
        stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(mainNode);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        mainController.init(stage, themeMode.mode, languageMode.mode);
        stage.show();
    }
    public void setMode(int mod3, Mode mode) {
        mode.mode = mod3;
    }

    public void EToV(ActionEvent actionEvent) {
        setMode(1, languageMode);
        languageLabel.setText("Ti???ng Anh - Ti???ng Vi???t");
    }

    public void VToE(ActionEvent actionEvent){
        setMode(2, languageMode);
        languageLabel.setText("Ti???ng Vi???t - Ti???ng Anh");
    }

    public void switchLightTheme(ActionEvent actionEvent) throws IOException {
        ParentPane.getStylesheets().add("stylesheet/lightmode.css");
        ParentPane.getStylesheets().remove("stylesheet/darkmode.css");
        ParentPane.getStylesheets().remove("stylesheet/warmmode.css");
        Image lightmode = new Image("resource/Option_lighttheme.png");
        ImageMode.setImage(lightmode);
        setMode(1, themeMode);
    }

    public void switchWarmTheme(ActionEvent actionEvent) throws IOException {
        ParentPane.getStylesheets().add("stylesheet/warmmode.css");
        ParentPane.getStylesheets().remove("stylesheet/lightmode.css");
        ParentPane.getStylesheets().remove("stylesheet/darkmode.css");
        Image warmmode = new Image("resource/Option_warmtheme.png");
        ImageMode.setImage(warmmode);
        setMode(2, themeMode);
    }

    public void switchDarkTheme(ActionEvent actionEvent) throws IOException {
        ParentPane.getStylesheets().add("stylesheet/darkmode.css");
        ParentPane.getStylesheets().remove("stylesheet/lightmode.css");
        ParentPane.getStylesheets().remove("stylesheet/warmmode.css");
        Image darkmode = new Image("resource/Option_darktheme.png");
        ImageMode.setImage(darkmode);
        setMode(3, themeMode);
    }

    public void changeVoice1(ActionEvent actionEvent) {
        voiceLabel.setText("Gi???ng ?????c 1");
    }

    public void changeVoice2(ActionEvent actionEvent) {
        voiceLabel.setText("Gi???ng ?????c 2");
    }
}
