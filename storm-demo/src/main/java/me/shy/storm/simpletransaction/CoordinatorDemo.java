/**
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Licence: GPLv3
 * @Description: -
 * @Since: 2019-04-20 13:42:03
 * @LastTime: 2019-05-06 15:51:29
 */

package me.shy.storm.simpletransaction;

import java.math.BigInteger;

import org.apache.storm.transactional.ITransactionalSpout.Coordinator;
import org.apache.storm.utils.Utils;

public class CoordinatorDemo implements Coordinator<MetaDataDemo> {
    private static final int TRANSACTION_NUM = 10;

    @Override
    public MetaDataDemo initializeTransaction(BigInteger txid, MetaDataDemo prevMetadata) {
        // Initialize metadata
        long begin = 0;
        if (prevMetadata == null) {
            begin = 0;
        } else {
            begin = prevMetadata.getBeginPoint() + prevMetadata.getNum();
        }
        MetaDataDemo metaData = new MetaDataDemo();
        metaData.setBeginPoint(begin);
        metaData.setNum(TRANSACTION_NUM);
        System.out.println("Coordinator started a transaction: " + metaData.toString());
        return metaData;
    }

    @Override
    public boolean isReady() {
        Utils.sleep(1000);
        System.out.println("Coordinator is ready...");
        return true;
    }

    @Override
    public void close() {
        System.out.println("Coordinator is closing...");
    }

}
