package com.lagou.web.servlet;

import com.lagou.base.Constants;
import com.lagou.pojo.Course;
import com.lagou.service.CourseService;
import com.lagou.service.impl.CourseServiceImpl;
import com.lagou.utils.DateUtils;
import com.lagou.utils.UUIDUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 保存课程营销信息
 *      收集表单信息，封装到course对象中，并将图片上传到Tomcat服务器
 * 该Servlet不继承BaseServlet的原因：
 *      提交的表单是文件上传表单，而不是普通表单，BaseServlet无法处理
 */
@WebServlet("/courseSalesInfo")
public class CourseSalesInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // 1. 创建Course对象，用于封装数据
            Course course = new Course();
            // 2. 创建Map对象，用于收集数据
            Map<String, Object> map = new HashMap<>();
            // 3. 使用 FileUpload 处理文件上传表单
            // 3-1. 创建 磁盘文件项工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // 3-2. 创建 FileUpload核心类 ServletFileUpload
            ServletFileUpload upload = new ServletFileUpload(factory);
            // 3-3. 解析request对象，获取FileItem集合
            List<FileItem> list = upload.parseRequest(req);
            // 3-4. 遍历集合，判断那些是普通表单项，那些是文件上传项
            for (FileItem item : list) {
                boolean formField = item.isFormField();
                if (formField) {
                    // 是普通表单项，获取其中的数据，并保存到map中
                    String fieldName = item.getFieldName();
                    String fieldValue = item.getString("UTF-8"); // 防止中文乱码
                    System.out.println(fieldName + " " + fieldValue);
                    map.put(fieldName, fieldValue);
                } else {
                    // 是文件上传项
                    // 获取文件名
                    String fileName = item.getName();
                    // 为避免文件名冲突，拼接UUID
                    String newFileName = UUIDUtils.getUUID() + "_" + fileName;
                    // 获取输入流
                    InputStream in = item.getInputStream();
                    // 获取webapps目录的路径
                    String realPath = this.getServletContext().getRealPath("/");
                    String appName = this.getServletContext().getContextPath().substring(1);
                    String webappsPath = realPath.substring(0, realPath.indexOf(appName));
                    // 创建输出流
                    OutputStream out = new FileOutputStream(webappsPath + "/upload/" + newFileName);
                    // 拷贝文件
                    IOUtils.copy(in, out);
                    out.close();
                    in.close();

                    // 将图片路径保存到 map中
                    map.put("course_img_url", Constants.LOCAL_URL + "/upload/" + newFileName);
                }
            }

            System.out.println(map);
            // 4. 使用 BeanUtils 将Map中的数据封装到course对象中
            BeanUtils.populate(course, map);

            String date = DateUtils.getDateFormart();
            CourseService cs = new CourseServiceImpl();

            // 判断文件上传表单中是否有id字段
            if (null != map.get("id")) {
                // 有，则是修改功能
                course.setUpdate_time(date);
                String result = cs.updateCourseSalesInfo(course);
                resp.getWriter().print(result);
            } else {
                // 没有，则是添加功能
                // 5. 补全信息
                course.setCreate_time(date); // 创建时间
                course.setUpdate_time(date); // 修改时间
                course.setStatus(1); // 上架
                // 6. 业务处理
                String result = cs.saveCourseSalesInfo(course);
                // 7. 响应结果
                resp.getWriter().print(result);
            }

        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
