<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="activeMenu" value="employee" scope="request"/>
<jsp:include page="../common/header.jsp"/>

<h3 class="page-header-title">
    <span class="glyphicon glyphicon-user"></span> ${employee.id == null ? '新增员工' : '编辑员工'}
</h3>

<c:if test="${not empty errorMsg}">
    <div class="alert alert-danger alert-dismissible" role="alert">
        <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>
        <span class="glyphicon glyphicon-exclamation-sign"></span> ${errorMsg}
    </div>
</c:if>

<div class="row">
    <div class="col-md-7">
        <form action="${pageContext.request.contextPath}/employee/save" method="post" class="form-horizontal">
            <input type="hidden" name="id" value="${employee.id}"/>

            <div class="form-group">
                <label class="col-sm-3 control-label"><span class="text-danger">*</span> 员工编号</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" name="empNo" value="${employee.empNo}"
                           placeholder="例如：EMP1001" required maxlength="30"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label"><span class="text-danger">*</span> 员工姓名</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" name="name" value="${employee.name}"
                           placeholder="请输入姓名" required maxlength="50"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label"><span class="text-danger">*</span> 所属部门</label>
                <div class="col-sm-9">
                    <select class="form-control" name="deptId" required>
                        <option value="">-- 请选择所属部门 --</option>
                        <c:forEach items="${departmentList}" var="d">
                            <option value="${d.id}" ${employee.deptId == d.id ? 'selected' : ''}>
                                ${d.deptName}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label"><span class="text-danger">*</span> 担任岗位</label>
                <div class="col-sm-9">
                    <select class="form-control" name="positionId" required>
                        <option value="">-- 请选择担任岗位 --</option>
                        <c:forEach items="${positionList}" var="p">
                            <option value="${p.id}" ${employee.positionId == p.id ? 'selected' : ''}>
                                ${p.positionName}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="form-group" style="margin-top: 30px;">
                <div class="col-sm-offset-3 col-sm-9">
                    <button type="submit" class="btn btn-primary" style="padding-left: 25px; padding-right: 25px;">
                        <span class="glyphicon glyphicon-floppy-disk"></span> 保存
                    </button>
                    <a href="${pageContext.request.contextPath}/employee/list" class="btn btn-default" style="margin-left: 10px;">
                        返回列表
                    </a>
                </div>
            </div>
        </form>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>
