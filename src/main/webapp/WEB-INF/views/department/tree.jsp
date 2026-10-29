<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="activeMenu" value="department_tree" scope="request"/>
<jsp:include page="../common/header.jsp"/>

<h3 class="page-header-title">
    <span class="glyphicon glyphicon-indent-left"></span> 部门架构树形展示
</h3>

<div class="row" style="margin-bottom: 15px;">
    <div class="col-md-12">
        <a href="${pageContext.request.contextPath}/department/list" class="btn btn-default">
            <span class="glyphicon glyphicon-arrow-left"></span> 返回列表视图
        </a>
        <a href="${pageContext.request.contextPath}/department/toAdd" class="btn btn-primary" style="margin-left: 10px;">
            <span class="glyphicon glyphicon-plus"></span> 新增部门
        </a>
        <button type="button" class="btn btn-default" id="btn-toggle-all" style="margin-left: 10px;">
            <span class="glyphicon glyphicon-resize-full"></span> 全部展开/折叠
        </button>
    </div>
</div>

<div class="dept-tree-container">
    <div id="tree-container">
        <!-- 树形结构容器 -->
    </div>
</div>

<script>
    $(function () {
        // 加载树形数据
        $.getJSON('${pageContext.request.contextPath}/department/treeData', function (data) {
            if (!data || data.length === 0) {
                $('#tree-container').html('<p style="color:#999; padding:20px; text-align:center;">暂无部门结构数据</p>');
                return;
            }

            function renderNode(node) {
                var hasChildren = node.children && node.children.length > 0;
                var html = '<li style="margin: 8px 0;">';
                html += '<div class="dept-node-box">';
                if (hasChildren) {
                    html += '<span class="glyphicon glyphicon-minus toggle-icon" style="cursor:pointer; margin-right:8px; color:#3182ce;"></span>';
                    html += '<span class="glyphicon glyphicon-folder-open" style="color:#f39c12; margin-right:6px;"></span>';
                } else {
                    html += '<span class="glyphicon glyphicon-minus" style="visibility:hidden; margin-right:8px;"></span>';
                    html += '<span class="glyphicon glyphicon-tag" style="color:#7f8c8d; margin-right:6px;"></span>';
                }
                html += '<strong>' + node.deptName + '</strong>';
                html += ' <a href="${pageContext.request.contextPath}/department/toEdit?id=' + node.id + '" class="btn btn-xs btn-link" style="padding:0 4px;">编辑</a>';
                html += '</div>';

                if (hasChildren) {
                    html += '<ul style="list-style:none; padding-left:28px; border-left:1px dashed #cbd5e0; margin-left:14px; margin-top:6px;">';
                    for (var i = 0; i < node.children.length; i++) {
                        html += renderNode(node.children[i]);
                    }
                    html += '</ul>';
                }
                html += '</li>';
                return html;
            }

            var treeHtml = '<ul style="list-style:none; padding-left:10px;">';
            for (var i = 0; i < data.length; i++) {
                treeHtml += renderNode(data[i]);
            }
            treeHtml += '</ul>';

            $('#tree-container').html(treeHtml);

            // 折叠/展开点击事件
            $('#tree-container').on('click', '.toggle-icon', function () {
                var $icon = $(this);
                var $subUl = $icon.closest('li').children('ul');
                if ($subUl.is(':visible')) {
                    $subUl.slideUp(150);
                    $icon.removeClass('glyphicon-minus').addClass('glyphicon-plus');
                } else {
                    $subUl.slideDown(150);
                    $icon.removeClass('glyphicon-plus').addClass('glyphicon-minus');
                }
            });

            // 全局展开/折叠
            var allExpanded = true;
            $('#btn-toggle-all').on('click', function () {
                if (allExpanded) {
                    $('#tree-container ul ul').slideUp(150);
                    $('.toggle-icon').removeClass('glyphicon-minus').addClass('glyphicon-plus');
                    allExpanded = false;
                } else {
                    $('#tree-container ul ul').slideDown(150);
                    $('.toggle-icon').removeClass('glyphicon-plus').addClass('glyphicon-minus');
                    allExpanded = true;
                }
            });
        });
    });
</script>

<jsp:include page="../common/footer.jsp"/>
