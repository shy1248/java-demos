package me.shy.bloom;

/**
 * @Since: 2020/5/10 16:44
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: 自定义 hash 函数
 *
 **/
public class HashFunction {
    private int size;
    private int seed;

    public HashFunction(int size, int seed) {
        this.size = size;
        this.seed = seed;
    }

    public int hash(String value) {
        int result = 0;
        int length = value.length();
        for(int i=0;i< length;i++){
            result = seed * result + value.charAt(i);
        }
        return (size - 1) & result;
    }
}
