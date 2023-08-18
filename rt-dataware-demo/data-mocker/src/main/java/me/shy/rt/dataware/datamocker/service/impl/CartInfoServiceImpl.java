/**
 * @Date        : 2021-04-13 20:56:04
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 购物车表 用户登录系统时更新冗余 服务实现类
 */

package me.shy.rt.dataware.datamocker.service.impl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import me.shy.rt.dataware.datamocker.bean.CartInfo;
import me.shy.rt.dataware.datamocker.bean.SkuInfo;
import me.shy.rt.dataware.datamocker.bean.UserInfo;
import me.shy.rt.dataware.datamocker.config.DataMockerConfig;
import me.shy.rt.dataware.datamocker.constant.Constant;
import me.shy.rt.dataware.datamocker.mapper.CartInfoMapper;
import me.shy.rt.dataware.datamocker.service.CartInfoService;
import me.shy.rt.dataware.datamocker.service.SkuInfoService;
import me.shy.rt.dataware.datamocker.service.UserInfoService;
import me.shy.rt.dataware.datamocker.util.RandomNumeric;
import me.shy.rt.dataware.datamocker.util.RandomWeightOption;

@Slf4j
@Service
public class CartInfoServiceImpl extends ServiceImpl<CartInfoMapper, CartInfo> implements CartInfoService {

    @Autowired
    DataMockerConfig config;
    @Autowired
    SkuInfoService skuInfoService;
    @Autowired
    UserInfoService userInfoService;

    @Override
    public void genCartInfo(boolean isClear) {
        if (isClear) {
            remove(new QueryWrapper<>());
        }
        List<SkuInfo> skuInfos = skuInfoService.list(new QueryWrapper<>());
        List<UserInfo> userInfos = userInfoService.list(new QueryWrapper<>());
        List<CartInfo> existCartInfos = this.list(new QueryWrapper<>());
        List<CartInfo> willBeAddedCartInfos = new ArrayList<>();
        for (UserInfo userInfo : userInfos) {
            if (!RandomWeightOption.<Boolean>builder().add(true, config.perUserInCartRate)
                    .add(false, 100 - config.perUserInCartRate).build().nextPayload()) {
                continue;
            }
            Set<Integer> alreadyInCartSkuIndexes = new HashSet<>();
            for (int i = 0; i < config.maxSkuTypeInCart; i++) {
                int skuIndex = RandomNumeric.nextInteger(0, skuInfos.size() - 1);
                boolean isSuccess = alreadyInCartSkuIndexes.add(skuIndex);
                if (isSuccess) {
                    SkuInfo skuInfo = skuInfos.get(skuIndex);
                    CartInfo cartInfo = initCartInfo(skuInfo, userInfo.getId(),
                            config.businessDate.atTime(LocalTime.now()));
                    cartInfo = checkAndMergeCart(cartInfo, existCartInfos);
                    willBeAddedCartInfos.add(cartInfo);
                }
            }
        }
        saveBatch(willBeAddedCartInfos, 1000);
        log.warn("共生成{}条添加购物车数据。", willBeAddedCartInfos.size());
    }

    private CartInfo checkAndMergeCart(CartInfo cartInfo, List<CartInfo> existCartInfos) {
        for (CartInfo exist : existCartInfos) {
            if (cartInfo.getUserId().equals(exist.getUserId()) && cartInfo.getSkuId().equals(exist.getSkuId())) {
                exist.setSkuNumber(exist.getSkuNumber() + cartInfo.getSkuNumber());
                return exist;
            }
        }
        return cartInfo;
    }

    private CartInfo initCartInfo(SkuInfo skuInfo, Long userId, LocalDateTime createDateTime) {
        CartInfo cartInfo = new CartInfo();
        Integer[] cartSourceTypeRates = config.sourceTypeOfInCartRates;
        String sourceType = RandomWeightOption.<String>builder().add(Constant.SOURCE_TYPE_QUREY, cartSourceTypeRates[0])
                .add(Constant.SOURCE_TYPE_PROMOTION, cartSourceTypeRates[1])
                .add(Constant.SOURCE_TYPE_AUTO_RECOMMEND, cartSourceTypeRates[2])
                .add(Constant.SOURCE_TYPE_ACTIVITY, cartSourceTypeRates[3]).build().nextPayload();
        cartInfo.setSkuId(skuInfo.getId());
        cartInfo.setSkuName(skuInfo.getSkuName());
        cartInfo.setSkuNumber(RandomNumeric.nextInteger(1, config.maxSkuNumberInCart) + 0L);
        cartInfo.setCartPrice(skuInfo.getPrice());
        cartInfo.setUserId(userId);
        cartInfo.setImgUrl(skuInfo.getSkuDefaultImg());
        cartInfo.setIsOrdered(0);
        cartInfo.setCreateTime(createDateTime);
        cartInfo.setSourceType(sourceType);
        if (sourceType.equals(Constant.SOURCE_TYPE_PROMOTION)) {
            cartInfo.setSourceId(RandomNumeric.nextInteger(10, 100) + 0L);
        } else if (sourceType.equals(Constant.SOURCE_TYPE_ACTIVITY)) {
            cartInfo.setSourceId(RandomNumeric.nextInteger(1, config.maxActivityCount) + 0L);
        }
        return cartInfo;
    }
}
