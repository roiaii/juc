package com.mysqlapplicatiion;


import java.math.BigDecimal;
import java.util.BitSet;

/**
 * 基于数据库唯一索引实现幂等
 *
 * 场景：支付场景，防止重复支付，使用tradeNo做幂等
 *
 * create table pay_record(
 * id int auto increment primary key,
 * trade_no int unique not null, // 唯一索引，幂等键
 * order_id bigint not null,
 * amount decimal not null,
 * status varchar(32) not null, // 状态：success failed paying
 * )
 */
public class Mideng {

    public void processPayment(String tradeNo, String orderId, BigDecimal amount) {

        try {
            // 1. 插入支付流水，幂等校验
            // insert into pay_record (trade_no, order_id, amount, status) value (tradeNo, orderId, "paying")

        } catch (Exception e) {
            // 如果记录已经存在
            // select status from pay_record where trade_no = tradeNo and order_id = orderId

            String status = "";
            if (status.equals("success")) {
                // 重复支付，直接返回成功
                return;
            }

            // 如果是其他状态，重试或等待
        }

        // 2. 执行支付业务逻辑
        // 调用下游三方支付，拿到支付终态结果
        boolean payStatus = true;

        // 3. 更新数据库记录状态
        // update pay_record set status = payStatus ? "success" : "failed" where trade_no = tradeNo and order_id = orderId

    }
}
