package com.lagou.base;

import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class BaseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }


    /**
     * 把 doGet方法 作为一个调度器/控制器，根据请求功能的不同，调用相应的方法。
     * 约定：
     *  1）请求必须带有参数 methodName
     *  2）参数值 addCourse 表示添加课程
     *  3）参数值 findByStatus 表示按课程状态查询
     *  4）参数值 findByName 表示按课程名称查询
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // 1. 获取请求参数: 获取要调用的方法名
//	    String methodName = request.getParameter("methodName");
        String methodName = null;

        // 获取POST请求的 Content-Type 请求头的值
        String contentType = request.getHeader("Content-Type");
//        System.out.println(contentType);
        //  判断传递的数据是否是JSON格式
        if ("application/json;charset=utf-8".equalsIgnoreCase(contentType)) {
            // 如果是，则调用 getPostJSON() 进行处理
            String postJSON = getPostJSON(request);

            // 将JSON字符串转换为map
            Map<String, Object> map = JSON.parseObject(postJSON, Map.class);

            // 从map中获取 methodName
            methodName = (String) map.get("methodName");

            // 将获取到的数据，保存到request域中
            request.setAttribute("map", map);

        } else {
            // 如果不是，则是 普通表单形式
            methodName = request.getParameter("methodName");
        }

	    // 2. 业务处理: 根据参数选择要调用的方法
	    /*
	    if ("addCourse".equals(methodName)){
		    addCourse(request, response);
	    } else if ("findByStatus".equals(methodName)){
		    findByStatus(request, response);
	    } else if ("findByName".equals(methodName)){
		    findByName(request, response);
	    } else {
		    System.out.println("当前模块中不存在该功能！");
	    }
	    */
	    // 使用反射替代嵌套if语句，提供代码的可维护性
        try {
            // 1. 获取字节码文件对象
            Class c = this.getClass();
            // 2. 根据传入的方法名，获取相应方法的Method对象
            Method method = c.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            // 3. 调用Method对象的invoke()方法，执行相应的功能
            method.invoke(this, request, response);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            System.out.println("请求的功能不存在！");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.out.println("无法访问该方法的Method对象");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            System.out.println("调用Method对象的invoke()方法时发生了异常");
        }
    }

    /**
     * 如果 POST请求的数据提交格式为 Content-Type:application/json;charset=utf-8
     * 那么 调用这个方法获取POST请求中的数据。
     * 该方法会使用IO流的方式获取POST请求中的JSON字符串。
     * @param request
     * @return
     */
    public String getPostJSON(HttpServletRequest request) {

        try {
            // 1. 从 request 中，获取字符缓冲输入流对象
            BufferedReader reader = request.getReader();

            // 2. 创建 StringBuilder类型的对象，用于保存读取到的数据
            StringBuilder sb = new StringBuilder();

            // 3. 循环读取
            String line = null;
            while ((line = reader.readLine()) != null) {
                // 追加到 StringBuffer 中
                sb.append(line);
            }

            // 4. 将读取到的内容转换为字符串，并返回
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
