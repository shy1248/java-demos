package me.shy.bloom;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * @Since: 2020/5/10 17:08
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: Google 的 BloomFilter 实现
 *
 **/
public class GuavaBloomFilterDemo {
    public static void main(String[] args) {

        // 创建一个整型的 BloomFilter
        // 后边两个参数：预计包含的数据量，和允许的误差值
        BloomFilter<Integer> integerBloomFilter = BloomFilter.create(Funnels.integerFunnel(), 100000, 0.01);
        for (int i = 0; i < 100000; i++) {
            integerBloomFilter.put(i);
        }
        System.out.println(integerBloomFilter.mightContain(1));
        System.out.println(integerBloomFilter.mightContain(2));
        System.out.println(integerBloomFilter.mightContain(3));
        System.out.println(integerBloomFilter.mightContain(100001));

        // 分布式环境中，布隆过滤器肯定还需要考虑是可以共享的资源，这时候我们会想到 Redis，是的，Redis 也实现了布隆过滤器。
        // 当然我们也可以把布隆过滤器通过 bloomFilter.writeTo() 写入一个文件，放入OSS、S3这类对象存储中。
        // integerBloomFilter.writeTo(null);
    }
}
