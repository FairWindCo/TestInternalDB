<%@ taglib prefix="cc" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 16.07.2015
  Time: 15:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Insert title here</title>
</head>
<body>
<h1>Persons List</h1>

<cc:url var="addUrl" value="TestInternalDB_war_exploded/person/add" />
<table style="border: 1px solid; width: 500px; text-align:center">
  <thead style="background:#fcf">
  <tr>
    <th>First Name</th>
    <th>Last Name</th>
    <th>Money</th>
    <th colspan="3"></th>
  </tr>
  </thead>
  <tbody>
  <c:forEach items="${persons}" var="person">
    <cc:url var="editUrl" value="TestInternalDB_war_exploded/person/edit?id=${person.personId}" />
    <cc:url var="deleteUrl" value="TestInternalDB_war_exploded/person/delete?id=${person.personId}" />
    <tr>
      <td><c:out value="${person.FIO}" /></td>
      <td><c:out value="${person.bethday}" /></td>
      <td><c:out value="${person.personStatus}" /></td>
      <td><a href="${editUrl}">Edit</a></td>
      <td><a href="${deleteUrl}">Delete</a></td>
      <td><a href="${addUrl}">Add</a></td>
    </tr>
  </c:forEach>
  </tbody>
</table>

<c:if test="${empty persons}">
  There are currently no persons in the list. <a href="${addUrl}">Add</a> a person.
</c:if>

</body>
</html>
