package me.topits.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.topits.annotations.Signature;
import me.topits.exception.BaseException;
import me.topits.utils.AesUtil;
import org.apache.logging.log4j.util.Strings;

import java.lang.reflect.Field;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author QingKe
 * @date 2020-09-14 16:33
 **/
@Slf4j
@Data
public class BaseSignRequest {

    private String sign;

    protected void generatorSign() {
        SortedMap<String, String> sortedMap = new TreeMap<>();

        String secret = "";

        Class<?> clazz = this.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    Signature signature = field.getAnnotation(Signature.class);
                    if (signature != null) {
                        // 打开私有访问
                        field.setAccessible(true);
                        // 获取属性值
                        Object fieldValue = field.get(this);
                        if (fieldValue != null) {
                            if (signature.isSecret()) {
                                secret = String.valueOf(fieldValue);
                                continue;
                            }
                            sortedMap.put(field.getName(), String.valueOf(fieldValue));
                        }
                    }
                }
            } catch (Exception e) {
                log.error("BaseSignRequest generatorSign Exception", e);
                throw new BaseException("请求签名生成异常");
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String key : sortedMap.keySet()) {
            String value = sortedMap.get(key);
            if (Strings.isNotBlank(value)) {
                stringBuilder.append(key).append("=").append(value).append("&");
            }
        }
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("&"));
        this.sign = AesUtil.encrypt(stringBuilder.toString(), secret);
    }

}
