/**
 * @Date        : 2021-04-13 20:57:30
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 商品评论表 服务实现类
 */

package me.shy.rt.dataware.datamocker.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import me.shy.rt.dataware.datamocker.bean.CommentInfo;
import me.shy.rt.dataware.datamocker.mapper.CommentInfoMapper;
import me.shy.rt.dataware.datamocker.service.CommentInfoService;

public class CommentInfoServiceImpl extends ServiceImpl<CommentInfoMapper, CommentInfo> implements CommentInfoService {

    @Override
    public void genComments(Boolean ifClear) {
    }
}
