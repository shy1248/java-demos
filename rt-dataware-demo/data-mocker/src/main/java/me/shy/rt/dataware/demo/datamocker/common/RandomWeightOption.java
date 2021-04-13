/**
 * @Date        : 2021-04-11 00:31:26
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : A tool of random generated a group of option with weight.
 */

package me.shy.rt.dataware.demo.datamocker.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class RandomWeightOption<T> {
    private int totalWeight;
    private List<WeightOption<T>> options;

    @SuppressWarnings("unchecked")
    public RandomWeightOption(String... payloads) {
        options = new ArrayList<>();
        for (String payload : payloads) {
            totalWeight += 1;
            options.add((WeightOption<T>) new WeightOption<String>(payload, 1));
        }
    }

    @SafeVarargs
    public RandomWeightOption(WeightOption<T>... weightOptions) {
        options = new ArrayList<>();
        for (WeightOption<T> option : weightOptions) {
            totalWeight += 1;
            this.options.add(option);
        }
    }

    @SuppressWarnings("unchecked")
    public RandomWeightOption(int trueWeight) {
        this((WeightOption<T>) new WeightOption<Boolean>(true, trueWeight),
                (WeightOption<T>) new WeightOption<Boolean>(false, 100 - trueWeight));
    }

    public WeightOption<T> nextWeightOption() {
        return options.get(new Random().nextInt(totalWeight));
    }

    public T nextPayload() {
        return nextWeightOption().getPayload();
    }

    public static class Builder<T> {
        private int totalWeight;
        private List<WeightOption<T>> options = new ArrayList<>();

        public RandomWeightOption<T> build() {
            return new RandomWeightOption<>(totalWeight, options);
        }

        public Builder<T> add(T payload, int weight) {
            WeightOption<T> option = new WeightOption<T>(payload, weight);
            totalWeight += weight;
            for (int i = 0; i < weight; i++) {
                options.add(option);
            }
            return this;
        }
    }
}
