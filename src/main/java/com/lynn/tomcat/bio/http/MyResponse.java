package com.lynn.tomcat.bio.http;

import java.io.IOException;
import java.io.OutputStream;

public class MyResponse {

    OutputStream os;


    public MyResponse(OutputStream os) {
        this.os = os;
    }

    public void write(String str) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 200 ok\n")
                .append("Content-Type: text/html; \n")
                .append("\r\n")
                .append(str);
        os.write(sb.toString().getBytes());

    }

}
