<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 11.12.2015
  Time: 10:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authentication var="user" property="principal" />
<div class="navbar-default sidebar" role="navigation">
  <div class="sidebar-nav navbar-collapse">
    <ul class="nav" id="side-menu">
      <sec:authorize ifAnyGranted="ROLE_ADD_TASK">
        <li>
          <a href="dossers/" onclick="$('#page-wrapper').load('dossers/'); return false;"><i class="glyphicon glyphicon-bell"></i> <c:message code="label.addcomplaint"/></a>
        </li>
      </sec:authorize>
      <li>
        <a href="${pageContext.request.contextPath}/dashboard" onclick="$('#page-wrapper').load('${pageContext.request.contextPath}/dashboard'); return false;"><i class="fa fa-dashboard fa-fw"></i> Dashboard</a>
      </li>
      <li>
        <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i><c:message code="label.statistic"/><span class="fa arrow"></span></a>
        <ul class="nav nav-second-level">
          <li>
            <a href="stats/" onclick="$('#page-wrapper').load('stats/'); return false;"><c:message code="label.statistic"/></a>
          </li>
          <li>
            <a href="stats/count" onclick="$('#page-wrapper').load('stats/count'); return false;"><c:message code="label.statistic.buble"/></a>
          </li>
          <li>
            <a href="stats/plot" onclick="$('#page-wrapper').load('stats/plot'); return false;"><c:message code="label.statistic.plot"/></a>
          </li>
        </ul>
        <!-- /.nav-second-level -->
      </li>
      <sec:authorize ifAnyGranted="ROLE_GROUP_INF_EDIT,ROLE_SUPER_INF_EDIT,ROLE_MAIN_INF_EDIT,ROLE_GROUP_INF_VIEW,ROLE_SUPER_INF_VIEW,ROLE_MAIN_INF_VIEW">
        <li>
          <a href="search/" onclick="$('#page-wrapper').load('search/'); return false;"><i class="fa fa-search fa-fw"></i> <c:message code="label.search"/></a>
        </li>
      </sec:authorize>
      <li>
        <a href="#"><i class="fa fa-inbox fa-fw"></i><c:message code="label.persons"/><span class="fa arrow"></span></a>
        <ul class="nav nav-second-level">
          <li>
            <a href="person/" onclick="$('#page-wrapper').empty();$('#page-wrapper').load('person/'); return false;"><c:message code="label.persons.clients"/></a>
          </li>
          <sec:authorize ifAnyGranted="ROLE_PERSONAL_VIEW,ROLE_PERSONAL_EDIT,ROLE_PERSONAL_ADD">
            <li>
              <a href="person/worker" onclick="$('#page-wrapper').empty();$('#page-wrapper').load('person/worker'); return false;"> <c:message code="label.persons.workers"/></a>
            </li>
          </sec:authorize>
        </ul>
        <!-- /.nav-second-level -->
      </li>
      <sec:authorize ifAnyGranted="ROLE_ADMIN">
        <li>
          <a href=""><i class="fa fa-wrench fa-fw"></i><c:message code="label.administrate"/><span class="fa arrow"></span></a>
          <ul class="nav nav-second-level">
            <li>
              <a href="journal/" onclick="$('#page-wrapper').load('journal/'); return false;"> <c:message code="label.journal"/></a>
            </li>
            <li>
              <a href="users/" onclick="$('#page-wrapper').load('users/'); return false;"> <c:message code="label.administrate.users"/></a>
            </li>
            <li>
              <a href="subdivisions/" onclick="$('#page-wrapper').load('subdivisions/'); return false;"> <c:message code="label.administrate.subdivisions"/></a>
            </li>
          </ul>
        </li>
      </sec:authorize>
      <sec:authorize ifAnyGranted="ROLE_GLOBAL_INFO_EDIT">
        <li>
          <a href=""><i class="fa fa-sitemap fa-fw"></i><c:message code="label.menu.globaldirectory"/><span class="fa arrow"></span></a>
          <ul class="nav nav-second-level">
            <li>
              <a href="filetypes/" onclick="$('#page-wrapper').load('filetypes/'); return false;"> <c:message code="label.menu.globaldirectory.filetype"/></a>
            </li>
            <li>
              <a href="activities/" onclick="$('#page-wrapper').load('activities/'); return false;"> <c:message code="label.menu.globaldirectory.activity"/></a>
            </li>
            <li>
              <a href="contacttypes/" onclick="$('#page-wrapper').load('contacttypes/'); return false;"> <c:message code="label.menu.globaldirectory.contacttype"/></a>
            </li>
            <li>
              <a href="hobbies/" onclick="$('#page-wrapper').load('hobbies/'); return false;"> <c:message code="label.menu.globaldirectory.hobbi"/></a>
            </li>
            <li>
              <a href="relatives/" onclick="$('#page-wrapper').load('relatives/'); return false;"> <c:message code="label.menu.globaldirectory.relative"/></a>
            </li>
            <li>
              <a href="segments/" onclick="$('#page-wrapper').load('segments/'); return false;"> <c:message code="label.menu.globaldirectory.segments"/></a>
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
              <a href="category/" onclick="$('#page-wrapper').load('category/'); return false;"> <c:message code="label.direcotry.category"/></a>
            </li>
            <li>
              <a href="info/" onclick="$('#page-wrapper').load('info/'); return false;"> <c:message code="label.direcotry.info"/></a>
            </li>
          </ul>
          <!-- /.nav-second-level -->
        </li>
      </sec:authorize>
      <li><a href="logout"><i class="fa fa-sign-out fa-fw"></i> <c:message code="label.menu.Logout"/></a>
    </ul>
  </div>
  <!-- /.sidebar-collapse -->
</div>