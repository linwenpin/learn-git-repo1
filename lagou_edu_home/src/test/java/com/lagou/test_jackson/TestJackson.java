package com.lagou.test_jackson;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TSFBuilder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 测试 Jackson的使用
 */
public class TestJackson {

    private ObjectMapper objectMapper = new ObjectMapper(); // 便于测试


    /**
     * 使用 基于"对象绑定"的模式 把 Java对象转换为JSON对象
     */
    @Test
    public void javaBeanToJSONByObjectMapper() throws JsonProcessingException {

        // 1. 创建一个Java对象
        Person p = new Person("zhangefei", 35, new Date());

        // 2. 使用ObjectMapper对象的writeValue()方法 把 Java对象转换为JSON字符串
        String jsonStr = objectMapper.writeValueAsString(p);

        // 3. 打印JSON字符串
        System.out.println(jsonStr);
        /*
        输出结果：
            {"username":"zhangefei","age":35,"birthday":1680938545612}
         */
    }

    /**
     * 把 Java数组/Java集合 转换为 JSON对象
     */
    @Test
    public void collectionToJSONByObjectMapper() throws JsonProcessingException {

        Person p1 = new Person("zhangfei", 30, new Date());
        Person p2 = new Person("guanyu", 35, new Date());
        Person p3 = new Person("liubei", 40, new Date());

        // 把 Java数组 转换为 JSON
        Person[] personArr = { p1, p2, p3 };
        // [{"username":"zhangfei","age":30,"birthday":1680939965627},{"username":"guanyu","age":35,"birthday":1680939965627},{"username":"liubei","age":40,"birthday":1680939965627}]
        String jsonStr1 = objectMapper.writeValueAsString(personArr);

        System.out.println(jsonStr1);

        // 把 Java集合对象 转换为 JSON
        Collection<Person> c = Arrays.asList(personArr);
        // [{"username":"zhangfei","age":30,"birthday":1680940085569},{"username":"guanyu","age":35,"birthday":1680940085569},{"username":"liubei","age":40,"birthday":1680940085569}]
        String jsonStr2 = objectMapper.writeValueAsString(c);

        System.out.println(jsonStr2);

    }

    /**
     * 把 JSON 转换为 JavaBean对象
     */
    @Test
    public void jsonToJavaBeanByObjectMapper() throws JsonProcessingException {

        String jsonStr = "{\"username\":\"zhangefei\",\"age\":35,\"birthday\":1680938545612}";

        Person p = objectMapper.readValue(jsonStr, Person.class);

        System.out.println(p);
    }

    /**
     * 把JSON转换为 Java数组 或 Java集合
     */
    @Test
    public void jsonToCollectionByObjectMapper() throws JsonProcessingException {

        String jsonStr = "[{\"username\":\"zhangfei\",\"age\":30,\"birthday\":1680940085569},{\"username\":\"guanyu\",\"age\":35,\"birthday\":1680940085569},{\"username\":\"liubei\",\"age\":40,\"birthday\":1680940085569}]";

        // 把 JSON 转换为 Java数组
        Person[] personArr = objectMapper.readValue(jsonStr, Person[].class);
        System.out.println(Arrays.toString(personArr));

        // 把 JSON 转换为 Java集合
        List<Person> personList = objectMapper.readValue(jsonStr, new TypeReference<List<Person>>() {});
        System.out.println(personList);
    }

    /**
     * 不允许未知的JSON字段
     * @throws JsonProcessingException
     */
    @Test
    public void notAllowExtraJSONKey() throws JsonProcessingException {

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String jsonStr = "{\"username\":\"zhangefei\",\"age\":35,\"birthday\":1680938545612, \"email\":\"1234@zf.com\"}";

        Person p = objectMapper.readValue(jsonStr, Person.class);
        System.out.println(p);
    }

    /**
     * 忽略JSON值为null而类型为原始类型的Java字段
     * @throws JsonProcessingException
     */
    @Test
    public void ignoreNullOnPrimaryType() throws JsonProcessingException {

        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);

        // 注意：age键的值为null
        String jsonStr = "{\"username\":\"zhangefei\",\"age\":null,\"birthday\":1680938545612}";

        Person p = objectMapper.readValue(jsonStr, Person.class);
        System.out.println(p);
    }

    /**
     * Jackson对 java.util.Date类型字段的处理规则
     */
    @Test
    public void dateFormatInJackson() throws JsonProcessingException {

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        objectMapper.setDateFormat(sdf);

        // 将 Java对象转换为JSON
        Person p = new Person("zhangefei", 35, new Date());
        String jsonStr = objectMapper.writeValueAsString(p);
        System.out.println(jsonStr);

        // 在将JSON转换为Java对象
        Person p2 = objectMapper.readValue(jsonStr, Person.class);
        System.out.println(p2);
    }

    /**
     * Jackson对 java.time.LocalDateTime类型字段的处理规则
     * 默认：InvalidDefinitionException
     */
    @Test
    public void dateFormatInJackson2() throws JsonProcessingException {

        objectMapper.findAndRegisterModules();

        Person p = new Person("zhangefei", 35, new Date(), LocalDateTime.now(), LocalDate.now(), "张飞");

        String jsonStr = objectMapper.writeValueAsString(p);
        System.out.println(jsonStr);

        Person p2 = objectMapper.readValue(jsonStr, Person.class);
        System.out.println(p2);
    }

    /**
     * @JsonCreator 和 @JsonProperty注解的使用测试
     */
    @Test
    public void testJsonCreatorAndJsonProperty() throws JsonProcessingException {

        // 序列化
        Student s = new Student("guanyu", 35, new Date());
        objectMapper.setFilterProvider(new SimpleFilterProvider().addFilter("myFilter", SimpleBeanPropertyFilter.serializeAll()));
        String jsonStr = objectMapper.writeValueAsString(s);
        System.out.println(jsonStr);

        // 反序列化
        Student s2 = objectMapper.readValue(jsonStr, Student.class);
        System.out.println(s2);
        System.out.println(s2.getReturnMap());
    }

    /**
     * 仅序列化部分字段
     */
    @Test
    public void testJustSerializedFewFiled() throws JsonProcessingException {

        Student s = new Student("guanyu", 35, new Date());

//        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept("birthday");
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("name", "birthday");

        FilterProvider fs = new SimpleFilterProvider().addFilter("myFilter",  filter);

        System.out.println( objectMapper.writer(fs).writeValueAsString(s) );

        String jsonStr = "{\"age\":35,\"name\":\"guanyu\",\"birthday\":1681131354777,\"a\":\"a11\",\"b\":\"b22\",\"c\":\"c33\"}";
        System.out.println(objectMapper.readValue(jsonStr, Student.class));
    }

    /**
     * ObjectNode：另一种生成JSON的方法
     */
    @Test
    public void testJsonGenerator() throws JsonProcessingException {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("status", 0);
        objectNode.put("message", "success");
        String json = objectMapper.writeValueAsString(objectNode);
        System.out.println(json);
    }

}
