package com.specl.api.client;

import java.net.InetSocketAddress;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

public class APIClient {
    private static final Logger logger = Logger.getLogger(APIClient.class);

    public APIClient() {

        /*
         * session = future.getSession(); Request message = new Request(); message.setMethod("/board/getBoardList");
         * message.setParam("test"); session.write(message);
         */

    }

    public static <T> T getApiData(Class<T> clazz, String url, String jsonStr, String mothed)  {
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
        Request message = new Request();
        Request message2 = new Request();
        session.setAttribute("clazz", clazz);
        message.setMethod(url);
        message.setParam(jsonStr);
        
        message2.setMethod(url);
        message2.setParam(jsonStr);
        // message.setMethod("/board/getBoard");
        // Board b = new Board();
        // b.setBoardId(12);
        // message.setParam(JsonUtil.toJson(b));

        session.write(message);
        session.write(message2);
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
        if (m instanceof Result) {
            try {
                return decode(clazz, ((Result) m).getJson());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;

    }

    public static void main(String[] args) throws Exception {
        long now = System.currentTimeMillis();
        int thread_num = 8;
        int client_num = 10000;
        Board b = new Board();
        Card c = new Card();
        int[] ids = { 12, 13, 14, 630, 781, 880 };
        Random r = new Random();
        int[] cardids ={155,156,157,158,159,160,161,162,163,164,165,166,167,168,169,170,171,172,173,174,175};
        b.setBoardId(ids[r.nextInt(ids.length)]);
        c.setCardId(cardids[r.nextInt(cardids.length)]);

        
        logger.info(APIClient.getApiData(Board.class, "/board/getBoard", JsonUtil.toJson(b), "get"));
        logger.info(APIClient.getApiData(Card.class, "/card/getCardByCardId", JsonUtil.toJson(c), "get"));
        // BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
        // ThreadPoolExecutor executor = new ThreadPoolExecutor(8, 8, 1, TimeUnit.DAYS, queue);
        // for (int index = 0; index < client_num; index++) {
        //
        // Runnable run = new Runnable() {
        // public void run() {
        // try {
        // Board b = new Board();
        // int[] ids = { 12, 13, 14, 630, 781, 880 };
        // Random r = new Random();
        //
        // b.setBoardId(ids[r.nextInt(ids.length)]);
        // logger.info(APIClient.getApiData(Board.class, "/board/getBoard", JsonUtil.toJson(b), "get"));
        // // logger.info(ApiUtils.getApiData(Board.class, "/board/getBoard.json", JsonUtil.toJson(b),
        // // "get"));
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // }
        // };
        //
        // executor.execute(run);
        // }
        //
        // executor.shutdown();
        // executor.awaitTermination(1, TimeUnit.MINUTES);
        now = System.currentTimeMillis() - now;
        logger.info(now + " ms.");

        // logger.info(APIClient.getApiData(Board.class, "/board/addBoard", JsonUtil.toJson(map), ""));

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
