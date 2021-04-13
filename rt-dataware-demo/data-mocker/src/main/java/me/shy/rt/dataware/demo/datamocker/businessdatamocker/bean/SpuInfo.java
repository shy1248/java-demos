/**
 * @Date        : 2021-04-12 22:35:46
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 商品表
 */
package me.shy.rt.dataware.demo.datamocker.businessdatamocker.bean;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpuInfo implements Serializable {
    private static final long serialVersionUID = -1655702814577999384L;

    /** 商品 id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /** 商品名称 */
    private String spuName;
    /** 商品描述(后台简述） */
    private String description;
    /** 三级分类 id */
    private Long category3Id;
    /** 品牌 id */
    private Long brandId;
}
