package com.lagou.test_fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.lagou.utils.DateUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Fastjson的使用测试
 */
public class TestFastjson {

    /**
     * 将 Java对象 转换为 JSON字符串
     */
    @Test
    public void javaBeanToJSON() {

        // 1. 创建一个Java对象
        Person p = new Person("zhangfei", 30, DateUtils.getDateFormart());

        // 2. 使用 Fastjson中的JSON类的静态方法toJSONString() 把Java对象转换为JSON字符串
        String jsonStr = JSON.toJSONString(p);

        // 3. 打印JSON字符串
        System.out.println(jsonStr);
        /*
        输出结果为：
            {"age":30,"birthday":"2023-04-08 14:22:27","username":"zhangfei"}
         */
    }

    // 把Java集合对象 转换为 JSON数组
    @Test
    public void listToJSON() {

        // 1. 创建一个 Java对象集合
        List<Person> personList = Arrays.asList(
                new Person("zhangfei", 30, DateUtils.getDateFormart()),
                new Person("guanyu", 35, DateUtils.getDateFormart()),
                new Person("liubei", 40, DateUtils.getDateFormart())
        );

        // 2. 使用 Fastjson中的 JSON.toJSONString()静态方法 把Java集合对象转换为JSON数组
        String jsonStr = JSON.toJSONString(personList);

        // 3. 打印JSON数组
        System.out.println(jsonStr);
        /*
        输出结果为：
            [{"age":30,"birthday":"2023-04-08 14:26:59","username":"zhangfei"},{"age":35,"birthday":"2023-04-08 14:26:59","username":"guanyu"},{"age":40,"birthday":"2023-04-08 14:26:59","username":"liubei"}]
         */
    }

    /**
     * 把 JSON对象 转换为 Java对象
     */
    @Test
    public void jsonToJavaBean() {

        // 1. 准备一个 JSON对象（字符串）
        String jsonStr = "{\"age\":30,\"birthday\":\"2023-04-08 14:22:27\",\"username\":\"zhangfei\"}";

        // 2. 使用 Fastjson中的 JSON.parseObject()静态方法 把 JSON字符串转换为Java对象
        Person p = JSON.parseObject(jsonStr, Person.class);

        // 3. 打印Java对象
        System.out.println(p);
    }

    /**
     * 把 JSON数组 转换为 Java集合对象
     */
    @Test
    public void jsonToList() {

        // 1. 准备一个 JSON数组（字符串）
        String jsonStr = "[{\"age\":30,\"birthday\":\"2023-04-08 14:26:59\",\"username\":\"zhangfei\"},{\"age\":35,\"birthday\":\"2023-04-08 14:26:59\",\"username\":\"guanyu\"},{\"age\":40,\"birthday\":\"2023-04-08 14:26:59\",\"username\":\"liubei\"}]";

        // 2. 使用 Fastjson中的 JSON.parseArray()静态方法 把 JSON字符串 转换为 Java集合对象
        List<Person> personList = JSON.parseArray(jsonStr, Person.class);

        // 3. 打印 Java集合对象
        System.out.println(personList);
    }

    /**
     * 另一种生成JSON的方式
     */
    @Test
    public void anotherWayToGenerateJSON() {
        JSONObject object = new JSONObject();
        object.put("status", 1);
        object.put("message", "fail");
        String json = object.toString();
        System.out.println(json);
    }

    // 动态过滤
    @Test
    public void testFilter() {
        Person p = new Person("zhangfei", 30, DateUtils.getDateFormart());

        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(/*"USERNAME"*/);
//        filter.getIncludes().add("USERNAME");
        filter.getExcludes().add("USERNAME");

        String json = JSON.toJSONString(p, filter);
        System.out.println(json);
    }
}
