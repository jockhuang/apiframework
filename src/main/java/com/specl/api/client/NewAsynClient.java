package com.specl.api.client;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.specl.model.BaseEntity;
import com.specl.model.Board;
import com.specl.model.Card;
import com.specl.utils.JsonUtil;

public class NewAsynClient {
    private static final Logger logger = Logger.getLogger(NewAsynClient.class);

    private static BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
    // TODO how to shutdown?
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(8, 8, 1, TimeUnit.DAYS, queue);

    public static Future request(final Class<? extends BaseEntity> clazz, final String url, final String jsonStr) {
        logger.debug("request(" + url + ", " + jsonStr + ") BEGIN");

        FutureTask<BaseEntity> future =
                new FutureTask<BaseEntity>(new Callable<BaseEntity>() {
                  public BaseEntity call() {
                    try {
                        return APIClient.getApiData(clazz, url, jsonStr, "get");
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        return null;
                    }
                }});
       
        executor.execute(future);
        logger.debug("request(" + url + ", " + jsonStr + ") END");

        // (3) 取回FutureData实体，作为传回值
        return future;
    }

    /**
     * 
     * @param args
     */
    public static void main(String[] args) throws Exception {
        long now = System.currentTimeMillis();
        Board b = new Board();
        Card c = new Card();
        int[] ids = { 12, 13, 14, 630, 781, 880 };
        Random r = new Random();
        int[] cardids ={155,156,157,158,159,160,161,162,163,164,165,166,167,168,169,170,171,172,173,174,175};
//        b.setBoardId(ids[r.nextInt(ids.length)]);
//        c.setCardId(cardids[r.nextInt(cardids.length)]);
//        Future<BaseEntity> data1 = NewAsynClient.request(Board.class, "/board/getBoard", JsonUtil.toJson(b));
//        Future<BaseEntity> data2 = NewAsynClient.request(Card.class, "/card/getCardByCardId", JsonUtil.toJson(c));
//        logger.info(data1.get());
//        logger.info(data2.get());
        int client_num = 10000;

        Future[] ds = new Future[client_num];
        for (int index = 0; index < client_num; index++) {
            b.setBoardId(ids[r.nextInt(ids.length)]);
            ds[index] = NewAsynClient.request(Board.class, "/board/getBoard", JsonUtil.toJson(b));
        }
        for (int index = 0; index < client_num; index++) {
            logger.info(ds[index].get());
        }
         
        now = System.currentTimeMillis() - now;
        logger.info(now + " ms.");


    }

}
