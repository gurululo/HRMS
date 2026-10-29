<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="activeMenu" value="position" scope="request"/>
<jsp:include page="../common/header.jsp"/>

<h3 class="page-header-title">
    <span class="glyphicon glyphicon-tags"></span> ${position.id == null ? '新增岗位' : '编辑岗位'}
</h3>

<c:if test="${not empty errorMsg}">
    <div class="alert alert-danger alert-dismissible" role="alert">
        <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>
        <span class="glyphicon glyphicon-exclamation-sign"></span> ${errorMsg}
    </div>
</c:if>

<div class="row">
    <div class="col-md-7">
        <form action="${pageContext.request.contextPath}/position/save" method="post" class="form-horizontal">
            <input type="hidden" name="id" value="${position.id}"/>

            <div class="form-group">
                <label class="col-sm-3 control-label"><span class="text-danger">*</span> 岗位名称</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" name="positionName" value="${position.positionName}"
                           placeholder="请输入岗位名称" required maxlength="50"/>
                </div>
            </div>

            <div class="form-group" style="margin-top: 30px;">
                <div class="col-sm-offset-3 col-sm-9">
                    <button type="submit" class="btn btn-primary" style="padding-left: 25px; padding-right: 25px;">
                        <span class="glyphicon glyphicon-floppy-disk"></span> 保存
                    </button>
                    <a href="${pageContext.request.contextPath}/position/list" class="btn btn-default" style="margin-left: 10px;">
                        返回列表
                    </a>
                </div>
            </div>
        </form>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>
