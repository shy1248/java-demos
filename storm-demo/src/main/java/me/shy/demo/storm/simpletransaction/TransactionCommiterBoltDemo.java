/**
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Licence: GPLv3
 * @Description: -
 * @Since: 2019-04-20 16:28:31
 * @LastTime: 2019-05-06 15:52:01
 */

package me.shy.demo.storm.simpletransaction;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.apache.storm.coordination.BatchOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseTransactionalBolt;
import org.apache.storm.transactional.ICommitter;
import org.apache.storm.transactional.TransactionAttempt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

public class TransactionCommiterBoltDemo extends BaseTransactionalBolt implements ICommitter {

    private static final long serialVersionUID = 1L;
    public static final String DB_KEY = "demo.total.count";
    public static Map<String, DBValueDemo> dbMapDemo = new HashMap<String, DBValueDemo>();

    public long sum = 0;
    public TransactionAttempt id;

    @Override
    public void execute(Tuple tuple) {
        // this.id = (TransactionAttempt) tuple.getValue(0);
        sum += (Long) tuple.getValue(1);
    }

    @Override
    public void finishBatch() {
        BigInteger currentTxid = this.id.getTransactionId();
        DBValueDemo dbValueDemo = dbMapDemo.get(DB_KEY);

        if (dbValueDemo == null) {
            dbValueDemo = new DBValueDemo();
            dbValueDemo.txid = currentTxid;
            dbValueDemo.totalCount = sum;
            System.out.println("First transaction[txid=" + currentTxid + ", attemptId=" + id.getAttemptId()
                    + "] commit, total count is: " + dbValueDemo.totalCount);
        } else {
            if (!dbValueDemo.txid.equals(currentTxid)) {
                dbValueDemo.txid = currentTxid;
                dbValueDemo.totalCount += sum;
                System.out.println("A transaction[txid=" + currentTxid + ", attemptId=" + id.getAttemptId()
                        + "] commit, total count is: " + dbValueDemo.totalCount);
            } else {
                System.out.println("Duplicate transaction committed, ignored!");
            }
        }

        dbMapDemo.put(DB_KEY, dbValueDemo);
    }

    @Override
    public void prepare(Map conf, TopologyContext context, BatchOutputCollector collector, TransactionAttempt id) {
        this.id = id;
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("tx", "total"));
    }

    public static class DBValueDemo {
        public BigInteger txid;
        public long totalCount = 0;
    }

}
