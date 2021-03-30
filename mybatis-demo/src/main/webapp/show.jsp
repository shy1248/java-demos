<%--
  Created by IntelliJ IDEA.
  User: shy
  Date: 2020/3/3
  Time: 23:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<html>
<head>
    <title>A demo application for mybatis!</title>
</head>
<body>
<h3>All Users</h3>
<hr/>

<table border="1">
    <tr>
        <th>ID</th>
        <th>NAME</th>
        <th>AGE</th>
    </tr>
    <c:forEach items="${users}" var="user">
        <tr>
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.age}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
