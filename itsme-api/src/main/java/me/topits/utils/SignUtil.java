package me.topits.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author QingKe
 * @date 2020-09-14 15:07
 **/
@Slf4j
public class SignUtil {

    private static final String HMAC_SHA_256 = "HmacSHA256";

    public static String getHmacSha256Sign(String data, String secret) {
        try {
            Mac mac = Mac.getInstance(HMAC_SHA_256);
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), HMAC_SHA_256));
            byte[] binaryData = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return URLEncoder.encode(new String(Base64.encodeBase64(binaryData)), "UTF-8");
        } catch (Exception e) {
            return "";
        }
    }

}
