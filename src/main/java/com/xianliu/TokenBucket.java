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
    private final long capacity; // 桶容量
    private final double refillRate; // 令牌生产速率
    private double tokens; // 令牌数量
    private long lastRefillTime; // 上次生成令牌时间

    public static void main(String[] args) throws InterruptedException {
        TokenBucket2 tokenBucket2 = new TokenBucket2(10, 1);

        for (int i=0; i<10; i++) {
            System.out.println("Request" + i + (tokenBucket2.acquireToken() ? "Accepted" : "Denied"));
            if (i == 5) {
                Thread.sleep(1000);
            }
        }
    }


    public TokenBucket2(long capacity, double refillRate) {
        this.capacity = capacity;
        this.refillRate = refillRate;
        this.lastRefillTime = System.currentTimeMillis();
    }

    // 请求获取令牌
    public synchronized boolean acquireToken() {
        // 先填充令牌
        refillTokens();
        if (tokens > 0) {
            tokens -= 1;
            return true;
        }
        return false;
    }

    // 填充令牌
    private void refillTokens() {
        long currentTime = System.currentTimeMillis();
        double times = (currentTime - lastRefillTime) / 1000.0;
        // 处理时钟回拨
        if (times < 0) {
            return;
        }
        double newTokens = times * refillRate;
        tokens = Math.min(newTokens + tokens, capacity); // 不能超过令牌桶容量
        lastRefillTime = currentTime;
        return;
    }
}
