<%--
  Created by IntelliJ IDEA.
  User: aoLemon
  Date: 2023/4/8
  Time: 10:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <a href="${pageContext.request.contextPath}/test?methodName=addCourse">添加课程</a>
    <a href="${pageContext.request.contextPath}/test?methodName=findByStatus">按课程状态查询</a>
    <a href="${pageContext.request.contextPath}/test?methodName=findByName">按课程名称查询</a>
    <a href="${pageContext.request.contextPath}/test3?methodName=show">show</a>
</body>
</html>
