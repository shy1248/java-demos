/**
 * @Date        : 2021-04-13 20:57:30
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 商品评论表 服务实现类
 */

package me.shy.rt.dataware.datamocker.service.impl;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import me.shy.rt.dataware.datamocker.bean.CommentInfo;
import me.shy.rt.dataware.datamocker.bean.OrderDetail;
import me.shy.rt.dataware.datamocker.bean.OrderInfo;
import me.shy.rt.dataware.datamocker.bean.SkuInfo;
import me.shy.rt.dataware.datamocker.config.DataMockerConfig;
import me.shy.rt.dataware.datamocker.constant.Constant;
import me.shy.rt.dataware.datamocker.mapper.CommentInfoMapper;
import me.shy.rt.dataware.datamocker.mapper.UserInfoMapper;
import me.shy.rt.dataware.datamocker.service.CommentInfoService;
import me.shy.rt.dataware.datamocker.service.OrderInfoService;
import me.shy.rt.dataware.datamocker.util.RandomNumeric;
import me.shy.rt.dataware.datamocker.util.RandomWeightOption;

@Slf4j
@Service
public class CommentInfoServiceImpl extends ServiceImpl<CommentInfoMapper, CommentInfo> implements CommentInfoService {

    @Autowired
    DataMockerConfig config;
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    OrderInfoService orderInfoService;

    @Override
    public void genComments(Boolean isClear) {
        if (isClear) {
            remove(new QueryWrapper<>());
        }

        Integer userTotal = userInfoMapper.selectCount(new QueryWrapper<>());

        List<CommentInfo> commentInfos = new ArrayList<>();
        List<OrderInfo> orderInfosFinished = orderInfoService
                .listWithDetail(new QueryWrapper<OrderInfo>().eq("order_status", Constant.ORDER_STATUS_FINISH), true);
        for (OrderInfo orderInfo : orderInfosFinished) {
            for (OrderDetail orderDetail : orderInfo.getOrderDetailList()) {
                Long userId = RandomNumeric.nextInteger(1, userTotal) + 0L;
                commentInfos.add(initCommentInfo(orderDetail.getSkuInfo(), orderInfo, userId));
            }
        }
        saveBatch(commentInfos, 1000);
        log.warn("共生成{}条评价", commentInfos.size());
    }

    private CommentInfo initCommentInfo(SkuInfo skuInfo, OrderInfo orderInfo, Long userId) {
        RandomWeightOption<String> commentGradeOption = RandomWeightOption.<String>builder()
                .add(Constant.COMMENT_GRADE_GOOD, config.commentGradeRates[0])
                .add(Constant.COMMENT_GRADE_SOSO, config.commentGradeRates[1])
                .add(Constant.COMMENT_GRADE_BAD, config.commentGradeRates[2])
                .add(Constant.COMMENT_GRADE_AUTO, config.commentGradeRates[3]).build();

        CommentInfo commentInfo = new CommentInfo();
        commentInfo.setOrderId(orderInfo.getId());
        commentInfo.setSkuId(skuInfo.getId());
        commentInfo.setSpuId(skuInfo.getSpuId());
        commentInfo.setUserId(userId);
        commentInfo.setCommentText("评论内容：" + RandomNumeric.nextString(1, 9, 50, ""));
        commentInfo.setCreateTime(config.businessDate.atTime(LocalTime.now()));
        commentInfo.setGrade(commentGradeOption.nextPayload());
        return commentInfo;
    }
}
