/**
 * @Date        : 2021-04-12 23:07:30
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 购物车表 用户登录系统时更新冗余 服务类
 */

package me.shy.rt.dataware.demo.datamocker.businessdatamocker.service;

import com.baomidou.mybatisplus.extension.service.IService;

import me.shy.rt.dataware.demo.datamocker.businessdatamocker.bean.CartInfo;

public interface CartInfoService extends IService<CartInfo> {
    public void  genCartInfo(boolean ifClear);
}
