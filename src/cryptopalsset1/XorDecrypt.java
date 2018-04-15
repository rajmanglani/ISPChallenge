/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptopalsset1;

/**
 *
 * @author raj
 */
public class XorDecrypt {
    
    private double score;
    private char key;
    private String text;
    private DataService cipher;

    public XorDecrypt(double score, char key, String text, DataService cipher) {
        this.score = score;
        this.key = key;
        this.text = text;
        this.cipher = cipher;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public char getKey() {
        return key;
    }

    public void setKey(char key) {
        this.key = key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public DataService getCipher() {
        return cipher;
    }

    public void setCipher(DataService cipher) {
        this.cipher = cipher;
    }
    
    
    
}
