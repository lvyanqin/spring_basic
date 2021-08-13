package com.lynn.tomcat.bio.servlet;


import com.lynn.tomcat.bio.http.MyRequest;
import com.lynn.tomcat.bio.http.MyResponse;

import java.io.IOException;

public abstract class MyAbstractServlet {

    public void service(MyRequest request, MyResponse response) throws IOException {
        if("GET".equalsIgnoreCase(request.getMethod())) {
            this.doGet(request, response);
        } else {
            this.doPost(request, response);
        }
    }

    public abstract void doGet(MyRequest request, MyResponse response) throws IOException ;

    public abstract void doPost(MyRequest request, MyResponse response) throws IOException;

}
