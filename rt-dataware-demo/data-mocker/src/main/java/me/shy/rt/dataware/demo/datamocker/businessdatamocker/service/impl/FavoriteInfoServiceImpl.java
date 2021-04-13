/**
 * @Date        : 2021-04-13 21:08:42
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 商品收藏表 服务实现类
 */

package me.shy.rt.dataware.demo.datamocker.businessdatamocker.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import me.shy.rt.dataware.demo.datamocker.businessdatamocker.bean.FavoriteInfo;
import me.shy.rt.dataware.demo.datamocker.businessdatamocker.mapper.FavoriteInfoMapper;
import me.shy.rt.dataware.demo.datamocker.businessdatamocker.service.FavoriteInfoService;

public class FavoriteInfoServiceImpl extends ServiceImpl<FavoriteInfoMapper, FavoriteInfo>
        implements FavoriteInfoService {

    @Override
    public void genFavors(Boolean isClear) {
    }
}
