package com.specl.api.client;

import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.session.IoSessionConfig;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public final class TestClient {
    private static final Logger logger = Logger.getLogger(TestClient.class);
    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        IoConnector connector;
        IoSession session;

        ConnectFuture future;

        connector = new NioSocketConnector();
        connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        // ApiClientHandler handler = new ApiClientHandler(APIClient.this);
        // connector.setHandler(handler);
        // connector.setHandler(new ServiceProtocolHandler());
        IoSessionConfig cfg = connector.getSessionConfig();
        cfg.setUseReadOperation(true);
        future = connector.connect(new InetSocketAddress("localhost", 1234));

        future.awaitUninterruptibly();
        session = future.getSession();
        
        // message.setMethod("/board/getBoard");
        // Board b = new Board();
        // b.setBoardId(12);
        // message.setParam(JsonUtil.toJson(b));
        Integer[] ids = new Integer[10000000];
        session.write(ids);
       
        ReadFuture readFuture = session.read();
        Object m = null;
        if (readFuture.awaitUninterruptibly(3000)) {
            m = readFuture.getMessage();
            logger.info("m1:"+m);
            Object m2 = readFuture.getMessage();
            logger.info("m2:"+m2);
        }
        session.close(true);
        session.getService().dispose(false);
       
    }

}
