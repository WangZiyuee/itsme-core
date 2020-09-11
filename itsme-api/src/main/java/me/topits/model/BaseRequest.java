package me.topits.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Wang Ziyue
 * @date 2020/5/20 21:13
 */
@Data
@Accessors(chain = true)
public class BaseRequest<T> {
    /** 请求参数 */
    private JSONObject request;
    /** 系统参数 */
    private SysParams sysParams;

    public T toJavaObject(Class<T> tClass) {
        if (request == null) {
            return null;
        }
        return JSONObject.toJavaObject(request, tClass);
    }
}
