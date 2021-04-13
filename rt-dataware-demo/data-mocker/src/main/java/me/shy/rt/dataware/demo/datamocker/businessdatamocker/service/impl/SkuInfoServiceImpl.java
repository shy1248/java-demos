/**
 * @Date        : 2021-04-13 21:24:38
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 库存单元表 服务实现类
 */

package me.shy.rt.dataware.demo.datamocker.businessdatamocker.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import me.shy.rt.dataware.demo.datamocker.businessdatamocker.bean.SkuInfo;
import me.shy.rt.dataware.demo.datamocker.businessdatamocker.mapper.SkuInfoMapper;
import me.shy.rt.dataware.demo.datamocker.businessdatamocker.service.SkuInfoService;

public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo> implements SkuInfoService {

    @Override
    public SkuInfo getSkuInfoById(List<SkuInfo> skuInfoList, Long skuId) {
        return null;
    }
}
