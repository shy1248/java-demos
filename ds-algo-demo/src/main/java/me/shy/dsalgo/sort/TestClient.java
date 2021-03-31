/**
 * @Date        : 2021-02-19 14:10:59
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 排序测试
 */
package me.shy.dsalgo.sort;

import java.util.Arrays;
import java.util.Random;

public class TestClient {
    public static void main(String[] args) {

        // int numberSize = 10;
        // Integer[] numbers = new Integer[numberSize];
        // for (int i = 0; i < numberSize; i++) {
        //     numbers[i] = Double.valueOf(Math.random() * 1000).intValue();
        // }

        // AbstractSorter<Integer> integerSorter = null;
        // integerSorter = new PositiveIntegerRadixSorter(10); // 非负整数的基数排序
        // System.out.println("Before sortted: " + Arrays.toString(numbers));
        // integerSorter.sort(numbers);
        // System.out.println("After sortted: " + Arrays.toString(numbers));


        Random random = new Random(Double.valueOf(Math.random() * 100).intValue());
        int pokerSize = 10;
        Poker[] pokers = new Poker[pokerSize];
        for (int i = 0; i < pokerSize; i++) {
            pokers[i] = new Poker(
                    PokerNames.values()[random.nextInt(PokerNames.values().length)],
                    PokerSuits.values()[random.nextInt(PokerSuits.values().length)]
                );
        }

        AbstractSorter<Poker> sorter = null;
        System.out.println("Before sortted: " + Arrays.toString(pokers));
        // sorter = new BubbleSorter<Poker>();
        // sorter = new SelectSorter<Poker>();
        // sorter = new InsertionSorter<Poker>();
        // sorter = new SwappedShellSorter<Poker>();
        // sorter = new MovedShellSorter<Poker>();
        // sorter = new QuickSorter<Poker>();
        // sorter = new MergeSorter<Poker>();
        sorter = new PokerRadixSorter(PokerNames.values().length + PokerSuits.values().length);
        sorter.sort(pokers);
        System.out.println("After sortted: " + Arrays.toString(pokers));
    }
}
