<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="activeMenu" value="department" scope="request"/>
<jsp:include page="../common/header.jsp"/>

<h3 class="page-header-title">
    <span class="glyphicon glyphicon-folder-open"></span> 部门管理
</h3>

<c:if test="${not empty successMsg}">
    <div class="alert alert-success alert-dismissible" role="alert">
        <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>
        <span class="glyphicon glyphicon-ok-sign"></span> ${successMsg}
    </div>
</c:if>

<c:if test="${not empty errorMsg}">
    <div class="alert alert-danger alert-dismissible" role="alert">
        <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>
        <span class="glyphicon glyphicon-exclamation-sign"></span> ${errorMsg}
    </div>
</c:if>

<div class="row" style="margin-bottom: 15px;">
    <div class="col-md-12">
        <a href="${pageContext.request.contextPath}/department/toAdd" class="btn btn-primary">
            <span class="glyphicon glyphicon-plus"></span> 新增部门
        </a>
        <a href="${pageContext.request.contextPath}/department/tree" class="btn btn-info" style="margin-left: 10px;">
            <span class="glyphicon glyphicon-indent-left"></span> 树形层级展示
        </a>
    </div>
</div>

<table class="table table-bordered table-striped table-hover table-custom">
    <thead>
    <tr>
        <th style="width: 80px; text-align: center;">序号</th>
        <th>部门名称</th>
        <th>上级部门</th>
        <th style="width: 160px; text-align: center;">操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${departmentList}" var="dept" varStatus="vs">
        <tr>
            <td style="text-align: center;">${vs.count}</td>
            <td><strong>${dept.deptName}</strong></td>
            <td>
                <c:choose>
                    <c:when test="${dept.parentId == null || dept.parentId <= 0}">
                        <span class="label label-default">顶级部门</span>
                    </c:when>
                    <c:otherwise>
                        <span class="label label-info">${dept.parentName}</span>
                    </c:otherwise>
                </c:choose>
            </td>
            <td style="text-align: center;">
                <a href="${pageContext.request.contextPath}/department/toEdit?id=${dept.id}" class="btn btn-xs btn-default">
                    <span class="glyphicon glyphicon-edit"></span> 编辑
                </a>
                <button type="button" class="btn btn-xs btn-danger" onclick="deleteDept(${dept.id}, '${dept.deptName}')">
                    <span class="glyphicon glyphicon-trash"></span> 删除
                </button>
            </td>
        </tr>
    </c:forEach>
    <c:if test="${empty departmentList}">
        <tr>
            <td colspan="4" style="text-align: center; color: #999; padding: 30px;">暂无部门数据</td>
        </tr>
    </c:if>
    </tbody>
</table>

<script>
    function deleteDept(id, name) {
        if (!confirm('确定要删除部门 [' + name + '] 吗？若存在子部门或关联在职员工将无法删除。')) {
            return;
        }
        $.ajax({
            url: '${pageContext.request.contextPath}/department/delete',
            type: 'POST',
            data: { id: id },
            dataType: 'json',
            success: function (res) {
                if (res.code === 200) {
                    alert('删除成功');
                    location.reload();
                } else {
                    alert('删除失败: ' + res.msg);
                }
            },
            error: function () {
                alert('网络请求异常，请稍后重试');
            }
        });
    }
</script>

<jsp:include page="../common/footer.jsp"/>
