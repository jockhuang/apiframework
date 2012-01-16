package com.specl.api.client;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class TestHandler extends IoHandlerAdapter {

    private static final Logger logger = Logger.getLogger(TestHandler.class);

   

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        logger.info("message received:"+message);
        
    }
}
