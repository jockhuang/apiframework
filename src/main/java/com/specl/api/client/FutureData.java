/**
 * Specl.com Inc.
 * Copyright (c) 2010-2011 All Rights Reserved.
 */
package com.specl.api.client;

import java.util.Observable;
import java.util.Observer;

import com.specl.model.BaseEntity;

/**
 * 
 * @author jock
 */
public class FutureData implements Data, Observer {
    private Object mutex = new Object();

    /**
     * 存放真实数据，并且标志真正的数据是否已经准备完毕 被多线程享受 如果realData==null，表示数据还准备好
     * */
    private volatile RealData realData = null;

    /**
     * 查看真正的数据是否准备完毕
     * */
    public boolean isFinished() {
        return realData != null;
    }

    /**
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable realData, Object event) {
        if (!(realData instanceof RealData)) {
            throw new IllegalArgumentException("主题的数据类型必须是RealData");
        }
        if (!(event instanceof String)) {
            throw new IllegalArgumentException("事件的数据类型必须是String");
        }
        synchronized (mutex) {
            if (isFinished()) {
                mutex.notifyAll();
                return;// 如果数据已经准备好了，直接返回.
            }
            if ("Finished".equals(event)) {

                this.realData = (RealData) realData;// 数据准备好了的时候，便可以通知数据准备好了
                mutex.notifyAll();// 唤醒被阻塞的线程
            }
        }

    }

    /**
     * 如果数据已经准备好，则返回真正的数据； 否则，阻塞调用线程，直到数据准备完毕后，才返回真实数据；
     * 
     * @see com.specl.api.client.Data#getContent()
     */
    @Override
    public BaseEntity getContent() {
        synchronized (mutex) {
            while (!isFinished()) {// 只要数据没有准备完毕，就阻塞调用线程
                try {

                    mutex.wait(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return realData.getContent();
        }
    }

}
