/**
 * @Date        : 2021-04-11 12:06:34
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */

package me.shy.rt.dataware.demo.datamocker.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class WeightOption<T> {
    @Getter
    private T payload;
    @Getter
    private int weight;
}
