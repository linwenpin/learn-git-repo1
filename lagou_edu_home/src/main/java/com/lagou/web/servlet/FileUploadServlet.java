package com.lagou.web.servlet;

import com.lagou.utils.UUIDUtils;
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
import java.util.List;

@WebServlet("/upload")
public class FileUploadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // 1. 创建 DiskFileItemFactory
            DiskFileItemFactory factory = new DiskFileItemFactory();

            // 2. 创建 ServletFileUpload
            ServletFileUpload upload = new ServletFileUpload(factory);

            // 3. 判断 请求体的内容 是否为 多部件内容
            boolean multipartContent = upload.isMultipartContent(req);
            if (multipartContent) {
                // 4. 设置上传文件名的字符编码
                upload.setHeaderEncoding("UTF-8");
                // 5. 如果是，则解析请求，获取 FileItem列表
                List<FileItem> list = upload.parseRequest(req);

                // 6. 遍历FileItem列表
                for (FileItem item : list) {
                    // 6-1. 判断FileItem是否为普通表单项
                    boolean formField = item.isFormField();
                    if (formField) {
                        // 6-2. 如果是，则打印表单项的name和value
                        String fieldName = item.getFieldName();
                        String fieldValue = item.getString("UTF-8");
                        System.out.println(fieldName + " = " + fieldValue);
                    } else {
                        // 6-3. 如果不是，则为 文件上传项，因此，使用IO完成文件拷贝
                        // 文件名
                        String fileName = item.getName();
                        // 为避免文件重名，使用UUID字符串进行拼接
                        String newFileName = UUIDUtils.getUUID() + "_" + fileName;
                        // 获取输入流
                        InputStream in = item.getInputStream();
                        // 获取输出流
                        // 1. 获取项目在磁盘中的路径
                        String realPath = this.getServletContext().getRealPath("/");
                        String contextPath = req.getContextPath();
                        int p = realPath.indexOf(contextPath.substring(1, contextPath.length()));
                        // 2. 获取 webapps目录
                        String webapps = realPath.substring(0, p-1);

                        FileOutputStream out = new FileOutputStream(webapps + "/upload/" + newFileName);
                        // 完成文件拷贝
                        IOUtils.copy(in, out);
//                        in.transferTo(out);
                        // 关闭流
                        out.close();
                        in.close();
                    }
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
