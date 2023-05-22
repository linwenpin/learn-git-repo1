package com.lagou.test_beanutils;

import com.lagou.pojo.Course;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class TestBeanUtils {

    @Test
    public void testBeanUtils() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        // 1. 把Map集合中数据封装到JavaBean对象中
        // 前提：Map中的key与JavaBean中的成员变量同名，value与成员变量的类型相同
        // 1.1 创建一个Course
        Course course =  new Course();
        // 1.2 创建一个Map，并添加数据
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
        map.put("course_name", "开挂36天");
        map.put("brief", "教你开启开挂人生");
        map.put("teacher_name", "雷教练");
        map.put("teacher_info", "雷雷雷");
        // 1.3 使用BeanUtils将Map中的数据封装到course对象中
        BeanUtils.populate(course, map);
        // 1.4 测试
        System.out.println(
                course.getId() + " "
                + course.getCourse_name() + " "
                + course.getBrief() + " "
                + course.getTeacher_name() + " "
                + course.getTeacher_info()
        );

        // 2. 设置和获取JavaBean对象中的属性值
        BeanUtils.setProperty(course, "price", 800.0);
        String value = BeanUtils.getProperty(course, "price");
        System.out.println(value);
    }
}
