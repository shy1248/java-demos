/**
 * @Date        : 2021-04-13 20:28:06
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 商品收藏表 Mapper 接口
 */

package me.shy.rt.dataware.datamocker.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

import me.shy.rt.dataware.datamocker.bean.FavoriteInfo;

@Mapper
public interface FavoriteInfoMapper extends BaseMapper<FavoriteInfo> {
}
