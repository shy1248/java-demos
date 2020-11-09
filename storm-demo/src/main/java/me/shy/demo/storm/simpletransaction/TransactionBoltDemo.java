/**
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Licence: GPLv3
 * @Description: -
 * @Since: 2019-04-20 16:10:51
 * @LastTime: 2019-05-06 15:51:54
 */

package me.shy.demo.storm.simpletransaction;

import java.math.BigInteger;
import java.util.Map;

import org.apache.storm.coordination.BatchOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseTransactionalBolt;
import org.apache.storm.transactional.TransactionAttempt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class TransactionBoltDemo extends BaseTransactionalBolt {

    private static final long serialVersionUID = 1L;
    public long count = 0;
    private BatchOutputCollector collector;
    private TransactionAttempt id;

    @Override
    public void execute(Tuple tuple) {
        TransactionAttempt tx = (TransactionAttempt) tuple.getValue(0);
        BigInteger txid = tx.getTransactionId();
        long attemptId = tx.getAttemptId();
        System.out.println("Bolt recevied TransactionAttempt[txid=" + txid + ", attemptId=" + attemptId + "]");

        String log = (String) tuple.getValue(1);
        if (log != null && log.length() > 0) {
            System.out.println("Bolt recevied log record[log=" + log + "]");
            count++;
        }
    }

    @Override
    public void finishBatch() {
        this.collector.emit(new Values(id, count));
    }

    @Override
    public void prepare(Map conf, TopologyContext context, BatchOutputCollector collector, TransactionAttempt id) {
        this.id = id;
        this.collector = collector;
        // System.out.println("Bolt prepare TransactionAttempt[attemptId=" + id + "]");
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("tx", "count"));
    }

}
