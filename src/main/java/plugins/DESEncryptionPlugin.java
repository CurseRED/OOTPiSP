package main.java.plugins;

import jdk.jfr.Description;
import main.java.util.Plugin;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.Key;

@Description("DES шифрование")
public class DESEncryptionPlugin implements Plugin {

    private static final String METHOD = "desede/CBC/PKCS5Padding";
    private static final String INIT_VECTOR = "enIntVec";

    @Override
    @Description(".des")
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
            key = formatString(key, 192);
            System.out.println(key);
            Key secretKey = SecretKeyFactory.getInstance("DESede").generateSecret(new DESedeKeySpec(key.getBytes(StandardCharsets.UTF_8)));
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

    protected String formatString(String str, int position) {
        final StringBuffer buffer = new StringBuffer();
        final int to = position - str.length();
        if (str.length() > position)
            buffer.append(str.substring(0, position));
        else
            buffer.append(str);
        for (int i = 0; i < to; i++) {
            buffer.append(" ");
        }
        return buffer.toString();
    }
}
