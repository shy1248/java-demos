package me.shy.spring.ioc;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
public class InjectionDemoBean {
    private int id;
    private String name;
    private String[] arrayItems;
    private List<String> listItems;
    private Set<Integer> setItems;
    private Map<Integer, String> entries;
    private Properties props;
    private People injectPerson;

    @Override
    public String toString() {
        return "InjectionDemoBean{" + "id=" + id + ", name='" + name + '\'' + ", arrayItems="
                + Arrays.toString(arrayItems) + ", listItems=" + listItems + ", setItems=" + setItems + ", entries="
                + entries + ", props=" + props + ", injectPerson=" + injectPerson + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getArrayItems() {
        return arrayItems;
    }

    public void setArrayItems(String[] arrayItems) {
        this.arrayItems = arrayItems;
    }

    public List<String> getListItems() {
        return listItems;
    }

    public void setListItems(List<String> listItems) {
        this.listItems = listItems;
    }

    public Set<Integer> getSetItems() {
        return setItems;
    }

    public void setSetItems(Set<Integer> setItems) {
        this.setItems = setItems;
    }

    public Map<Integer, String> getEntries() {
        return entries;
    }

    public void setEntries(Map<Integer, String> entries) {
        this.entries = entries;
    }

    public Properties getProps() {
        return props;
    }

    public void setProps(Properties props) {
        this.props = props;
    }

    public People getInjectPerson() {
        return injectPerson;
    }

    public void setInjectPerson(People injectPerson) {
        this.injectPerson = injectPerson;
    }
}
