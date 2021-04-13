/**
 * @Date        : 2021-04-12 21:34:36
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 省份
 */
package me.shy.rt.dataware.demo.datamocker.businessdatamocker.bean;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Province implements Serializable {
    private static final long serialVersionUID = -477438933788226656L;

    /** id */
    private Long id;
    /** 省名称 */
    private String name;
    /** 大区id */
    private String regionId;
    /** 行政区位码 */
    private String areaCode;
    /** 国际编码 */
    private String isoCode;
}
