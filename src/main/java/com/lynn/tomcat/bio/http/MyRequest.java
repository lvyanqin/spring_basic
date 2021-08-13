package com.lynn.tomcat.bio.http;

import java.io.InputStream;

public class MyRequest {

    String url;
    String method;

    public MyRequest(InputStream is) {
        try {
            String content = "";
            byte[] buff = new byte[1024];
            int len = 0;
            if ((len = is.read(buff)) > 0) {
                content = new String(buff, 0, len);
            }
            System.out.println("content: \n" + content);
            String line = content.split("\\n")[0];
            String[] arr = line.split("\\s");
            this.method = arr[0];
            this.url = arr[1].split("\\?")[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUrl() {
        return this.url;
    }

    public String getMethod() {
        return this.method;
    }

}
