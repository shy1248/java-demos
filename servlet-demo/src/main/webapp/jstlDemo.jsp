<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: shy
  Date: 2020/3/2
  Time: 20:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--
1.JSTL标签库
  JSTL是apache对EL表达式的扩展，依赖于EL，是一种标签语言，与JSP的动作标签一样，不是内置的，需要导入依赖包。jakarta-taglibs-standard-1.1.2.zip。
  作用是使用标签的方式来进行逻辑代码的编写，以提高效率。

  Idea依赖包安装（MyEclipse自带）：
  下载依赖包：http://archive.apache.org/dist/jakarta/taglibs/standard/binaries/jakarta-taglibs-standard-1.1.2.zip
  解压
  将解压后lib目录下的jstl.jar与standard.jar包拷贝到项目webroot的lib目录下。
  将解压后tld目录下的c.tld文件拷贝到webroot的WEB-INF目录下。否则会报如下错误信息：
    无法在web.xml或使用此应用程序部署的jar文件中解析绝对uri：[http://java.sun.com/jsp/jstl/core]

2.JSTL核心标签库
  声明jstl标签库的引入：
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

  <c:out value="Demo String" default="Default!"></c:out>
  value可以是常量值，也可以是EL表达式，作用是将数据直接输出。

  <c:set value="hello, cset tag for jstl!" var="hello" scope="application"></c:set>
  设置属性和其值，以方便数据流转，scope默认不写则为page，即本jsp有效。

  <c:remove var="hello" scope="page"></c:remove>
  删除c:set标签中设置的变量，如不指定scope，则四个作用域全删除。

  <c:if test="${not empty hello}">
    <b>Found value is ${hello} for keyed hello!</b>
  </c:if>
  单分支条件判断，test指定条件，且必须为EL表达式。

  <c:choose>
    <c:when test="${score >= 95}">Very Good!</c:when>
    <c:when test="${score >= 85 && score < 95}">Is OK!</c:when>
    <c:otherwise>Just so so!</c:otherwise>
  </c:choose>
  多分支条件判断，test指定条件，且必须为EL表达式。都不成立则执行c:otherwise中的语句。

  <c:forEach begin="0" end="10" step="2" varStstus="i"></c:forEach>
  <c:forEach items="${list}" var="item"></c:forEach>
  循环标签：
    第一种为条件循环，begin指定循环开始，end指定循环结束条件，step指定步长，varStatus保存了循环变量信息。
    第一种为集合迭代，var参数保存了集合中的每个元素。

  还有很多其它标签，参加菜鸟教程。

3.JSTL格式化标签库
  声明jstl标签库的引入：
  <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

  格式化标签库只要用来进行格式输出的。

  <fmt:formatDate value="${now}" type="both" dateStyle="short" timeStyle="short"></fmt:formatDate>
  <fmt:parseDate var="pasredDay" value="${aDay}" pattern="mm-dd-yyyy"></fmt:parseDate>
  <fmt:formatNumber type="CURRENCY" value="${aNum}"></fmt:formatNumber>

4.JSTL的SQL标签库
5.JSTL函数标签库
6.JSTLXML标签库

--%>

<%
    pageContext.setAttribute("demo", "Hello, JSTL!");
%>

<html>
<head>
    <title>A demo page for JSTL!</title>
</head>
<>
<h3>Base Tag</h3>
<hr/>
<c:out value="${demo}" default="" escapeXml=""></c:out><br/>
<c:set value="hello, cset tag for jstl with PageScope!" var="hello" scope="page"></c:set>
<c:set value="hello, cset tag for jstl with RequestScope!" var="hello" scope="request"></c:set>
<c:set value="hello, cset tag for jstl with SessionScope!" var="hello" scope="session"></c:set>
<c:set value="hello, cset tag for jstl with ApplicationScope!" var="hello" scope="application"></c:set>
<c:out value="${hello}"></c:out><br/>
<c:out value="${sessionScope.hello}"></c:out><br/>
<c:remove var="hello" scope="page"></c:remove>
<c:out value="${hello}"></c:out><br/>
<h3>Logic Tag</h3>
<hr/>
<c:if test="${not empty hello}">
    Found value "${hello}" for keyed hello!<br/>
</c:if>

<c:set var="score" value="90"></c:set>
<c:choose>
    <c:when test="${score >= 95}">Very Good!</c:when>
    <c:when test="${score >= 85 && score < 95}">Is OK!</c:when>
    <c:otherwise>Just so so!</c:otherwise>
</c:choose>

<h3>Loop Tag</h3>
<hr/>
<c:out value="Constant itreate"></c:out><br/>
<c:forEach begin="0" end="10" step="2" varStatus="i">
    Index=${i.index}, Count=${i.count}, Begin=${i.begin}, End=${i.end}, Step=${i.step}, Current=${i.current},
    IsFirst=${i.first}, IsLast=${i.last}<br/>
</c:forEach>
<br/>
<c:out value="Collection itreate, Print big letters!"></c:out><br/>

<%!
    public class Letter {

        private int code;
        private String display;

        public Letter(int code, String display) {
            this.code = code;
            this.display = display;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }
    }
%>

<%
    List<Letter> letters = new ArrayList<Letter>();
    for (int i = 65; i <= 90; i++) {
        Letter letter = new Letter(i, (char) i + "");
        letters.add(letter);
    }
    pageContext.setAttribute("letters", letters);
%>

<table border="1px">
    <tr>
        <td>Code</td>
        <td>Letter</td>
    </tr>
    <c:forEach items="${letters}" var="letter">
        <c:if test="${letter.code<=70}">
            <tr>
                <td>${letter.code}</td>
                <td>${letter.display}</td>
            </tr>
        </c:if>
    </c:forEach>
</table>

<h3>Format Tag</h3>
<hr/>
DateTime format demos:<br/>
<c:set var="now" value="<%=new java.util.Date()%>"></c:set>
<fmt:formatDate value="${now}" type="both" dateStyle="short" timeStyle="short"></fmt:formatDate>
<br/>
DateTime parse demos:<br/>
<c:set var="aDay" value="03-24-2019"></c:set>
<fmt:parseDate var="pasredDay" value="${aDay}" pattern="mm-dd-yyyy"></fmt:parseDate>
<c:out value="${pasredDay}"></c:out><br/>
<c:set var="aNum" value="123456.0789"></c:set>
Number format demos:<br/>
<fmt:formatNumber type="CURRENCY" value="${aNum}"></fmt:formatNumber>
</body>
</html>
