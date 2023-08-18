/**
 * @Date        : 2021-04-13 21:24:38
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 库存单元表 服务实现类
 */

package me.shy.rt.dataware.datamocker.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import me.shy.rt.dataware.datamocker.bean.SkuInfo;
import me.shy.rt.dataware.datamocker.mapper.SkuInfoMapper;
import me.shy.rt.dataware.datamocker.service.SkuInfoService;

@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo> implements SkuInfoService {

    @Override
    public SkuInfo getSkuInfoById(List<SkuInfo> skuInfoList, Long skuId) {
        for (SkuInfo skuInfo : skuInfoList) {
            if (skuInfo.getId().equals(skuId)) {
                return skuInfo;
            }
        }
        return null;
    }
}
