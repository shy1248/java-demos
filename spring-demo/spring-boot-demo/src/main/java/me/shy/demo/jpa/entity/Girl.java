/**
 * @Since: 2019-12-07 14:51:29
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 * @LastTime: 2019-12-07 16:19:26
 */
package me.shy.demo.jpa.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity public class Girl implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue private long id;
    private String cupSize;
    private int age;

    public Girl() {

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
