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

    <!-- Bootstrap Core CSS -->
    <script src="<c:url value="/resources/jquery-1.11.3.min.js"/>"></script>
    <script src="<c:url value="/resources/jquery-ui.min.js"/>"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="<c:url value="/resources/metisMenu.min.js"/>"></script>

    <!-- Custom Theme JavaScript -->
    <script src="<c:url value="/resources/sb-admin-2.js"/>"></script>


    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css"/>" type="text/css">
    <link rel="stylesheet" href="<c:url value="/resources/css/font-awesome.min.css"/>"  type="text/css">
    <link rel="stylesheet" href="<c:url value="/resources/metisMenu.min.css"/>"  type="text/css">
    <link rel="stylesheet" href="<c:url value="/resources/sb-admin-2.css"/>"  type="text/css">



    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

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
                            <c:message code="label.loginincorect"/><br/>
                        </core:if>
                        <a href="?lang=en">en</a> | <a href="?lang=az">az</a> | <a href="?lang=ru">ru</a><br/>
                        <core:out value="param.language: [${param.language}] language: [${language}] pageContext.request.locale: [${pageContext.request.locale}] country: [${pageContext.request.locale.displayCountry}]" /><br/>
                    </div>
                </div>
            </div>
        </div>
    </div>




</body>

</html>
