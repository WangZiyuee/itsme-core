package me.topits.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * 对称加密, 适合长数据.
 * @author QingKe
 * @date 2020-09-14 16:18
 **/
@Slf4j
public class AesUtil {

    private static final String KEY_ALGORITHM = "AES";
    /** 默认的加密算法 */
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * AES 加密操作
     *
     * @param data   待加密内容
     * @param secret 密钥
     * @return 返回Base64转码后的加密数据
     */
    public static String encrypt(String data, String secret) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(secret));

            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);

            // 加密
            byte[] binaryData = cipher.doFinal(dataBytes);
            // 通过Base64转码返回
            return Base64.getEncoder().encodeToString(binaryData);
        } catch (Exception e) {
            log.info("encrypt", e);
        }
        return null;
    }

    /**
     * AES 解密操作
     *
     * @param data   待解密数据
     * @param secret 密钥
     * @return 解密数据
     */
    public static String decrypt(String data, String secret) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(secret));
            byte[] binaryData = cipher.doFinal(Base64.getDecoder().decode(data));
            return new String(binaryData, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.info("decrypt Exception", e);
        }
        return null;
    }

    /**
     * 生成加密秘钥
     *
     * @return SecretKeySpec
     */
    private static SecretKeySpec getSecretKey(String secret) {
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(secret.getBytes());
            // AES 要求密钥长度为 128
            keyGenerator.init(128, random);
            // 生成一个密钥
            SecretKey secretKey = keyGenerator.generateKey();
            return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);
        } catch (Exception e) {
            log.info("getSecretKey Exception", e);
        }
        return null;
    }

    public static void main(String[] args) {
        String data = "data";
        String encryptData = AesUtil.encrypt(data, "123");
        String decryptData = AesUtil.decrypt(encryptData, "123");
        log.info("data: {}", data);
        log.info("encryptData: {}", encryptData);
        log.info("decryptData: {}", decryptData);
    }

}

