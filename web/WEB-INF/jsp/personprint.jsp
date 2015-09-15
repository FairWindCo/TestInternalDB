<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="cc" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="jp" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 16.07.2015
  Time: 15:52
  To change this template use File | Settings | File Templates.
--%>
<%-- JQuery --%>
<%@include file="/WEB-INF/include/jquery_lib_include.jsp" %>

<%-- BootStrup--%>
<%@include file="/WEB-INF/include/bootstrup_include.jsp" %>
<%----%>

<%-- JQueryUI --%>
<%@include file="/WEB-INF/include/jquery_ui_include.jsp" %>
<%-- JTable --%>
<%@include file="/WEB-INF/include/jtable_include.jsp" %>


<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Insert title here</title>
</head>
<body>
<h1>
${person.personType=='WORKER'?'СОТРУДНИК:':'КЛИЕНТ:'}
${person.fio}</h1>
<div class="panel panel-primary">
  <div class="panel-heading"><c:message code="label.edit.persons.info_title"/></div>
  <div class="panel-body">

<table class="table">
  <tbody>
  <tr>
    <td rowspan="12">
    <jp:if test="${not empty person.photo}">
      <img src="${pageContext.request.contextPath}/file/view?fileID=${person.photo.fileId}" width="150" height="150" /></td>
    </jp:if>
  </tr>
  <tr>
    <td><c:message code="label.clientstables.col_title.code"/></td>
    <td><B><jp:out value="${person.code}" /></B></td>
  </tr>
  <tr>
    <td><c:message code="label.clientstables.col_title.bethday"/></td>
    <td><jp:out value="${person.bethday}" /></td>
  </tr>
  <tr>
    <td><c:message code="label.clientstables.col_title.status"/></td>
    <td><jp:out value="${person.personStatus}" /></td>
  </tr>
  <tr>
    <td><c:message code="label.edit.persons.passportInfo"/></td>
    <td><jp:out value="${person.passportInfo}" /></td>
  </tr>
  <tr>
    <td><c:message code="label.edit.persons.hobbie_ids"/></td><td>
    ${empty person.hobbie?'':person.hobbie.hobbieName}
  </td>
  </tr>
  <tr>
    <td><c:message code="label.edit.persons.hobbiesComments"/></td><td>
    <jp:out value="${person.hobbiesComments}" />
  </td>
  </tr>
  <tr>
    <td><c:message code="label.edit.persons.activities_ids"/></td><td>
    ${empty person.activities?'':person.activities.activitiesTypeName}
  </td>
  </tr>
  <tr>
    <td><c:message code="label.edit.persons.activitiesComments"/></td><td>
    <jp:out value="${person.activitiesComments}" />
  </td>
  </tr>
  <tr>
    <td><c:message code="label.edit.persons.sergments_ids"/></td><td>
    ${(empty person.additionalInfo or empty person.additionalInfo.clientSegment)?'':person.additionalInfo.clientSegment.name}
  </td>
  </tr>
  <tr>
    <td><c:message code="label.edit.persons.clientColorCode"/></td><td>
    ${empty person.additionalInfo?'':person.additionalInfo.clientColorCODE}
  </td>
  </tr>
  <tr>
    <td><c:message code="label.edit.persons.clientColorComments"/></td><td>
    ${empty person.additionalInfo?'':person.additionalInfo.clientColorComments}
  </td>
  </tr>
  </tbody>
</table>
  </div>
</div>
<table class="table"><tr><td>
<jp:if test="${not empty person.contacts}">
<div class="panel panel-info">
  <div class="panel-heading">
    <h3 class="panel-title"><c:message code="label.edit.persons.contact_title"/></h3>
  </div>
  <div class="panel-body">
<table class="table">
  <thead><tr>
    <th><c:message code="label.edit.persons.contactstable.contact_type.col_title"/></th>
    <th><c:message code="label.edit.persons.contactstable.contact_info.col_title"/></th>
  </tr></thead>
  <tbody>
  <jp:forEach items="${person.contacts}" var="contact">
    <tr>
      <td>${contact.contactType==null?'--':contact.contactType.cobtactTypeName}</td>
      <td>${contact.contactinfo}</td>
    </tr>
  </jp:forEach>
  </tbody>
</table>
  </div>
</div>
</jp:if>
    </td><td>
