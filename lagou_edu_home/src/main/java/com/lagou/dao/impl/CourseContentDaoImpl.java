package com.lagou.dao.impl;

import com.lagou.dao.CourseContentDao;
import com.lagou.pojo.Course;
import com.lagou.pojo.Course_Lesson;
import com.lagou.pojo.Course_Section;
import com.lagou.utils.DateUtils;
import com.lagou.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * 课程内容管理 DAO层实现类
 * */
public class CourseContentDaoImpl implements CourseContentDao {

    // 根据 课程ID 查询课程内容信息
    @Override
    public List<Course_Section> findSectionAndLessonByCourseId(int courseId) {

        try {
            // 1. 创建 QueryRunner 自动模式
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

            // 2. 编写SQL
            String sql = "SELECT\n" +
                    "id, \n" +
                    "course_id, \n" +
                    "section_name, \n" +
                    "description, \n" +
                    "order_num, \n" +
                    "STATUS\n" +
                    "FROM course_section \n" +
                    "WHERE course_id = ?";

            // 3. 设置参数、执行查询、处理查询结果
            List<Course_Section> sectionList = qr.query(sql, new BeanListHandler<Course_Section>(Course_Section.class),
                    courseId);

            // 4. 根据 章节ID 查询课时信息
            for (Course_Section course_section : sectionList) {
                // 获取指定章节的课时信息
                List<Course_Lesson> lessonList = findLessonBySectionId(course_section.getId());
                // 把课时信息封装到章节对象中
                course_section.setLessonList(lessonList);
            }

            return sectionList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 根据 章节ID 查询课时信息
    @Override
    public List<Course_Lesson> findLessonBySectionId(int sectionId) {

        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

            String sql = "SELECT \n" +
                    "id, \n" +
                    "course_id,\n" +
                    "section_id, \n" +
                    "theme, \n" +
                    "duration, \n" +
                    "is_free, \n" +
                    "order_num, \n" +
                    "STATUS \n" +
                    "FROM course_lesson\n" +
                    "WHERE section_id = ?";

            List<Course_Lesson> lessonList = qr.query(sql, new BeanListHandler<Course_Lesson>(Course_Lesson.class),
                    sectionId);

            return lessonList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 根据课程ID查询课程信息
    @Override
    public Course findCourseByCourseId(int courseId) {

        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

            String sql = "SELECT id, course_name FROM course WHERE id = ?";

            Course course = qr.query(sql, new BeanHandler<Course>(Course.class), courseId);

            return course;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 保存章节信息
    @Override
    public int saveSection(Course_Section section) {

        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

            String sql = "INSERT INTO course_section(\n" +
                    "course_id,\n" +
                    "section_name, \n" +
                    "description, \n" +
                    "order_num, \n" +
                    "STATUS, \n" +
                    "create_time, \n" +
                    "update_time\n" +
                    ") VALUE(?,?,?,?,?,?,?)";

            Object[] params = { section.getCourse_id(), section.getSection_name(), section.getDescription(),
                    section.getOrder_num(), section.getStatus(), section.getCreate_time(),section.getUpdate_time() };

            int row = qr.update(sql, params);

            return row;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 修改章节信息
    @Override
    public int updateSection(Course_Section section) {

        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

            String sql = "UPDATE course_section SET\n" +
                    "section_name = ?, \n" +
                    "description =?, \n" +
                    "order_num = ?, \n" +
                    "update_time = ? \n" +
                    "WHERE id = ?";

            Object[] params = {section.getSection_name(), section.getDescription(), section.getOrder_num(),
                    section.getUpdate_time(), section.getId() };

            int row = qr.update(sql, params);
            return row;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 修改章节状态
    @Override
    public int updateSectionStatus(int id, int status) {

        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

            String sql = "UPDATE course_section SET STATUS = ?, update_time = ? WHERE id = ?";

            Object[] params = { status, DateUtils.getDateFormart(), id };

            int row = qr.update(sql, params);
            return row;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
