/**
 * @Date        : 2021-04-13 20:42:17
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 活动表 服务实现类
 */

package me.shy.rt.dataware.datamocker.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import me.shy.rt.dataware.datamocker.bean.ActivityInfo;
import me.shy.rt.dataware.datamocker.mapper.ActivityInfoMapper;
import me.shy.rt.dataware.datamocker.service.ActivityInfoService;

public class ActivityInfoServiceImpl extends ServiceImpl<ActivityInfoMapper, ActivityInfo>
        implements ActivityInfoService {
}
