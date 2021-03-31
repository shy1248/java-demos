/**
 * @Date        : 2021-02-20 16:05:46
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 扑克牌，不包含大小鬼
 */
package me.shy.dsalgo.sort;

public class Poker implements Comparable<Poker> {
    private PokerNames name;
    private PokerSuits suit;

    public Poker(PokerNames name, PokerSuits suit) {
        this.name = name;
        this.suit = suit;
    }

    @Override
    public String toString() {
        return "" + suit + name + "";
    }

    public PokerNames getName() {
        return name;
    }

    public void setName(PokerNames name) {
        this.name = name;
    }

    public PokerSuits getSuit() {
        return suit;
    }

    public void setSuit(PokerSuits suit) {
        this.suit = suit;
    }

    @Override
    public int compareTo(Poker o) {
        if (this.suit.compareTo(o.suit) == 0) {
            if (this.name.compareTo(o.name) == 0) { // 如果花色相同，牌面也相同，相等
                return 0;
            } else if (this.name.compareTo(o.name) < 0) { // 如果花色相同，牌面较小，小
                return -1;
            } else {
                return 1;
            }
        } else if (this.suit.compareTo(o.suit) < 0) { // 花色较小
            return -1;
        } else {
            return 1;
        }
    }
}

enum PokerSuits {
    // 方块、梅花、红心、黑桃
    DIANMOND, CLUB, HEARTS, SPADES
}

enum PokerNames {
    _2, _3, _4, _5, _6, _7, _8, _9, _10, _J, _Q, _K, _A
}
