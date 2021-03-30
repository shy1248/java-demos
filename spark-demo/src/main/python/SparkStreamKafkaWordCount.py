#!/usr/bin/env python
# -*- coding=UTF-8 -*-
'''
@ Since: 2019-05-29 16:40:17
@ Author: shy
@ Email: yushuibo@ebupt.com / hengchen2005@gmail.com
@ Version: v1.0
@ Licence: GPLv3
@ Description: -
@ LastTime: 2019-05-29 16:55:13
'''

from __future__ import print_function
from __future__ import unicode_literals

import sys

from pyspark import SparkContext
from pyspark.streaming import StreamingContext
from pyspark.streaming.kafka import KafkaUtils

if __name__ == "__main__":
    if len(sys.argv) != 3:
        print('Usage: SparkStreamKafkaWordCount.py <kafka_brokers> <topic>')
        sys.exit(-1)

    sc = SparkContext(appName='SparkStreamKafkaWordCount')
    ssc = StreamingContext(sc)

    brokers, topic = sys.argv[1:]
    kvs = KafkaUtils.createDirectStream(ssc, [topic], {'metadata.broker.list': brokers})
    print('Recived: %s' % kvs)
    lines = kvs.map(lambda x: x[1])
    counts = words = lines.flatMap(lambda line: line.split(" ")).map(lambda word: (word, 1)).reduceByKey(
        lambda x, y: x + y)
    counts.pprint()

    ssc.start()
    ssc.awaitTermination()
