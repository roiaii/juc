package zookeeper;

import org.apache.curator.CuratorZookeeperClient;
import org.apache.curator.RetryPolicy;
import org.apache.curator.RetrySleeper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.WatcherRemoveCuratorFramework;
import org.apache.curator.framework.api.*;
import org.apache.curator.framework.api.transaction.CuratorMultiTransaction;
import org.apache.curator.framework.api.transaction.CuratorTransaction;
import org.apache.curator.framework.api.transaction.TransactionOp;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.listen.Listenable;
import org.apache.curator.framework.schema.SchemaSet;
import org.apache.curator.framework.state.ConnectionStateErrorPolicy;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.EnsurePath;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.server.quorum.flexible.QuorumVerifier;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class TestCurator {
    public static void main(String[] args) {
        //启动zk
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.1:2181", retryPolicy);
        client.start();

        //创建结点 /myznode
        try {
            client.create().forPath("/myznode","123".getBytes());
        }catch(Exception e){
            e.printStackTrace();
        }

        //关闭客户端
        client.close();


    }
}
