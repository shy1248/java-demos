/**
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Licence: GPLv3
 * @Description: -
 * @Since: 2019-04-20 15:00:54
 * @LastTime: 2019-05-06 15:51:48
 */

package me.shy.storm.simpletransaction;

import org.apache.storm.Config;
import org.apache.storm.transactional.TransactionalTopologyBuilder;

import me.shy.storm.utils.DemosSubmitter;


public class TopologyDemo {
    public static void main(String[] args) {
        TransactionalTopologyBuilder builder = new TransactionalTopologyBuilder("demos_tx_topo",
                "demos_tx_spout", new TransactionSpoutDemo(), 1);
        builder.setBolt("demos_bc_bolt", new TransactionBoltDemo(), 3).shuffleGrouping("demos_tx_spout");
        builder.setBolt("demos_tc_bolt", new TransactionCommiterBoltDemo(), 1)
                .shuffleGrouping("demos_bc_bolt");

        Config conf = new Config();
        conf.setDebug(false);

        // Local submit
        // DemosSubmitter.localSubmitter("demos_tx_topo", conf, builder.buildTopology());

        // Remote submit
        String jar = "D:\\source\\demo\\storm.shy.com\\target\\storm.shy.com-1.0-SNAPSHOT-jar-with-dependencies.jar";
        DemosSubmitter.remoteSubmit("demos_tx_topo", conf, builder.buildTopology(), jar);
    }
}
