package com.specl.api.client;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.specl.api.codec.Result;

public class ApiClientHandler extends IoHandlerAdapter {

    private static final Logger logger = Logger.getLogger(ApiClientHandler.class);
    private CallBack callback;

    public ApiClientHandler(CallBack callback) {
        this.callback = callback;
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        logger.info(message);
        if (message instanceof Result) {
            Class clazz = (Class) session.getAttribute("clazz");
            callback.decode(clazz, ((Result) message).getJson());
        }
        session.close(true);
    }
}
