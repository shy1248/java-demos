#!/usr/bin/env python
# -*- coding=UTF-8 -*-
'''
@ Since: 2019-07-25 10:18:03
@ Author: shy
@ Email: yushuibo@ebupt.com / hengchen2005@gmail.com
@ Version: v1.0
@ Description: -
@ LastTime: 2019-07-25 10:39:17
'''

import abc


class Chart(object):
    __Meta__ = abc.ABCMeta

    @abc.abstractmethod
    def display(self):
        pass


class PieChart(Chart):

    def __init__(self):
        print('PieChart initilized!')

    def display(self):
        print('PieChart displayed!')


class LineChart(Chart):

    def __init__(self):
        print('LineChart initilized!')

    def display(self):
        print('LineChart displayed!')


class HistogramChart(Chart):

    def __init__(self):
        print('HistogramChart initilized!')

    def display(self):
        print('HistogramChart displayed!')


class ChartFactory(object):
    PIE_CHART = 1
    LINE_CHART = 2
    HISTOGRAM_CHART = 3

    @staticmethod
    def create_chart(_type):
        if _type == ChartFactory.PIE_CHART:
            chart = PieChart()
        elif _type == ChartFactory.LINE_CHART:
            chart = LineChart()
        elif _type == ChartFactory.HISTOGRAM_CHART:
            chart = HistogramChart()
        else:
            raise ValueError('Unsuport chart type: {}'.format(_type))

        return chart


if __name__ == "__main__":
    chart = ChartFactory.create_chart(ChartFactory.PIE_CHART)
    chart.display()
    chart = ChartFactory.create_chart(ChartFactory.LINE_CHART)
    chart.display()
    chart = ChartFactory.create_chart(ChartFactory.HISTOGRAM_CHART)
    chart.display()
