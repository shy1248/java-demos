/**
 * @Date        : 2021-04-12 23:07:30
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 商品库存表 服务类
 */

package me.shy.rt.dataware.demo.datamocker.businessdatamocker.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

import me.shy.rt.dataware.demo.datamocker.businessdatamocker.bean.SkuInfo;

public interface SkuInfoService extends IService<SkuInfo> {
    public SkuInfo getSkuInfoById(List<SkuInfo> skuInfoList, Long skuId);
}
