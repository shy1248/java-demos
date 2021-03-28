/**
 * @Date        : 2021-02-20 16:03:14
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 采用基数排序实现对扑克牌的按照花色与面值排序
 */
package me.shy.demo.sort;

public class PokerRadixSorter extends RadixSorter<Poker> {

    public PokerRadixSorter(int bucketsNumber) {
        super(bucketsNumber);
    }

    @Override
    int getKeySize(Poker[] array) {
        // 扑克牌排序有2个纬度，花色和牌面，因此key的数量为2
        return 2;
    }

    @Override
    int getKey(Poker e, int index) {
        // 桶的数量需要为扑克牌花色加上牌面值的数量，即 4 + 13 = 17 （不包含大小鬼）
        // 桶的顺序为先牌面后花色
        if (index == 0) { // 第一按照牌面值分配扑克牌致前面的 13 个桶中
            return e.getName().ordinal();
        } else { // 第二按照牌花色分配扑克牌致后面的 4 个桶中
            return PokerNames.values().length + e.getSuit().ordinal();
        }

    }

}
