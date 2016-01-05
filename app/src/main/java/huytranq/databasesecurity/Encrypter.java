package huytranq.databasesecurity;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by huytr on 04-01-2016.
 */
public class Encrypter extends Security {
    @Override
    public String getName() {
        return "Encryption";
    }

    @Override
    public String digest(String text) {
        if (text == null)
            return null;
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(text.getBytes());

            return android.util.Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
