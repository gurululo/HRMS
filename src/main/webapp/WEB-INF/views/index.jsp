<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="activeMenu" value="index" scope="request"/>
<jsp:include page="common/header.jsp"/>

<h3 class="page-header-title">
    <span class="glyphicon glyphicon-dashboard"></span> 系统首页概览
</h3>

<div class="row" style="margin-top: 25px;">
    <div class="col-md-3">
        <div class="stat-box blue">
            <span class="glyphicon glyphicon-user icon"></span>
            <div class="label-text">员工总数</div>
            <div class="number">${employeeCount}</div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="stat-box green">
            <span class="glyphicon glyphicon-folder-open icon"></span>
            <div class="label-text">部门数量</div>
            <div class="number">${departmentCount}</div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="stat-box orange">
            <span class="glyphicon glyphicon-tags icon"></span>
            <div class="label-text">岗位设置</div>
            <div class="number">${positionCount}</div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="stat-box purple">
            <span class="glyphicon glyphicon-file icon"></span>
            <div class="label-text">合同档案</div>
            <div class="number">${contractCount}</div>
        </div>
    </div>
</div>

<div class="panel panel-default" style="margin-top: 20px;">
    <div class="panel-heading">
        <strong><span class="glyphicon glyphicon-send"></span> 快捷业务操作</strong>
    </div>
    <div class="panel-body">
        <a href="${pageContext.request.contextPath}/employee/toAdd" class="btn btn-default" style="margin-right: 15px;">
            <span class="glyphicon glyphicon-plus text-primary"></span> 录入新员工
        </a>
        <a href="${pageContext.request.contextPath}/department/toAdd" class="btn btn-default" style="margin-right: 15px;">
            <span class="glyphicon glyphicon-plus text-success"></span> 添加新部门
        </a>
        <a href="${pageContext.request.contextPath}/position/toAdd" class="btn btn-default" style="margin-right: 15px;">
            <span class="glyphicon glyphicon-plus text-warning"></span> 添加新岗位
        </a>
        <a href="${pageContext.request.contextPath}/contract/toAdd" class="btn btn-default" style="margin-right: 15px;">
            <span class="glyphicon glyphicon-plus text-info"></span> 登记新合同
        </a>
        <a href="${pageContext.request.contextPath}/department/tree" class="btn btn-default">
            <span class="glyphicon glyphicon-indent-left text-danger"></span> 查看组织架构树
        </a>
    </div>
</div>

<div class="panel panel-default">
    <div class="panel-heading">
        <strong><span class="glyphicon glyphicon-info-sign"></span> 系统运行信息</strong>
    </div>
    <div class="panel-body">
        <table class="table table-bordered">
            <tbody>
            <tr>
                <th style="width: 20%; background-color: #f9f9f9;">系统架构</th>
                <td>Java Web 单体架构 (Spring MVC + JSP + Bootstrap 3 + jQuery)</td>
            </tr>
            <tr>
                <th style="background-color: #f9f9f9;">持久化存储</th>
                <td>本地数据文件持久化存储 (JSON File Storage)</td>
            </tr>
            <tr>
                <th style="background-color: #f9f9f9;">当前登录账号</th>
                <td>${sessionScope.LOGIN_USER} (单系统管理员)</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="common/footer.jsp"/>
