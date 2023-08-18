/**
 * @Date        : 2021-04-13 21:25:39
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 商品表 服务实现类
 */

package me.shy.rt.dataware.datamocker.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import me.shy.rt.dataware.datamocker.bean.SpuInfo;
import me.shy.rt.dataware.datamocker.mapper.SpuInfoMapper;
import me.shy.rt.dataware.datamocker.service.SpuInfoService;

@Service
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoMapper, SpuInfo> implements SpuInfoService {
}
