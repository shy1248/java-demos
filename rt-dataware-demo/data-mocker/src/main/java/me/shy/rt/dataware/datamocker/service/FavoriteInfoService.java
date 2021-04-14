/**
 * @Date        : 2021-04-12 23:07:30
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 商品收藏表 服务类
 */

package me.shy.rt.dataware.datamocker.service;

import com.baomidou.mybatisplus.extension.service.IService;

import me.shy.rt.dataware.datamocker.bean.FavoriteInfo;

public interface FavoriteInfoService extends IService<FavoriteInfo> {
    public void  genFavors(Boolean isClear);
}
