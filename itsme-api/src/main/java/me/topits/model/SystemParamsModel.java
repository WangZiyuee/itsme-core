package me.topits.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Wang Ziyue
 * @since 2020/5/20 21:14
 * 系统参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SystemParamsModel {

    /** token */
    private String token;

    /** 请求签名 */
    private String sign;

    /** app版本 */
    private String appVersion;

    /** 系统类型 */
    private String osType;


}
