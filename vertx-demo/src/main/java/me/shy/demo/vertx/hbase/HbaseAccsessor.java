/**
 * @Since: 2019-08-05 21:11:51
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 * @LastTime: 2019-08-05 23:57:01
 */

package me.shy.demo.vertx.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HbaseAccsessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(HbaseAccsessor.class);

    public KeyValue get(String table, String rowKey, String family, String column) {
        KeyValue holder = null;
        TableName tn = TableName.valueOf(table);
        try {
            HTable hTable = (HTable)HbaseUtil.getConnection().getTable(tn);
            Get hGet = new Get(Bytes.toBytes(rowKey));
            hGet.addColumn(Bytes.toBytes(family), Bytes.toBytes(column));
            long start = System.currentTimeMillis();
            Result rs = hTable.get(hGet);
            LOGGER.info("Hbase get cost: " + (System.currentTimeMillis() - start));
            if (rs.size() != 0) {
                holder = rs.list().get(0);
            }
        } catch (IOException e) {
            LOGGER.error(
                "An error occurred during accsess hbase [OP=GET,RK=" + rowKey + ",T=" + table + ", reason: " + e
                    .getMessage() + "\n" + e.getStackTrace());
        }
        return holder;
    }

    public void put(String table, String rowKey, String family, String column, String value) {
        try {
            TableName tn = TableName.valueOf(table);
            HTable hTable = (HTable)HbaseUtil.getConnection().getTable(tn);
            List<Put> puts = new ArrayList<Put>();
            Put put = new Put(rowKey.getBytes());
            put.add(family.getBytes(), column.getBytes(), value.getBytes());
            puts.add(put);
            long startTime = System.currentTimeMillis();
            hTable.put(puts);
            LOGGER.info("Hbase put cost: " + (System.currentTimeMillis() - startTime));
        } catch (IOException e) {
            LOGGER.error(
                "An error occourded during accsess hbase [OP=PUT,RK=" + rowKey + ",F=" + family + ",C=" + column + ",V="
                    + value + ",T=" + table + ", reason: " + e.getMessage() + "\n" + e.getStackTrace());
        }
    }

}
