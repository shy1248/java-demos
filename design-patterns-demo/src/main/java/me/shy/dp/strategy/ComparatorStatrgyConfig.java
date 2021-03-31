/**
 * @Date        : 2021-02-11 15:17:03
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.dp.strategy;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class ComparatorStatrgyConfig {
    private Map<String, List<Map<String, String>>> strategies;

    public String getCatComparatorClass() {
        return this.getStrategies().get("comparator").get(0).get("cat");
    }

    public String getDogComparatorClass() {
        return this.getStrategies().get("comparator").get(0).get("dog");
    }
}
