总结：
1：对于map/set的选择使用
    HashMap 不需要多线程的情况下使用
    TreeMap 不需要多线程的情况下使用
    LinkedHashMap 不需要多线程的情况下使用
    Hashtable 并发量比较小
    Collections.sychronizedXXX 并发量比较小
    ConcurrentHashMap 高并发
    ConcurrentSkipListMap 高并发同时要求排好顺序

2：队列
    ArrayList 不需要同步的情况
    LinkedList 不需要同步的情况
    Collections.synchronizedXXX 并发量低
    Vector 并发量低
    CopyOnWriteList 写的时候少，读时候多
    Queue
        CocurrentLinkedQueue //concurrentArrayQueue 高并发队列
        BlockingQueue 阻塞式
        LinkedBQ 无界
        ArrayBQ 有界
        TransferQueue 直接给消费者线程，如果没有消费者阻塞
        SynchronusQueue 特殊的transferQueue,容量0
        DelayQueue执行定时任务
