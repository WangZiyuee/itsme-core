package me.topits.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author Wang Ziyue
 * @date 2020/5/20 21:14
 * 系统参数
 */
@Data
@Accessors(chain = true)
public class SysParams {

    // os info
    /** 操作系统类型 android / ios */
    private String osType;
    /** 操作系统版本 */
    private String osVersion;

    // app
    /** 应用版本 */
    private String appVersion;

    // sign
    /** 签名 */
    private String sign;
    /** 时间戳 */
    private Long timestamp;
    /** 访问令牌 */
    private String accessToken;

    // others
    /** ip */
    private String ipAddress;
    /** 网络类型 */
    private String netType;

    public SysParams(Map<String, String> header) {
        this.osType = header.get("osType");
        this.osVersion = header.get("osVersion");
        this.appVersion = header.get("appVersion");
        this.sign = header.get("sign");
        this.timestamp = StringUtils.hasText(header.get("timestamp")) ? Long.parseLong(header.get("timestamp")) : null;
        this.accessToken = header.get("accessToken");
        this.ipAddress = header.get("ipAddress");
        this.netType = header.get("netType");
    }

}
