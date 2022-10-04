package sample;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryManagement {

    public static Dictionary dictionary = new Dictionary();

    public static void insert(String word, String meaning) throws IllegalAccessException {
        if (word == null || word.isEmpty()) {
            throw new IllegalArgumentException("Invalid Input");
        }
        word = word.toLowerCase();
        Trie.TrieNode current = dictionary.trie.root;
        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';
            if (current.children[index] == null) {
                String thisWord = current.word;
                Trie.TrieNode node = new Trie.TrieNode();
                current.children[index] = node;
                current = node;
                current.setWord(thisWord + word.charAt(i));
            } else {
                current = current.children[index];
            }
        }
        if(isExist(word,meaning) == 1) {//Nếu là từ mới, chưa có nghĩa isExist = 1
            current.meaning = meaning;
        } else {
            if (isExist(word,meaning) == 2) {//Nếu từ đã có nghĩa, thêm nghĩa khác isExist = 2
                StringBuilder str = new StringBuilder();
                str = str.append(current.meaning + ", " + meaning);
                current.meaning = str.toString();
            } else {
                current.meaning = meaning;
            }
        }
        current.endOfWord = true;
    }

     public static void modifyWord(String word, String meaning) {

     }

    public static void insertFromFile(String path) throws IOException, IllegalAccessException { //Load các cặp từ từ file .txt vào mảng các Word
        File filename = new File(path);
        Scanner sc = new Scanner(filename);
        while(sc.hasNextLine()){
            String currentLine = sc.nextLine();
            int indexOfTab = currentLine.indexOf("\t");
            String word = currentLine.substring(0, indexOfTab);
            String meaning = currentLine.substring(indexOfTab + 1, currentLine.length());
            insert(word, meaning);
        }
        sc.close();
    }

    public static Trie.TrieNode search(String word) throws IllegalAccessException {
        if((word == null || word.isEmpty()) && (isExist(word, "") != 0)) {
            throw new IllegalArgumentException("Invalid or empty string");
        }
        word = word.toLowerCase();
        Trie.TrieNode current = dictionary.trie.root;
        for(int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';
            if(current.children[index] != null) {
                current = current.children[index];
            }
        }
        return current;
    }

    public static void loadToList(Trie.TrieNode node, ArrayList list) {
        Trie.TrieNode current = node;
        if (current != null) {
            if (current.endOfWord) {
                list.add(current.getWord());
            }
            for (int i = 0; i < 26; i++) {
                if (current.children[i] != null) {
                    loadToList(current.children[i], list);
                }
            }
        }
    }

    public static void delete(String word) throws IllegalAccessException {
        Trie.TrieNode node = search(word);
        node.setEndOfWord(false);
        node.setMeaning("");
    }

    public static int isExist(String word, String meaning) throws IllegalAccessException {
        Trie.TrieNode current = search(word);
        if (current == null) { //Chưa có node
            return 0;
        } else { //Đã có các node đó
            if (!current.endOfWord) {//Nhưng không có nghĩa, node đó không phải kết thúc của từ
                return 1;
            } else {//Node là kết thúc của từ
                if ((current.endOfWord) && (meaning != current.meaning)) {//meaning khác với nghĩa của từ đã có
                    return 2;
                }
                return 3;//meaning giống với nghĩa đã có của từ
            }
        }
    }

    public static void clear() {
        DictionaryManagement.dictionary = new Dictionary();
    }
}
