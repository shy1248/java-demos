<%--
  Created by IntelliJ IDEA.
  User: shy
  Date: 2020/3/2
  Time: 18:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--
1.什么是EL表达式？
    EL表达式用来帮助我们简化在JSP中接收pageContext、request、session以及application等四个JSP内置对象传递的数据。
    EL表达式的作用域即为上面4个JSP内置对象。

2.如何使用EL表达式
  EL表达式为：${表达式}
    （1）获取request的参数：${param.XXX}，如请求URL为http://localhost/demo?user=shy&passwd=123456，则可根据
        ${param.user}和${param.passwd}来获取对应的值；当同有多个同名参数时，可以使用${paramValues.key}来获取同名
        参数的数组；
    （2）获取个对象的setAttribute(key, value)方法传递过来的数据，直接使用${key}来获取，支持对象属性和集合操作；
        普通对象属性获取：${key.属性.属性...}
        List与数组中单个元素获取：${key[index]}
        Map键获取：${key.键名}

3.EL表达试的查找顺序为：pageContext -> request -> session -> application，当在一个对象中找到后就不再继续找下去了。
  当然可以指定作用域查找，使用方法为：
  ${pageScope.key}              ====>  在pageContext对象中查找
  ${requestScope.key}           ====>  在request对象中查找
  ${sessionScope.key}           ====>  在session对象中查找
  ${applicationScope.key}       ====>  在application对象中查找

4.EL表达式运算
  算术运算：+, -, *, /, %，需注意+操作不支持非数字类型，会报错
  逻辑运算：&&, ||, !
  关系运算：>, >=, ==, <, <=, !=, 支持三目运算，如：sex == 1? "男":"女"

5.EL的空值判断：${empty key}, ${not empty key}, ${! empty key}，空字符串、集合将为true。

6.EL表达式获取请求头与Cookie数据
  请求头数据：
  ${header}                     ====>  返回所有请求头数据
  ${header[key]}                ====>  返回指定键名的值
  ${headerValues[key]}          ====>  返回指定键名（同键不同值）的值的数组
  Cookie数据：
  ${cookie}                     ====>  返回所有的Cookie数据
  ${cookie.key}                 ====>  返回指定的Cookie对象
  ${cookie.key.name}            ====>  返回指定的Cookie对象的name属性
  ${cookie.key.vlaue}           ====>  返回指定的Cookie对象的value属性

--%>

<%!
    public class User {

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String name;
        private int age;
        private String sex;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }
    }
%>

<%
    pageContext.setAttribute("demoKeyForPage", "Hello, Page!");
    request.setAttribute("demoKeyForRequest", "Hello, Request!");
    session.setAttribute("demoKeyForSession", "Hello, Session!");
    application.setAttribute("demoKeyForApp", "Hello, Application!");
    User user = new User();
    user.setName("Jerry");
    user.setAge(15);
    user.setSex("Female");
    application.setAttribute("userDemo", user);
%>

<html>
<head>
    <title>A demo page for EL!</title>
</head>
<body>
<h1>Demos for Base</h1>
<hr/>
${demoKeyForPage}<br/>
${demoKeyForRequest}<br/>
${empty demoKeyForRequest}<br/>
${demoKeyForSession}<br/>
${demoKeyForApp}<br/>
<h1>Demos for Object</h1>
<hr/>
${userDemo.name}<br/>
${userDemo.age}<br/>
${userDemo.sex}<br/>
<h1>Demos for Header and Cookie</h1>
<hr/>
${header}<br/>
${header["User-Agent"]}<br/>
${cookie}<br/>
${cookie.JSESSIONID.name}<br/>
${cookie.JSESSIONID.value}<br/>
</body>
</html>