<jp:if test="${not empty person.additionalInfo}">
  <jp:if test="${not empty person.additionalInfo.relationDegrees}">
    <div class="panel panel-default">
      <div class="panel-heading">
        <h3 class="panel-title"><c:message code="label.edit.persons.relations_title"/></h3>
      </div>
      <div class="panel-body">
        <table class="table">
          <thead><tr>
            <th><c:message code="label.edit.persons.relation.person_name.col_title"/></th>
            <th><c:message code="label.edit.persons.relation.relative_name.col_title"/></th>
          </tr></thead>
          <tbody>
          <jp:forEach items="${person.additionalInfo.relationDegrees}" var="relation">
            <tr>
              <td>${relation.person==null?'':relation.person.fio}</td>
              <td>${relation.relatives==null?'':relation.relatives.name}</td>
            </tr>
          </jp:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </jp:if>
</jp:if>
</td><td>
<jp:if test="${not empty person.files}">
<div class="panel panel-warning">
  <div class="panel-heading">
    <h3 class="panel-title"><c:message code="label.edit.persons.files_title"/></h3>
  </div>
  <div class="panel-body">
  <table class="table">
    <thead>
    <tr>
      <th><c:message code="label.edit.persons.files.file_comments.col_title"/></th>
      <th><c:message code="label.edit.persons.files.file_mimetime.col_title"/></th>
      <th><c:message code="label.edit.persons.files.file_type.col_title"/></th>
      <th><c:message code="label.edit.persons.files.file.col_title"/></th>
    </tr>
    </thead>
    <tbody>
    <jp:forEach items="${person.files}" var="file">
      <tr>
        <td>${file.fileNameComments}</td>
        <td><${file.fileMimeType}/td>
        <td>${file==null?'---':(file.filesType==null?'----':file.filesType.filesTypeName)}</td>
        <td>
          <jp:if test="${not empty file}">
            <a src="${pageContext.request.contextPath}/file/view?fileID=${file.fileId}">${pageContext.request.contextPath}/file/view?fileID=${file.fileId}</a>
          </jp:if>
        </td>
      </tr>
    </jp:forEach>
    </tbody>
  </table>
  </div>
</div>
</jp:if>
</td></tr></table>


<jp:if test="${not empty dossers}">
<div class="panel panel-danger">
  <div class="panel-heading">
    <h3 class="panel-title"><c:message code="label.clientstables.col_title.dossers"/></h3>
  </div>
  <div class="panel-body">
<table class="table">
  <thead style="background:#fcf">
  <tr>
    <th><c:message code="label.clientstables.dosers.col_title.confidentional"/></th>
    <th><c:message code="label.clientstables.dosers.col_title.subdiv"/></th>
    <th><c:message code="label.clientstables.dosers.col_title.categeory"/></th>
    <th><c:message code="label.clientstables.dosers.col_title.info"/></th>
    <th><c:message code="label.clientstables.dosers.col_title.text"/></th>
    <th><c:message code="label.clientstables.dosers.col_title.filytype"/></th>
    <th><c:message code="label.clientstables.dosers.col_title.file"/></th>
    <th><c:message code="label.clientstables.dosers.col_title.filecomments"/></th>
    <th><c:message code="label.clientstables.dosers.col_title.created"/></th>
  </tr>
  </thead>
  <tbody>
  <jp:forEach items="${dossers}" var="dosser">
    <tr>
      <td>${dosser.confidential?'+':'-'}</td>
      <td>${dosser.subdivision==null?'---':dosser.subdivision.name}</td>
      <td>${dosser.category==null?'---':dosser.category.name}</td>
      <td>${dosser.infotype==null?'---':dosser.infotype.typeName}</td>
      <td>${dosser.textinfo}</td>
      <td>${dosser.fileinfo==null?'---':(dosser.fileinfo==null?'---':(dosser.fileinfo.filesType==null?'----':dosser.fileinfo.filesType.filesTypeName))}</td>
      <td>
      <jp:if test="${not empty dosser.fileinfo}">
      <a src="${pageContext.request.contextPath}/file/view?fileID=${dosser.fileinfo.fileId}">${pageContext.request.contextPath}/file/view?fileID=${dosser.fileinfo.fileId}</a>
      </jp:if>
      </td>
      <td>${dosser.fileinfo==null?'---':dosser.fileinfo.fileNameComments}</td>
      <td>${dosser.formatedCreationTime}</td>
    </tr>
  </jp:forEach>
  </tbody>
</table>
  </div>
</div>
</jp:if>
</body>
</html>
