package com.specl.api.controller;

import com.specl.api.codec.Request;

public interface Controller {
    public void receive(Request data);
}
