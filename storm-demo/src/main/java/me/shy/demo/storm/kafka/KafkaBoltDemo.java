/**
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Licence: GPLv3
 * @Description: -
 * @Since: 2019-04-29 15:20:52
 * @LastTime: 2019-05-12 20:18:31
 */

package me.shy.demo.storm.kafka;

import java.util.Map;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.IBasicBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

public class KafkaBoltDemo implements IBasicBolt {

    private static final long serialVersionUID = 1L;

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("demo_log"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }

    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        System.err.println("This is Bolt prepare method...");
    }

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        System.err.println("Recived records: " + input.getString(0));
        // System.err.println("Recived records: " + input.getString(1));
    }

    @Override
    public void cleanup() {
        System.err.println("This is Bolt cleanup method...");
    }

}
