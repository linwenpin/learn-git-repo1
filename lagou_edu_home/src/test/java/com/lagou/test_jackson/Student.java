package com.lagou.test_jackson;

import com.fasterxml.jackson.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//@JsonPropertyOrder({"birthday", "age", "name"}
@JsonFilter("myFilter")
@JsonPropertyOrder(alphabetic = true)
public class Student {

    private String name;

    private int age;

    private Date birthday;

    @JsonAnyGetter
    private Map<String, Object> initMap = new HashMap<>() {
        {
            put("a", "a11");
            put("b", "b22");
            put("c", "c33");
        }
    };

    @JsonIgnore
    private Map<String, Object> returnMap = new HashMap<>();

    public Map<String, Object> getReturnMap() {
        return this.returnMap;
    }

    @JsonAnySetter
    public void otherProperty(String key, Object value) {
        returnMap.put(key, value);
//        System.out.println(key + " " + value + " " + returnMap);
    }

//    @JsonCreator
    public Student(/*@JsonProperty("name")*/ String name, /*@JsonProperty("age")*/ int age, /*@JsonProperty("birthday")*/ Date birthday) {
        this.name = name;
        this.age = age;
        this.birthday = birthday;
    }

    @JsonCreator
    public static Student createStudent(
            @JsonProperty("name") String name,
            @JsonProperty("age") int age,
            @JsonProperty("property") Date birthday) {
        return new Student(name, age, birthday);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                '}';
    }
}
