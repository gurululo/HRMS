<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="activeMenu" value="contract" scope="request"/>
<jsp:include page="../common/header.jsp"/>

<h3 class="page-header-title">
    <span class="glyphicon glyphicon-file"></span> 合同管理
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

<!-- 合同查询过滤栏 -->
<div class="filter-card">
    <form action="${pageContext.request.contextPath}/contract/list" method="get" class="form-inline">
        <div class="form-group" style="margin-right: 15px;">
            <label for="kw">员工编号/姓名：</label>
            <input type="text" class="form-control" id="kw" name="keyword" value="${keyword}" placeholder="工号或员工姓名">
        </div>
        <button type="submit" class="btn btn-primary">
            <span class="glyphicon glyphicon-search"></span> 查 询
        </button>
        <a href="${pageContext.request.contextPath}/contract/list" class="btn btn-default" style="margin-left: 5px;">
            重 置
        </a>
        <a href="${pageContext.request.contextPath}/contract/toAdd" class="btn btn-success pull-right">
            <span class="glyphicon glyphicon-plus"></span> 登记新合同
        </a>
    </form>
</div>

<table class="table table-bordered table-striped table-hover table-custom">
    <thead>
    <tr>
        <th style="width: 70px; text-align: center;">序号</th>
        <th>关联员工工号</th>
        <th>员工姓名</th>
        <th>合同开始日期</th>
        <th>合同结束日期</th>
        <th style="width: 160px; text-align: center;">操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${contractList}" var="c" varStatus="vs">
        <tr>
            <td style="text-align: center;">${vs.count}</td>
            <td><code>${c.empNo}</code></td>
            <td><strong>${c.employeeName}</strong></td>
            <td><span class="glyphicon glyphicon-calendar text-muted"></span> ${c.startDate}</td>
            <td><span class="glyphicon glyphicon-calendar text-muted"></span> ${c.endDate}</td>
            <td style="text-align: center;">
                <a href="${pageContext.request.contextPath}/contract/toEdit?id=${c.id}" class="btn btn-xs btn-default">
                    <span class="glyphicon glyphicon-edit"></span> 编辑
                </a>
                <button type="button" class="btn btn-xs btn-danger" onclick="deleteContract(${c.id})">
                    <span class="glyphicon glyphicon-trash"></span> 删除
                </button>
            </td>
        </tr>
    </c:forEach>
    <c:if test="${empty contractList}">
        <tr>
            <td colspan="6" style="text-align: center; color: #999; padding: 30px;">未查询到匹配的合同记录</td>
        </tr>
    </c:if>
    </tbody>
</table>

<script>
    function deleteContract(id) {
        if (!confirm('确定要删除该劳动合同记录吗？删除后不可撤回。')) {
            return;
        }
        $.ajax({
            url: '${pageContext.request.contextPath}/contract/delete',
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
