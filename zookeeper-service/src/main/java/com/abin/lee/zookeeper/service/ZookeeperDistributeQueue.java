package com.abin.lee.zookeeper.service;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * Created by abin on 2017/10/27 2017/10/27.
 * zookeeper-svr
 * com.abin.lee.zookeeper.service
 */
public class ZookeeperDistributeQueue {


    public static void doOne() throws Exception {
        String host1 = "127.0.0.1:2181";
//        String host1 = "172.16.2.146:2181";
        ZooKeeper zk = connection(host1);
        initQueue(zk);
        joinQueue(zk, 1);
        joinQueue(zk, 2);
        joinQueue(zk, 3);
        zk.close();
    }
    //创建一个与服务器的连接
    public static ZooKeeper connection(String host) throws IOException {
        ZooKeeper zk = new ZooKeeper(host, 60000, new Watcher() {
            // 监听/queue/start创建的事件
            public void process(WatchedEvent event) {
                if (event.getPath() != null && event.getPath().equals("/queue/start") && event.getType() == Event.EventType.NodeCreated) {
                    System.out.println("Queue has Completed.Finish testing!!!");
                }
            }
        });
        return zk;
    }
    //出始化队列
    public static void initQueue(ZooKeeper zk) throws KeeperException, InterruptedException {
        System.out.println("WATCH => /queue/start");
        zk.exists("/queue/start", true);

        if (zk.exists("/queue", false) == null) {
            System.out.println("create /queue task-queue");
            zk.create("/queue", "task-queue".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } else {
            System.out.println("/queue is exist!");
        }
    }
    //增加队列节点
    public static void joinQueue(ZooKeeper zk, int x) throws KeeperException, InterruptedException {
        System.out.println("create /queue/x" + x + " x" + x);
        zk.create("/queue/x" + x, ("x" + x).getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        isCompleted(zk);
    }
    //检查队列是否完整
    public static void isCompleted(ZooKeeper zk) throws KeeperException, InterruptedException {
        int size = 3;
        int length = zk.getChildren("/queue", true).size();

        System.out.println("Queue Complete:" + length + "/" + size);
        if (length >= size) {
            System.out.println("create /queue/start start");
            zk.create("/queue/start", "start".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        }
    }
   // 启动函数main
    public static void main(String[] args) throws Exception {
        doOne();
    }






}
