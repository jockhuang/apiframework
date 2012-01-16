package com.specl.api.codec;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

public class Request implements Serializable {

    private String method;
    private Object param;

    public String getMethod() {
        return method;
    }

    public void setMethod(String request) {
        this.method = request;
    }

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }

    public int getMethodLength() throws UnsupportedEncodingException {
        return method.getBytes("UTF-8").length;
    }

    
}
