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
 * 坏处：没有业务含义，不做集群有可能单点故障
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

/**
 * 基于UUID来实现
 * UUID是一个32个16进制组成的字符串，一共128位，32 * 4（16进制的数由4位01串组成）
 * 好处：简单易用
 * 坏处：存储空间大，不连续，没有业务含义
 */


/**
 * 雪花算法
 * 由64位二进制数组成，被分成不同的部分，每个部分有不同的含义
 * 1位表示符号位，42位表示时间戳，5+5位表示机房和机器号，12表示序列号，其中序列号自增
 *
 *
 * 好处：连续自增，可以加入业务特征
 * 坏处：会有时钟活回拨问题，出现重复的id
 *
 * 解决
 *              * 1. 等待时钟恢复，实现简单，有可能会出现长时间无法恢复，长时间阻塞
 *              * 2. 使用逻辑时钟 + 物理时钟，预留出表示逻辑时钟的位，当出现时钟回拨，使用逻辑时钟填充逻辑时钟位。需要维护原子变量逻辑时钟，且逻辑时钟位数有限
 *              * 3. 使用zk持有节点记录上次生成令牌的时间，出现时钟回拨，读取持久化的令牌生成时间记录。
 */