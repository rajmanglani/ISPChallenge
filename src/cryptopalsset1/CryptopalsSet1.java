/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptopalsset1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64.Decoder;
import java.util.List;
import javax.xml.crypto.Data;

/**
 *
 * @author student
 */
public class CryptopalsSet1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        System.out.println(hexToBase64("49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d"));
        System.out.println(fixedXor("1c0111001f010100061a024b53535009181c", DataService.Encoding.HEX, "686974207468652062756c6c277320657965", DataService.Encoding.HEX));
        System.out.println(crackSingleXor("1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736").getText());
        System.out.println(findCrackXor("/home/raj/Desktop/4.txt").getText());
        System.out.println(repeatingXor("Burning 'em, if you ain't quick and nimble I go crazy when I hear a cymbal", "ICE"));
        
    }
    
    public static String hexToBase64(String hex){
        //create new byte[]
        DataService ds = new DataService(hex, DataService.Encoding.HEX);
        return ds.getBase64();
    }
    
    public static String fixedXor(String hex1, DataService.Encoding enc1, String hex2, DataService.Encoding enc2){
        DataService ds1 = new DataService(hex1, enc1);
        DataService ds2 = new DataService(hex2, enc2);
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
    
    public static XorDecrypt crackSingleXor(String hex1){
        //list for all possible decryptions and then select the best based on scoring
        List<XorDecrypt> decryptions = new ArrayList<>();
        
        DataService ds1 = new DataService(hex1, DataService.Encoding.HEX);
        char c0 = DataService.chr(0);
        char c127 = DataService.chr(127);
        
        for (char possible = c0; possible <= c127; possible++){
            int cipherSize = ds1.getSize();
            StringBuffer sb = new StringBuffer(cipherSize);
            for(int i =0; i<cipherSize; i++){
                //create a string with the same length but with all chars
                sb.append(possible);
            }
            String str = sb.toString();
            String plainTextinHex = fixedXor(str, DataService.Encoding.ASCII, hex1, DataService.Encoding.HEX);
            DataService plainTextData = new DataService(plainTextinHex, DataService.Encoding.HEX);
            String plainTextinAscii = plainTextData.getASCII();
            //now score the plain text 
            double score = 0.0;
            for(int j=0; j<plainTextinAscii.length(); j++){
                if(('a' <= plainTextinAscii.charAt(j) && plainTextinAscii.charAt(j) < 'z') || ('A' <= plainTextinAscii.charAt(j) && plainTextinAscii.charAt(j) <= 'Z') || (plainTextinAscii.charAt(j) == ' ')){
                    // if it is a letter or space
                    score++;
                }
                if(' ' <= plainTextinAscii.charAt(j) && plainTextinAscii.charAt(j) < '~'){
                    //if it can be printed
                    score++;
                }
                
            }
            //avg score
            score = score/plainTextinAscii.length();
            XorDecrypt option = new XorDecrypt(score, possible, plainTextinAscii, ds1);
            decryptions.add(option);
        }
        
        //now get the best decryption based on score
        XorDecrypt best = decryptions.get(0);
        for (XorDecrypt c : decryptions) {
            if (c.getScore() > best.getScore()) {
                best = c;
            }
        }
        return best;
    } 
    
    public static XorDecrypt findCrackXor(String file) throws FileNotFoundException, IOException{
        XorDecrypt best = null;
        XorDecrypt c ;
        String current;
        InputStream in = new FileInputStream(file);
        InputStreamReader input = new InputStreamReader(in, Charset.forName("UTF-8"));
        BufferedReader buffer = new BufferedReader(input);
        while((current=buffer.readLine())!=null){
            c = crackSingleXor(current);
            if(best == null || c.getScore() > best.getScore()){
                best = c;
            }
        }
        
        return best;
    }
    
    public static String repeatingXor(String text, String key){
        //make string of same length from key to xor
        StringBuilder sb = new StringBuilder(text.length()+key.length()-1);
        while(sb.length() < text.length()){
            sb.append(key);
        }
        sb.setLength(text.length());
        String keyToXor = sb.toString();
        
        return fixedXor(keyToXor, DataService.Encoding.ASCII, text, DataService.Encoding.ASCII);
        
    }
    
    
}
