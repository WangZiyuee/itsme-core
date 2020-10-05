package me.topits.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.experimental.Accessors;
import me.topits.validator.JsonValidator;

/**
 * @author Wang Ziyue
 * @date 2020/5/20 21:13
 */
@Data
@Accessors(chain = true)
public class BaseRequest<T> {
    /** 请求参数 */
    private JSONObject request;
    /** 拓展参数 */
    private JSONObject extraParams;
    /** 系统参数 */
    private SysParams sysParams;

    public T toJavaObject(Class<T> tClass) {
        if (request == null) {
            return null;
        }
        return JSONObject.toJavaObject(request, tClass);
    }

    public T toJavaObjectAndValidator(Class<T> tClass){
        return JsonValidator.toJavaObject(request, tClass);
    }
}
