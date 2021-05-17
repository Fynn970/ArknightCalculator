package com.yusuo.zsshop;

public class Person {
    String name;
    int age;
    double slary;

    public Person(String name, int age, double slary) {
        this.name = name;
        this.age = age;
        this.slary = slary;
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

    public double getSlary() {
        return slary;
    }

    public void setSlary(double slary) {
        this.slary = slary;
    }
}
