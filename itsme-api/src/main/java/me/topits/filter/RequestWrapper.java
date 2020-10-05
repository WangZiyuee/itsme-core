package me.topits.filter;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author QingKe
 * @date 2020-09-07 16:19
 **/
public class RequestWrapper extends HttpServletRequestWrapper {

    private byte[] body;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param httpServletRequest The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public RequestWrapper(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
        try {
            if (httpServletRequest.getContentType() != null
                    && httpServletRequest.getContentType().contains("json")) {
                body = StreamUtils.copyToByteArray(httpServletRequest.getInputStream());
            } else {
                body = "{}".getBytes();
            }
        } catch (Exception ignore) {
        }
    }

    public String getRequestBody() {
        return new String(body, StandardCharsets.UTF_8);
    }

    @Override
    public ServletInputStream getInputStream() {
        return new ServletInputStreamWrapper(body);
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(getInputStream(), Charset.defaultCharset()));
    }

    static class ServletInputStreamWrapper extends ServletInputStream {
        private final ByteArrayInputStream byteArrayInputStream;

        public ServletInputStreamWrapper(byte[] bytes) {
            this.byteArrayInputStream = new ByteArrayInputStream(bytes);
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener listener) {

        }

        @Override
        public int read() {
            return this.byteArrayInputStream.read();
        }
    }
}