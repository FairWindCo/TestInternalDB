<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 16.07.2015
  Time: 14:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authentication var="user" property="principal" />
<html>
  <head>
    <title></title>

    <%@include file="/WEB-INF/include/full_script_library_loaad.jsp" %>
    </head>
  <body>
  <div id="wrapper">

    <!-- Navigation -->
    <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
          <span class="sr-only">Toggle navigation</span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="index.html"><c:message code="label"/></a>
      </div>
      <!-- /.navbar-header -->
      <ul class="nav navbar-top-links navbar-right">
      <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
          <i class="fa fa-user fa-fw"></i>
          Hi, ${user.username}
          <i class="fa fa-caret-down"></i>
        </a>

        <ul class="dropdown-menu dropdown-user">
          <li><a href="#"><i class="fa fa-user fa-fw"></i> User Profile</a>
          </li>
          <li><a href="#"><i class="fa fa-gear fa-fw"></i> Settings</a>
          </li>
          <li class="divider"></li>
          <li><a href="logout"><i class="fa fa-sign-out fa-fw"></i> <c:message code="label.menu.Logout"/></a>
          </li>
        </ul>
        <!-- /.dropdown-user -->
      </li>
      </ul>

      <jsp:include page="menu.jsp" />
      </nav>

    <div id="page-wrapper">
      <sec:authorize ifAnyGranted="ROLE_ADD_TASK">
        <jsp:include page="complaint.jsp" />
      </sec:authorize>
      <sec:authorize ifNotGranted="ROLE_ADD_TASK">
        <jsp:include page="statdonut.jsp" />
      </sec:authorize>
    </div>
  </div>
  </body>
</html>
