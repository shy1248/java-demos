package me.shy.demo;

import java.util.BitSet;

/**
 * @Since: 2020/5/10 16:40
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: BloomFilter 的实现
 *
 * 布隆过滤器数据结构
 * BloomFilter 是由一个固定大小的二进制向量或者位图（bitmap）和一系列映射函数组成的。
 * 在初始状态时，对于长度为 m 的位数组，它的所有位都被置为0；
 * 当有变量被加入集合时，通过 K 个映射函数将这个变量映射成位图中的 K 个点，把它们置为 1；
 * 查询某个变量的时候我们只要看看这些点是不是都是 1 就可以大概率知道集合中有没有它了：
 * 如果这些点有任何一个 0，则被查询变量一定不在；
 * 如果都是 1，则被查询变量很可能存在
 * 为什么说是可能存在，而不是一定存在呢？那是因为映射函数本身就是散列函数，散列函数是会有碰撞的。
 *
 * 误判率
 * 布隆过滤器的误判是指多个输入经过哈希之后在相同的bit位置1了，这样就无法判断究竟是哪个输入产生的，因此误判的根源在于相同的 bit 位被多次映射且置 1。
 *
 * 这种情况也造成了布隆过滤器的删除问题，因为布隆过滤器的每一个 bit 并不是独占的，很有可能多个元素共享了某一位。如果我们直接删除这一位的话，会影响其他的元素。(比如上图中的第 3 位)
 *
 * 特性
 * 一个元素如果判断结果为存在的时候元素不一定存在，但是判断结果为不存在的时候则一定不存在。
 * 布隆过滤器可以添加元素，但是不能删除元素。因为删掉元素会导致误判率增加。
 * 添加与查询元素步骤
 * 添加元素
 * 将要添加的元素给 k 个哈希函数
 * 得到对应于位数组上的 k 个位置
 * 将这k个位置设为 1
 * 查询元素
 * 将要查询的元素给k个哈希函数
 * 得到对应于位数组上的k个位置
 * 如果k个位置有一个为 0，则肯定不在集合中
 * 如果k个位置全部为 1，则可能在集合中
 * 优点
 * 相比于其它的数据结构，布隆过滤器在空间和时间方面都有巨大的优势。布隆过滤器存储空间和插入/查询时间都是常数 $O(K)$，另外，散列函数相互之间没有关系，方便由硬件并行实现。布隆过滤器不需要存储元素本身，在某些对保密要求非常严格的场合有优势。
 *
 * 布隆过滤器可以表示全集，其它任何数据结构都不能；
 *
 * 缺点
 * 但是布隆过滤器的缺点和优点一样明显。误算率是其中之一。随着存入的元素数量增加，误算率随之增加。但是如果元素数量太少，则使用散列表足矣。
 *
 * 另外，一般情况下不能从布隆过滤器中删除元素。我们很容易想到把位数组变成整数数组，每插入一个元素相应的计数器加 1, 这样删除元素时将计数器减掉就可以了。然而要保证安全地删除元素并非如此简单。首先我们必须保证删除的元素的确在布隆过滤器里面。这一点单凭这个过滤器是无法保证的。另外计数器回绕也会造成问题。
 *
 * 在降低误算率方面，有不少工作，使得出现了很多布隆过滤器的变种。
 *
 * 布隆过滤器使用场景和实例
 * 在程序的世界中，布隆过滤器是程序员的一把利器，利用它可以快速地解决项目中一些比较棘手的问题。
 *
 * 如网页 URL 去重、垃圾邮件识别、大集合中重复元素的判断和缓存穿透等问题。
 *
 * 布隆过滤器的典型应用有：
 *
 * 数据库防止穿库。 Google Bigtable，HBase 和 Cassandra 以及 Postgresql 使用BloomFilter来减少不存在的行或列的磁盘查找。避免代价高昂的磁盘查找会大大提高数据库查询操作的性能。
 *
 * 业务场景中判断用户是否阅读过某视频或文章，比如抖音或头条，当然会导致一定的误判，但不会让用户看到重复的内容。
 *
 * 缓存宕机、缓存击穿场景，一般判断用户是否在缓存中，如果在则直接返回结果，不在则查询db，如果来一波冷数据，会导致缓存大量击穿，造成雪崩效应，这时候可以用布隆过滤器当缓存的索引，只有在布隆过滤器中，才去查询缓存，如果没查询到，则穿透到db。如果不在布隆器中，则直接返回。
 *
 * WEB拦截器，如果相同请求则拦截，防止重复被攻击。用户第一次请求，将请求参数放入布隆过滤器中，当第二次请求时，先判断请求参数是否被布隆过滤器命中。可以提高缓存命中率。Squid 网页代理缓存服务器在 cache digests 中就使用了布隆过滤器。Google Chrome浏览器使用了布隆过滤器加速安全浏览服务
 *
 * Venti 文档存储系统也采用布隆过滤器来检测先前存储的数据。
 *
 * SPIN 模型检测器也使用布隆过滤器在大规模验证问题时跟踪可达状态空间
 *
 **/
public class MyBloomFilter {
    // 比特位长度，10亿
    private static final int DEFAULT_SIZE = 256 << 22;
    // 为了降低错误率，使用加法hash算法，所以定义一个8个元素的质数数组
    private static final int[] SEEDS = {3, 5, 7, 11, 13, 31, 37, 61};
    // 构建 8 个不同的 hash 算法
    private static HashFunction[] functions = new HashFunction[SEEDS.length];
    // 初始化布隆过滤器的 bitmap
    private static BitSet bitSet = new BitSet(DEFAULT_SIZE);

    // 添加数据
    public static void add(String value) {
        if (null != value) {
            // 使用8个hash函数分别计算 hash 值并修改 bitmap 中相应位置为 true
            for (HashFunction function : functions) {
                bitSet.set(function.hash(value), true);
            }
        }
    }

    // 判断数据是否存在
    public static boolean contains(String value) {
        if (null == value) {
            return false;
        }

        boolean ret = true;
        for (HashFunction function : functions) {
            ret = bitSet.get(function.hash(value));
            // 一个 hash 函数返回 false 则跳出循环，表示不存在
            if (!ret) {
                break;
            }
        }
        return ret;
    }

    // 模拟用户是不是会员，或用户在不在线。。。
    public static void main(String[] args) {
        // 初始化8个hash函数
        for (int i = 0; i < SEEDS.length; i++) {
            functions[i] = new HashFunction(DEFAULT_SIZE, SEEDS[i]);
        }

        // 添加1亿数据
        for (int i = 0; i < 100000000; i++) {
            add(String.valueOf(i));
        }

        String id = "123456789";
        add(id);

        System.out.println(contains(id));   // true
        System.out.println(contains("234567890"));  //false
    }
}
