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
    <c:forEach items="${pageInfo.currentUsers}" var="user">
        <tr>
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.age}</td>
        </tr>
    </c:forEach>
</table>
Current: <c:out value="${pageInfo.pageNumber}"></c:out>
<%-- 此处使用javascript行内式来禁止标签的原生功能，使用c:if标签来进行条件限制 --%>
<a href="page?pageNumber=${pageInfo.pageNumber-1}&pageSize=${pageInfo.pageSize}" <c:if test="${pageInfo.pageNumber
<= 1}">onclick="javascript: return false;"</c:if>> Next</a>
<a href="page?pageNumber=${pageInfo.pageNumber+1}&pageSize=${pageInfo.pageSize}" <c:if test="${pageInfo.pageNumber
>= pageInfo.totalPage}">onclick="javascript: return false;"</c:if>>Back</a>
Total: <c:out value="${pageInfo.totalPage}"></c:out>
</body>
</html>
