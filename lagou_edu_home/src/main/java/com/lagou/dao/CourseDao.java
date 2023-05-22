package com.lagou.dao;

import com.lagou.pojo.Course;

import java.util.List;

/**
 * 课程管理模块 DAO层接口 课程
 */
public interface CourseDao {

    /**
     * 查询课程列表信息
     * @return
     */
    public List<Course> findCourseList();

    /**
     * 根据条件查询课程信息
     * @return
     */
    public List<Course> findByCourseNameAndStatus(String courseName, String status);

    /**
     * 添加课程营销信息
     * @param course
     * @return
     */
    public int saveCourseSalesInfo(Course course);

    /**
     * 根据 课程ID 查找课程营销信息
     * @param id
     * @return
     */
    public Course findCourseById(int id);

    /**
     * 修改课程营销信息
     * @param course
     * @return
     */
    public int updateCourseSalesInfo(Course course);

    // 根据课程ID修改指定课程的状态
    int updateCourseStatus(Course course);
}
