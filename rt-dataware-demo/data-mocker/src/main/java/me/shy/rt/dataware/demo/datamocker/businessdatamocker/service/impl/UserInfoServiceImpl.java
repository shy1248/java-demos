/**
 * @Date        : 2021-04-13 21:26:36
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 用户表 服务实现类
 */

package me.shy.rt.dataware.demo.datamocker.businessdatamocker.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import me.shy.rt.dataware.demo.datamocker.businessdatamocker.bean.UserInfo;
import me.shy.rt.dataware.demo.datamocker.businessdatamocker.mapper.UserInfoMapper;
import me.shy.rt.dataware.demo.datamocker.businessdatamocker.service.UserInfoService;

public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Override
    public void genUserInfos(Boolean isClear) {
    }
}
