<%--
  Created by IntelliJ IDEA.
  User: aoLemon
  Date: 2023/4/11
  Time: 17:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文件上传测试</title>
</head>
<body>

    <form method="POST" enctype="multipart/form-data" action="${pageContext.request.contextPath}/upload">
        <input type="file" name="fileUpload">
        <br>
        <input type="text" name="name">
        <input type="text" name="password">
        <input type="submit" value="上传文件">
    </form>

    <img src="/upload/762ecfe0954b4a6082f6429aa9c601c8_猫.jpg" width="300px">
</body>
</html>
