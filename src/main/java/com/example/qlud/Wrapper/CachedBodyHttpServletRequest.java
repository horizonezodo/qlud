package com.example.qlud.Wrapper;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class CachedBodyHttpServletRequest extends HttpServletRequestWrapper {

    private final byte[] cachedBody;

    public CachedBodyHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);
        this.cachedBody = readBytes(request.getInputStream());
    }

    private byte[] readBytes(ServletInputStream inputStream) throws IOException {

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int length;
        while ((length = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, length);
        }
        return buffer.toByteArray();
    }

    @Override
    public ServletInputStream getInputStream() {
        return new CachedBodyServletInputStream(cachedBody);
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(getInputStream(), StandardCharsets.UTF_8));
    }

    private static class CachedBodyServletInputStream extends ServletInputStream {

        private final byte[] body;
        private int index;

        public CachedBodyServletInputStream(byte[] body) {
            this.body = body;
            this.index = 0;
        }

        @Override
        public boolean isFinished() {
            return index >= body.length;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener listener) {
            // No-op
        }

        @Override
        public int read() {
            if (index >= body.length) {
                return -1;
            }
            return body[index++] & 0xFF;
        }
    }
}
