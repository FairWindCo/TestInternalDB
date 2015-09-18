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
      title: '<c:message code="label.statistic.table_title"/>',
      selecting: true, //Enable selecting
      paging: true, //Enable paging
      pageSize: 10, //Set page size (default: 10)
      sorting: false, //Enable sorting
      messages: <c:message code="label.messages"/>,
      actions: {
        //listAction: 'datatable/getAllExpenses',
        listAction: '${pageContext.request.contextPath}/stats/import',
      },
      fields: {
        importDateTime: {
          title: '<c:message code="label.statistic.col_title.date"/>',
          width: '10%',
          display: function (data) {
            var options = {
              year: 'numeric',
              month: 'long',
              day: 'numeric',
            };
            var date=new Date(data.record.importDateTime);
            return date.toLocaleString("ru", options);
          },
        },
        personImportCounter: {
          title: '<c:message code="label.statistic.col_title.personsCounter"/>',
          width: '5%',
        },
        dosserImportCounter: {
          title: '<c:message code="label.statistic.col_title.dosserCounter"/>',
          width: '5%',
        },
        subdivisionsImportCounter: {
          title: '<c:message code="label.statistic.col_title.subdivisionCounter"/>',
          width: '5%',
        },
        categoryImportCounter: {
          title: '<c:message code="label.statistic.col_title.categoryCounter"/>',
          width: '5%',
        },
        infotypeImportCounter: {
          title: '<c:message code="label.statistic.col_title.infotypeCounter"/>',
          width: '5%',
        },
        activitiesImportCounter: {
          title: '<c:message code="label.statistic.col_title.activityCounter"/>',
          width: '5%',
        },
        contacttypesImportCounter: {
          title: '<c:message code="label.statistic.col_title.contactstypesCounter"/>',
          width: '5%',
        },
        hobbiImportCounter: {
          title: '<c:message code="label.statistic.col_title.hobbyCounter"/>',
          width: '5%',
        },
        relativesImportCounter: {
          title: '<c:message code="label.statistic.col_title.relativiesCounter"/>',
          width: '5%',
        },
        segmentsImportCounter: {
          title: '<c:message code="label.statistic.col_title.segmentsCounter"/>',
          width: '5%',
        },
        filetypesImportCounter: {
          title: '<c:message code="label.statistic.col_title.filyTypeCounter"/>',
          width: '5%',
        },
        importerUserName: {
          title: '<c:message code="label.statistic.col_title.user"/>',
          width: '15%',
        },
      },
      //Register to selectionChanged event to hanlde events
    });

    $('#LoadRecordsButton').click(function (e) {
      e.preventDefault();
      $('#ExpenseTableContainer').jtable('load', {
        startDate: $('#startDate').val(),
        endDate: $('#endDate').val(),
      });
    });

    $('#AllRecordsButton').click(function (e) {
      e.preventDefault();
      $('#ExpenseTableContainer').jtable('load');
    });

    $('#ExpenseTableContainer').jtable('load');

    $('#startDate').datepicker();
    $('#endDate').datepicker();

  });
</script>

<style>
  div.jtable-main-container table.jtable thead th{
    vertical-align: top;
  }
  th.jtable-column-header{
    height: 50px;
    align-content: center;
    padding-top: 0;
    margin-top: 0;
  }
</style>

<div class="filtering">
  <form>
    <c:message code="label.field.startDate"/>: <input type="text" name="startDate" id="startDate" />
    <c:message code="label.field.endDate"/>: <input type="text" name="endDate" id="endDate" />

    <button type="button" id="LoadRecordsButton" class="btn btn-default">'<c:message code="label.button.loadrecord"/>'</button>
    <button type="button" id="AllRecordsButton" class="btn btn-default">'<c:message code="label.button.allrecord"/>'</button>
  </form>
</div>
<div>
  <div id="ExpenseTableContainer" style="width:99%;"></div>
</div>
<div id="view_image_dialog"></div>