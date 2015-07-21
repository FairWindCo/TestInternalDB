<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 16.07.2015
  Time: 14:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<html>
  <head>
    <title></title>
    <script src="<c:url value="/resources/jquery-1.11.3.min.js"/>"></script>
    <script src="<c:url value="/resources/jquery-ui.min.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/resources/jquery-ui.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/jquery-ui.theme.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/jquery-ui.structure.min.css"/>">

    <link rel="stylesheet" href="<c:url value="/resources/slick.grid.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/controls/slick.pager.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/controls/slick.columnpicker.css"/>">
    <script src="<c:url value="/resources/lib/firebugx.js"/>"></script>
    <script src="<c:url value="/resources/lib/jquery.event.drag-2.2.js"/>"></script>
    <script src="<c:url value="/resources/plugins/slick.rowselectionmodel.js"/>"></script>
    <script src="<c:url value="/resources/slick.core.js"/>"></script>
    <script src="<c:url value="/resources/slick.grid.js"/>"></script>
    <script src="<c:url value="/resources/slick.dataview.js"/>"></script>
    <script src="<c:url value="/resources/controls/slick.pager.js"/>"></script>
    <script src="<c:url value="/resources/controls/slick.columnpicker.js"/>"></script>

  </head>
  <body>
  <table width="100%">
    <tr>
      <td valign="top" width="50%">
        <div id="myGrid" style="width:600px;height:500px;"></div>
      </td>
      <td valign="top">
        <h2>Demonstrates:</h2>
        <ul>
          <li>basic grid with minimal configuration</li>
        </ul>
        <h2>View Source:</h2>
        <ul>
          <li><A href="https://github.com/mleibman/SlickGrid/blob/gh-pages/examples/example1-simple.html" target="_sourcewindow"> View the source for this example on Github</a></li>
        </ul>
      </td>
    </tr>
  </table>
  <script>
    var grid;
    var columns = [
      {id: "title", name: "Title", field: "title"},
      {id: "duration", name: "Duration", field: "duration"},
      {id: "%", name: "% Complete", field: "percentComplete"},
      {id: "start", name: "Start", field: "start"},
      {id: "finish", name: "Finish", field: "finish"},
      {id: "effort-driven", name: "Effort Driven", field: "effortDriven"}
    ];
    var options = {
      enableCellNavigation: true,
      enableColumnReorder: false
    };
    $(function () {
      var data = [];
      for (var i = 0; i < 500; i++) {
        data[i] = {
          title: "Task " + i,
          duration: "5 days",
          percentComplete: Math.round(Math.random() * 100),
          start: "01/01/2009",
          finish: "01/05/2009",
          effortDriven: (i % 5 == 0)
        };
      }
      grid = new Slick.Grid("#myGrid", data, columns, options);
    })
  </script>
  </body>
</html>
