package com.specl.api.controller;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.specl.api.codec.Request;

public class ServiceProtocolHandler extends IoHandlerAdapter {

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        
        
        if (message instanceof Request) {

            Request rq = (Request) message;
            
            Controller endpoint = new ApiBaseController(session);
            
            endpoint.receive(rq);

        }
        super.messageReceived(session, message);
        session.close(false);
    }

}
