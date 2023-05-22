package com.lagou.service.impl;

import com.lagou.base.StatusCode;
import com.lagou.dao.CourseDao;
import com.lagou.dao.impl.CourseDaoImpl;
import com.lagou.pojo.Course;
import com.lagou.service.CourseService;
import com.lagou.utils.DateUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 课程管理模块 Service层实现类
 */
public class CourseServiceImpl implements CourseService {

    CourseDao courseDao = new CourseDaoImpl();

    @Override
    public List<Course> findCourseList() {
        List<Course> result = courseDao.findCourseList();
        return result;
    }

    @Override
    public List<Course> findByCourseNameAndStatus(String courseName, String status) {
        List<Course> result = courseDao.findByCourseNameAndStatus(courseName, status);
        return result;
    }

    // 添加课程营销信息
    @Override
    public String saveCourseSalesInfo(Course course) {

        // 1. 补全课程信息
        course.setStatus(1);
        String date = DateUtils.getDateFormart();
        course.setCreate_time(date);
        course.setUpdate_time(date);

        // 2. 调用DAO层添加课程信息
        int row = courseDao.saveCourseSalesInfo(course);
        if (row > 0) {
            // 保存成功
            String result = StatusCode.SUCCESS.toString();
            return result;
        } else {
            // 保存失败
            String result = StatusCode.FAIL.toString();
            return result;
        }
    }

    @Override
    public Course findCourseById(int id) {
        return courseDao.findCourseById(id);
    }

    @Override
    public String updateCourseSalesInfo(Course course) {
        int row = courseDao.updateCourseSalesInfo(course);

        // 根据返回结果判断保存是否成功
        if (row > 0) {
            // 保存成功
            String result = StatusCode.SUCCESS.toString();
            return result;
        } else {
            // 保存失败
            String result = StatusCode.FAIL.toString();
            return result;
        }
    }

    @Override
    public Map<String, Integer> updateCourseStatus(Course course) {

        int row = courseDao.updateCourseStatus(course);

        // 如果修改课程状态成功，则需要返回 封装课程状态的Map
        Map<String, Integer> map = new HashMap<>();
        if (course.getStatus() == 0) {
            map.put("status", 0);
        } else {
            map.put("status", 1);
        }

        return map;
    }
}
