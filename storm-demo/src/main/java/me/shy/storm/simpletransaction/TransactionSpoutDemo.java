/**
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Licence: GPLv3
 * @Description: A demo for storm transaction spout
 * @Since: 2019-04-20 12:04:33
 * @LastTime: 2019-05-06 15:51:19
 */

package me.shy.storm.simpletransaction;

import java.util.Map;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.transactional.ITransactionalSpout;
import org.apache.storm.tuple.Fields;

import me.shy.storm.utils.DemosDataProducer;

public class TransactionSpoutDemo implements ITransactionalSpout<MetaDataDemo> {

    private static final long serialVersionUID = 1L;

    private Map<Long, String> dataMapDemo = DemosDataProducer.produce(100);

    @Override
    public Coordinator<MetaDataDemo> getCoordinator(Map arg0, TopologyContext arg1) {
        return new CoordinatorDemo();
    }

    @Override
    public Emitter<MetaDataDemo> getEmitter(Map arg0, TopologyContext arg1) {
        return new EmitterDemo(this.dataMapDemo);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer arg0) {
        arg0.declare(new Fields("tx", "log"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }

 }
