package com.abin.lee.zookeeper.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * 新建一个类实现接口Watcher. 是指：
 This interface specifies the public interface an event handler class must implement.
 A ZooKeeper client will get various events from the ZooKeepr server it connects to.
 An application using such a client handles these events by registering a callback object with the client.
 The callback object is expected to be an instance of a class that implements Watcher interface.
 *
 *
 *
 */
public abstract class AbstractZooKeeper implements Watcher{

    private static Log log = LogFactory.getLog(AbstractZooKeeper.class.getName());

    //缓存时间
    private static final int SESSION_TIME   = 2000;
    protected ZooKeeper zooKeeper;
    protected CountDownLatch countDownLatch=new CountDownLatch(1);

    public void connect(String hosts) throws IOException, InterruptedException{
        zooKeeper = new ZooKeeper(hosts,SESSION_TIME,this);
        countDownLatch.await();
    }

    /* (non-Javadoc)
     * @see org.apache.zookeeper.Watcher#process(org.apache.zookeeper.WatchedEvent)
     */
    @Override
    public void process(WatchedEvent event) {
        // TODO Auto-generated method stub
        if(event.getState()== Event.KeeperState.SyncConnected){
            countDownLatch.countDown();
        }
    }

    public void close() throws InterruptedException{
        zooKeeper.close();
    }
}
