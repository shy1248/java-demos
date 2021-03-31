/**
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Licence: GPLv3
 * @Description: -
 * @Since: 2019-04-20 15:14:47
 * @LastTime: 2019-05-06 15:51:34
 */

package me.shy.storm.simpletransaction;

import java.math.BigInteger;
import java.util.Map;

import org.apache.storm.coordination.BatchOutputCollector;
import org.apache.storm.transactional.ITransactionalSpout.Emitter;
import org.apache.storm.transactional.TransactionAttempt;
import org.apache.storm.tuple.Values;

public class EmitterDemo implements Emitter<MetaDataDemo> {

    private Map<Long, String> dataMapDemo;

    public EmitterDemo(Map<Long, String> dataMapDemo) {
        this.dataMapDemo = dataMapDemo;
    }

    @Override
    public void emitBatch(TransactionAttempt tx, MetaDataDemo coordinatorMeta, BatchOutputCollector collector) {
        if (coordinatorMeta != null) {
            long begin = coordinatorMeta.getBeginPoint();
            for (long i = begin; i < begin + coordinatorMeta.getNum(); i++) {
                if (this.dataMapDemo.get(i) == null) {
                    continue;
                }
                Values values = new Values(tx, this.dataMapDemo.get(i));
                System.out.println("Emitter send values: " + values);
                collector.emit(values);
            }
        }
    }

    @Override
    public void cleanupBefore(BigInteger txid) {
        System.out.println("Emitter is cleanup...");
    }

    @Override
    public void close() {
        System.out.println("Emitter is closing...");
    }

}
