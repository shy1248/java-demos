/**
 * @Date        : 2021-04-13 21:08:42
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 商品收藏表 服务实现类
 */

package me.shy.rt.dataware.datamocker.service.impl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import me.shy.rt.dataware.datamocker.bean.FavoriteInfo;
import me.shy.rt.dataware.datamocker.config.DataMockerConfig;
import me.shy.rt.dataware.datamocker.mapper.FavoriteInfoMapper;
import me.shy.rt.dataware.datamocker.mapper.SkuInfoMapper;
import me.shy.rt.dataware.datamocker.mapper.UserInfoMapper;
import me.shy.rt.dataware.datamocker.service.FavoriteInfoService;
import me.shy.rt.dataware.datamocker.util.RandomNumeric;
import me.shy.rt.dataware.datamocker.util.RandomWeightOption;

@Slf4j
@Service
public class FavoriteInfoServiceImpl extends ServiceImpl<FavoriteInfoMapper, FavoriteInfo>
        implements FavoriteInfoService {

    @Autowired
    DataMockerConfig config;
    @Autowired
    FavoriteInfoMapper favoriteInfoMapper;
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    SkuInfoMapper skuInfoMapper;

    @Override
    public void genFavoriteSkus(Boolean isClear) {
        if (isClear) {
            remove(new QueryWrapper<>());
        }
        Integer totalUserInfos = userInfoMapper.selectCount(new QueryWrapper<>());
        Integer totalSkuInfos = skuInfoMapper.selectCount(new QueryWrapper<>());
        List<FavoriteInfo> favoriteInfos = new ArrayList<>();
        for (int i = 0; i < config.totalFavoriteCount; i++) {
            FavoriteInfo favoriteInfo = initFavoriteInfo(RandomNumeric.nextInteger(1, totalUserInfos),
                    RandomNumeric.nextInteger(1, totalSkuInfos));
            favoriteInfos.add(favoriteInfo);
        }
        saveBatch(favoriteInfos, 1000);
        log.warn("共生成{}条收藏数据。", favoriteInfos.size());
    }

    private FavoriteInfo initFavoriteInfo(Integer userId, Integer skuId) {
        FavoriteInfo favoriteInfo = new FavoriteInfo();
        Boolean isCancelFavorite = RandomWeightOption.<Boolean>builder().add(Boolean.TRUE, config.cancelFavoritedRate)
                .add(Boolean.FALSE, 100 - config.cancelFavoritedRate).build().nextPayload();
        favoriteInfo.setUserId(userId.longValue());
        favoriteInfo.setSkuId(skuId.longValue());
        favoriteInfo.setIsCancel(isCancelFavorite == Boolean.TRUE ? "1" : "0");
        LocalDateTime operatedDateTime = config.businessDate.atTime(LocalTime.now());
        if (isCancelFavorite) {
            favoriteInfo.setCancelTime(operatedDateTime);
        } else {
            favoriteInfo.setCreateTime(operatedDateTime);
        }
        return favoriteInfo;
    }
}
