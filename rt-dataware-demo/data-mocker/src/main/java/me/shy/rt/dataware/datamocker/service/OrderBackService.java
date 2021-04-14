/**
 * @Date        : 2021-04-12 23:07:30
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 退单表 服务类
 */

package me.shy.rt.dataware.datamocker.service;

import com.baomidou.mybatisplus.extension.service.IService;

import me.shy.rt.dataware.datamocker.bean.OrderBack;

public interface OrderBackService extends IService<OrderBack> {
    public void  genRefundsOrFinish(Boolean isClear);
}
