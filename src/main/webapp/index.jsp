<%--
  Created by IntelliJ IDEA.
  User: dingpengwei
  Date: 7/27/16
  Time: 18:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<form action="${basePath}/sayHello" method="post">

  请输入您的名字：

  <input type="text" name="userName" id="userName"/>

  <input type="submit" value="确定"/>

</form>


</body>
</html>
