package com.specl.api.controller;

import org.apache.mina.core.session.IoSession;

import com.specl.api.codec.Request;
import com.specl.api.codec.Result;

public abstract class AbstractController implements Controller {

    private IoSession session;

    public AbstractController(IoSession session) {
        this.session = session;
    }

    public void receive(Request data) {

        if (session.isConnected()) {
            session.write(processRequest(data));
            // session.close(true);
        }

    }

    public abstract Result processRequest(Request data);
}
