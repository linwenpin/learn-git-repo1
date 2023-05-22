package com.lagou.service;

import com.lagou.pojo.Course;
import com.lagou.pojo.Course_Section;

import java.util.List;

/**
 * 课程内容管理 Service层接口
 */
public interface CourseContentService {

    // 根据 课程ID 查询课程内容
    public List<Course_Section> findSectionAndLessonByCourseId(int courseId);

    // 根据 课程ID 查询课程信息
    public Course findCourseByCourseId(int courseId);

    // 保存章节信息
    public String saveSection(Course_Section section);

    // 修改章节信息
    public String updateSection(Course_Section section);

    // 修改章节状态
    public String updateSectionStatus(int id, int status);
}
