package main.java.plugins;

import jdk.jfr.Description;
import main.java.util.Plugin;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

@Description("AES шифрование")
public class AESEncryptionPlugin implements Plugin {

    private static final String METHOD = "AES/CBC/PKCS5Padding";
    private static final String INIT_VECTOR = "encryptionIntVec";

    @Override
    @Description(".aes")
    public void encode(File file, String key) {
        doCrypt(Cipher.ENCRYPT_MODE, key, file);
    }

    @Override
    public void decode(File file, String key) {
        doCrypt(Cipher.DECRYPT_MODE, key, file);
    }

    private void doCrypt(int mode, String key, File file) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes(StandardCharsets.UTF_8));
            for (int i = 0; i < 16-key.length(); i++)
                key += " ";
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance(METHOD);
            cipher.init(mode, secretKey, iv);
            bis = new BufferedInputStream(new FileInputStream(file));
            File tempFile = File.createTempFile("encrypt", null);
            bos = new BufferedOutputStream(new FileOutputStream(tempFile));
            byte[] buffer = new byte[(int) file.length()];
            bis.read(buffer);
            buffer = cipher.doFinal(buffer);
            bos.write(buffer);
            bis = new BufferedInputStream(new FileInputStream(tempFile));
            bis.read(buffer);
            bos = new BufferedOutputStream(new FileOutputStream(file));
            bos.write(buffer);
            bis.close();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
