package com.specl.api.client;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.specl.model.BaseEntity;
import com.specl.model.Board;
import com.specl.model.Card;
import com.specl.utils.JsonUtil;

public class ApiAsynClient {
    
    private static final Logger logger = Logger.getLogger(ApiAsynClient.class);
    
    private static BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
    //TODO how to shutdown?
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(8, 8, 1, TimeUnit.DAYS, queue);
    
    public static Data request(final Class<? extends BaseEntity> clazz, final String url, final String jsonStr) {
        logger.debug("request(" + url + ", " + jsonStr + ") BEGIN");

        // (1) 建立FutureData的实体
        final FutureData future = new FutureData();

        // (2) 为了建立RealData的实体，启动新的线程
        Runnable run = new Runnable() {
            public void run() {
                RealData realdata = new RealData();
                realdata.addObserver(future);// 以便当RealData把数据准备完毕后，通过该回调口子，通知FutureData表示数据已经贮备好了
                realdata.createRealData(clazz, url, jsonStr);
            }

           
        };
        executor.execute(run);
        logger.debug("request(" + url + ", " + jsonStr + ") END");

        // (3) 取回FutureData实体，作为传回值
        return future;
    }
    @Override
    protected void finalize() throws Throwable {
        executor.shutdown();
        super.finalize();
    }
    public static void main(String[] args) throws Exception {
        long now = System.currentTimeMillis();
        Board b = new Board();
        Card c = new Card();
        int[] ids = { 12, 13, 14, 630, 781, 880 };
        Random r = new Random();
        int[] cardids ={155,156,157,158,159,160,161,162,163,164,165,166,167,168,169,170,171,172,173,174,175};
        b.setBoardId(ids[r.nextInt(ids.length)]);
        c.setCardId(cardids[r.nextInt(cardids.length)]);
//        Data data1 = ApiAsynClient.request(Board.class, "/board/getBoard", JsonUtil.toJson(b));
//        Data data2 = ApiAsynClient.request(Card.class, "/card/getCardByCardId", JsonUtil.toJson(c));
//        logger.info(data1.getContent());
//        logger.info(data2.getContent());
        int client_num = 10000;

        Data[] ds = new Data[client_num];
        for (int index = 0; index < client_num; index++) {
            b.setBoardId(ids[r.nextInt(ids.length)]);
            ds[index] = ApiAsynClient.request(Board.class, "/board/getBoard", JsonUtil.toJson(b));
        }
        for (int index = 0; index < client_num; index++) {
            logger.info(ds[index].getContent());
        }
         
        now = System.currentTimeMillis() - now;
        logger.info(now + " ms.");
    }

}
