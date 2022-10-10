package sample;

public class Trie {

    public static class TrieNode{
        TrieNode[] children;
        boolean endOfWord;
        String target;
        String target_Normalize;
        String meaning;
        public TrieNode() {
            this.children = new TrieNode[26];
            this.endOfWord = false;
            this.target = "";
            this.meaning = "";
            this.target_Normalize = "";
            for(int i= 0; i < 26; i++) {
                this.children[i] = null;
            }
        }
        public String getTarget() {
            return target;
        }
        public String getMeaning() {
            return meaning;
        }
        public String getTarget_Normalize() {
            return target_Normalize;
        }
        public boolean getEndOfWord() {
            return endOfWord;
        }
        public void setMeaning(String meaning) {
            this.meaning = meaning;
        }
        public void setTarget(String target) {
            this.target = target;
        }
        public void setTarget_Normalize(String target_Normalize) {
            this.target_Normalize = target_Normalize;
        }
        public void setEndOfWord(boolean endOfWord) {
            this.endOfWord = endOfWord;
        }
    }

    TrieNode root;
    public Trie() {
        root = new TrieNode();
    }
}