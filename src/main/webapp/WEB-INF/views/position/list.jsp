<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="activeMenu" value="position" scope="request"/>
<jsp:include page="../common/header.jsp"/>

<h3 class="page-header-title">
    <span class="glyphicon glyphicon-tags"></span> 岗位管理
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

<!-- 查询过滤栏 -->
<div class="filter-card">
    <form action="${pageContext.request.contextPath}/position/list" method="get" class="form-inline">
        <div class="form-group">
            <label for="posName">岗位名称：</label>
            <input type="text" class="form-control" id="posName" name="positionName"
                   value="${positionName}" placeholder="输入岗位名称关键字">
        </div>
        <button type="submit" class="btn btn-primary" style="margin-left: 10px;">
            <span class="glyphicon glyphicon-search"></span> 查 询
        </button>
        <a href="${pageContext.request.contextPath}/position/list" class="btn btn-default" style="margin-left: 5px;">
            重 置
        </a>
        <a href="${pageContext.request.contextPath}/position/toAdd" class="btn btn-success pull-right">
            <span class="glyphicon glyphicon-plus"></span> 新增岗位
        </a>
    </form>
</div>

<table class="table table-bordered table-striped table-hover table-custom">
    <thead>
    <tr>
        <th style="width: 80px; text-align: center;">序号</th>
        <th>岗位名称</th>
        <th style="width: 160px; text-align: center;">操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${positionList}" var="pos" varStatus="vs">
        <tr>
            <td style="text-align: center;">${vs.count}</td>
            <td><strong>${pos.positionName}</strong></td>
            <td style="text-align: center;">
                <a href="${pageContext.request.contextPath}/position/toEdit?id=${pos.id}" class="btn btn-xs btn-default">
                    <span class="glyphicon glyphicon-edit"></span> 编辑
                </a>
                <button type="button" class="btn btn-xs btn-danger" onclick="deletePos(${pos.id}, '${pos.positionName}')">
                    <span class="glyphicon glyphicon-trash"></span> 删除
                </button>
            </td>
        </tr>
    </c:forEach>
    <c:if test="${empty positionList}">
        <tr>
            <td colspan="3" style="text-align: center; color: #999; padding: 30px;">暂无匹配的岗位数据</td>
        </tr>
    </c:if>
    </tbody>
</table>

<script>
    function deletePos(id, name) {
        if (!confirm('确定要删除岗位 [' + name + '] 吗？若有在职员工关联此岗位将无法删除。')) {
            return;
        }
        $.ajax({
            url: '${pageContext.request.contextPath}/position/delete',
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
