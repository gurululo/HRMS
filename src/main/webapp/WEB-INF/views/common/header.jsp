<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>企业人事管理系统 - HRMS</title>
    <link rel="stylesheet" href="<%=path%>/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=path%>/static/css/style.css">
    <script src="<%=path%>/static/js/jquery-1.12.4.min.js"></script>
    <script src="<%=path%>/static/js/bootstrap.min.js"></script>
</head>
<body>
<!-- 顶部导航 -->
<nav class="navbar navbar-custom">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#top-navbar">
                <span class="sr-only">切换导航</span>
                <span class="icon-bar" style="background:#fff;"></span>
                <span class="icon-bar" style="background:#fff;"></span>
                <span class="icon-bar" style="background:#fff;"></span>
            </button>
            <a class="navbar-brand" href="<%=path%>/index">
                <span class="glyphicon glyphicon-briefcase"></span> 企业人事管理系统 (HRMS)
            </a>
        </div>
        <div class="collapse navbar-collapse" id="top-navbar">
            <ul class="nav navbar-nav navbar-right">
                <li class="navbar-text">
                    <span class="glyphicon glyphicon-user"></span> 管理员: <strong>${sessionScope.LOGIN_USER}</strong>
                </li>
                <li>
                    <a href="<%=path%>/logout" onclick="return confirm('确定要安全退出系统吗？');">
                        <span class="glyphicon glyphicon-log-out"></span> 退出登录
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="container-fluid main-wrapper">
    <div class="row">
        <!-- 左侧菜单栏 -->
        <div class="col-md-2">
            <div class="sidebar-panel">
                <div class="panel-heading">
                    <span class="glyphicon glyphicon-th-list"></span> 功能导航
                </div>
                <ul class="sidebar-menu">
                    <li class="${activeMenu == 'index' ? 'active' : ''}">
                        <a href="<%=path%>/index"><span class="glyphicon glyphicon-home"></span> 首页概览</a>
                    </li>
                    <li class="${activeMenu == 'employee' ? 'active' : ''}">
                        <a href="<%=path%>/employee/list"><span class="glyphicon glyphicon-user"></span> 员工管理</a>
                    </li>
                    <li class="${activeMenu == 'department' ? 'active' : ''}">
                        <a href="<%=path%>/department/list"><span class="glyphicon glyphicon-folder-open"></span> 部门管理</a>
                    </li>
                    <li class="${activeMenu == 'department_tree' ? 'active' : ''}">
                        <a href="<%=path%>/department/tree"><span class="glyphicon glyphicon-indent-left"></span> 部门架构树</a>
                    </li>
                    <li class="${activeMenu == 'position' ? 'active' : ''}">
                        <a href="<%=path%>/position/list"><span class="glyphicon glyphicon-tags"></span> 岗位管理</a>
                    </li>
                    <li class="${activeMenu == 'contract' ? 'active' : ''}">
                        <a href="<%=path%>/contract/list"><span class="glyphicon glyphicon-file"></span> 合同管理</a>
                    </li>
                </ul>
            </div>
        </div>
        <!-- 右侧主体内容 -->
        <div class="col-md-10">
            <div class="content-panel">
