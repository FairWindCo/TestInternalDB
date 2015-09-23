<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 04.08.2015
  Time: 12:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- JQuery --%>
<%@include file="/WEB-INF/include/jquery_include.jsp" %>

<!-- Bootstrap Core JavaScript -->
<%@include file="/WEB-INF/include/bootstrup_include.jsp" %>
<html>
<head>
    <title></title>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="login-panel panel panel-danger">
                <div class="panel-heading">
                    <h3 class="panel-title"><c:message code="label.s404.title"/></h3>
                </div>
                <div class="panel-body">
                    <div class="alert alert-danger">
                        <c:message code="label.s404.message"/>
                    </div>
                    <a href="${pageContext.request.contextPath}/" class="btn btn-default"><c:message code="label.s404.link"/></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
