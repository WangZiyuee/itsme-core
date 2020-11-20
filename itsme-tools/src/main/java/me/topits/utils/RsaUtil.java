package me.topits.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * 非对称加密, 不适合长数据
 *
 * @author QingKe
 * @date 2020-10-04 23:28
 **/
@Slf4j
public class RsaUtil {

    private static Cipher cipher;

    static {
        try {
            cipher = Cipher.getInstance("RSA");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            log.error("RsaUtil init", e);
        }
    }

    /**
     * 生成密钥对
     *
     * @param filePath 生成密钥的路径
     * @return 密钥对
     */
    public static Map<String, String> generateKeyPair(String filePath) {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            // 密钥位数
            keyPairGen.initialize(2048);
            // 密钥对
            KeyPair keyPair = keyPairGen.generateKeyPair();
            // 公钥
            PublicKey publicKey = keyPair.getPublic();
            // 私钥
            PrivateKey privateKey = keyPair.getPrivate();
            // 得到公钥字符串
            String publicKeyString = getKeyString(publicKey);
            // 得到私钥字符串
            String privateKeyString = getKeyString(privateKey);
            if (filePath != null) {
                // 将密钥对写入到文件
                FileWriter publicKeyFileWriter = new FileWriter(filePath + "/publicKey.keystore");
                FileWriter privateKeyFileWriter = new FileWriter(filePath + "/privateKey.keystore");
                BufferedWriter publicKeyBufferedWriter = new BufferedWriter(publicKeyFileWriter);
                BufferedWriter privateKeyBufferedWriter = new BufferedWriter(privateKeyFileWriter);

                publicKeyBufferedWriter.write(publicKeyString);
                publicKeyBufferedWriter.flush();
                publicKeyBufferedWriter.close();
                publicKeyFileWriter.close();
                privateKeyBufferedWriter.write(privateKeyString);
                privateKeyBufferedWriter.flush();
                privateKeyBufferedWriter.close();
                privateKeyFileWriter.close();
            }
            // 将生成的密钥对返回
            Map<String, String> map = new HashMap<>(2);
            map.put("publicKey", publicKeyString);
            map.put("privateKey", privateKeyString);
            return map;
        } catch (Exception e) {
            log.error("RsaUtil generateKeyPair", e);
        }
        return null;
    }

    /**
     * 获取密钥字符串（经过base64编码）
     *
     * @param key key
     * @return 密钥字符串
     */
    public static String getKeyString(Key key) {
        byte[] keyBytes = key.getEncoded();
        return new String(Base64.getEncoder().encode(keyBytes));
    }

    /**
     * 获取公钥
     *
     * @param key 密钥字符串 (经过base64编码)
     * @return 公钥
     * @throws Exception Exception
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = Base64.getDecoder().decode(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 获取私钥
     *
     * @param key 密钥字符串 (经过base64编码)
     * @return 私钥
     * @throws Exception Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 使用公钥对明文进行加密, 返回BASE64编码的字符串
     *
     * @param publicKey publicKey
     * @param plainText plainText
     * @return 密文
     */
    public static String encrypt(PublicKey publicKey, String plainText) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] enBytes = cipher.doFinal(plainText.getBytes());
            return new String(Base64.getEncoder().encode(enBytes));
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            log.error("RsaUtil encrypt", e);
        }
        return null;
    }

    /**
     * 使用keystore对明文进行加密
     *
     * @param publicKeystore 公钥文件路径
     * @param plainText      明文
     * @return 密文
     */
    public static String fileEncrypt(String publicKeystore, String plainText) {
        try {
            FileReader fileReader = new FileReader(publicKeystore);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder publicKeyString = new StringBuilder();
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                publicKeyString.append(str);
            }
            bufferedReader.close();
            fileReader.close();
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKeyString.toString()));
            byte[] enBytes = cipher.doFinal(plainText.getBytes());
            return new String(Base64.getEncoder().encode(enBytes));
        } catch (Exception e) {
            log.error("RsaUtil fileEncrypt", e);
        }
        return null;
    }

    /**
     * 使用公钥对明文进行加密
     *
     * @param publicKey 公钥
     * @param plainText 明文
     * @return 密文
     */
    public static String encrypt(String publicKey, String plainText) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
            byte[] enBytes = cipher.doFinal(plainText.getBytes());
            return new String(Base64.getEncoder().encode(enBytes));
        } catch (Exception e) {
            log.error("RsaUtil encrypt", e);
        }
        return null;
    }

    /**
     * 使用私钥对密文进行解密
     *
     * @param privateKey privateKey
     * @param enStr      密文
     * @return 明文
     */
    public static String decrypt(PrivateKey privateKey, String enStr) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] deBytes = cipher.doFinal(Base64.getDecoder().decode(enStr));
            return new String(deBytes);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            log.error("RsaUtil decrypt", e);
        }
        return null;
    }

    /**
     * 使用私钥对密文进行解密
     *
     * @param privateKey 私钥
     * @param enStr      密文
     * @return 明文
     */
    public static String decrypt(String privateKey, String enStr) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKey));
            byte[] deBytes = cipher.doFinal(Base64.getDecoder().decode(enStr));
            return new String(deBytes);
        } catch (Exception e) {
            log.error("RsaUtil decrypt", e);
        }
        return null;
    }

    /**
     * 使用keystore对密文进行解密
     *
     * @param privateKeystore 私钥路径
     * @param enStr           密文
     * @return 明文
     */
    public static String fileDecrypt(String privateKeystore, String enStr) {
        try {
            FileReader fileReader = new FileReader(privateKeystore);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder privateKeyString = new StringBuilder();
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                privateKeyString.append(str);
            }
            bufferedReader.close();
            fileReader.close();
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKeyString.toString()));
            byte[] deBytes = cipher.doFinal(cipher.doFinal(Base64.getDecoder().decode(enStr)));
            return new String(deBytes);
        } catch (Exception e) {
            log.error("RsaUtil fileDecrypt", e);
        }
        return null;
    }

    public static void main(String[] args) {
        Map<String, String> keyPair = RsaUtil.generateKeyPair(null);

        assert keyPair != null;
        String publicKey = keyPair.get("publicKey");
        String privateKey = keyPair.get("privateKey");
        log.info("publicKey {}", publicKey);
        log.info("privateKey {}", privateKey);
        log.info("{}", "公钥加密, 私钥解密");
        String source = "Hello World!";
        log.info("source {}", source);
        String encryptStr = RsaUtil.encrypt(publicKey, source);
        log.info("encryptStr {}", encryptStr);
        String decryptStr = RsaUtil.decrypt(privateKey, encryptStr);
        log.info("decryptStr {}", decryptStr);
    }


}
