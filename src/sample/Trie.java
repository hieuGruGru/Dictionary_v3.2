package sample;

public class Trie {

    public static class TrieNode{
        TrieNode[] children;
        boolean endOfWord;
        String meaning;
        String word;
        public TrieNode() {
            this.children = new TrieNode[26];
            this.endOfWord = false;
            this.meaning = "";
            this.word = "";
            for(int i= 0; i < 26; i++) {
                this.children[i] = null;
            }
        }
        public String getMeaning() {
            return meaning;
        }
        public String getWord() {
            return word;
        }
        public void setWord(String word) {
            this.word = word;
        }
        public void setEndOfWord(boolean endOfWord) {
            this.endOfWord = endOfWord;
        }
        public void setMeaning(String meaning) {
            this.meaning = meaning;
        }
    }

    TrieNode root;
    public Trie() {
        root = new TrieNode();
    }


}