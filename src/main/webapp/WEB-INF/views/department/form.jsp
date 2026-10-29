<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="activeMenu" value="department" scope="request"/>
<jsp:include page="../common/header.jsp"/>

<h3 class="page-header-title">
    <span class="glyphicon glyphicon-folder-open"></span> ${department.id == null ? '新增部门' : '编辑部门'}
</h3>

<c:if test="${not empty errorMsg}">
    <div class="alert alert-danger alert-dismissible" role="alert">
        <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>
        <span class="glyphicon glyphicon-exclamation-sign"></span> ${errorMsg}
    </div>
</c:if>

<div class="row">
    <div class="col-md-7">
        <form action="${pageContext.request.contextPath}/department/save" method="post" class="form-horizontal">
            <input type="hidden" name="id" value="${department.id}"/>

            <div class="form-group">
                <label class="col-sm-3 control-label"><span class="text-danger">*</span> 部门名称</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" name="deptName" value="${department.deptName}"
                           placeholder="请输入部门名称" required maxlength="50"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label">上级部门</label>
                <div class="col-sm-9">
                    <select class="form-control" name="parentId">
                        <option value="0">-- 顶级部门（无上级） --</option>
                        <c:forEach items="${parentList}" var="p">
                            <c:if test="${department.id == null || department.id != p.id}">
                                <option value="${p.id}" ${department.parentId == p.id ? 'selected' : ''}>
                                    ${p.deptName}
                                </option>
                            </c:if>
                        </c:forEach>
                    </select>
                    <p class="help-block" style="font-size: 12px; color: #777;">
                        若不选择上级部门，则默认作为一级顶级部门。
                    </p>
                </div>
            </div>

            <div class="form-group" style="margin-top: 30px;">
                <div class="col-sm-offset-3 col-sm-9">
                    <button type="submit" class="btn btn-primary" style="padding-left: 25px; padding-right: 25px;">
                        <span class="glyphicon glyphicon-floppy-disk"></span> 保存
                    </button>
                    <a href="${pageContext.request.contextPath}/department/list" class="btn btn-default" style="margin-left: 10px;">
                        返回列表
                    </a>
                </div>
            </div>
        </form>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>
