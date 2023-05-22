package com.lagou.web.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.lagou.base.BaseServlet;
import com.lagou.pojo.Course;
import com.lagou.pojo.Course_Section;
import com.lagou.service.CourseContentService;
import com.lagou.service.impl.CourseContentServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * 课程内容管理 Servlet
 */
@WebServlet("/courseContent")
public class CourseContentServlet extends BaseServlet {

    // 根据 课程ID 展示相应课程的章节和课时信息
    public void findSectionAndLessonByCourseId(HttpServletRequest request, HttpServletResponse response) {

        try {
            // 获取请求参数
            String courseId = request.getParameter("course_id");

            // 业务处理
            CourseContentService ccs = new CourseContentServiceImpl();
            List<Course_Section> sections =  ccs.findSectionAndLessonByCourseId(Integer.parseInt(courseId));

            // 响应结果
            String result = JSON.toJSONString(sections);
            response.getWriter().print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 根据课程ID回显课程信息
    public void findCourseById(HttpServletRequest request, HttpServletResponse response) {

        try {
            // 接收请求参数
            String courseId = request.getParameter("course_id");

            // 业务处理
            CourseContentService ccs = new CourseContentServiceImpl();
            Course course = ccs.findCourseByCourseId(Integer.parseInt(courseId));

            // 把JSON响应给前端
            SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Course.class, "id", "course_name");

            String result = JSON.toJSONString(course, filter);
            response.getWriter().print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 保存&修改 章节信息
    public void saveOrUpdateSection(HttpServletRequest request, HttpServletResponse response) {

        try {
            // 获取请求参数 从request域中获取
            Map<String, Object> map = (Map) request.getAttribute("map");

//            System.out.println(map.get("section").getClass());

            Course_Section section = new Course_Section();
            // 把map中的数据封装为一个Course_Section对象
            BeanUtils.copyProperties(section, map.get("section"));

            // 业务处理
            CourseContentService contentService = new CourseContentServiceImpl();

            // 判断请求所携带的数据是否包含id（章节ID）
            if (section.getId() == 0) {
                // 如果没有，则表示请求的是 添加章节
                String result = contentService.saveSection(section);
                // 返回JSON字符串 响应给前端
                response.getWriter().print(result);
            } else {
                // 如果有，则表示请求的是 修改章节
                String result = contentService.updateSection(section);
                response.getWriter().write(result);
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 修改章节状态
    public void updateSectionStatus(HttpServletRequest request, HttpServletResponse response) {

        try {
            // 获取请求参数
            String id = request.getParameter("id"); // 章节ID
            String status = request.getParameter("status");

            // 业务处理
            CourseContentService contentService = new CourseContentServiceImpl();
            String result = contentService.updateSectionStatus(Integer.parseInt(id), Integer.parseInt(status));

            // 响应结果
            response.getWriter().print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
