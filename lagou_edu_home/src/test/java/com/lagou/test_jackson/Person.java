package com.lagou.test_jackson;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"comment"})
public class Person {

    @JsonPropertyOrder
    private String username;

    private int age;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime now;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private LocalDate today;

//    @JsonIgnore
    private String comment;

    @JsonCreator
    public Person(String username, int age, Date birthday) {
        this.username = username;
        this.age = age;
        this.birthday = birthday;
    }

    @JsonGetter(value = "name")
    public String getUsername() {
        return username;
    }

    @JsonSetter(value = "name")
    public void setUsername(String username) {
        this.username = username;
    }
}
