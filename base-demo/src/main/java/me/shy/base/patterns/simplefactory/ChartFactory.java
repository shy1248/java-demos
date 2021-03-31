/**
 * @Since: 2019-07-24 22:51:03
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 * @LastTime: 2019-07-24 23:38:33
 */

package me.shy.base.patterns.simplefactory;

public class ChartFactory {

    public static final int PIE_CHART = 1;
    public static final int LINE_CHART = 2;
    public static final int HISTOGRAM_CHART = 3;

    /**实际中，通常将chartType当作配置文件传入 */
    public static Chart createChart(int chartType) {
        Chart chart = null;
        if (chartType == ChartFactory.PIE_CHART) {
            chart = new PieChart();
        } else if (chartType == ChartFactory.LINE_CHART) {
            chart = new LineChart();
        } else if (chartType == ChartFactory.HISTOGRAM_CHART) {
            chart = new HistogramChart();
        } else {
            new RuntimeException("Unsuport chart type: " + chartType);
        }
        return chart;
    }
}

