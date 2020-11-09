package me.shy.demo.TradeDemo;

/**
 * @Since: 2020/5/10 15:33
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: 订单，封装交易数据
 *
 **/
public class Trade {
    // 订单Id
    private String id;
    // 订单用户名
    private String name;
    // 订单价格
    private double price;

    public Trade(String id) {
        this.id = id;
    }

    @Override public String toString() {
        return "Trade{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", price=" + price + '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
