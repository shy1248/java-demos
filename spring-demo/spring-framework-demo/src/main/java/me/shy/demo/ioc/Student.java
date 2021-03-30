package me.shy.demo.ioc;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
public class Student {
    private int no;
    private String name;
    private int age;
    public Student(int no, String name) {
        this.no = no;
        this.name = name;
        System.out.println("Class me.shy.demo.framework.ioc.Student is birthed with 2 attributes!");
    }

    public Student() {
        System.out.println("Class me.shy.demo.framework.ioc.Student is birthed with non attributes!");
    }

    public Student(int no, String name, int age) {
        this.no = no;
        this.name = name;
        this.age = age;
        System.out.println("Class me.shy.demo.framework.ioc.Student is birthed with 3 attributes!");
    }

    @Override public String toString() {
        return "Student{" + "no=" + no + ", name='" + name + '\'' + ", age=" + age + '}';
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
