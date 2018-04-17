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
public class AESDecrypt {
    private String key;
    private String text;
    private DataService ds;

    public AESDecrypt(String key, String text, DataService ds) {
        this.key = key;
        this.text = text;
        this.ds = ds;
    }
    
    
}
