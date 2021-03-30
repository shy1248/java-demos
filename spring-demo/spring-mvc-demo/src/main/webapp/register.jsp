<%--
  Created by IntelliJ IDEA.
  User: shy
  Date: 2020/3/10
  Time: 11:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>A demo page for spring MVC!</title>
</head>
<body>
<h3>Welcome to regist this page.</h3>
<hr/>
<form action="regist" method="post">
    <div><span>UserName:</span><input type="text" name="username" placeholder="Please type you username"/></div>
    <div><span>Password:</span><input type="password" name="password" placeholder="Please type you password"/></div>
    <div><span>Interest:</span></div>
    <div><input type="checkbox" name="favers" value="moives"/>Moives</div>
    <div><input type="checkbox" name="favers" value="reading"/>Reading</div>
    <div><input type="checkbox" name="favers" value="shopping"/>Shopping</div>
    <div><input type="checkbox" name="favers" value="sports"/>Sports</div>
    <label for="icon">Please choose a icon:</label>
    <div><span>Icon:<input type="file" name="icon" accept="image/png, image/ipg"/></span></div>
    <div><input type="submit" value="Submit"/></div>
</form>

</body>
</html>
