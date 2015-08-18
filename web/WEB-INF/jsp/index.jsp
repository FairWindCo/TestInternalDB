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
    <%-- JQuery --%>
    <%@include file="/WEB-INF/include/jquery_include.jsp" %>
    <!-- Bootstrap Core JavaScript -->
    <%@include file="/WEB-INF/include/bootstrup_include.jsp" %>

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
        <a class="navbar-brand" href="index.html">Internal DB Admin v0.1</a>
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
          <li><a href="logout"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
          </li>
        </ul>
        <!-- /.dropdown-user -->
      </li>
      </ul>

      <div class="navbar-default sidebar" role="navigation">
        <div class="sidebar-nav navbar-collapse">
          <ul class="nav" id="side-menu">
            <li>
              <a href="index.jsp"><i class="fa fa-dashboard fa-fw"></i> Dashboard</a>
            </li>
            <li>
              <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i> Charts<span class="fa arrow"></span></a>
              <ul class="nav nav-second-level">
                <li>
                  <a href="flot.html">Flot Charts</a>
                </li>
                <li>
                  <a href="morris.html">Morris.js Charts</a>
                </li>
              </ul>
              <!-- /.nav-second-level -->
            </li>
            <sec:authorize ifAnyGranted="ROLE_ADMIN">
            <li>
              <a href=""><i class="fa fa-wrench fa-fw"></i><c:message code="label.administrate"/><span class="fa arrow"></span></a>
            <ul class="nav nav-second-level">
              <li>
                <a href="users/" onclick="$('#page-wrapper').load('users/'); return false;"><c:message code="label.administrate.users"/></a>
              </li>
              <li>
                <a href="subdivisions/" onclick="$('#page-wrapper').load('subdivisions/'); return false;"><c:message code="label.administrate.subdivisions"/></a>
              </li>
            </ul>
            </li>
            </sec:authorize>
            <sec:authorize ifAnyGranted="ROLE_GLOBAL_INFO_EDIT">
            <li>
              <a href=""><i class="fa fa-sitemap fa-fw"></i>the Global Directory<span class="fa arrow"></span></a>
              <ul class="nav nav-second-level">
                <li>
                  <a href="filetypes/" onclick="$('#page-wrapper').load('filetypes/'); return false;">File Types</a>
                </li>
                <li>
                  <a href="activities/" onclick="$('#page-wrapper').load('activities/'); return false;">Activities</a>
                </li>
                <li>
                  <a href="contacttypes/" onclick="$('#page-wrapper').load('contacttypes/'); return false;">Contact Types</a>
                </li>
                <li>
                  <a href="hobbies/" onclick="$('#page-wrapper').load('hobbies/'); return false;">Hobbies</a>
                </li>
                <li>
                  <a href="relatives/" onclick="$('#page-wrapper').load('relatives/'); return false;">Relativies</a>
                </li>
                <li>
                  <a href="segments/" onclick="$('#page-wrapper').load('segments/'); return false;">Segments</a>
                </li>
              </ul>
              <!-- /.nav-second-level -->
            </li>
              </sec:authorize>

            <sec:authorize ifAnyGranted="ROLE_GROUP_INF_EDIT,ROLE_SUPER_INF_EDIT,ROLE_MAIN_INF_EDIT">
            <li>
              <a href=""><i class="fa fa-edit fa-fw"></i><c:message code="label.direcotry"/><span class="fa arrow"></span></a>
              <ul class="nav nav-second-level">
                <li>
                  <a href="category/" onclick="$('#page-wrapper').load('category/'); return false;"><c:message code="label.direcotry.category"/></a>
                </li>
                <li>
                  <a href="">Info Types</a>
                </li>
              </ul>
              <!-- /.nav-second-level -->
            </li>
            </sec:authorize>
            <li><a href="logout"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
          </ul>
        </div>
        <!-- /.sidebar-collapse -->
      </div>
      </nav>

    <div id="page-wrapper">
      <div class="row">
        <div class="col-lg-12">
          <h1 class="page-header">Dashboard</h1>
        </div>
        <!-- /.col-lg-12 -->
      </div>
    </div>
  </div>
  </body>
</html>
