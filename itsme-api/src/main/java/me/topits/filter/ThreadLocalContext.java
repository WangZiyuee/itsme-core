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

    public final static InheritableThreadLocal<Map<String, Object>> PARAMS = new InheritableThreadLocal<>();
    public final static InheritableThreadLocal<Map<String, String>> HEADER = new InheritableThreadLocal<>();
    public final static InheritableThreadLocal<SysParams> SYS_PARAMS = new InheritableThreadLocal<>();
    public final static InheritableThreadLocal<Object> RESULT = new InheritableThreadLocal<>();

    public static void buildRequestContext(RequestWrapper requestWrapper) {
        // TODO: 2020-09-07 body异常json 抛针对性异常
        // params
        Map<String, Object> params = new HashMap<>(16);
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
        Map<String, String> header = new HashMap<>(32);
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

    public static Object getParams(String key) {
        return PARAMS.get().get(key);
    }

    public static String getHeader(String key) {
        return HEADER.get().get(key);
    }

    public static SysParams getSysParams() {
        return SYS_PARAMS.get();
    }

}
