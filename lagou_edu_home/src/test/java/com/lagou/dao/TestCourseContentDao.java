package com.lagou.dao;

import com.lagou.dao.impl.CourseContentDaoImpl;
import com.lagou.pojo.Course;
import com.lagou.pojo.Course_Lesson;
import com.lagou.pojo.Course_Section;
import com.lagou.utils.DateUtils;
import org.junit.Test;

import java.util.List;

public class TestCourseContentDao {

    CourseContentDao courseContentDao = new CourseContentDaoImpl();

    // 测试 根据 课程ID 查询课程内容信息
    @Test
    public void testFindSectionAndLessonByCourseId() {

        List<Course_Section> sectionList = courseContentDao.findSectionAndLessonByCourseId(59);

        for (Course_Section section : sectionList) {
            System.out.println(section.getId() + " " + section.getSection_name());
            List<Course_Lesson> lessonList = section.getLessonList();
            for (Course_Lesson lesson : lessonList) {
                System.out.println("\t" + lesson.getId() + " " + lesson.getTheme() + " " + lesson.getSection_id());
            }
        }
    }

    // 测试 根据课程ID查询课程信息
    @Test
    public void testFindCourseByCourseId() {

        Course c = courseContentDao.findCourseByCourseId(59);
        System.out.println(c.getId() + " " + c.getCourse_name());
    }

    // 测试 保存章节信息
    @Test
    public void testSaveSection() {

        Course_Section section = new Course_Section();
        section.setCourse_id(59);
        section.setSection_name("Vue高级3");
        section.setDescription("Vue相关的高级技术升级版");
        section.setOrder_num(9);
        section.setStatus(2); // 0:隐藏；1：待更新；2：已发布

        String date = DateUtils.getDateFormart();
        section.setCreate_time(date);
        section.setUpdate_time(date);

        int row = courseContentDao.saveSection(section);
        System.out.println(row);
    }

    // 测试 修改章节信息
    @Test
    public void testUpdateSection() {

        Course_Section section = new Course_Section();
        section.setId(45);
        section.setSection_name("大数据分析");
        section.setDescription("和雷教练一起学习如何进行大数据分析");
        section.setOrder_num(5);
        String date = DateUtils.getDateFormart();
        section.setUpdate_time(date);

        int row = courseContentDao.updateSection(section);
        System.out.println(row);
    }

    // 测试 修改章节状态
    @Test
    public void testUpdateSectionStatus() {

        int row = courseContentDao.updateSectionStatus(1, 1);
        System.out.println(row);
    }
}
