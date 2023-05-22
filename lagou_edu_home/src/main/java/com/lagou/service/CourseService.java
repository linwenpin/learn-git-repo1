package com.lagou.service;

import com.lagou.pojo.Course;

import java.util.List;
import java.util.Map;

/**
 * 课程管理模块 Service层接口 课程
 */
public interface CourseService {

    /**
     * 查姓课程列表信息
     * @return
     */
    public List<Course> findCourseList();

    /**
     * 根据条件查姓课程信息
     * @param courseName
     * @param status
     * @return
     */
    public List<Course> findByCourseNameAndStatus(String courseName, String status);


    /**
     * 添加课程营销信息
     * @param course
     * @return
     */
    public String saveCourseSalesInfo(Course course);

    public Course findCourseById(int id);

    public String updateCourseSalesInfo(Course course);

    public Map<String, Integer> updateCourseStatus(Course course);
}
