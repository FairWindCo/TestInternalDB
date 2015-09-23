<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SB Admin 2 - Bootstrap Admin Theme</title>

    <%-- JQuery --%>
    <%@include file="/WEB-INF/include/jquery_include.jsp" %>

    <!-- Bootstrap Core JavaScript -->
    <%@include file="/WEB-INF/include/bootstrup_include.jsp" %>





</head>

<body>

    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title"><c:message code="label.loginform"/></h3>
                    </div>
                    <div class="panel-body">
                        <form role="form" action="j_spring_security_check" method="post">
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" placeholder="<c:message code="label.loginname"/>" id="username" name="username" type="text" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="<c:message code="label.loginpass"/>" id="password" name="password" type="password" value="">
                                </div>
                                <!--
                                <div class="checkbox">
                                    <label>
                                        <input name="_spring_security_remember_me" type="checkbox" value="Remember Me">Remember Me
                                    </label>
                                </div>
                                <!-- Change this to a button or input when using this as a form -->
                                <input type="submit" value="<c:message code="label.login"/>" class="btn btn-lg btn-success btn-block">
                            </fieldset>
                        </form>
                        <core:if test="${param.error != null}">
                            <div class="alert alert-warning"><c:message code="label.loginincorect"/><br/></div>
                        </core:if>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-body" align="center">
                            <div class="btn-group">
                                <a href="?lang=en_US" class="btn btn-default">EN</a>
                                <a href="?lang=ua_UA" class="btn btn-default">UA</a>
                                <a href="?lang=ru_RU" class="btn btn-default">RU</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>




</body>

</html>
