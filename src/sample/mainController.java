package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class mainController implements Initializable {
    @FXML
    public Pane ParentPane;
    @FXML
    public Pane titlePane;
    @FXML
    ListView<String> listView = new ListView<>();
    @FXML
    TextField SearchText = new TextField();
    @FXML
    TextField EText = new TextField();
    @FXML
    TextField VText = new TextField();
    @FXML
    TextField StatusText = new TextField();
    @FXML
    Button buttonSearch;
    @FXML
    Button buttonAdd;
    @FXML
    Button buttonFix;
    @FXML
    Button buttonDel;
    @FXML
    Button buttonSave;
    @FXML
    Button Speech;
    @FXML
    Button buttonReset;
    @FXML
    ImageView imageMode;

    private Trie newTrie = new Trie();
    private double x, y;
    private double mode = 0;
    Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            DictionaryManagement.insertFromFile("D:/java/Dictionary/Dictionary_v3.2/src/data/dictionaries.txt");
            DictionaryManagement.loadToList(DictionaryManagement.dictionary.trie.root,DictionaryManagement.dictionary.list1);
            showList(listView, DictionaryManagement.dictionary.list1);
        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
        }
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

    public void switchTheme(ActionEvent actionEvent) throws IOException {
        if(mode == 0) {
            ParentPane.getStylesheets().add("stylesheet/mainLightmode.css");
            ParentPane.getStylesheets().remove("stylesheet/mainDarkmode.css");
            Image lightmode = new Image("resource/lightmode.png");
            imageMode.setImage(lightmode);
            mode = 1;
        } else {
            if (mode == 1) {
                ParentPane.getStylesheets().add("stylesheet/mainWarmmode.css");
                ParentPane.getStylesheets().remove("stylesheet/mainLightmode.css");
                Image warmmode = new Image("resource/warmmode.png");
                imageMode.setImage(warmmode);
                mode = 2;
            } else {
                ParentPane.getStylesheets().add("stylesheet/mainDarkmode.css");
                ParentPane.getStylesheets().remove("stylesheet/mainWarmmode.css");
                Image darkmode = new Image("resource/darkmode.png");
                imageMode.setImage(darkmode);
                mode = 0;
            }
        }
    }

    @FXML
    protected void showAllWord(InputEvent inputEvent) {
        DictionaryManagement.dictionary.list1.clear();
        listView.getItems().clear();
        DictionaryManagement.loadToList(DictionaryManagement.dictionary.trie.root,DictionaryManagement.dictionary.list1);
        showList(listView, DictionaryManagement.dictionary.list1);
    }


    public void lookUp(ActionEvent event) throws IllegalAccessException {
        if (!SearchText.getText().isEmpty()) {
            listView.getItems().clear();
            DictionaryManagement.dictionary.list2.clear();
            String EString = SearchText.getText();
            Trie.TrieNode node = DictionaryManagement.search(EString);
            DictionaryManagement.loadToList(node, DictionaryManagement.dictionary.list2);
            showList(listView, DictionaryManagement.dictionary.list2);
        }
        EText.clear();
        VText.clear();
    }

    public void getMeaning(MouseEvent event) throws IllegalAccessException {
        String word = listView.getSelectionModel().getSelectedItem();
        EText.setText(word);
        VText.setText((DictionaryManagement.search(word)).getMeaning());
        StatusText.clear();
    }

    public void addWord(ActionEvent event) throws IllegalAccessException {
        String word = EText.getText();
        String meaning = VText.getText();
        if (DictionaryManagement.isExist(word,meaning) == 1) {
            DictionaryManagement.insert(word, meaning);
            StatusText.setText("Đã thêm từ mới " + word + " vào từ điển.");
        }
        EText.clear();
        VText.clear();
    }

    public void modifyWord(ActionEvent event) throws IllegalAccessException {
        String word = EText.getText();
        String meaning = VText.getText();
        if (DictionaryManagement.isExist(word, meaning) == 2) {
            DictionaryManagement.insert(word, meaning);
            StatusText.setText("Đã thêm nghĩa mới của từ " + word + " vào từ điển.");
        }
    }

    public void pronounce_E(ActionEvent event) {
        String textPronounce1 = EText.getText();
        Audio.Text_Speech(textPronounce1);
    }

    public void deletelWord(ActionEvent actionEvent) throws IllegalAccessException {
        String word = listView.getSelectionModel().getSelectedItem();
        int index = listView.getSelectionModel().getSelectedIndex();
        confirmAlert.setTitle("Wait a minute");
        confirmAlert.setHeaderText("Bạn có chắc muốn xóa từ " + word + " khỏi từ điển?");
        confirmAlert.setContentText("");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.get() == ButtonType.OK) {
            listView.getItems().remove(index);
            listView.refresh();
            DictionaryManagement.delete(word);
            StatusText.setText("Đã xóa từ " + word + " khỏi từ điển");
            EText.clear();
            VText.clear();
        } else {
            StatusText.setText("May cho mày đấy " + word + " ༼ つ ◕_◕ ༽つ");
        }
    }

    public void saveFile(ActionEvent event) throws FileNotFoundException, UnsupportedEncodingException {
    }

    public void reset(ActionEvent event) {
    }

    public void showList(ListView listView, ArrayList list) {
        for (int i = 0; i < list.size(); i++) {
            listView.getItems().add(list.get(i));
        }
    }

}