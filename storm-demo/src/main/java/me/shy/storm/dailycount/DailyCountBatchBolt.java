/**
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Licence: GPLv3
 * @Description: -
 * @Since: 2019-04-20 23:00:38
 * @LastTime: 2019-04-21 19:43:25
 */

package me.shy.storm.dailycount;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.coordination.BatchOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBatchBolt;
import org.apache.storm.transactional.TransactionAttempt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class DailyCountBatchBolt extends BaseBatchBolt<TransactionAttempt> {

    private static final long serialVersionUID = 1L;
    public TransactionAttempt id;
    public BatchOutputCollector collector;
    public Map<String, Long> cacheMap = new HashMap<String, Long>();

    @Override
    public void prepare(Map conf, TopologyContext context, BatchOutputCollector collector, TransactionAttempt id) {
        this.id = id;
        this.collector = collector;
    }

    @Override
    public void execute(Tuple tuple) {
        String record = (String) tuple.getValue(1);
        if (null != record) {
            String[] cols = record.split("\\t");
            if (cols.length == 3) {
                String day = cols[2].split(" ")[0];
                Long count = cacheMap.get(day);
                if (null == count) {
                    count = 0L;
                }
                count++;
                cacheMap.put(day, count);
            }
        }
    }

    @Override
    public void finishBatch() {
        // System.err.println("Batch[txid=" + this.id + ", day=" + day + ", count=" +
        // cacheMap.get(day) + "] count finish.");
        this.collector.emit(new Values(this.id, cacheMap));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("tx", "batchCount"));
    }

}
