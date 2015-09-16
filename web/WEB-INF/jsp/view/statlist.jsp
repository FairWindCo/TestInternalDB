<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 17.08.2015
  Time: 11:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>

<%-- JQuery --%>
<%@include file="/WEB-INF/include/jquery_lib_include.jsp" %>

<%-- BootStrup--%>
<%@include file="/WEB-INF/include/bootstrup_include.jsp" %>
<%----%>

<%-- JQueryUI --%>
<%@include file="/WEB-INF/include/jquery_ui_include.jsp" %>
<%-- JTable --%>
<%@include file="/WEB-INF/include/jtable_include.jsp" %>

<%-- JCombobox
<%@include file="/WEB-INF/include/jcombo_include.jsp" %>--%>
<%-- JMultiSelect--%>
<%@include file="/WEB-INF/include/jmultselect_include.jsp" %>

<%-- customized javascript code to manage JTable --%>
<script>
  $(document).ready(function() {
    //setup the jtable that will display the results
    $('#ExpenseTableContainer').jtable({
      title: '<c:message code="label.usertables"/>',
      selecting: true, //Enable selecting
      paging: true, //Enable paging
      pageSize: 10, //Set page size (default: 10)
      sorting: true, //Enable sorting
      messages: <c:message code="label.messages"/>,
      actions: {
        //listAction: 'datatable/getAllExpenses',
        listAction: '${pageContext.request.contextPath}/stats/import',
      },
      fields: {
        importDateTime: {
          title: '<c:message code="label.clientstables.dosers.col_title.confidentional"/>',
          width: '10%',
        },
        personImportCounter: {
          title: '<c:message code="label.clientstables.dosers.col_title.subdiv"/>',
          width: '5%',
        },
        dosserImportCounter: {
          title: '<c:message code="label.clientstables.dosers.col_title.categeory"/>',
          width: '5%',
        },
        subdivisionsImportCounter: {
          title: '<c:message code="label.clientstables.dosers.col_title.info"/>',
          width: '5%',
        },
        categoryImportCounter: {
          title: '<c:message code="label.clientstables.col_title.fio"/>',
          width: '5%',
        },
        infotypeImportCounter: {
          title: '<c:message code="label.clientstables.col_title.code"/>',
          width: '5%',
        },
        activitiesImportCounter: {
          title: '<c:message code="label.clientstables.col_title.bethday"/>',
          width: '5%',
        },
        contacttypesImportCounter: {
          title: '<c:message code="label.clientstables.dosers.col_title.text"/>',
          width: '5%',
        },
        hobbiImportCounter: {
          title: '<c:message code="label.clientstables.dosers.col_title.filecomments"/>',
          width: '5%',
        },
        relativesImportCounter: {
          title: '<c:message code="label.clientstables.dosers.col_title.created"/>',
          width: '5%',
        },
        segmentsImportCounter: {
          title: '<c:message code="label.clientstables.dosers.col_title.filytype"/>',
          width: '5%',
        },
        filetypesImportCounter: {
          title: '<c:message code="label.usertables.table.col_title.enabled"/>',
          width: '5%',
        },
        importerUserName: {
          title: '<c:message code="label.usertables.table.col_title.enabled"/>',
          width: '15%',
        },
      },
      //Register to selectionChanged event to hanlde events
    });

    $('#LoadRecordsButton').click(function (e) {
      e.preventDefault();
      $('#ExpenseTableContainer').jtable('load', {
        searchname: $('#searchname').val(),
        searchcode: $('#searchcode').val(),
      });
    });

    $('#AllRecordsButton').click(function (e) {
      e.preventDefault();
      $('#ExpenseTableContainer').jtable('load');
    });

    $('#ExpenseTableContainer').jtable('load');

  });
</script>


<div class="filtering">
  <form>
    <c:message code="label.field.name2"/>: <input type="text" name="searchname" id="searchname" />
    <c:message code="label.field.name2"/>: <input type="text" name="searchcode" id="searchcode" />

    <button type="submit" id="LoadRecordsButton">'<c:message code="label.button.loadrecord"/>'</button>
    <button type="submit" id="AllRecordsButton">'<c:message code="label.button.allrecord"/>'</button>
  </form>
</div>
<div>
  <div id="ExpenseTableContainer" style="width:99%;"></div>
</div>
<div id="view_image_dialog"></div>