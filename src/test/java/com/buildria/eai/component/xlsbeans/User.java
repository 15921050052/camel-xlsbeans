package com.buildria.eai.component.xlsbeans;

import net.java.amateras.xlsbeans.annotation.Column;

public class User {

    @Column(columnName = "id")
    public int id;
    
    @Column(columnName = "name")
    public String name;
    
    @Column(columnName = "gender")
    public String gender;
    
    @Column(columnName = "age")
    public int age;

    public User() {
    }
    
    public User(int id, String name, String gender, int age) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
    }
    
}
