package com.lagou.dao.impl;

import com.lagou.dao.CourseDao;
import com.lagou.pojo.Course;
import com.lagou.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 课程管理模块 DAO层实现类 课程
 */
public class CourseDaoImpl implements CourseDao {

    @Override
    public List<Course> findCourseList() {

        try {
            QueryRunner qr =  new QueryRunner(DruidUtils.getDataSource());
            // course表采用逻辑删除，is_del字段表示课程是否删除，0-表示未删除，1-表示删除
            String sql = "SELECT \n" +
                    "\tid, \n" +
                    "\tcourse_name, \n" +
                    "\tprice, \n" +
                    "\tsort_num, \n" +
                    "\tSTATUS\n" +
                    "FROM course\n" +
                    "WHERE is_del = ?";

            List<Course> result = qr.query(sql, new BeanListHandler<Course>(Course.class), 0);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 根据条件查询课程信息
    @Override
    public List<Course> findByCourseNameAndStatus(String courseName, String status) {

        try {
            // 1. 创建QueryRunner
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

            // 2. 编写sql
            String sql = "SELECT id, course_name, price, sort_num, STATUS FROM course WHERE 1=1 AND is_del = ? ";
            List<Object> params = new ArrayList<>();
            params.add(0);
            // 如果参数courseName存在，则拼接条件并添加占位符参数；否则不添加
            if (null != courseName && !"".equals(courseName)) {
                sql += " AND course_name LIKE ?";
                // MySQL的模糊查询，需要添加 %
                courseName = "%" + courseName + "%";
                // 添加占位符参数
                params.add(courseName);
            }
            // 如果参数status存在，则需要拼接根据状态查询的条件并设置占位符参数；
            if (null != status && !"".equals(status)) {
                sql += " AND STATUS = ?";
                // 注意：表course中status字段的类型是int
                params.add( Integer.parseInt(status) );
            }

            // 3. 设置占位符参数
            // 4. 执行查询
            // 5. 处理查询结果
            List<Course> result = qr.query(sql, new BeanListHandler<Course>(Course.class), params.toArray());
            return result;
            // 6. 释放资源：自动模式的QueryRunner，资源的释放由DBUtils自动管理。
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 添加课程营销信息
     * @param course
     * @return
     */
    @Override
    public int saveCourseSalesInfo(Course course) {
        try {
            // 1. 获取QueryRunner对象
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
            // 2. 编写Sql
            String sql = "INSERT INTO course(\n" +
                    "course_name, \n" +
                    "brief, \n" +
                    "teacher_name, \n" +
                    "teacher_info, \n" +
                    "preview_first_field, \n" +
                    "preview_second_field, \n" +
                    "discounts, \n" +
                    "price, \n" +
                    "price_tag, \n" +
                    "share_image_title, \n" +
                    "share_title, \n" +
                    "share_description, \n" +
                    "course_description, \n" +
                    "course_img_url, \n" +
                    "STATUS, \n" +
                    "create_time, \n" +
                    "update_time\n" +
                    ") \n" +
                    "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            // 3. 设置占位符参数
            Object[] params = {course.getCourse_name(), course.getBrief(), course.getTeacher_name(),
                    course.getTeacher_info(), course.getPreview_first_field(), course.getPreview_second_field(),
                    course.getDiscounts(), course.getPrice(), course.getPrice_tag(), course.getShare_image_title(),
                    course.getShare_title(), course.getShare_description(), course.getCourse_description(),
                    course.getCourse_img_url(), course.getStatus(), course.getCreate_time(), course.getUpdate_time()};
            // 4. 执行查询
            int row = qr.update(sql, params);
            // 5. 处理查询结果
            return row;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 根据课程ID查找课程营销信息
    @Override
    public Course findCourseById(int id) {

        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

            String sql = "SELECT \n" +
                    "id, \n" +
                    "course_name, \n" +
                    "brief, \n" +
                    "teacher_name, \n" +
                    "teacher_info, \n" +
                    "preview_first_field, \n" +
                    "preview_second_field, \n" +
                    "discounts, \n" +
                    "price, \n" +
                    "price_tag, \n" +
                    "share_image_title, \n" +
                    "share_title, \n" +
                    "share_description, \n" +
                    "course_description, \n" +
                    "course_img_url, \n" +
                    "STATUS\n" +
                    "FROM course \n" +
                    "WHERE id = ?";

            Course result = qr.query(sql, new BeanHandler<Course>(Course.class), id);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 修改课程营销信息
    @Override
    public int updateCourseSalesInfo(Course course) {

        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

            String sql = "UPDATE course SET \n" +
                    "course_name = ?, \n" +
                    "brief = ?, \n" +
                    "teacher_name = ?, \n" +
                    "teacher_info = ?, \n" +
                    "preview_first_field = ?, \n" +
                    "preview_second_field = ?, \n" +
                    "discounts = ?, \n" +
                    "price = ?, \n" +
                    "price_tag = ?, \n" +
                    "share_image_title = ?, \n" +
                    "share_title = ?, \n" +
                    "share_description = ?, \n" +
                    "course_description = ?, \n" +
                    "course_img_url = ?,\n" +
                    "update_time = ?\n" +
                    "WHERE id = ?;";

            Object[] params = { course.getCourse_name(), course.getBrief(), course.getTeacher_name(),
                    course.getTeacher_info(), course.getPreview_first_field(), course.getPreview_second_field(),
                    course.getDiscounts(), course.getPrice(), course.getPrice_tag(), course.getShare_image_title(),
                    course.getShare_title(), course.getShare_description(), course.getCourse_description(),
                    course.getCourse_img_url(), course.getUpdate_time(), course.getId() };

            int row = qr.update(sql, params);
            return row;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 根据ID修改指定课程的状态
    @Override
    public int updateCourseStatus(Course course) {

        try {
            QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());

            String sql = "UPDATE course SET STATUS = ?, update_time = ? WHERE id = ?";

            Object[] params = { course.getStatus(), course.getUpdate_time(), course.getId() };

            int row = runner.update(sql, params);
            return row;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
