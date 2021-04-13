/**
 * @Date        : 2021-04-13 22:32:25
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 模拟生成业务数据库数据
 */

package me.shy.rt.dataware.demo.datamocker.businessdatamocker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import me.shy.rt.dataware.demo.datamocker.businessdatamocker.config.DatabaseMockerConfig;
import me.shy.rt.dataware.demo.datamocker.businessdatamocker.service.UserInfoService;

@Slf4j
@Component
@ComponentScan("me.shy.rt.dataware.demo.datamocker")
public class DatabaseMocker implements Runnable {

    // @Autowired(required = true)
    // UserInfoService UserInfoService;

    @Override
    public void run() {
        log.warn("Starting mock business data...");
        System.out.println(new DatabaseMockerConfig());
        // Boolean ifClear = ParamUtil.checkBoolean(this.ifClear);
        log.warn("--------开始生成用户数据--------");
        // userInfoService.genUserInfos(ParamUtil.checkBoolean(this.ifClearUser));
        log.warn("--------开始生成收藏数据--------");
        // favorInfoService.genFavors(ifClear);
        log.warn("--------开始生成购物车数据--------");
        // cartInfoService.genCartInfo(ifClear);
        log.warn("--------开始生成订单数据--------");
        // orderInfoService.genOrderInfos(ifClear);
        log.warn("--------开始生成支付数据--------");
        // paymentInfoService.genPayments(ifClear);
        log.warn("--------开始生成退单数据--------");
        // orderRefundInfoService.genRefundsOrFinish(ifClear);
        log.warn("--------开始生成评价数据--------");
        // commentInfoService.genComments(ifClear);
    }

}
