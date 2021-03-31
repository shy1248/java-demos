/**
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Licence: GPLv3
 * @Description: -
 * @Since: 2019-04-20 23:38:23
 * @LastTime: 2019-04-21 20:06:11
 */

package me.shy.storm.dailycount;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.apache.storm.coordination.BatchOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBatchBolt;
import org.apache.storm.transactional.ICommitter;
import org.apache.storm.transactional.TransactionAttempt;
import org.apache.storm.tuple.Tuple;

public class DailyCountCommitBolt extends BaseBatchBolt<TransactionAttempt> implements ICommitter {

    private static final long serialVersionUID = 1L;
    public TransactionAttempt id;
    public BatchOutputCollector collector;
    public static Map<String, DBValue> dbValueMap = new HashMap<String, DBValue>();
    public Map<String, Long> cacheMap;

    @Override
    public void prepare(Map conf, TopologyContext context, BatchOutputCollector collector, TransactionAttempt id) {
        this.id = id;
        this.collector = collector;
    }

    @Override
    public void execute(Tuple tuple) {
        cacheMap = (Map<String, Long>) tuple.getValue(1);
    }

    @Override
    public void finishBatch() {
        BigInteger currentTxid = this.id.getTransactionId();
        for (String d : cacheMap.keySet()) {
            DBValue dbValue = dbValueMap.get(d);
            if (dbValue == null) {
                dbValue = new DBValue();
                dbValue.txid = currentTxid;
                dbValue.day = d;
                dbValue.totalCount = cacheMap.get(d);
            } else {
                dbValue.txid = currentTxid;
                dbValue.day = d;
                dbValue.totalCount += cacheMap.get(d);
            }
            dbValueMap.put(d, dbValue);
        }

        for (String d : dbValueMap.keySet()) {
            System.err.println("Finished transcation, result is: " + dbValueMap.get(d).toString());
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    }

    public static class DBValue {
        public BigInteger txid;
        public String day;
        public Long totalCount;

        public String toString() {
            return "DBValue[txid=" + txid + ", day=" + day + ", total=" + totalCount + "]";
        }
    }

}
