/**
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Licence: GPLv3
 * @Description: -
 * @Since: 2019-04-20 15:20:55
 * @LastTime: 2019-04-20 21:16:33
 */

package me.shy.demo.storm.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DemosDataProducer {

    // Produce demo data from storm spout
    public static Map<Long, String> produce(int size) {
        Random random = new Random();
        Map<Long, String> demosDataMap = new HashMap<Long, String>();
        String[] hosts = { "demo.shy.com" };
        String[] sessions = { "aaaaaaaa", "bbbbbbbb", "ccccccc", "ddddddd", "eeeeeee" };
        String[] dates = { "2019-04-15", "2019-04-16", "2019-04-17", "2019-04-18", "2019-04-19" };
        String[] times = { "09:10:15", "10:30:45", "11:20:30", "12:45:10", "14:20:18" };

        if (size <= 0) {
            System.err.println("Producer size must be great then zero!");
            System.exit(0);
        }
        for (long i = 0; i < size; i++) {
            String record = hosts[0] + "\t" + sessions[random.nextInt(sessions.length)] + "\t"
                    + dates[random.nextInt(dates.length)] + " " + times[random.nextInt(times.length)];
            System.out.println("Produce a record: " + record);
            demosDataMap.put(i, record);
        }
        return demosDataMap;
    }
}
