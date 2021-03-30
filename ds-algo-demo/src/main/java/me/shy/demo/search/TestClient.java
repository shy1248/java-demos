/**
 * @Date        : 2021-02-21 17:40:23
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 查找算法测试
 */
package me.shy.demo.search;

public class TestClient {

    public static void main(String[] args) {
        Integer[] numbers = new Integer[] { -4, 3, 7, 10, 22, 56, 78, 99, 102, 150 };
        System.out.println("Using sequence search:");
        int res = Searcher.seqSearch(numbers, -4);
        System.out.println("Numbe -4 index is:" + res);
        res = Searcher.seqSearch(numbers, 150);
        System.out.println("Numbe 150 index is:" + res);
        res = Searcher.seqSearch(numbers, 10);
        System.out.println("Numbe 10 index is:" + res);
        res = Searcher.seqSearch(numbers, 32);
        System.out.println("Numbe 32 index is:" + res);

        System.out.println("Using binaray search:");
        res = Searcher.binSearch(numbers, 0, numbers.length - 1, -4);
        System.out.println("Numbe -4 index is:" + res);
        res = Searcher.binSearch(numbers, 0, numbers.length - 1, 150);
        System.out.println("Numbe 150 index is:" + res);
        res = Searcher.binSearch(numbers, 0, numbers.length - 1, 10);
        System.out.println("Numbe 10 index is:" + res);
        res = Searcher.binSearch(numbers, 0, numbers.length - 1, 32);
        System.out.println("Numbe 32 index is:" + res);

        System.out.println("Using insertion search:");
        res = Searcher.insertSearch(numbers, 0, numbers.length - 1, -4);
        System.out.println("Numbe -4 index is:" + res);
        res = Searcher.insertSearch(numbers, 0, numbers.length - 1, 150);
        System.out.println("Numbe 150 index is:" + res);
        res = Searcher.insertSearch(numbers, 0, numbers.length - 1, 10);
        System.out.println("Numbe 10 index is:" + res);
        res = Searcher.insertSearch(numbers, 0, numbers.length - 1, 32);
        System.out.println("Numbe 32 index is:" + res);

        System.out.println("Using fibnacci search:");
        res = Searcher.fibSearch(numbers, -4);
        System.out.println("Numbe -4 index is:" + res);
        res = Searcher.fibSearch(numbers, 150);
        System.out.println("Numbe 150 index is:" + res);
        res = Searcher.fibSearch(numbers, 10);
        System.out.println("Numbe 10 index is:" + res);
        res = Searcher.fibSearch(numbers, 32);
        System.out.println("Numbe 32 index is:" + res);
    }

}
