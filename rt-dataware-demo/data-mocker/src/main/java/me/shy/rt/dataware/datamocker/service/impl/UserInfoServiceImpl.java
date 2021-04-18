/**
 * @Date        : 2021-04-13 21:26:36
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 用户表 服务实现类
 */

package me.shy.rt.dataware.datamocker.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import me.shy.rt.dataware.datamocker.bean.UserInfo;
import me.shy.rt.dataware.datamocker.config.DataMockerConfig;
import me.shy.rt.dataware.datamocker.mapper.UserInfoMapper;
import me.shy.rt.dataware.datamocker.service.UserInfoService;
import me.shy.rt.dataware.datamocker.util.RandomEmail;
import me.shy.rt.dataware.datamocker.util.RandomName;
import me.shy.rt.dataware.datamocker.util.RandomNumeric;
import me.shy.rt.dataware.datamocker.util.RandomWeightOption;
import me.shy.rt.dataware.datamocker.util.WeightOption;

@Slf4j
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
    @Autowired
    DataMockerConfig config;
    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public void genUserInfos(Boolean isClear) {
        List<UserInfo> userInfos = new ArrayList<>();
        if (isClear) {
            userInfoMapper.truncate();
        } else {
            updateUserInfo(config.businessDate);
        }

        for (int i = 0; i < config.userLogonCount; i++) {
            userInfos.add(initUserInfo(config.businessDate));
        }

        saveBatch(userInfos, 1000);
        log.warn("共生成{}名用户信息。", userInfos.size());
    }

    private UserInfo initUserInfo(LocalDate businessDate) {
        UserInfo userInfo = new UserInfo();
        String email = RandomEmail.nextEmailAddress(6, 12);
        String loginName = email.split("@")[0];
        userInfo.setLoginName(loginName);
        userInfo.setEmail(email);
        userInfo.setGender(new RandomWeightOption<String>(new WeightOption<String>("M", config.maleUserRate),
                new WeightOption<String>("F", 100 - config.maleUserRate)).nextPayload());
        String lastName = RandomName.nextLastName(userInfo.getGender());
        userInfo.setName(RandomName.nextFamilyName() + lastName);
        userInfo.setNickName(RandomName.getNickName(userInfo.getGender(), lastName));
        userInfo.setBirthday(businessDate.minusMonths(12L * RandomNumeric.nextInteger(15, 55)));
        userInfo.setCreateTime(businessDate.atTime(LocalTime.now()));
        userInfo.setUserLevel(
                RandomWeightOption.<String>builder().add("1", 7).add("2", 2).add("3", 1).build().nextPayload());
        userInfo.setPhoneNumber("13" + RandomNumeric.nextString(1, 9, 9, ""));
        return userInfo;
    }

    private void updateUserInfo(LocalDate businessDate) {
        if (config.userInfoUpdateRate == 0) {
            return;
        }

        int allUserInfoCount = count(new QueryWrapper<UserInfo>());
        String willBeUpdatedUserIds = RandomNumeric.nextString(1, allUserInfoCount,
                allUserInfoCount * config.userInfoUpdateRate / 100, ", ", false);
        List<UserInfo> willBeUpdatedUserInfos = list(new QueryWrapper<UserInfo>().inSql("id", willBeUpdatedUserIds));
        for (UserInfo userInfo : willBeUpdatedUserInfos) {
            int randomInteger = RandomNumeric.nextInteger(2, 7);
            if (randomInteger % 2 == 0) {
                String lastName = RandomName.nextLastName(userInfo.getGender());
                userInfo.setNickName(RandomName.getNickName(userInfo.getGender(), lastName));
            } else if (randomInteger % 3 == 0) {
                userInfo.setUserLevel(
                        RandomWeightOption.<String>builder().add("1", 7).add("2", 2).add("3", 1).build().nextPayload());
            } else if (randomInteger % 5 == 0) {
                userInfo.setEmail(RandomEmail.nextEmailAddress(6, 12));
            } else if (randomInteger % 7 == 0) {
                userInfo.setPhoneNumber("13" + RandomNumeric.nextString(1, 9, 9, ""));
            }
            userInfo.setOperateTime(businessDate.atTime(LocalTime.now()));
        }
        saveOrUpdateBatch(willBeUpdatedUserInfos);
        log.warn("总共更新了{}名用户的信息。", willBeUpdatedUserInfos.size());
    }
}
