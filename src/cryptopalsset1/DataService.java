/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptopalsset1;

import java.nio.ByteBuffer;
import java.util.Base64;

public class DataService {

    // Enum type
    public enum Encoding { DEC, HEX, BASE64, ASCII }

    // Byte array to store information
    private byte[] data;

    // Constructorsto convert all input data to byte[]
    public DataService(int n) {
        data = intToBytes(n);
    }
    public DataService(byte[] bytes) {
        data = bytes;
    }
    public DataService(String info, Encoding encoding) {
        if (encoding == Encoding.DEC) {
            data = intToBytes(Integer.parseInt(info));
        } else if (encoding == Encoding.HEX) {
            if (info.length() == 1) {
                info = "0" + info;
            } else if (info.length() == 0) {
                info = "00";
            }
            int halfLength = info.length() / 2;
            data = new byte[halfLength];
            for (int i = 0; i < halfLength; i++) {
                data[i] = hexToByte(info.charAt(2*i), info.charAt(2*i + 1));
            }
        } else if (encoding == Encoding.BASE64) {
            data = Base64.getDecoder().decode(info);
        } else {                // must be ASCII
            data = new byte[info.length()];
            for (int i = 0; i < info.length(); i++) {
                data[i] = (byte)(info.charAt(i));
            }
        }
    }

    // Return data as an integer.
    public int getInt() {
        int result = 0;
        for (byte b : data) {
            result = (result << 8) + b + ((b < 0) ? 256 : 0);
        }
        return result;
    }

    // Return data as a hex string.
    public String getHex() {
        String result = "";
        for (byte b : data) {
            result += byteToHex(b);
        }
        if (result.length() % 2 == 1) {
            result = "0" + result;
        } else if (result.length() == 0) {
            result = "00";
        }
        return result;
    }

    // Return byte[] as a base 64 string.
    public String getBase64() {
        return Base64.getEncoder().encodeToString(data);
    }

    // Return data as an ASCII string.
    public String getASCII() {
        String result = "";
        for (byte b : data) {
            result += chr(b);
        }
        return result;
    }

    // Return data as a byte array.
    public byte[] getBytes() {
        return data;
    }

    // Return the number of bytes in this object.
    public int getSize() {
        return data.length;
    }

    // Return the ASCII character corresponding to integer n.
    public char chr(int n) {
        return (char)n;
    }

    // Set data based on the byte values of integer n.
    private byte[] intToBytes(int n) {
        return ByteBuffer.allocate(4).putInt(n).array();
    }

    // Return a byte corresponding to hex characters x and y.
    private byte hexToByte(char x, char y) {
        return (byte) (16 * hexDigitToInt(x) + hexDigitToInt(y));
    }

    // Return a length two hex string corresponding to byte b.
    private String byteToHex(byte b) {
        int byteVal = (int)b;
        return "" + intToHexDigit(byteVal / 16) + intToHexDigit(byteVal % 16);
    }

    // Return a hex digit corresponding to integer n.
    private char intToHexDigit(int n) {
        if (0 <= n && n <= 9) {
            return chr(48 + n); // 48 == ord('0')
        } else {
            return chr(97 + n - 10); // 97 == ord('a')
        }
    }

    // Return an integer corresponding to hex digit x.
    private int hexDigitToInt(char x) {
        if ('0' <= x && x <= '9') {
            return x - 48;      // 48 == ord('0')
        } else if ('A' <= x && x <= 'Z') {
            return x + 10 - 65; // 65 == ord('A')
        } else {
            return x + 10 - 97; // 97 == ord('a')
        }
    }

}

