<%--
  Created by IntelliJ IDEA.
  User: shy
  Date: 2020/3/1
  Time: 0:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
1.Ajax是什么？
Ajax全程为Asynchronous Javascrpit And XML，是一种用来支持异步请求服务端数据，并局部刷新页面的浏览器端技术。

2.Ajax用来解决什么问题？
Ajax主要用来解决当需要在当前页面展示下一次请求的结果。在没有Ajax技术之前，要实现这个需求，只能在后端将两次
请求的结果进行拼接后再一起发送给浏览器。但是这种方式会导致当前页面的内容再发生一次请求。

3.Ajax使用流程

    // 创建Ajax引擎对象
    var ajax
    if (window.XMLHttpRequest) {            // 主流浏览器支持
      ajax = new XMLHttpRequest()
    } else if (window.ActiveXObject) {      // IE6支持
      ajax = new ActiveXObject("Msxml12.XMLHTTP")
    }

    // 获取元素对象
    var textFrame = document.getElementById("textFrame")

    // 复写Ajax对象的onreadystatement函数，注册监听函数
    ajax.onreadystatechange = function () {
      // 判断Ajax状态码，当为4时才表示请求已返回结果
      // 解析:
      //     0 － （未初始化）还没有调用send()方法
      //     1 － （载入）已调用send()方法，正在发送请求
      //     2 － （载入完成）send()方法执行完成，已经接收到全部响应内容
      //     3 － （交互）正在解析响应内容
      //     4 － （完成）响应内容解析完成，可以在客户端调用了
      // 如果不判断，以下的alert函数会执行5次，前4次是还没有响应结果，所以不需要
      // alert(result)

      if (ajax.readyState == 4) {
        // 获取响应状态码
        var ok = ajax.status
        // 获取响应内容
        var result
        if (ok == 200) {
          result = ajax.responseText       // json格式和普通字符串
          // result = ajax.responseXML     // XML格式响应
        } else {
          result = "Error with status: " + ok
        }

        // 设置响应结果为元素内容
        textFrame.innerText = result
      } else {
        textFrame.innerText = "Waitting ..."
      }
    }

    // // 发送请求，使用get方式
    // // 第三个参数指定Ajax是以异步还是同步模式执行，默认为异步，当设置为false时表示为同步执行
    // ajax.open("get", "ajaxServlet?name=demo&password=123456", true)
    // // 由于使用get方式，因此没有请求体
    // ajax.send(null)

    // 发送请求，使用Post方式
    // 第三个参数指定Ajax是以异步还是同步模式执行，默认为异步，当设置为false时表示为同步执行
    ajax.open("post", "ajaxServlet", true)
    // 注意：使用post方式时需要加上这个请求头，否则后端拿不到请求数据
    ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded")
    // 由于使用post方式，单独发送请求体
    ajax.send("name=demo&password=123456")

4.Ajax的响应数据格式
    普通字符串，JSON，XML。其中JSON格式使用较多。其实也是特定格式的字符串。
    将后台需要响应的数据拼接成Json格式，然后在ajax中使用eval("var obj =" + resp )进行动态生成js对象。

--%>

<html>
<head>
    <title>A demo page for Ajax!</title>
    <script type="text/javascript">
      function flushByJS() {
        var textFrame = document.getElementById("textFrame")
        var now = new Date()
        var datetime = now.getFullYear() + "-" + (now.getMonth() + 1)
            + "-" + now.getDate() + " " + now.getHours() + ":" + now.getMinutes() + ":" + now.getSeconds() + "." +
            now.getMilliseconds()
        textFrame.innerText = "This message is set by JS.\nNow: " + datetime
      }

      function flushByAjax() {
        // 创建Ajax引擎对象
        var ajax
        if (window.XMLHttpRequest) {            // 主流浏览器支持
          ajax = new XMLHttpRequest()
        } else if (window.ActiveXObject) {      // IE6支持
          ajax = new ActiveXObject("Msxml12.XMLHTTP")
        }

        // 获取元素对象
        var textFrame = document.getElementById("textFrame")

        // 复写Ajax对象的onreadystatement函数，注册监听函数
        ajax.onreadystatechange = function () {
          // 判断Ajax状态码，当为4时才表示请求已返回结果
          // 解析:
          //     0 － （未初始化）还没有调用send()方法
          //     1 － （载入）已调用send()方法，正在发送请求
          //     2 － （载入完成）send()方法执行完成，已经接收到全部响应内容
          //     3 － （交互）正在解析响应内容
          //     4 － （完成）响应内容解析完成，可以在客户端调用了
          // 如果不判断，以下的alert函数会执行5次，前4次是还没有响应结果，所以不需要
          // alert(result)

          if (ajax.readyState == 4) {
            // 获取响应状态码
            var ok = ajax.status
            // 获取响应内容
            var result
            if (ok == 200) {
              result = ajax.responseText
            } else {
              result = "Error with status: " + ok
            }

            // 设置响应结果为元素内容
            textFrame.innerText = result
          } else {
            textFrame.innerText = "Waitting ..."
          }
        }

        // // 发送请求，使用get方式
        // // 第三个参数指定Ajax是以异步还是同步模式执行，默认为异步，当设置为false时表示为同步执行
        // ajax.open("get", "ajaxServlet?name=demo&password=123456", true)
        // // 由于使用get方式，因此没有请求体
        // ajax.send(null)

        // 发送请求，使用Post方式
        // 第三个参数指定Ajax是以异步还是同步模式执行，默认为异步，当设置为false时表示为同步执行
        ajax.open("post", "ajaxServlet", true)
        // 注意：使用post方式时需要加上这个请求头，否则后端拿不到请求数据
        ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded")
        // 由于使用post方式，单独发送请求体
        ajax.send("name=demo&password=123456")
      }
    </script>
    <style type="text/css">
        #textFrame {
            border: solid;
            width: 600px;
            height: 100px;
        }
    </style>
</head>
<body>
<h1>A Demo Message!</h1>
<hr/>
<br/>
<div id="textFrame"></div>
<br/>
<input type="button" value="Flush by JS" onclick="flushByJS()"/>
<input type="button" value="Flush by Ajax" onclick="flushByAjax()"/>
<br/>
</body>
</html>
