<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="activeMenu" value="contract" scope="request"/>
<jsp:include page="../common/header.jsp"/>

<h3 class="page-header-title">
    <span class="glyphicon glyphicon-file"></span> ${contract.id == null ? '登记劳动合同' : '编辑劳动合同'}
</h3>

<c:if test="${not empty errorMsg}">
    <div class="alert alert-danger alert-dismissible" role="alert">
        <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>
        <span class="glyphicon glyphicon-exclamation-sign"></span> ${errorMsg}
    </div>
</c:if>

<div class="row">
    <div class="col-md-7">
        <form action="${pageContext.request.contextPath}/contract/save" method="post" class="form-horizontal">
            <input type="hidden" name="id" value="${contract.id}"/>

            <div class="form-group">
                <label class="col-sm-3 control-label"><span class="text-danger">*</span> 关联员工</label>
                <div class="col-sm-9">
                    <select class="form-control" name="empNo" id="empNoSelect" required>
                        <option value="">-- 请选择关联员工 --</option>
                        <c:forEach items="${employeeList}" var="emp">
                            <option value="${emp.empNo}" ${contract.empNo == emp.empNo ? 'selected' : ''}>
                                [${emp.empNo}] ${emp.name} (${emp.deptName} - ${emp.positionName})
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label"><span class="text-danger">*</span> 合同开始日期</label>
                <div class="col-sm-9">
                    <input type="date" class="form-control" name="startDate" value="${contract.startDate}"
                           placeholder="格式：yyyy-MM-dd" required/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label"><span class="text-danger">*</span> 合同结束日期</label>
                <div class="col-sm-9">
                    <input type="date" class="form-control" name="endDate" value="${contract.endDate}"
                           placeholder="格式：yyyy-MM-dd" required/>
                </div>
            </div>

            <div class="form-group" style="margin-top: 30px;">
                <div class="col-sm-offset-3 col-sm-9">
                    <button type="submit" class="btn btn-primary" style="padding-left: 25px; padding-right: 25px;">
                        <span class="glyphicon glyphicon-floppy-disk"></span> 保存
                    </button>
                    <a href="${pageContext.request.contextPath}/contract/list" class="btn btn-default" style="margin-left: 10px;">
                        返回列表
                    </a>
                </div>
            </div>
        </form>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>
