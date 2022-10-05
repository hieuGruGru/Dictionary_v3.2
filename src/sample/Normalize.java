package sample;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class Normalize {
    public static String remove(String s) {
        s = s.toLowerCase();
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").replace('đ','d').replace('Đ','D').replace('ê','e').replace('Ê','E').replaceAll("\\s+", "");
    }
}