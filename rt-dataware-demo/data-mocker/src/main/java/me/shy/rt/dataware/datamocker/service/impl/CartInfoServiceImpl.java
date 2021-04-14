/**
 * @Date        : 2021-04-13 20:56:04
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 购物车表 用户登录系统时更新冗余 服务实现类
 */

package me.shy.rt.dataware.datamocker.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import me.shy.rt.dataware.datamocker.bean.CartInfo;
import me.shy.rt.dataware.datamocker.mapper.CartInfoMapper;
import me.shy.rt.dataware.datamocker.service.CartInfoService;

public class CartInfoServiceImpl extends ServiceImpl<CartInfoMapper, CartInfo> implements CartInfoService {

    @Override
    public void genCartInfo(boolean ifClear) {
    }
}
