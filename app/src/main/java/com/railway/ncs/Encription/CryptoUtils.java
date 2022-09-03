package com.railway.ncs.Encription;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class CryptoUtils {

/*    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void main(String[] args) throws Exception {
        String publicKey =
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiO1lWgkTZeDWQgXlDF8t92YLYZm/ENvCvKPJNuj9WZfGCF5RIUFaYolb/HAhoAHKxgYRUS81WFfHuMROT+B/d0cW+Ii/sqLzTfFjepExonCj1I8m4WLdBAdZCRlWLo+bdO39OpxfK14XaPmRMdb8+uTpZ0hZBhDzZDnXChCm4fgsn63ZT2VEHdHX8PgmKTViR4VXsvyZCkT60FiEix2JdLCuSGF+tPr9GQnlSDJK4vRCZl+/TD/IaIbeAFWcx0Y6kdLpUBBUHbxY8cXcsr/HfJ6/WMEBSlUCOvbZhrx41yC/182WMPppaqCDeDamDV2T+ufzrQkT1nU40gm9h7uoXwIDAQAB";
        String privateKey =
                "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCI7WVaCRNl4NZCBeUMXy33Zgthmb8Q28K8o8k26P1Zl8YIXlEhQVpiiVv8cCGgAcrGBhFRLzVYV8e4xE5P4H93Rxb4iL+yovNN8WN6kTGicKPUjybhYt0EB1kJGVYuj5t07f06nF8rXhdo+ZEx1vz65OlnSFkGEPNkOdcKEKbh+CyfrdlPZUQd0dfw+CYpNWJHhVey/JkKRPrQWISLHYl0sK5IYX60+v0ZCeVIMkri9EJmX79MP8hoht4AVZzHRjqR0ulQEFQdvFjxxdyyv8d8nr9YwQFKVQI69tmGvHjXIL/XzZYw+mlqoIN4NqYNXZP65/OtCRPWdTjSCb2Hu6hfAgMBAAECggEARXP0xrh3yGNFCnQm/CGDFUwDVdxAV/LAUiLZuypu53Tek/wTNsUJbZWyvNa7l4eRLOSDACTr8AWGXqfj+QwsW3oJosLqSVfyBYT9KnxfKr/rubNqy0P0S0jW1pCtcCyTc7oLiiEq/LpbMXn1NcyQwUo/QY4m6bbLXfkgpBBSNaeHaDyMmlfjildljyS0mfAMngUYThv/mtze6M8fKpmGGcISa2i14WyZy8h0kh2MdgHexH2otPoEy+eDVqQAcNKwxx08N/D6qtv8gVHioVDaIf4EpuSvIgOiePPIHITw2kKH+9RsixgWW9acihXvO5ZqhDucnFnSYlT9zpdwKdK0cQKBgQDNhzuWj+P/1QODsqvBx/cQ7f4smYjvyFdVYSTF/4tRIxbkfcbaAhkMxTErleGmxnDd7yAiBEvSwCU+Ljm549P8u6kpMAIe+mX8oIKkZaN507jVlWOhFft50Y7SccTuE4xl3i2RXgEr8yWO9G6r4IKBNAHSnDa63+YTWx0Lj2YZ6wKBgQCqjXztkSfrYZDEadag+4mlSos7Zq0VMIUo0VBcnsHKwMXCmkNlNbh3dqdT6hXgkYVvWjZJbTSO5RQ7675HXbczRazIO62F9GLrFfYKNWUo9xgK1w3K2/8uBvAo11mc2jHWTIQ+7MhnaFEqgnABT2Pj6x+6Fj1q/3dpFVuCGw86XQKBgBBF5dTs/aty7T1PN0Q3lftULKzRV0NNBIUxFN1Md3ZsJdLflpAKXHtjXZpK2rv+uE8KMOwglPRR3GEI/e8W9+Dp5uHo+DIFt2+CMfXcSk5KEjdvBZ1qhSfHDNzivynuiE5Egi0hWUxmixG/jsezFgIu4PoQ/lBbi/b6XtlceEQTAoGAOd01JHjaHV8Fuc3a1tVglubapDWqxYSnuPiDFll6kGqk7ehes0jtpAJqtOq3r3BP4D6wKmtVqKGKIuZRsW7XSBr8ixbMQaVvpijWSBJjlul2i7742/mV8yzBcGnX6oKQ5A8j0yGMwKDA4PZOP/OKhXpxUjZXciTog4uOd27s1LUCgYAo0Z62Jc43WbmRpw+9O0KlKjAjiVXenbhuhctDpF9E97M+bBhQevHAR70bFyl5wPp+18zSfcyDHpTt3rWXoc/QnZDEqGwBRtLP6ePv8h/Zq9WQqAIKvySf3QsYmk+CCdgveFnVXKQ6M/3FRqH8q1MIJl9PJ8aM/QVe8/wMUhqupw==";
        byte[] encryptedData = encrypt("1234", publicKey);
        System.out.println("Encrypted " + encryptedData);
        byte[] decryptedData = decryptAsymmetric(Base64.getEncoder().encodeToString(encryptedData), "RSA/ECB/PKCS1Padding", privateKey);
        System.out.println("Decrypted " + new String(decryptedData));
    }*/
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static byte[] decryptAsymmetric(String data, String algorithm, String key) {
        byte[] decryptedBytes = null;
        try {
            String[] algo = algorithm.split("/");
            KeyFactory kf = KeyFactory.getInstance(algo[0]);
            PrivateKey pk = kf.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(key)));
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, pk);
            decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(data));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptedBytes;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static byte[] encrypt(String data, String publicKey) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
        return cipher.doFinal(data.getBytes());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static PublicKey getPublicKey(String base64PublicKey) {
        PublicKey publicKey = null;
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey;
    }

}
