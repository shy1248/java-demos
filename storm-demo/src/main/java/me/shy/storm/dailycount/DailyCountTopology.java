/**
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Licence: GPLv3
 * @Description: -
 * @Since: 2019-04-20 23:59:02
 * @LastTime: 2019-05-06 23:22:41
 */

package me.shy.storm.dailycount;

import org.apache.storm.Config;
import org.apache.storm.transactional.TransactionalTopologyBuilder;

import me.shy.storm.simpletransaction.TransactionSpoutDemo;
import me.shy.storm.utils.DemosSubmitter;

public class DailyCountTopology {
    public static void main(String[] args) {
        TransactionalTopologyBuilder builder = new TransactionalTopologyBuilder("daily_topo", "daily_spout",
                new TransactionSpoutDemo(), 1);
        builder.setBolt("daily_count_bolt", new DailyCountBatchBolt(), 1).shuffleGrouping("daily_spout");
        builder.setBolt("daily_commit_bolt", new DailyCountCommitBolt(), 1).shuffleGrouping("daily_count_bolt");
        Config conf = new Config();
        conf.setDebug(false);
        DemosSubmitter.localSubmit("daily_topo", conf, builder.buildTopology());
    }
}
