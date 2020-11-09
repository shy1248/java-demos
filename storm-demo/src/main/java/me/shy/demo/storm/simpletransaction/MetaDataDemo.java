/**
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Licence: GPLv3
 * @Description: -
 * @Since: 2019-04-20 12:05:09
 * @LastTime: 2019-05-06 15:51:39
 */

package me.shy.demo.storm.simpletransaction;

import java.io.Serializable;

public class MetaDataDemo implements Serializable {
    private static final long serialVersionUID = 1L;

    // begin offset of transaction batch
    private long beginPoint;
    // number of transaction batch
    private int num;

    /**
     * @return the beginPoint
     */
    public long getBeginPoint() {
        return beginPoint;
    }

    /**
     * @param beginPoint the beginPoint to set
     */
    public void setBeginPoint(long beginPoint) {
        this.beginPoint = beginPoint;
    }

    /**
     * @return the num
     */
    public int getNum() {
        return num;
    }

    /**
     * @param num the num to set
     */
    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "MetaDataDemo[begin=" + this.getBeginPoint() + ", num=" + this.getNum() + "]";
    }
}
