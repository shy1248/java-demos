package me.shy.base.oo;

public class Cow {
    private int age;

    public Cow bear() {
        Cow cowChild = new Cow();
        return cowChild;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
