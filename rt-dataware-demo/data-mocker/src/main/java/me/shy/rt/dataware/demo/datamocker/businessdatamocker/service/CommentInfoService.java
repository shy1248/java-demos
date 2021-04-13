/**
 * @Date        : 2021-04-12 23:07:30
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 商品评论表 服务类
 */

package me.shy.rt.dataware.demo.datamocker.businessdatamocker.service;

import com.baomidou.mybatisplus.extension.service.IService;

import me.shy.rt.dataware.demo.datamocker.businessdatamocker.bean.CommentInfo;

public interface CommentInfoService extends IService<CommentInfo> {
    public  void genComments(Boolean ifClear);
}
