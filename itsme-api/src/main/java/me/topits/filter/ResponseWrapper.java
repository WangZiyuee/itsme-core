package me.topits.filter;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author QingKe
 * @date 2020-09-10 16:10
 **/
public class ResponseWrapper extends HttpServletResponseWrapper {

    private final ByteArrayOutputStream byteArrayOutputStream;
    private final HttpServletResponse httpServletResponse;

    /**
     * Constructs a response adaptor wrapping the given response.
     *
     * @param httpServletResponse The response to be wrapped
     * @throws IllegalArgumentException if the response is null
     */
    public ResponseWrapper(HttpServletResponse httpServletResponse) {
        super(httpServletResponse);
        this.byteArrayOutputStream = new ByteArrayOutputStream();
        this.httpServletResponse = httpServletResponse;
    }

    public String getResponseBody() {
        return new String(this.byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
    }

    @Override
    public ServletOutputStream getOutputStream() {
        return new ServletOutputStreamWrapper(this.byteArrayOutputStream, this.httpServletResponse);
    }

    @Override
    public PrintWriter getWriter() {
        return new PrintWriter(new ServletOutputStreamWrapper(this.byteArrayOutputStream, this.httpServletResponse));
    }

    static class ServletOutputStreamWrapper extends ServletOutputStream {
        private final ByteArrayOutputStream byteArrayOutputStream;
        private final HttpServletResponse httpServletResponse;

        public ServletOutputStreamWrapper(ByteArrayOutputStream byteArrayOutputStream, HttpServletResponse httpServletResponse) {
            this.byteArrayOutputStream = byteArrayOutputStream;
            this.httpServletResponse = httpServletResponse;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener listener) {

        }

        @Override
        public void write(int b) {
            this.byteArrayOutputStream.write(b);
        }

        @Override
        public void write(byte[] b) {
            this.byteArrayOutputStream.write(b, 0, b.length);
        }

        @Override
        public void write(byte[] b, int off, int len) {
            this.byteArrayOutputStream.write(b, off, len);
        }

        @Override
        public void flush() throws IOException {
            if (!httpServletResponse.isCommitted()) {
                byte[] bytes = this.byteArrayOutputStream.toByteArray();
                ServletOutputStream servletOutputStream = this.httpServletResponse.getOutputStream();
                servletOutputStream.write(bytes);
                servletOutputStream.flush();
            }
        }
    }
}
