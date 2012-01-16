package com.specl.api.codec;

import java.io.Serializable;

public class Result implements Serializable{
    private String json;

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
    
}
