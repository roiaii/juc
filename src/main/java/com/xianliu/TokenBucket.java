package com.xianliu;

import java.util.UUID;

/**
 * 实现简易版限流算法，令牌桶算法
 * 分析需求：
 * 1. 支持按照一定速率生产令牌
 * 2. 请求需要先获取令牌
 * 3. 注意并发控制，比如多个线程同时获取令牌时，要互斥访问令牌
 *
 * 需要维护的变量
 * refillRate 令牌程程速率
 * tokens 令牌数
 * lastRefillTime 上次填充令牌时的时间
 * capacity 令牌桶的容量
 *
 */
public class TokenBucket {
    private final long capacity;
    private final double refillRate;
    private double tokens;
    private long lastRefillTime;

    public TokenBucket(long capacity, double refillRate) {
        this.capacity = capacity;
        this.refillRate = refillRate;
        tokens = 0;
        lastRefillTime = System.currentTimeMillis();
    }

    // 获取令牌
    public synchronized boolean acquireToken() {
        // 先生成令牌
        refillTokens();
        if (tokens > 0) {
            tokens -= 1;
            return true;
        }
        return false;

    }

    // 生成令牌
    private void refillTokens() {
        long currentTime = System.currentTimeMillis();
        if (currentTime < lastRefillTime) { // 时钟回拨问题
            return;
        }
        double times = (currentTime - lastRefillTime) / 1000.0;
        double newTokens = refillRate * times;
        if (newTokens > 0) {
            tokens = Math.min(capacity, tokens + newTokens);
            lastRefillTime = currentTime;
        }

    }

    public static void main(String[] args) throws InterruptedException {
        TokenBucket tokenBucket = new TokenBucket(10, 1);
        TokenBucket bucket = new TokenBucket(10, 5);
        // UUID uuid = UUID.randomUUID();
        System.out.println(UUID.randomUUID().toString());
        // 模拟请求
        for (int i = 0; i < 15; i++) {
            System.out.println("Request " + i + ": " + (bucket.acquireToken() ? "Accepted" : "Denied"));
            if (i == 10) {
                Thread.sleep(1000); // 等待1秒补充令牌
            }
        }
    }
}

// 再写一遍
class TokenBucket2 {
    private final long capacity;
    private double tokens;
    private double refillRate;
    private long lastRefillTime;

    public TokenBucket2 (long capacity, double refillRate) {
        this.capacity = capacity;
        this.refillRate = refillRate;
        tokens = 0;
        lastRefillTime = System.currentTimeMillis();
    }

    //
    public synchronized boolean acquireToken() {
        refillTokens();
        if (tokens > 0) {
            tokens -= 1;
            return true;
        }
        return false;
    }

    private void refillTokens() {
        long curTime = System.currentTimeMillis();
        if (curTime < lastRefillTime) {
            // 时钟回拨
            /**
             * 解决
             * 1. 等待时钟恢复，实现简单，有可能会出现长时间无法恢复，长时间阻塞
             * 2. 使用逻辑时钟 + 物理时钟，预留出表示逻辑时钟的位，当出现时钟回拨，使用逻辑时钟填充逻辑时钟位。需要维护原子变量逻辑时钟，且逻辑时钟位数有限
             * 3. 使用zk持有节点记录上次生成令牌的时间，出现时钟回拨，读取持久化的令牌生成时间记录。
             */
            return;
        }
        double times = (curTime - lastRefillTime) / 1000;
        double newTokens = times * refillRate;
        tokens = Math.min(capacity, tokens + newTokens);
        lastRefillTime = curTime;
    }
}
