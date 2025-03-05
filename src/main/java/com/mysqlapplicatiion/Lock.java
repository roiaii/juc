package com.mysqlapplicatiion;

import org.springframework.beans.factory.annotation.Autowired;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;

/**
 * 基于数据库的唯一索引实现分布式锁
 * 加锁，如果唯一性标识已经存在，则加锁失败，不存在，则插入。该操作满足互斥性，原子性（唯一索引的特性）类似Redis中的原子性命令 SETNX命令
 * 释放锁，需要释放该客户端加的那把锁
 *
 * 具体场景：在下单前需要获取订单锁，基于数据库的唯一索引实现
 *
 * create table order_lock (
 * order_id varchar(50) primary key, // 订单id，唯一性标识，唯一索引
 * locked_by varchar(50) not null comment '客户端唯一标识',
 * expire_time bigint not null comment '过期时间'
 *
 * )
 */
public class Lock {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 加锁
    public boolean tryLock(String orderId, String clientId, long expireSeconds) {
        long now = System.currentTimeMillis();
        long expireTime = now + expireSeconds * 1000;

        try {
            // jdbcTemplate.update()
            // insert into order_lock (order_id, locked_by, expire_time) values (orderId, clientId, expireTime)
            return true; // 唯一主键不存在，插入成功
        } catch (DuplicateKeyException e) {
            // 插入失败
            // 检查是否过期
            // select expire_time from order_lock where order_id = orderId and locked_by = clientId
            long expire_time = 0;
            if (now > expire_time) {
                // 过期，尝试重新获取锁
                // jdbcTemplate.update();
                // 如果重试成功，返回成功，否则插入加锁失败
            }
            return false; // 锁未过期，加锁失败

        }
    }

    // 释放锁
    public boolean releaseLock(String orderId, String clientId) {
        // delete from order_lock where order_id = orderId and locked_by = clientId
        return true;
    }

}

class JdbcTemplate {

}
