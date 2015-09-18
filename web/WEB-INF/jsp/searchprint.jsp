<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 18.09.2015
  Time: 16:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="cc" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="jp" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- JQuery --%>
<%@include file="/WEB-INF/include/jquery_lib_include.jsp" %>

<%-- BootStrup--%>
<%@include file="/WEB-INF/include/bootstrup_include.jsp" %>
<%----%>
<html>
<head>
    <title></title>
</head>
<body>
<jp:if test="${not empty dossers}">
  <div class="panel panel-danger">
    <div class="panel-heading">
      <h3 class="panel-title"><c:message code="label.button.print"/></h3>
    </div>
    <div class="panel-body">
      <table class="table">
        <thead style="background:#fcf">
        <tr>
          <th><c:message code="label.clientstables.dosers.col_title.confidentional"/></th>
          <th><c:message code="label.clientstables.dosers.col_title.subdiv"/></th>
          <th><c:message code="label.clientstables.dosers.col_title.categeory"/></th>
          <th><c:message code="label.clientstables.dosers.col_title.info"/></th>
          <th><c:message code="label.clientstables.col_title.fio"/></th>
          <th><c:message code="label.clientstables.col_title.code"/></th>
          <th><c:message code="label.clientstables.col_title.bethday"/></th>
          <th><c:message code="label.clientstables.dosers.col_title.text"/></th>
          <th><c:message code="label.clientstables.dosers.col_title.filytype"/></th>
          <th><c:message code="label.clientstables.dosers.col_title.filecomments"/></th>
          <th><c:message code="label.clientstables.dosers.col_title.created"/></th>
        </tr>
        </thead>
        <tbody>
        <jp:forEach items="${dossers}" var="dosser">
          <tr>
            <td>${dosser.confidentional?'+':'-'}</td>
            <td>${dosser.subdivision}</td>
            <td>${dosser.category}</td>
            <td>${dosser.infotype}</td>
            <td>${dosser.fio}</td>
            <td>${dosser.code}</td>
            <td>${dosser.bethday}</td>
            <td>${dosser.info}</td>
            <td>${dosser.fileType}</td>
            <td>${dosser.fileComments}</td>
            <td>${dosser.creationTime}</td>
          </tr>
          <tr>
            <td>
              <jp:if test="${not empty dosser.mime}">
              <a href="${url}${dosser.fileUrl}" class="btn btn-default"  target="_blank" ><c:message code="label.clientstables.dosers.col_title.file"/></a>
              </jp:if>
            </td>
            <td colspan="5">
              <jp:if test="${not empty dosser.mime}">
                <a href="${url}${dosser.fileUrl}"  target="_blank" >${url}${dosser.fileUrl}</a>
              </jp:if>
            </td>
            <td colspan="5">
              <jp:if test="${not empty dosser.personnPrintUrl}">
                <a href="${url}${dosser.personnPrintUrl}" target="_blank" class="btn btn-default"><i class="fa fa-print fa-fw"></i></a>
                <a href="${url}${dosser.personnPrintUrl}"  target="_blank" >${url}${dosser.personnPrintUrl}</a>
              </jp:if>
            </td>
          </tr>
        </jp:forEach>
        </tbody>
      </table>
    </div>
  </div>
</jp:if>
</body>
</html>
