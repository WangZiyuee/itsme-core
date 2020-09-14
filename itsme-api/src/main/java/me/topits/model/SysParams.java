package me.topits.model;

import com.alibaba.fastjson.JSONObject;
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

    // sign
    /** 签名 */
    private String sign;
    /** 时间戳 */
    private String timestamp;
    /** 访问令牌 */
    private String accessToken;

    // os info
    /** 操作系统类型 android / ios */
    private String osType;
    /** 操作系统版本 */
    private String osVersion;

    // app
    /** 应用版本 */
    private String appVersion;

    // others
    /** ip */
    private String ipAddress;
    /** 网络类型 */
    private String netType;

    public SysParams(JSONObject header) {
        this.sign = header.getString("sign");
        this.accessToken = header.getString("access_token");
        this.timestamp = header.getString("timestamp");
        this.osType = header.getString("os_type");
        this.osVersion = header.getString("os_version");
        this.appVersion = header.getString("app_version");
        this.ipAddress = header.getString("ip_address");
        this.netType = header.getString("net_type");
    }

}
