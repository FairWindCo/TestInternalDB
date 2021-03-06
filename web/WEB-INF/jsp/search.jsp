<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>

<ol class="breadcrumb">
    <li><a href="${pageContext.request.contextPath}/"><c:message code="label.main"/></a></li>
    <li><a href="#"><c:message code="label.search"/></a></li>
</ol>
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title"><c:message code="label.search.title"/></h3>
    </div>
    <div class="panel-body">
        <jsp:include page="view/searchview.jsp" />
    </div>
</div>

