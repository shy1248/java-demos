/**
 * @Date        : 2021-04-13 21:03:36
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 优惠券表 服务实现类
 */

package me.shy.rt.dataware.datamocker.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import me.shy.rt.dataware.datamocker.bean.CouponInfo;
import me.shy.rt.dataware.datamocker.mapper.CouponInfoMapper;
import me.shy.rt.dataware.datamocker.service.CouponInfoService;

@Service
public class CouponInfoServiceImpl extends ServiceImpl<CouponInfoMapper, CouponInfo> implements CouponInfoService {
}
