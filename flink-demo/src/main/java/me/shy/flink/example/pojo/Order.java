/**
 * @Date        : 2021-04-02 23:16:54
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 订单
 */
package me.shy.flink.example.pojo;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.shy.flink.example.util.Util;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String orderId;
    private Integer userId;
    private Integer categoryId;
    private Integer goodsId;
    private Integer goodsCount;
    private BigDecimal amount;
    private Long eventTime;

    @Override
    public String toString() {
        return String.format("[oid=%s,uid=%d,cid=%d,gid=%s,c=%s,m=%s,t=%s]", orderId, userId, categoryId, goodsId,
                goodsCount, amount, Util.ts2ds(eventTime));
    }
}
