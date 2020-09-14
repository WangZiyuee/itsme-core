package me.topits.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author QingKe
 * @date 2020-09-14 17:34
 **/
@Data
@Configuration
public class SysSecretProperties {

    public static String BASE_REQUEST_SECRET;

    @Value("${sys.base.secret.request}")
    public void setBaseRequestSecret(String baseRequestSecret) {
        SysSecretProperties.BASE_REQUEST_SECRET = baseRequestSecret;
    }

}
