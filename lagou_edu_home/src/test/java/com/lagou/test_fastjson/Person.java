package com.lagou.test_fastjson;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @JSONField(name="USERNAME", ordinal = 1)
    private String username;

    @JSONField(name="AGE", ordinal = 2)
    private int age;

//    @JSONField(serialize = false)
    private String birthday;
}
