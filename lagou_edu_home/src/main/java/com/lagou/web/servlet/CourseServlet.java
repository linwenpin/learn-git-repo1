package com.lagou.web.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.lagou.base.BaseServlet;
import com.lagou.pojo.Course;
import com.lagou.service.CourseService;
import com.lagou.service.impl.CourseServiceImpl;
import com.lagou.utils.DateUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 课程管理模块 servlet 课程
 */
@WebServlet("/course")
public class CourseServlet extends BaseServlet {

    // 查询课程列表信息
    public void findCourseList(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 1. 请求参数methodName 已在BaseServlet中处理了
            // 2. 业务处理
            CourseService cs = new CourseServiceImpl();
            List<Course> courseList = cs.findCourseList();

            // 告诉 Fastjson 只需要序列化那几个字段
            SimplePropertyPreFilter sppf = new SimplePropertyPreFilter(Course.class,
                    "id", "course_name", "price", "sort_num", "status");
            String result = JSON.toJSONString(courseList, sppf);
            /*SimpleBeanPropertyFilter sbpf = SimpleBeanPropertyFilter.filterOutAllExcept(
                    "id", "course_name", "price", "sort_num", "status"
            );
            SimpleFilterProvider sfp = new SimpleFilterProvider().addFilter("findCourseList", sbpf);
            ObjectMapper objectMapper = new ObjectMapper();
            String result = objectMapper.writer(sfp).writeValueAsString(courseList);*/

            // 3. 响应结果
            response.getWriter().print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 根据课程名称 和/或 课程状态 查询课程信息
    public void findByCourseNameAndStatus(HttpServletRequest request, HttpServletResponse response) {

        try {
            // 1. 获取请求参数
            String course_name = request.getParameter("course_name");
            String status = request.getParameter("status");
            // 2. 调用Service层进行条件查询
            CourseService cs = new CourseServiceImpl();
            List<Course> courses = cs.findByCourseNameAndStatus(course_name, status);
            // 3. 将查询结果转换为json
            SimplePropertyPreFilter sppf = new SimplePropertyPreFilter(Course.class, "id", "course_name", "price", "sort_num", "status");
            String result = JSON.toJSONString(courses, sppf);
            // 4. 将json响应给客户端
            response.getWriter().print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 根据课程ID查询课程营销信息
    public void findCourseById(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 1. 获取请求参数
            String id = request.getParameter("id");
            // 2. 业务处理
            CourseService cs = new CourseServiceImpl();
            Course course  = cs.findCourseById(Integer.parseInt(id));

            // 3. 响应结果, 响应数据为JSON格式
            SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Course.class);
            List<String> includes = Arrays.asList(
                    "id", "course_name", "brief", "teacher_name", "teacher_info",
                    "preview_first_field", "preview_second_field", "discounts",
                    "price", "price_tag", "course_img_url", "share_title", "share_image_title",
                    "share_description", "course_description", "status"
            );
//            filter.getExcludes().addAll(Arrays.asList("isDel", "sort_num"));
            filter.getIncludes().addAll(includes);

            String result = JSON.toJSONString(course, filter);
            response.getWriter().print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 根据课程ID修改指定课程的状态
    public void updateCourseStatus(HttpServletRequest request, HttpServletResponse response) {

        try {
            // 获取参数
            String id = request.getParameter("id");

            // 根据课程ID查询课程营销信息
            CourseService cs = new CourseServiceImpl();
            Course course = cs.findCourseById( Integer.parseInt(id) );

            // 判断课程状态，如果是0则改为1，如果是1则改为0，也就是执行一个取反操作
            int status = course.getStatus();
            if (0 == status) {
                course.setStatus(1);
            } else {
                course.setStatus(0);
            }
            // 修改课程的更新时间
            course.setUpdate_time(DateUtils.getDateFormart() );

            // 业务处理
            Map<String, Integer> map = cs.updateCourseStatus(course);

            // 响应结果
            String result = JSON.toJSONString(map);
            response.getWriter().print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
