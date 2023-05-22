package com.lagou.dao;

import com.lagou.dao.impl.CourseDaoImpl;
import com.lagou.pojo.Course;
import com.lagou.utils.DateUtils;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * 课程模块 DAO层 CourseDao的测试类
 */
public class TestCourseDao {

    CourseDao courseDao = new CourseDaoImpl();

    /**
     * 测试 findCourseList() 方法, 即 查询课程列表信息
     */
    @Test
    public void testFindCourseList() {

        List<Course> courseList = courseDao.findCourseList();
        System.out.println(courseList);
    }

    /**
     * 测试 findByCourseNameAndStatus() 方法, 即 根据课程名称 和/或 课程状态 查询课程信息
     */
    @Test
    public void testFindByCourseNameAndStatus() {

        List<Course> courses = courseDao.findByCourseNameAndStatus("微服务", "");
        for (Course c : courses) {
            System.out.println(c.getId() + " " + c.getCourse_name() + " " + c.getStatus());
        }
    }

    // 测试 saveCourseSalesInfo() 方法，即添加课程营销信息的功能
    /*

course_name,
brief,
teacher_name,
teacher_info,
preview_first_field,
preview_second_field,
discounts,
price,
price_tag,
share_image_title,
share_title,
share_description,
course_description,
course_img_url,
STATUS,
create_time,
update_time
     */
    @Test
    public void testSaveCourseSalesInfo() {
        Course course = new Course();
        course.setCourse_name("爱情36计");
        course.setBrief("如何找到另一半");
        course.setTeacher_name("药水哥");
        course.setTeacher_info("人人都是药水哥");
        course.setPreview_first_field("5讲");
        course.setPreview_second_field("每周日更新");
        course.setDiscounts(88.88);
        course.setPrice(188.0);
        course.setPrice_tag("爱情秘籍");
        course.setShare_image_title("哈哈哈");
        course.setShare_title("嘻嘻嘻");
        course.setShare_description("爱情是一个秘密");
        course.setCourse_description("爱情36计，是你的秘密游戏");
        course.setCourse_img_url("https://www.xxx.com/xxx.jpg");
        course.setStatus(1);
        String now = DateUtils.getDateFormart();
        course.setCreate_time(now);
        course.setUpdate_time(now);

        int update = courseDao.saveCourseSalesInfo(course);

        System.out.println(update);
    }

    // 测试修改课程营销信息
    @Test
    public void testUpdateCourse() {
        // 1. 根据课程ID查找课程营销信息
        Course course = courseDao.findCourseById(1);
        System.out.println(course);

        // 2. 修改课程营销信息，并保存到数据库
        course.setCourse_name("32个Java面试必考点");
        course.setTeacher_name("李老师");
        course.setDiscounts(0.0);

        int row = courseDao.updateCourseSalesInfo(course);
        System.out.println(row);
    }
}
