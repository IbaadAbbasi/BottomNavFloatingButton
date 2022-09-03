package com.railway.ncs.Encription;

import android.util.Log;

import com.railway.ncs.Utils.Constant;
import com.railway.ncs.Utils.Keys;

import java.security.MessageDigest;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Cryptography_Android {

    public static String Encrypt(String text, String key) throws Exception {
//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        Cipher cipher = Cipher.getInstance("AES/CTR/PKCS7Padding");
        byte[] keyBytes = new byte[16];
        byte[] b = key.getBytes("UTF-8");
        int len = b.length;
        if (len > keyBytes.length)
            len = keyBytes.length;
        System.arraycopy(b, 0, keyBytes, 0, len);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
//        IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
       String stringIV="NcRfUjWnZr4u7x";
       byte[] bytes=stringIV.getBytes();
        IvParameterSpec ivSpec = new IvParameterSpec(bytes);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

        byte[] results = new byte[text.length()];
        try {
            results = cipher.doFinal(text.getBytes("UTF-8"));

        } catch (Exception e) {
            Log.i("Error in Decryption", e.toString());
        }

        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(results); // it returns the result as a String
    }

    public static String Decrypt(String text, String key) throws Exception {
//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding"); //this parameters should not be changed
        Cipher cipher = Cipher.getInstance("AES/CTR/PKCS7Padding"); //this parameters should not be changed
        byte[] keyBytes = new byte[16];
        byte[] b = key.getBytes("UTF-8");
        int len = b.length;
        if (len > keyBytes.length)
            len = keyBytes.length;
        System.arraycopy(b, 0, keyBytes, 0, len);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        String stringIV="NcRfUjWnZr4u7x";
        byte[] bytes=stringIV.getBytes();
        IvParameterSpec ivSpec = new IvParameterSpec(bytes);
//        IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] results = new byte[text.length()];
        try {
            results = cipher.doFinal(android.util.Base64.decode(text, 0));

        } catch (Exception e) {
            Log.i("Erron in Decryption", e.toString());
        }
        Log.i("Data", new String(results, "UTF-8"));
        return new String(results, "UTF-8"); // it returns the result as a String
    }

    public static String EncryptWitPrivate(String text, String key,String Privcatekey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        Cipher cipher = Cipher.getInstance(   "aes-256-cbc","PKCS5Padding");
        byte[] keyBytes = new byte[16];
        byte[] b = key.getBytes("UTF-8");
        int len = b.length;
        if (len > keyBytes.length)
            len = keyBytes.length;
        System.arraycopy(b, 0, keyBytes, 0, len);

        byte[] PrivatekeyBytes = new byte[16];
        byte[] Privateb = Privcatekey.getBytes("UTF-8");
        int Privatelen = Privateb.length;
        if (Privatelen > PrivatekeyBytes.length)
            Privatelen = PrivatekeyBytes.length;
        System.arraycopy(Privateb, 0, PrivatekeyBytes, 0, Privatelen);

        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(PrivatekeyBytes);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

        byte[] results = new byte[text.length()];
        try {
            results = cipher.doFinal(text.getBytes("UTF-8"));

        } catch (Exception e) {
            Log.i("Error in Decryption", e.toString());
        }

        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(results); // it returns the result as a String
    }

    public static String DecryptPrivateKey(String text, String key,String PrivateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding"); //this parameters should not be changed


        byte[] keyBytes = new byte[16];
        byte[] b = key.getBytes("UTF-8");
        int len = b.length;
        if (len > keyBytes.length)
            len = keyBytes.length;
        System.arraycopy(b, 0, keyBytes, 0, len);


        byte[] PrivatekeyBytes = new byte[16];
        byte[] Privateb = PrivateKey.getBytes("UTF-8");
        int Privatelen = Privateb.length;
        if (Privatelen > PrivatekeyBytes.length)
            Privatelen = PrivatekeyBytes.length;
        System.arraycopy(Privateb, 0, PrivatekeyBytes, 0, Privatelen);

        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(PrivatekeyBytes);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] results = new byte[text.length()];
        try {
            results = cipher.doFinal(android.util.Base64.decode(text, 0));

        } catch (Exception e) {
            Log.i("Erron in Decryption", e.toString());
        }
        Log.i("Data", new String(results, "UTF-8"));
        return new String(results, "UTF-8"); // it returns the result as a String
    }


    private static class BASE64Encoder {
        public String encode(byte[] results) {

            String s1 = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                s1 = Base64.getEncoder().encodeToString(results);
            }
            else {
                s1 = android.util.Base64.encodeToString(results, 16);
            }

            return s1;
        }
    }

    //generate key at run time with pakage name and some extra string
    public static String getMD5(String message) {
        String md5str = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] input = message.getBytes();
            byte[] buff = md.digest(input);
            md5str = bytesToHex(buff);

        } catch (Exception e) {
            //e.printStackTrace();
            Log.e("SecurityUtil",e.toString());
        }
        return md5str;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder md5str = new StringBuilder();
        int digital;
        for (byte aByte : bytes) {
            digital = aByte;

            if (digital < 0) {
                digital += 256;
            }
            if (digital < 16) {
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString().toLowerCase();
    }


}

