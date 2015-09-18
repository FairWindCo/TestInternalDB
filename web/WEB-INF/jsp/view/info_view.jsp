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


<%-- JMultiSelect--%>
<%@include file="/WEB-INF/include/jmultselect_include.jsp" %>


<%-- customized javascript code to manage JTable --%>
<script>
  $(document).ready(function() {

    //setup the jtable that will display the results
    $('#ExpenseTableContainer').jtable({
      title: '<c:message code="label.infotables"/>',
      selecting: true, //Enable selecting
      paging: true, //Enable paging
      pageSize: 10, //Set page size (default: 10)
      sorting: true, //Enable sorting
      messages: <c:message code="label.messages"/>,
      actions: {
        //listAction: 'datatable/getAllExpenses',
        listAction: '${pageContext.request.contextPath}/info/listedit',
        createAction: '${pageContext.request.contextPath}/info/add',
        updateAction: '${pageContext.request.contextPath}/info/update',
        deleteAction: '${pageContext.request.contextPath}/info/delete'
      },
      fields: {
        typeId: {
          title: 'ID',
          key: true,
          list: true,
          create: false,
          edit: false,
          visibility:"hidden"
        },
        typeName: {
          title: '<c:message code="label.infotables.table.col_title.name"/>',
          width: '65%',
        },
        categoryName: {
          title: '<c:message code="label.infotables.table.col_title.category"/>',
          width: '65%',
          display: function (data) {
            if(data.record.category!==null && data.record.category!==undefined){
              return '<b>' + data.record.category.name + '</b>';
            } else {
              return '<b>----</b>';
            }
          },
          edit: false,
          create: false,
        },
        categoryId: {
          title: '<c:message code="label.infotables.table.col_title.category"/>',
          width: '30%',
          //options: 'avaibleRolesOpt?userID=' + rolesdata.record.userID,
          options: '${pageContext.request.contextPath}/info/avaibleCategoryes',
          list: false,
        },
        key1c: {
          title: '<c:message code="label.sql.table.label"/>',
          width: '25%',
          list: true,
          create: true,
          edit: true,
        },
        versionid: {
          title: '<c:message code="label.version"/>',
          defaultValue:'0',
          edit: true,
          create: false,
          width: '5%',
          input:function(data){
            return '<input name="versionid" id="Edit-versionid" value="'+data.value+'" readonly>';
          },
        },
      },
      formCreated:function(event, data){
        $('#Edit-categoryId').multiselect({
          show: "bounce",
          hide: "explode",
          multiple: false,
          selectedList: 1
        }).multiselectfilter();


      },
      //Register to selectionChanged event to hanlde events
      recordAdded: function (event, data) {
        //after record insertion, reload the records
        //$('#ExpenseTableContainer').jtable('load');
      },
      recordUpdated: function (event, data) {
        //after record updation, reload the records
        $('#ExpenseTableContainer').jtable('load');
      }
    });

    $('#LoadRecordsButton').click(function (e) {
      e.preventDefault();
      $('#ExpenseTableContainer').jtable('load', {
        searchname: $('#searchname').val()
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
    <c:message code="label.field.name"/>: <input type="text" name="searchname" id="searchname" />
    <button type="button" id="LoadRecordsButton" class="btn btn-default">'<c:message code="label.button.loadrecord"/>'</button>
    <button type="button" id="AllRecordsButton" class="btn btn-default">'<c:message code="label.button.allrecord"/>'</button>
  </form>
</div>
<div>
  <div id="ExpenseTableContainer" style="width:99%;"></div>
</div>
