<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>

<ol class="breadcrumb">
    <li><a href="${pageContext.request.contextPath}/"><c:message code="label.main"/></a></li>
    <li><a href="#"><c:message code="label.direcotry"/></a></li>
    <li class="active"><c:message code="label.direcotry.info"/></li>
</ol>
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title"><c:message code="label.direcotry.info.title"/></h3>
    </div>
    <div class="panel-body">
        <jsp:include page="view/info_view.jsp" />
    </div>
</div>

