package com.lagou.service.impl;

import com.lagou.base.StatusCode;
import com.lagou.dao.CourseContentDao;
import com.lagou.dao.impl.CourseContentDaoImpl;
import com.lagou.pojo.Course;
import com.lagou.pojo.Course_Section;
import com.lagou.service.CourseContentService;
import com.lagou.utils.DateUtils;
import jdk.net.SocketFlow;

import java.util.List;

/**
 * 课程内容管理 Service层实现类
 */
public class CourseContentServiceImpl implements CourseContentService {

    CourseContentDao courseContentDao = new CourseContentDaoImpl();

    // 根据 课程ID 查询课程内容信息
    @Override
    public List<Course_Section> findSectionAndLessonByCourseId(int courseId) {
        List<Course_Section> sectionList = courseContentDao.findSectionAndLessonByCourseId(courseId);
        return sectionList;
    }

    // 根据 课程ID 查询课程信息
    @Override
    public Course findCourseByCourseId(int courseId) {
        Course course = courseContentDao.findCourseByCourseId(courseId);
        return course;
    }

    // 保存章节信息
    @Override
    public String saveSection(Course_Section section) {

        // 补全信息
        section.setStatus(2); // 状态：0-隐藏，1-待更新，2-已发布
        String date = DateUtils.getDateFormart();
        section.setCreate_time(date);
        section.setUpdate_time(date);

        int row = courseContentDao.saveSection(section);

        // 判断是否保存成功
        if (row > 0) {
            // 如果成功，则返回保存成功的JSON字符串
            String result = StatusCode.SUCCESS.toString();
            return result;
        } else {
            // 如果失败，则返回保存失败的JSON字符串
            String result = StatusCode.FAIL.toString();
            return result;
        }
    }

    // 修改章节信息
    @Override
    public String updateSection(Course_Section section) {

        // 1. 补全信息
        String date = DateUtils.getDateFormart();
        section.setUpdate_time(date);

        // 2. 调用dao
        int row = courseContentDao.updateSection(section);

        // 3. 如果更新成功，则返回表示成功的JSON字符串，否则返回表示失败的JSON字符串
        if (row > 0) {
            String result = StatusCode.SUCCESS.toString();
            return result;
        } else {
            String result = StatusCode.FAIL.toString();
            return result;
        }
    }

    // 修改章节状态
    @Override
    public String updateSectionStatus(int id, int status) {

        int row = courseContentDao.updateSectionStatus(id, status);

        if (row > 0) {
            String result = StatusCode.SUCCESS.toString();
            return result;
        } else {
            String result = StatusCode.FAIL.toString();
            return result;
        }
    }
}
