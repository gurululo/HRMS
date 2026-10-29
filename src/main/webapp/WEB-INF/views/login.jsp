<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>系统登录 - 企业人事管理系统</title>
    <link rel="stylesheet" href="<%=path%>/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=path%>/static/css/style.css">
</head>
<body class="login-body">
<div class="login-card">
    <div class="login-header">
        <h3><span class="glyphicon glyphicon-briefcase"></span> HRMS</h3>
        <p>企业人事管理系统 · 管理员登录</p>
    </div>

    <c:if test="${not empty errorMsg}">
        <div class="alert alert-danger alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <span class="glyphicon glyphicon-exclamation-sign"></span> ${errorMsg}
        </div>
    </c:if>

    <form action="<%=path%>/doLogin" method="post">
        <div class="form-group">
            <label for="username"><span class="glyphicon glyphicon-user"></span> 管理员账号</label>
            <input type="text" class="form-control input-lg" id="username" name="username"
                   placeholder="请输入管理员用户名" required autofocus>
        </div>
        <div class="form-group">
            <label for="password"><span class="glyphicon glyphicon-lock"></span> 登录密码</label>
            <input type="password" class="form-control input-lg" id="password" name="password"
                   placeholder="请输入密码" required>
        </div>
        <div style="margin-top: 25px;">
            <button type="submit" class="btn btn-primary btn-lg btn-block">
                <span class="glyphicon glyphicon-log-in"></span> 登 录
            </button>
        </div>
    </form>
</div>

<script src="<%=path%>/static/js/jquery-1.12.4.min.js"></script>
<script src="<%=path%>/static/js/bootstrap.min.js"></script>
</body>
</html>
