<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="activeMenu" value="employee" scope="request"/>
<jsp:include page="../common/header.jsp"/>

<h3 class="page-header-title">
    <span class="glyphicon glyphicon-user"></span> 员工管理
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

<!-- 员工查询过滤栏 -->
<div class="filter-card">
    <form action="${pageContext.request.contextPath}/employee/list" method="get" class="form-inline">
        <div class="form-group" style="margin-right: 15px;">
            <label for="empNo">员工编号：</label>
            <input type="text" class="form-control" id="empNo" name="empNo" value="${empNo}" placeholder="输入工号">
        </div>
        <div class="form-group" style="margin-right: 15px;">
            <label for="name">姓名：</label>
            <input type="text" class="form-control" id="name" name="name" value="${name}" placeholder="输入姓名">
        </div>
        <div class="form-group" style="margin-right: 15px;">
            <label for="deptId">所属部门：</label>
            <select class="form-control" id="deptId" name="deptId">
                <option value="">-- 全部部门 --</option>
                <c:forEach items="${departmentList}" var="d">
                    <option value="${d.id}" ${deptId == d.id ? 'selected' : ''}>${d.deptName}</option>
                </c:forEach>
            </select>
        </div>
        <button type="submit" class="btn btn-primary">
            <span class="glyphicon glyphicon-search"></span> 查 询
        </button>
        <a href="${pageContext.request.contextPath}/employee/list" class="btn btn-default" style="margin-left: 5px;">
            重 置
        </a>
        <a href="${pageContext.request.contextPath}/employee/toAdd" class="btn btn-success pull-right">
            <span class="glyphicon glyphicon-plus"></span> 新增员工
        </a>
    </form>
</div>

<table class="table table-bordered table-striped table-hover table-custom">
    <thead>
    <tr>
        <th style="width: 70px; text-align: center;">序号</th>
        <th>员工编号</th>
        <th>姓名</th>
        <th>所属部门</th>
        <th>担任岗位</th>
        <th style="width: 160px; text-align: center;">操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${employeeList}" var="emp" varStatus="vs">
        <tr>
            <td style="text-align: center;">${vs.count}</td>
            <td><code>${emp.empNo}</code></td>
            <td><strong>${emp.name}</strong></td>
            <td>
                <span class="label label-info">${emp.deptName}</span>
            </td>
            <td>${emp.positionName}</td>
            <td style="text-align: center;">
                <a href="${pageContext.request.contextPath}/employee/toEdit?id=${emp.id}" class="btn btn-xs btn-default">
                    <span class="glyphicon glyphicon-edit"></span> 编辑
                </a>
                <button type="button" class="btn btn-xs btn-danger" onclick="deleteEmp(${emp.id}, '${emp.name}')">
                    <span class="glyphicon glyphicon-trash"></span> 删除
                </button>
            </td>
        </tr>
    </c:forEach>
    <c:if test="${empty employeeList}">
        <tr>
            <td colspan="6" style="text-align: center; color: #999; padding: 30px;">未查询到匹配的员工数据</td>
        </tr>
    </c:if>
    </tbody>
</table>

<script>
    function deleteEmp(id, name) {
        if (!confirm('确定要删除员工 [' + name + '] 吗？若名下存在有效劳动合同将无法删除。')) {
            return;
        }
        $.ajax({
            url: '${pageContext.request.contextPath}/employee/delete',
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
