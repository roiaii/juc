package com.idgenerator;

/**
 * 模拟实现分布式ID
 * 基于数据库的号段模式
 *
 * 需求分析
 * 1. 每次从数据获取一个号段，避免频繁访问数据库
 * 2. 注意并发安全问题，采用cas解决
 * 3. 单点故障，数据库做集群，做主从读写分离
 * 4. 应用获取到号段后宕机，避免号段浪费问题
 *
 * 综上实现高性能高可用的分布式id服务
 *
 *
 * 库表设计
 * CREATE TABLE id_generator (
 *   id INT PRIMARY KEY AUTO_INCREMENT,
 *   biz_type VARCHAR(50) NOT NULL COMMENT '业务类型',  -- 如 order、user 等
 *   max_id BIGINT NOT NULL COMMENT '当前最大ID',
 *   step INT NOT NULL COMMENT '号段步长',             -- 每次分配的ID数量
 *   version BIGINT NOT NULL DEFAULT 0 COMMENT '版本号(乐观锁)',
 *   UNIQUE KEY (biz_type)
 * );
 *
 *
 */
public class IDGenerator {
    private long step;
    private volatile long currentId;
    private volatile long maxId;
    private String bizType;

    // 获取id
    public synchronized long nextId() {
        if (currentId < maxId) {
            return ++currentId;
        }
        // 号段用完，获取新的号段
        acquireNextSegment();
        return nextId();
    }

    private void acquireNextSegment() {
        try {
            // 1. 读取数据库，获取当前业务类型的号段
            // select max_id, step, version from id_generator where biz_type = BizType;
            long newMaxId = 1111; // mock 数据库数据
            step = 5;
            long version = 1;

            // 2. 计算新号段
            maxId = newMaxId + step;

            // 3. 尝试更新数据库
            // update max_id = maxId, step = step, version = version + 1, where version = version and biz_type = bizType;

            // 异常处理
            boolean isSuccess = false;
            // 如果更新失败，重试
            if(isSuccess) {
                acquireNextSegment();
            } else {
                this.currentId = newMaxId;
                step = step;
                maxId = maxId;
            }
        } catch (Exception e) {
            throw new RuntimeException("获取号段失败", e);
        }
    }

}
