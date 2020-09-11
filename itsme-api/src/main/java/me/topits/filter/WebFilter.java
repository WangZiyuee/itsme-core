package me.topits.filter;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author QingKe
 * @date 2020-09-11 11:19
 **/
@Slf4j
@Component
@Order(-999)
public class WebFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest httpServletRequest,
                                    @NonNull HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();

        RequestWrapper requestWrapper = new RequestWrapper(httpServletRequest);
        ThreadLocalContext.buildRequestContext(requestWrapper);
        ResponseWrapper responseWrapper = new ResponseWrapper(httpServletResponse);

        // doFilter
        filterChain.doFilter(requestWrapper, responseWrapper);

        log.info("> HTTP | Method:{} | URL:{} | Status:{} | Header:{} | RequestBody:{} | ResponseBody:{} | Time:{}ms",
                httpServletRequest.getMethod(),
                httpServletRequest.getRequestURI(),
                httpServletResponse.getStatus(),
                JSONObject.toJSONString(ThreadLocalContext.HEADER.get(), SerializerFeature.WriteMapNullValue),
                JSONObject.toJSONString(ThreadLocalContext.PARAMS.get(), SerializerFeature.WriteMapNullValue),
                JSONObject.toJSONString(ThreadLocalContext.RESULT.get(), SerializerFeature.WriteMapNullValue),
                System.currentTimeMillis() - startTime);
    }
}
