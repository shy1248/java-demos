/**
 * @Date        : 2021-04-13 20:14:55
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 活动与订单关联表 Mapper 接口
 */
package me.shy.rt.dataware.datamocker.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

import me.shy.rt.dataware.datamocker.bean.OrderDetailActivity;

@Mapper
public interface OrderDetailActivityMapper extends BaseMapper<OrderDetailActivity> {
}
