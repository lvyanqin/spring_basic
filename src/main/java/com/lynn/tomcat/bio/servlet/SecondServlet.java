package com.lynn.tomcat.bio.servlet;


import com.lynn.tomcat.bio.http.MyRequest;
import com.lynn.tomcat.bio.http.MyResponse;

import java.io.IOException;

public class SecondServlet extends MyAbstractServlet {

    public void doGet(MyRequest request, MyResponse response) throws IOException {
        this.doPost(request, response);
    }

    public void doPost(MyRequest request, MyResponse response) throws IOException {
        response.write("this is second servler bio.");
    }

}
