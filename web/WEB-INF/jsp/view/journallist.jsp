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
      title: '<c:message code="label.journal.table_title"/>',
      selecting: true, //Enable selecting
      paging: true, //Enable paging
      pageSize: 10, //Set page size (default: 10)
      sorting: true, //Enable sorting
      messages: <c:message code="label.messages"/>,
      actions: {
        //listAction: 'datatable/getAllExpenses',
        listAction: '${pageContext.request.contextPath}/journal/list',
      },
      fields: {
        journalDate: {
          title: '<c:message code="label.statistic.col_title.date"/>',
          width: '10%',
          display: function (data) {
            var options = {
              year: 'numeric',
              month: 'long',
              day: 'numeric',
              hour:'numeric',
              minute:'numeric',
              second:'numeric',
            };
            var date=new Date(data.record.journalDate);
            return date.toLocaleString("ru", options);
          },
        },
        operation: {
          title: '<c:message code="label.journal.col_title.operation"/>',
          width: '5%',
        },
        user: {
          title: '<c:message code="label.statistic.col_title.user"/>',
          width: '15%',
        },
        object: {
          title: '<c:message code="label.journal.col_title.object"/>',
          width: '25%',
        },
        info: {
          title: '<c:message code="label.journal.col_title.info"/>',
          width: '45%',
        },
      },
      //Register to selectionChanged event to hanlde events
    });

    $('#LoadRecordsButton').click(function (e) {
      e.preventDefault();
      $('#ExpenseTableContainer').jtable('load', {
        startDate: $('#startDate').val(),
        endDate: $('#endDate').val(),
        name: $('#name').val(),
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
    <c:message code="label.field.user"/>: <input type="text" name="name" id="name" />

    <button type="submit" id="LoadRecordsButton">'<c:message code="label.button.loadrecord"/>'</button>
    <button type="submit" id="AllRecordsButton">'<c:message code="label.button.allrecord"/>'</button>
  </form>
</div>
<div>
  <div id="ExpenseTableContainer" style="width:99%;"></div>
</div>
<div id="view_image_dialog"></div>