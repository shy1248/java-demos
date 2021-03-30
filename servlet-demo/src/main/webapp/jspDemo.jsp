<%--
  Created by IntelliJ IDEA.
  User: shy
  Date: 2020/2/29
  Time: 17:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>

<%--
1.JSP其实是一种Serlvet，由Tomcat服务器将其转译成Servlet的Java代码。前端访问一个JSP，其实是访问该JSP转译后的Servlet。

2.JSP的三种注释：
    前端语言注释：会被转译，也会被发送，但是不会被浏览器执行；
    Java语言注释：会被转译，但是不会被Servlet执行；
    JSP的注释：不会被转移。

3.JSP的page指令
    配置JSP转译相关的参数。语法如下（可以有多个）：

    <%@ 属性名="属性值" 属性名="属性值" 属性名="属性值" ... %>

    属性：
        language="java"：指定JSP被转译的语言；
        import="java.util.*,java.math.MathContext"：声明b被转译的java文件要导入的包，不同的包使用逗号隔开；
        pageEncoding="utf-8"：设置JSP文件的编码以及设置；
        contentType="text/html;charset=UTF-8"：设置Serlet的请求和响应编码；
        extends：设置被转译的Servlet需要继承的父类，需要指定全限定路径（包名.类名）；
        session：设置转译后的servlet中是否开启session支持，默认为true，表示开启，false表示关闭；
        errorPage：指定JSP被转译后的Servlet出现错误时要跳转（forward）的页面；

4.Java局部代码块
    JSP文件中Java局部代码块必须包含在"<% ... %>"中，可以跨行以及Java的代码注释。这些代码原样转译到Jsp对应的Servlet文件的_jspService方法中。
    代码块中声明的变量都是局部变量。缺点是在Jsp中进行逻辑判断书写麻烦，代码难以阅读。

5.Java全局代码块
    由于局部代码块不能声明方法和成员变量，因此还有全局代码块，全局代码块声明的内容会被转译到对应Servlet中的方法和成员变量。全局代码块必须包含在
    "<%! ... %>"中，可以跨行和代码注释。局部代码块中可以调用全局代码块中的声明和定义。

6.脚本段
    脚本段是为了帮助我们快速的获取变量或方法返回值作为数据响应给浏览器。脚本段语法格式为：<%=变量或者方法调用%>，注意关闭符前边不能有分号。如：
    <%=str%>
    <%=test()%>
    等价于在局部代码块中写：
    <% out.println(str); %>
    <% out.println(test()); %>

7.JSP的静态引入和动态引入
    很多公共的网页部分（如页头和页脚以及网站声明等）可以提取为单独的JSP文件，然后通过引入的方式嵌入到其它JSP文件中以方便维护。
    静态引入是指把当前的JSP和其引入的JSP文件都转译到一个Servlet文件中，除非直接访问该引入的JSP。需要注意的是：由于生成的是一个Java文件，因此
    要注意相同变量重复定义的问题。使用：
    <%@include file="static.jsp" %>
    file指定要引入的jsp文件的相对路径。
    动态引入是分别对JSP进行转译，当前JSP转译的Servlet文件去调用引入的JSP转译的Servlet文件。在网页中显示合并的效果。由于是分开转译为不同的Servlet，
    因此没有重复定义的问题。使用方式如下：
    <jsp:include page="dynamic.jsp"></jsp:include>
    page指定要引入的jsp文件的相对路径。

8.JSP的转化（forward）
    JSP的转化本质上就是Servlet的转发，因此具有和Servlet转发相同的特点。使用方式如下：
    <jsp:forward page="to.jsp">
        <jsp:param name="k" value="v" />
    </jsp:forward>
    上边实质上就是：to.jsp?k=v
    page指定要转发的jsp文件的相对路径。注意：如果没有携带数据传给下一页面，forword标签之间不能有空格以及其它非法字符。
    由于JSP代码其实是在写Servlet的service方法体，因此可以使用request.getParameter(key)方式取出上一个JSP页面传过来的数据。

9.JSP的九大内置对象
    JSP页面实质上是在写JSP转译后的Servlet的java文件的_jspService方法体中try - catch中的部分，_jspService在try之前还定义了
    一些变量对象，这些对象被称为JSP的内置对象，在JSP页面中可以直接使用。
        pageContext：Jsp文件的页面上下文对象，封存了其它的内置对象，因此Jsp的运行信息都保存这个对象中。作用域为当前jsp。除非往下进行流转。
        request：封存了当前的请求信息，由tomcat创建。
        session：封存了用户不同请求的共享数据，作用域为一次会话。
        application：也就是servletConext对象，项目共享数据。
        response：封装了响应信息，用来响应请求处理结果给浏览器。设置响应头、重定向等。
        out：响应对象，JSP内部使用。带有缓冲区的响应对象，效率高于response对象，但是不能设置响应头信息。
        page：代表当前JSP对象，即java中的this。
        exception：异常对象，存储了当前运行的异常信息。注意，使用此对象需要在page标签中使用属性isError="true"来开启。
        config：也就是ServletConfig对象，主要用来获取web.xml中Servlet标签中的配置项，完成一些初始化数据的读取。

10.JSP的路径问题
    1.JSP中可以使用相对路径进行跳转，但是资源文件位置不可随意更改，使用../跳出目录，使用起来比较麻烦。
    2.也可使用绝对路径：/项目名/资源路径，开发比较常用。
    3.使用JSP的全局路径声明。在JSP页面声明：
        <%
            String path = request.getContextPath();
            String basePath = request.getSchema() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        %>
    然后在html的head标签中添加base标签：<base href="<%=basePath%>" />，给资源自动添加全局路径。
--%>

<html>
<head>
    <title>A demo page for JSP!</title>
</head>
<body>

</body>
</html>
