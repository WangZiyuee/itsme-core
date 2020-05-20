package me.topits.model;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Wang Ziyue
 * @since 2020/5/20 21:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BaseRequest {
    /** 公共参数 */
    private SystemParamsModel system;
    /** 请求参数 */
    private JSONObject params;
}
