/**
 * @Date        : 2021-02-14 17:12:55
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.demo.builder;

public class Table {
    private String name;
    private int height;
    private int width;
    private int length;
    private String color;
    private String shap;

    private Table() {
    }

    public static TableBuilder builder() {
        return new TableBuilder(new Table());
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public String getColor() {
        return color;
    }

    public String getShap() {
        return shap;
    }

    @Override
    public String toString() {
        return "Table [color=" + color + ", height=" + height + ", length=" + length + ", name=" + name + ", shap="
                + shap + ", width=" + width + "]";
    }

    public static class TableBuilder {
        private Table t;

        public TableBuilder(Table t) {
            this.t = t;
        }

        public TableBuilder setName(String name) {
            this.t.name = name;
            return this;
        }

        public TableBuilder setHeight(int height) {
            this.t.height = height;
            return this;
        }

        public TableBuilder setWidth(int width) {
            this.t.width = width;
            return this;
        }

        public TableBuilder setLength(int length) {
            this.t.length = length;
            return this;
        }

        public TableBuilder setCorlor(String color) {
            this.t.color = color;
            return this;
        }

        public TableBuilder setShap(String shap) {
            this.t.shap = shap;
            return this;
        }

        public Table build() {
            return t;
        }
    }
}
