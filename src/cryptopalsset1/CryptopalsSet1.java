/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptopalsset1;

import java.util.Base64.Decoder;
import javax.xml.crypto.Data;

/**
 *
 * @author student
 */
public class CryptopalsSet1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println(hexToBase64("49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d"));
        System.out.println(fixedXor("1c0111001f010100061a024b53535009181c", "686974207468652062756c6c277320657965"));
    }
    
    public static String hexToBase64(String hex){
        //create new byte[]
        DataService ds = new DataService(hex, DataService.Encoding.HEX);
        return ds.getBase64();
    }
    
    public static String fixedXor(String hex1, String hex2){
        DataService ds1 = new DataService(hex1, DataService.Encoding.HEX);
        DataService ds2 = new DataService(hex2, DataService.Encoding.HEX);
        //get byte[] for both 
        byte[] s1 = ds1.getBytes();
        byte[] s2 = ds2.getBytes();
        byte[] result = new byte[s2.length];
        for(int i =0; i<s1.length;i++){
            int x1 = (int) s1[i];
            int x2 = (int) s2[i];
            int res = x1 ^ x2;
            result[i] = (byte) (0xff & res);
        }
        
        DataService ds3 = new DataService(result);
        return ds3.getHex();
    }
    
}
