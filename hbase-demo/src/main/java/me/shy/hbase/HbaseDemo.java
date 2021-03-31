/**
 * @Date        : 2020-08-10 22:02:24
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 *
 * DDL:
 * Create namespace
 * Table exist or not
 * Create table
 * Delete table
 * DML:
 * Put
 * Get and Scan
 * Delete
 *
 */
package me.shy.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.NamespaceExistException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptor;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder.ModifyableTableDescriptor;
import org.apache.hadoop.hbase.util.Bytes;

public class HbaseDemo {
    private static Connection connection;
    private static HBaseAdmin admin;

    static {
        final Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "demos01:2181");
        try {
            final Connection connection = ConnectionFactory.createConnection(configuration);
            admin = (HBaseAdmin) connection.getAdmin();
        } catch (final IOException e) {
            System.err.print("An error occourd during get Hbase connection: ");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * create namespace
     * @param ns the name of the namespace
     * @throws IOException
     */
    public static void createNamespace(String ns) {
        NamespaceDescriptor namespace = NamespaceDescriptor.create(ns).build();
        try {
            admin.createNamespace(namespace);
        } catch (NamespaceExistException e) {
            System.out.println(String.format("Namespace %s already exist.", ns));
        } catch (IOException e) {
            System.err.print(String.format("An error occourd during create namespace %s: ", ns));
            e.printStackTrace();
        }
    }

    /**
     * if table exist or not
     * @param tableName the name of the table
     * @return return true if the table already exist, otherwise, return false
     * @throws IOException
     */
    public static boolean isTableExist(final String tableName) throws IOException {
        return admin.tableExists(TableName.valueOf(tableName));
    }

    /**
     * DDL: Create table
     * @param tableName the name of the table
     * @param columnFamiles a list of column families, at least one
     * @return return true if the table create success, when an error occurd or table already exist, return false
     */
    public static void createTable(String tableName, String... columnFamiles) {
        if (columnFamiles.length == 0) {
            throw new IllegalArgumentException("Must be gave at least one column family.");
        }
        try {
            if (!isTableExist(tableName)) {
                ModifyableTableDescriptor table = (ModifyableTableDescriptor) TableDescriptorBuilder
                        .newBuilder(TableName.valueOf(tableName)).build();
                for (String cf : columnFamiles) {
                    ColumnFamilyDescriptor columnFamily = ColumnFamilyDescriptorBuilder.newBuilder(cf.getBytes())
                            .build();
                    table.setColumnFamily(columnFamily);
                }
                admin.createTable(table);
                System.out.println(String.format("Create table %s successfuly.", tableName));
            } else {
                System.err.println(String.format("Table %s already exist.", tableName));
            }
        } catch (IOException e) {
            System.err.print(String.format("An error occourd during create table %s: ", tableName));
            e.printStackTrace();
        } finally {
            close();
        }
    }

    /**
     * put data
     * @param tableName the name of the table
     * @param rowKey row key
     * @param columnFamily the name of column family
     * @param columnQualifier the name of column
     * @param value the value of column
     */
    public static void insert(String tableName, String rowKey, String columnFamily, String columnQualifier,
            String value) {
        try {
            if (!isTableExist(tableName)) {
                System.err.println(String.format("Table %s does not exist, please create it ad first.", tableName));
                return;
            }
            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(columnQualifier), Bytes.toBytes(value));
            Table table = connection.getTable(TableName.valueOf(tableName));
            table.put(put);
        } catch (IOException e) {
            System.err.print(String.format("An error occord during put to table %s: ", tableName));
            e.printStackTrace();
        }
    }

    public static void close() {
        if (null != connection) {
            try {
                connection.close();
            } catch (final IOException e) {
                System.err.print("An error occourd during close Hbase connection: ");
                e.printStackTrace();
            }
        }

        if (null != admin) {
            try {
                admin.close();
            } catch (IOException e) {
                System.err.print("An error occourd during close Hbase admin: ");
                e.printStackTrace();
            }
        }
    }

    public static void main(final String[] args) throws IOException {
        // System.out.println(isTableExist("demo:t1"));
        // createNamespace("demo01");
        // createTable("t3", "cf1", "cf2");
        insert("t1", "10000", "cf1", "name", "Tom");
    }
}
