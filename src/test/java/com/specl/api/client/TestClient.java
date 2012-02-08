package com.specl.api.client;

import java.net.InetSocketAddress;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.session.IoSessionConfig;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.specl.model.Board;
import com.specl.utils.JsonUtil;

/**
 * @author litianyi
 * @version 2012-2-8
 */
public class TestClient {

    public TestClient(String message, GenericObjectPool pool) {

        try {
            // session = future.getSession();
            IoSession session = (IoSession) pool.borrowObject();
            session.write(message);
            ReadFuture readFuture = session.read();
            // // 阻塞线程 同步获得数据
            if (readFuture.awaitUninterruptibly(3000)) {
                System.out.println(readFuture.getMessage());
            }

            pool.returnObject(session);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public TestClient(String message, IoConnector connector) {

        try {
            IoSessionConfig cfg = connector.getSessionConfig();
            // 这个方法设置IoSession 的read()方法是否可用，默认是false。
            cfg.setUseReadOperation(true);
            ConnectFuture future = connector.connect(new InetSocketAddress("localhost", 8888));
            // 阻塞线程等待链接，使异步转变成同步
            future.awaitUninterruptibly();
            
            // session = future.getSession();
            IoSession session =future.getSession();
            session.write(message);
            ReadFuture readFuture = session.read();
            // // 阻塞线程 同步获得数据
            if (readFuture.awaitUninterruptibly(3000)) {
                System.out.println(readFuture.getMessage());
            }

            session.close(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        ConnectFuture future;
        IoConnector connector = new NioSocketConnector();
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        IoSessionConfig cfg = connector.getSessionConfig();
        // 这个方法设置IoSession 的read()方法是否可用，默认是false。
        cfg.setUseReadOperation(true);
        future = connector.connect(new InetSocketAddress("localhost", 8888));
        // 阻塞线程等待链接，使异步转变成同步
        future.awaitUninterruptibly();
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(8, 8, 1, TimeUnit.DAYS, queue);
        
        PoolableObjectFactory factory = new PoolFactory(connector);
        GenericObjectPool pool = new GenericObjectPool(factory, 10, GenericObjectPool.WHEN_EXHAUSTED_GROW, 10, true,
                true);

       
        for (int index = 0; index < 100; index++) {

            TestRunnable run = new TestRunnable(pool) ;
            
            executor.execute(run);
        }
        
        // pool.close();
         
         
        /*
        for (int index = 0; index < 100; index++) {

            TestRunnable2 run = new TestRunnable2(connector) ;
            
            executor.execute(run);
        }
        */
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.MINUTES);
        System.out.println("over!");
    }
    
}
class TestRunnable implements Runnable{
    GenericObjectPool pool;
    public TestRunnable(GenericObjectPool pool){
        this.pool = pool;
    }
    public void run() {
        Random r = new Random();
        new TestClient("HELLO" + r.nextInt(), pool);
    }
}

class TestRunnable2 implements Runnable{
    IoConnector future;
    public TestRunnable2(IoConnector pool){
        this.future = pool;
    }
    public void run() {
        Random r = new Random();
        new TestClient("HELLO" + r.nextInt(), future);
    }
}
