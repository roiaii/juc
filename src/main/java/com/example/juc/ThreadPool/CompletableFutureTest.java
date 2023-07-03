package com.example.juc.ThreadPool;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
/*
public class CompletableFutureTest {
    public static void main(String[] args) throws InterruptedException {
        List<CompletableFuture<Double>> cfs = Lists.newArrayList();
        CompletableFuture<Double> cf = null;
        for(int i=0; i<10; i++) {
            cf = CompletableFuture.supplyAsync(CompletableFutureTest::fetchPrice);
            cfs.add(cf);
        }
        cfs.forEach(cf1->cf1.thenAccept(res -> {
            System.out.println("执行成功：" + res);
            cf1.exceptionally(e-> {
                        e.printStackTrace();
                        return null;
                    });
        }));
        Thread.sleep(1000000000);
    }
/*
    public static Double fetchPrice() {
        try {
            Thread.sleep(300);

        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        if (Math.random() < 0.3) {
            throw new RuntimeException("failed");
        }
        System.out.println("线程" + Thread.currentThread().getName());
        return 5 + Math.random() * 20;
    }
}

/**
 * demo
 * 场景：任务一依赖任务二的执行结果
 * 任务一内并发、任务二内并发
 * 任务一和任务二之间并发
 */
    /*
class ParallelCompletableFuture {
    public static void main(String[] args) throws Exception {
        // 两个CompletableFuture执行异步查询:
        List<CompletableFuture<String>> cfs = Collections.synchronizedList(Lists.newArrayList());
        CompletableFuture<String> cf1 = null;
        for (int i=0; i<30; i++) {
            cf1 = CompletableFuture.supplyAsync(() -> {
                return queryCode("中国石油", "https://finance.sina.com.cn/code/");
            });
            cfs.add(cf1);
        }


        // 用anyOf合并为一个新的CompletableFuture:
        CompletableFuture<Void> cfQuery = CompletableFuture.allOf(cfs.toArray(new CompletableFuture[cfs.size()]));

        CompletableFuture<String> cfsAll = cfQuery.thenApply(v->{
            return cfs.stream().map(cf11-> {
                try {
                    return cf11.get();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList());
        });


        List<CompletableFuture<Double>> cfs2 = Collections.synchronizedList(Lists.newArrayList());
        CompletableFuture<Double> cf2 = null;



        // 两个CompletableFuture执行异步查询:
        for (int i=0; i<1; i++) {
            cf2 = cfsAll.thenApplyAsync((code) -> {
                        return fetchPrice((String) code, "https://money.163.com/price/");
                    }
                );
            }
            cfs2.add(cf2);
        }


        // 用anyOf合并为一个新的CompletableFuture:
        CompletableFuture<Void> cfFetch = CompletableFuture.allOf(cfs2.toArray(new CompletableFuture[cfs2.size()]));

        // 最终结果:
        cfFetch.thenAccept((result) -> {
            System.out.println("price: " + result);
        });
        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:
        Thread.sleep(200000000);
    }

    static String queryCode(String name, String url) {
        System.out.println(Thread.currentThread().getName() + "query code from " + url + "...");
        try {
            Thread.sleep((long) (Math.random() * 10));
        } catch (InterruptedException e) {
        }
        return "601857";
    }

    static Double fetchPrice(String code, String url) {
        System.out.println(Thread.currentThread().getName() + "query price from " + url + "...");
        try {
            Thread.sleep((long) (Math.random() * 10));
        } catch (InterruptedException e) {
        }
        return 5 + Math.random() * 20;
    }
}
*/
