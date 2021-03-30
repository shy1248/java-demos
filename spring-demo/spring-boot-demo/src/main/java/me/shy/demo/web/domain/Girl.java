/**
 * @Since: 2019-12-07 14:51:29
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 * @LastTime: 2019-12-07 22:16:05
 */
package me.shy.demo.web.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;

@Entity public class Girl implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue private long id;
    private String cupSize;
    @Min(value = 8, message = "Hello, kids!") private int age;

    public Girl() {

    }

    @Override public String toString() {
        return "Girl[Id=" + this.id + ",CupSize=" + this.cupSize + ",Age=" + this.age + "]";
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the cupSize
     */
    public String getCupSize() {
        return cupSize;
    }

    /**
     * @param cupSize the cupSize to set
     */
    public void setCupSize(String cupSize) {
        this.cupSize = cupSize;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

}
