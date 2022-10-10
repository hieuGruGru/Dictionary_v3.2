package sample;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryManagement {

    public static Dictionary dictionary = new Dictionary();

    public static void insert(String target, String meaning) throws IllegalAccessException {
        if (target == null || target.isEmpty()) {
            throw new IllegalArgumentException("Invalid Input");
        }
        String target_n = Normalize.remove(target);
        Trie.TrieNode current = dictionary.trie.root;
        for (int i = 0; i < target_n.length(); i++) {
            int index = target_n.charAt(i) - 'a';
            if (current.children[index] == null) {
                Trie.TrieNode node = new Trie.TrieNode();
                String temp = current.getTarget_Normalize();
                current.children[index] = node;
                current = node;
                current.setTarget_Normalize(temp + target_n.charAt(i));
            } else {
                current = current.children[index];
            }
        }
        current.setMeaning(meaning);
        current.setEndOfWord(true);
        current.setTarget(target);
    }

    public static void insertFromFile(String path) throws IOException, IllegalAccessException { //Load các cặp từ từ file .txt vào mảng các Word
        File filename = new File(path);
        Scanner sc = new Scanner(filename);
        while (sc.hasNextLine()) {
            String currentLine = sc.nextLine();
            int indexOfTab = currentLine.indexOf("\t");
            String word = currentLine.substring(0, indexOfTab);
            String meaning = currentLine.substring(indexOfTab + 1, currentLine.length());
            insert(word, meaning);
        }
        sc.close();
    }

    public static Trie.TrieNode search(String word) throws IllegalAccessException {
        if (word == null || word.isEmpty()) {
            throw new IllegalArgumentException("Invalid or empty string");
        }
        String target_N = Normalize.remove(word);
        Trie.TrieNode current = dictionary.trie.root;
        for (int i = 0; i < target_N.length(); i++) {
            int index = target_N.charAt(i) - 'a';
            if (current.children[index] != null) {
                current = current.children[index];
            } else {
                return null;
            }
        }
        return current;
    }

    public static void exportToFile(String path) throws IOException {
        dictionary.list1.clear();
        dictionary.list3.clear();
        loadToList(dictionary.trie.root, dictionary.list1, 1);
        loadToList(dictionary.trie.root, dictionary.list3, 3);
        BufferedWriter bWrite = new BufferedWriter(new FileWriter(new File(path)));
        for(int i = 0; i < dictionary.list1.size(); i ++) {
            bWrite.write(dictionary.list1.get(i) + "\t");
            bWrite.write(dictionary.list3.get(i) + "\n");
        }
        bWrite.close();
    }

    public static void loadToList(Trie.TrieNode node, ArrayList list, int mode) {
        Trie.TrieNode current = node;
        if (mode == 1) {// Lưu target
            if (current != null) {
                if (current.getEndOfWord()) {
                    list.add(current.getTarget());
                }
                for (int i = 0; i < 26; i++) {
                    if (current.children[i] != null) {
                        loadToList(current.children[i], list, mode);
                    }
                }
            }
        } else {//Lưu meaning
            if (current != null) {
                if (current.getEndOfWord()) {
                    list.add(current.getMeaning());
                }
                for (int i = 0; i < 26; i++) {
                    if (current.children[i] != null) {
                        loadToList(current.children[i], list, 2);
                    }
                }
            }
        }
    }

    public static void delete(String word) throws IllegalAccessException {
        Trie.TrieNode node = search(word);
        node.setEndOfWord(false);
        node.setTarget("");
        node.setMeaning("");
    }

    public static int isExist(String word, String meaning) throws IllegalAccessException {
        Trie.TrieNode current = search(Normalize.remove(word));
        if (current == null) { //Chưa có node
            return 0;
        } else { //Đã có các node đó
            if (!current.getEndOfWord()) {//Nhưng không có nghĩa, node đó không phải kết thúc của từ
                return 1;
            } else {//Node là kết thúc của từ
                if ((current.getEndOfWord()) && (!meaning.equals(current.getMeaning()))) {//meaning khác với nghĩa của từ đã có
                    return 2;
                }
                if ((meaning.equals(current.getMeaning()))) {
                    return 3;
                }
            }
        }
        return -1;
    }

    public static void clear() {
        DictionaryManagement.dictionary = new Dictionary();
    }
}
