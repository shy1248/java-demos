/**
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Licence: GPLv3
 * @Description: -
 * @Since: 2019-04-20 18:57:55
 * @LastTime: 2019-05-12 19:17:45
 */

package me.shy.storm.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.utils.Utils;

public class DemosSubmitter {
    public static final String NIMBUS_HOST = "demo.shy.com";
    public static final int NIMBUS_PORT = 6627;
    public static final List<String> ZOOKEEPER_SERVERS = Arrays.asList("demo.shy.com");
    public static final int ZOOKEEPER_PORT = 2181;
    public static final int TOPOLOGY_WORKERS = 3;

    public static void localSubmit(String id, Config conf, StormTopology topology) {
        new LocalCluster().submitTopology(id, conf, topology);
    }

    public static void remoteSubmit(String id, Config conf, StormTopology topology, String jar) {
        conf.put(Config.NIMBUS_HOST, NIMBUS_HOST);
        conf.put(Config.NIMBUS_THRIFT_PORT, NIMBUS_PORT);
        conf.put(Config.STORM_ZOOKEEPER_SERVERS, ZOOKEEPER_SERVERS);
        conf.put(Config.STORM_ZOOKEEPER_PORT, ZOOKEEPER_PORT);
        conf.put(Config.TOPOLOGY_WORKERS, TOPOLOGY_WORKERS);
        Map<String, Object> defaultConf = Utils.readStormConfig();
        defaultConf.putAll(conf);

        System.setProperty("storm.jar", jar);

        try {
            // submit topology
            StormSubmitter.submitTopology(id, defaultConf, topology);
        } catch (Exception e) {
            System.err.println("Toplogy " + id + " submit failed! The reason is: " + e.getMessage());
        }
    }
}
