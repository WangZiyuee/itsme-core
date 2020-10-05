package me.topits.filter;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import me.topits.model.SysParams;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author QingKe
 * @date 2020-09-07 16:44
 **/
@Slf4j
public class ThreadLocalContext {

    public final static InheritableThreadLocal<JSONObject> PARAMS = new InheritableThreadLocal<>();
    public final static InheritableThreadLocal<JSONObject> HEADER = new InheritableThreadLocal<>();
    public final static InheritableThreadLocal<SysParams> SYS_PARAMS = new InheritableThreadLocal<>();
    public final static InheritableThreadLocal<Object> RESULT = new InheritableThreadLocal<>();

    public static void buildRequestContext(RequestWrapper requestWrapper) {
        // params
        JSONObject params = new JSONObject(16);
        if (requestWrapper.getContentType() != null
                && requestWrapper.getContentType().contains("json")) {
            // application/json
            JSONObject requestBody = JSONObject.parseObject(requestWrapper.getRequestBody());
            for (String key : requestBody.keySet()) {
                params.put(key, requestBody.get(key));
            }
        } else {
            // other
            Enumeration<String> parameterNames = requestWrapper.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String key = parameterNames.nextElement();
                params.put(key, requestWrapper.getParameter(key));
            }
        }
        ThreadLocalContext.PARAMS.set(params);

        // header
        JSONObject header = new JSONObject(32);
        Enumeration<String> headerNames = requestWrapper.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            header.put(key, requestWrapper.getHeader(key));
        }
        ThreadLocalContext.HEADER.set(header);

        // sys params
        ThreadLocalContext.SYS_PARAMS.set(new SysParams(header));
    }

    public static void buildResponseContext(Object object) {
        // result
        ThreadLocalContext.RESULT.set(object);
    }

    public static JSONObject getParams() {
        return PARAMS.get();
    }

    public static SysParams getSysParams() {
        return SYS_PARAMS.get();
    }

    public static String getHeader(String key) {
        return HEADER.get().getString(key);
    }


}
