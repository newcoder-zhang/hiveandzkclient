package com.huawei.www.com.zk.www;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.List;

public class MyZK implements Watcher {
    public static String url = "beifeng:2181,beifeng1:2181,beifeng2:2181";

    public static String username;
    public static String passwd;
    public static String userNode = "/usernode";
    public static String passwdNode = "/pwdnode";
    private static ZooKeeper zk;
    private static MyZK watcher;
    public static List<String> userchildred;
    public static List<String> passchildred;


    @Override
    public void process(WatchedEvent event) {
        System.out.println(event.getType());
        try {
            if (event.getType() == Event.EventType.NodeChildrenChanged) {
                userchildred = zk.getChildren(userNode, true);
                passchildred = zk.getChildren(passwdNode, true);
                printChildNodeInfo();

            } else if (event.getType() == Event.EventType.NodeDataChanged) {
                //子节点的数据变化也会触发这个事件
                System.out.println(new String(zk.getData(userNode, true, null)));
                System.out.println(new String(zk.getData(passwdNode, true, null)));
                if(!(userchildred.isEmpty()&&passchildred.isEmpty())){
                    printChildNodeInfo();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printChildNodeInfo() throws KeeperException, InterruptedException {
        System.out.println(userchildred);
        System.out.println(passchildred);
        for (String userpath : userchildred
                ) {
            System.out.println(new String(zk.getData(userNode+"/"+userpath, true, null)));
        }
        for (String pwdpath : passchildred
                ) {
            System.out.println(new String(zk.getData(passwdNode+"/"+pwdpath, true, null)));
        }
    }

    /**
     * 获取数据的同时保持监听
     */
    public static void initData() {
        try {
            username = new String(zk.getData(userNode, true, null));
            passwd = new String(zk.getData(passwdNode, true, null));
            //只能监控子节点的有无,并不能监控子节点的数据变化
            userchildred = zk.getChildren(userNode, true);
            passchildred = zk.getChildren(passwdNode, true);
//            System.out.println(username);
//            System.out.println(passwd);
//            System.out.println(userchildred);
//            System.out.println(passchildred);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        watcher = new MyZK();
        zk = new ZooKeeper(url, 3000, watcher);
        while (zk.getState() != ZooKeeper.States.CONNECTED) {
            Thread.sleep(3000);
        }

        while (true) {
            initData();
           Thread.sleep(3000);
        }

    }

    public static void listPath(String path, ZooKeeper zk) throws KeeperException, InterruptedException {
        List<String> children = zk.getChildren(path, true);
        System.out.println(children);
    }
}
