package com.example.qlud.Wrapper;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class CachedBodyHttpServletResponse extends HttpServletResponseWrapper {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private PrintWriter writer;

    public CachedBodyHttpServletResponse(HttpServletResponse response) {
        super(response);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new CachedBodyServletOutputStream(outputStream);
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (writer == null) {
            writer = new PrintWriter(outputStream);
        }
        return writer;
    }

    public byte[] getContent() throws IOException {
        if (writer != null) {
            writer.flush();
        }
        return outputStream.toByteArray();
    }

    private static class CachedBodyServletOutputStream extends ServletOutputStream implements com.example.qlud.Wrapper.CachedBodyServletOutputStream {
        private final ByteArrayOutputStream outputStream;

        public CachedBodyServletOutputStream(ByteArrayOutputStream outputStream) {
            this.outputStream = outputStream;
        }

        @Override
        public void write(int b) throws IOException {
            outputStream.write(b);
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setWriteListener(WriteListener listener) {
        }

        @Override
        public boolean isFinished() {
            return outputStream.size() >= Integer.MAX_VALUE;
        }
    }
}
