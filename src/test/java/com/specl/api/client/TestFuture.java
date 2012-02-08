package com.specl.api.client;

import java.net.InetSocketAddress;
import java.util.Random;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.session.IoSessionConfig;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.specl.api.codec.Request;
import com.specl.api.codec.Result;
import com.specl.model.Board;
import com.specl.model.Card;
import com.specl.utils.JsonUtil;

public class TestFuture {
    private static final Logger logger = Logger.getLogger(TestFuture.class);

    /**
     * 
     * @param args
     */
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        IoConnector connector;
        IoSession session;

        ConnectFuture future;

        connector = new NioSocketConnector();
        // connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        // ApiClientHandler handler = new ApiClientHandler(APIClient.this);
        // connector.setHandler(handler);
        // connector.setHandler(new ServiceProtocolHandler());
        IoSessionConfig cfg = connector.getSessionConfig();
        cfg.setUseReadOperation(true);
        future = connector.connect(new InetSocketAddress("localhost", 16111));

        future.awaitUninterruptibly();
        session = future.getSession();
        IoSession session1 = future.getSession();
        Board b = new Board();
        Card c = new Card();
        int[] ids = { 12, 13, 14, 630, 781, 880 };
        Random r = new Random();
        int[] cardids = { 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172,
                173, 174, 175 };
        b.setBoardId(ids[r.nextInt(ids.length)]);

        Request message = new Request();
        Request message2 = new Request();
        session.setAttribute("clazz", Board.class);
        logger.info("boardId1:" + b.getBoardId());
        message.setMethod("/board/getBoard");
        message.setParam(JsonUtil.toJson(b));

        b.setBoardId(ids[r.nextInt(ids.length)]);
        logger.info("boardId2:" + b.getBoardId());
        message2.setMethod("/board/getBoard");
        message2.setParam(JsonUtil.toJson(b));
        // message.setMethod("/board/getBoard");
        // Board b = new Board();
        // b.setBoardId(12);
        // message.setParam(JsonUtil.toJson(b));

        session.write(message);
        session1.write(message2);
        ReadFuture readFuture = session.read();
        Object m = null;
        if (readFuture.awaitUninterruptibly(3000)) {
            m = readFuture.getMessage();
            logger.info("m1:" + ((Result) m).getJson());

        }
        ReadFuture readFuture1 = session1.read();
        if (readFuture1.awaitUninterruptibly(3000)) {
        Object m2 = readFuture1.getMessage();
        logger.info("m2:" + ((Result) m2).getJson());
        }
        session.close(true);
        session.getService().dispose(false);
    }

}
