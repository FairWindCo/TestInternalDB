<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>

<ol class="breadcrumb">
    <li><a href="#"><c:message code="label.main"/></a></li>
    <li><a href="#"><c:message code="label.administrate"/></a></li>
    <li class="active"><c:message code="label.administrate.subdivisions"/></li>
</ol>
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title"><c:message code="label.administrate.subdivisions.title"/></h3>
    </div>
    <div class="panel-body">
        <jsp:include page="view/subdivision_view.jsp" />
    </div>
</div>

