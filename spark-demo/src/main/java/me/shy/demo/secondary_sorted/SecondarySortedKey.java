/**
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Licence: GPLv3
 * @Description: -
 * @Since: 2019-05-02 17:46:22
 * @LastTime: 2019-05-04 20:56:01
 */
package me.shy.demo.secondary_sorted;

import java.io.Serializable;
import scala.math.Ordered;

public class SecondarySortedKey implements Ordered<SecondarySortedKey>, Serializable {

    private static final long serialVersionUID = 1L;

    private int first;
    private int second;

    public SecondarySortedKey(int first, int second) {
        this.first = first;
        this.second = second;
    }

    /**
     * @return the first
     */
    public int getFirst() {
        return first;
    }

    /**
     * @param first the first to set
     */
    public void setFirst(int first) {
        this.first = first;
    }

    /**
     * @return the second
     */
    public int getSecond() {
        return second;
    }

    /**
     * @param second the second to set
     */
    public void setSecond(int second) {
        this.second = second;
    }

    @Override
    public int compare(SecondarySortedKey that) {
        if (this.first - that.getFirst() != 0) {
            return this.first - that.getFirst();
        } else {
            return this.second - that.getSecond();
        }
    }

    @Override
    public boolean $greater(SecondarySortedKey that) {
        if (this.first > that.getFirst()) {
            return true;
        } else if (this.first == that.getFirst() && this.second > that.getSecond()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean $greater$eq(SecondarySortedKey that) {
        if (this.$greater(that)) {
            return true;
        } else if (this.first == that.getFirst() && this.second == that.getSecond()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean $less(SecondarySortedKey that) {
        if (this.$greater$eq(that)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean $less$eq(SecondarySortedKey that) {
        if (this.$greater(that)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public int compareTo(SecondarySortedKey that) {
        if (this.first - that.getFirst() != 0) {
            return this.first - that.getFirst();
        } else {
            return this.second - that.getSecond();
        }
    }

}
