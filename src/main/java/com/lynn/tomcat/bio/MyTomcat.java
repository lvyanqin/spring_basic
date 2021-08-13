package com.lynn.tomcat.bio;


import com.lynn.tomcat.bio.http.MyRequest;
import com.lynn.tomcat.bio.http.MyResponse;
import com.lynn.tomcat.bio.servlet.MyAbstractServlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MyTomcat {

    Properties webXml = new Properties();

    ServerSocket server = null;

    int port = 8080;

    Map<String, MyAbstractServlet> servletMapping = new HashMap<>();

    public static void main(String[] args) {
        new MyTomcat().start();
    }

    private void start() {
        try {
            init();
            server = new ServerSocket(port);
            System.out.println("MyTomcat已启动，端口是：" + port);
            while (true) {
                Socket client = server.accept();
                process(client);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        String web_inf = this.getClass().getResource("/").getPath();
        try {
            FileInputStream fis = new FileInputStream(web_inf + "tomcat-bio.properties");
            webXml.load(fis);

            for (Object k : webXml.keySet()) {
                String key = k.toString();
                if(key.endsWith(".url")) {
                    String servletName = key.replaceAll("\\.url", "");
                    String url = webXml.getProperty(key);
                    String className = webXml.getProperty(servletName + ".className");
                    Object instance = Class.forName(className).newInstance();
                    servletMapping.put(url, (MyAbstractServlet) instance);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void process(Socket client) throws IOException {
        InputStream is = client.getInputStream();
        OutputStream os = client.getOutputStream();
        MyRequest request = new MyRequest(is);
        MyResponse response = new MyResponse(os);
        if(!servletMapping.containsKey(request.getUrl())) {
            response.write("404 - not found!");
        } else {
            servletMapping.get(request.getUrl()).service(request, response);
        }

        os.flush();
        os.close();
        is.close();
        client.close();
    }

}
