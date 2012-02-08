/**
 * Specl.com Inc.
 * Copyright (c) 2010-2011 All Rights Reserved.
 */
package com.specl.api.client;

import java.net.InetSocketAddress;
import java.util.Observable;

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

import com.specl.api.codec.Request;
import com.specl.api.codec.Result;
import com.specl.model.BaseEntity;
import com.specl.utils.JsonUtil;

/**
 * 
 * @author jock
 */
public class RealData extends Observable implements Data {

    private static final Logger logger = Logger.getLogger(RealData.class);

    private BaseEntity content;

    /**
     * @see com.specl.api.client.Data#getContent()
     */
    @Override
    public BaseEntity getContent() {
        return content;
    }

    public void createRealData(Class<? extends BaseEntity> clazz, String url, String jsonStr) {
        logger.debug("get RealData(" + url + ", " + jsonStr + ") BEGIN");
        IoConnector connector;
        IoSession session;

        ConnectFuture future;

        connector = new NioSocketConnector();
        //connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        // ApiClientHandler handler = new ApiClientHandler(APIClient.this);
        // connector.setHandler(handler);
        // connector.setHandler(new ServiceProtocolHandler());
        IoSessionConfig cfg = connector.getSessionConfig();
        cfg.setUseReadOperation(true);
        try {
            future = connector.connect(new InetSocketAddress("localhost", 16111));

            future.awaitUninterruptibly();
            session = future.getSession();
            Request message = new Request();

            message.setMethod(url);
            message.setParam(jsonStr);

            // message.setMethod("/board/getBoard");
            // Board b = new Board();
            // b.setBoardId(12);
            // message.setParam(JsonUtil.toJson(b));

            session.write(message);
            ReadFuture readFuture = session.read();
            Object m = null;
            if (readFuture.awaitUninterruptibly(3000)) {
                m = readFuture.getMessage();

            }
            session.close(true);
            session.getService().dispose(false);

            logger.debug("get RealData(" + url + ", " + jsonStr + ") END");
            if (m instanceof Result) {

                try {
                    this.content = decode(clazz, ((Result) m).getJson());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                this.content = null;
            }
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        // 真实数据准备完毕了，通知FutureData2说数据已经准备好了.
        setChanged();// 必须先设置本对象的状态发生了变化，并且通知所有的观察者
        notifyObservers("Finished");
    }

    public static <T> T decode(Class<T> clazz, String returnStr) throws Exception {

        String result = JsonUtil.jsonToObject(returnStr, "result", String.class);
        if ("1".equals(result)) {
            return JsonUtil.jsonToObject(returnStr, "data", clazz);
        } else {
            String errorCode = "";
            String errorMessage = "";
            try {
                errorCode = JsonUtil.jsonToObject(returnStr, "errorCode", String.class);
                errorMessage = JsonUtil.jsonToObject(returnStr, "errorMessage", String.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
