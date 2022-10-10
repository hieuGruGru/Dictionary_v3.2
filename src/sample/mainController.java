package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

public class mainController {
    Stage stage;
    Scene scene;
    @FXML
    public Pane ParentPane;
    @FXML
    public Pane titlePane;
    @FXML
    ListView<String> listView = new ListView<>();
    @FXML
    TextField SearchText = new TextField();
    @FXML
    Label firstLabel;
    @FXML
    Label secondLabel;
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

    public String srcPath = "";
    public String desPath = "";
    private Trie newTrie = new Trie();
    private double x, y;
    Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);

    public void init(Stage stage, int themeMode, int languageMode) {
        try {
            if(themeMode == 1) {//LightTheme
                ParentPane.getStylesheets().add("stylesheet/mainLightmode.css");
                ParentPane.getStylesheets().remove("stylesheet/mainDarkmode.css");
            } else {
                if (themeMode == 2) {//WarmTheme
                    ParentPane.getStylesheets().add("stylesheet/mainWarmmode.css");
                    ParentPane.getStylesheets().remove("stylesheet/mainLightmode.css");
                } else {
                    if (themeMode == 3) {//DarkTheme
                        ParentPane.getStylesheets().add("stylesheet/mainDarkmode.css");
                        ParentPane.getStylesheets().remove("stylesheet/mainWarmmode.css");
                    }
                }
            }

            if (languageMode == 1) {//E-V
                srcPath = "D:/java/Dictionary/Dictionary_v3.2/src/data/3900w_E-V.txt";
                DictionaryManagement.insertFromFile(srcPath);
                firstLabel.setText("Tiếng Anh");
                secondLabel.setText("Tiếng Việt");
            } else {//V-E
                srcPath = "D:/java/Dictionary/Dictionary_v3.2/src/data/3900w_V-E.txt";
                DictionaryManagement.insertFromFile(srcPath);
                firstLabel.setText("Tiếng Việt");
                secondLabel.setText("Tiếng Anh");
            }
            DictionaryManagement.loadToList(DictionaryManagement.dictionary.trie.root,
                    DictionaryManagement.dictionary.list1, 1);
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
    @FXML
    protected void showAllWord(InputEvent inputEvent) {
        DictionaryManagement.dictionary.list1.clear();
        listView.getItems().clear();
        DictionaryManagement.loadToList(DictionaryManagement.dictionary.trie.root,
                DictionaryManagement.dictionary.list1, 1);
        showList(listView, DictionaryManagement.dictionary.list1);
        VText.clear();
    }

    public void lookUp(ActionEvent event) throws IllegalAccessException {
        if (!SearchText.getText().isEmpty()) {
            listView.getItems().clear();
            DictionaryManagement.dictionary.list2.clear();
            String searchString = SearchText.getText();
            Trie.TrieNode node = DictionaryManagement.search(searchString);
            DictionaryManagement.loadToList(node,
                    DictionaryManagement.dictionary.list2, 1);
            showList(listView, DictionaryManagement.dictionary.list2);
        }
        EText.clear();
        VText.clear();
    }

    public void getMeaning(MouseEvent event) throws IllegalAccessException {
        String word = listView.getSelectionModel().getSelectedItem();
        EText.setText(word);
        VText.setText((DictionaryManagement.search(word)).getMeaning()
        );
        SearchText.clear();
    }

    public void addWord(ActionEvent event) throws IllegalAccessException {
        String word =  EText.getText();
        String meaning = VText.getText();
        if (!word.isEmpty() && !meaning.isEmpty()) {
            if (DictionaryManagement.isExist(word,meaning) == 0
                || DictionaryManagement.isExist(word, meaning) == 1) {
                DictionaryManagement.insert(word, meaning);
                StatusText.setText("Từ '" + EText.getText() + "' đã được thêm");
                EText.clear();
                VText.clear();
            } else {
                StatusText.setText("Từ '" + EText.getText() + "' đã tồn tại");
            }
        } else {
            StatusText.setText("Hãy nhập từ mới và nghĩa để thêm");
        }
    }

    public void modifyWord(ActionEvent event) throws IllegalAccessException {
        if (listView.getSelectionModel().getSelectedItems().size() != 0) {
            String word = EText.getText();
            String meaning = VText.getText();
            if (DictionaryManagement.isExist(word, meaning) == 2) {
                DictionaryManagement.insert(word, meaning);
                StatusText.setText("Đã sửa nghĩa của từ " + word + " thành " + "'" + meaning + "'");
            } else {
                if (DictionaryManagement.isExist(word, meaning) == 3) {
                    StatusText.setText("Nghĩa nó có khác gì đâu mà sửa O_O");
                }
            }
        } else {
            StatusText.setText("Hãy chọn từ để sửa nghĩa");
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
            StatusText.setText("Đã xóa từ '" + word + "' khỏi từ điển");
            EText.clear();
            VText.clear();
        } else {
            StatusText.setText("May cho mày đấy " + word + " ༼ つ ◕_◕ ༽つ");
        }
    }

    public void saveFile(ActionEvent event) throws IOException {
        desPath  = srcPath;
        DictionaryManagement.exportToFile(desPath);
        StatusText.setText("Đã lưu các thay đổi vào file");
    }

    public void reset(ActionEvent event) {
    }

    public void logOut(ActionEvent actionEvent) throws IOException {
        Parent logout = FXMLLoader.load(getClass().getResource("Option.fxml"));
        stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(logout);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
        DictionaryManagement.clear();
    }

    public void showList(ListView listView, ArrayList list) {
        for (int i = 0; i < list.size(); i++) {
            listView.getItems().add(list.get(i));
        }
    }
}
