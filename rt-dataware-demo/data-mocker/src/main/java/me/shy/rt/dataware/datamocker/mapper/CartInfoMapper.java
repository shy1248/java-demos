/**
 * @Date        : 2021-04-13 20:22:09
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 购物车表 用户登录系统时更新冗余 Mapper 接口
 */

package me.shy.rt.dataware.datamocker.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

import me.shy.rt.dataware.datamocker.bean.CartInfo;

@Mapper
public interface CartInfoMapper extends BaseMapper<CartInfo> {
}
