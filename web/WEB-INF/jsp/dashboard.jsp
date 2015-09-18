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

    <%-- JQuery --%>
    <%@include file="/WEB-INF/include/jquery_include.jsp" %>
    <!-- Bootstrap Core JavaScript -->
    <%@include file="/WEB-INF/include/bootstrup_include.jsp" %>
<sec:authorize ifAnyGranted="ROLE_GROUP_INF_EDIT,ROLE_SUPER_INF_EDIT,ROLE_MAIN_INF_EDIT,ROLE_GROUP_INF_VIEW,ROLE_SUPER_INF_VIEW,ROLE_MAIN_INF_VIEW">
    <%-- JQueryUI --%>
    <%@include file="/WEB-INF/include/jquery_ui_include.jsp" %>
    <%-- JTable --%>
    <%@include file="/WEB-INF/include/jtable_include.jsp" %>

    <%-- customized javascript code to manage JTable --%>
    <script>
      $(document).ready(function() {
        //setup the jtable that will display the results
        $('#ExpenseTableContainer').jtable({
          title: '<c:message code="label.search.table_title"/>',
          selecting: true, //Enable selecting
          paging: true, //Enable paging
          pageSize: 10, //Set page size (default: 10)
          sorting: true, //Enable sorting
          messages: <c:message code="label.messages"/>,
          actions: {
            //listAction: 'datatable/getAllExpenses',
            listAction: '${pageContext.request.contextPath}/search/last',
          },
          fields: {
            confidentional: {
              title: '<c:message code="label.clientstables.dosers.col_title.confidentional"/>',
              width: '10%',
              display:function(data){
                if(data!==null && data!==undefined && data.record!==null && data.record!==undefined && data.record.confidentional!=null && data.record.confidentional!==undefined && data.record.confidentional){
                  return '<i class="glyphicon glyphicon-eye-close">';
                } else {
                  return '<i class="glyphicon glyphicon-eye-open">';
                }
              }
            },
            subdivision: {
              title: '<c:message code="label.clientstables.dosers.col_title.subdiv"/>',
              width: '10%',
            },
            category: {
              title: '<c:message code="label.clientstables.dosers.col_title.categeory"/>',
              width: '10%',
            },
            infotype: {
              title: '<c:message code="label.clientstables.dosers.col_title.info"/>',
              width: '10%',
            },
            fio: {
              title: '<c:message code="label.clientstables.col_title.fio"/>',
              width: '15%',
            },
            code: {
              title: '<c:message code="label.clientstables.col_title.code"/>',
              width: '5%',
            },
            bethday: {
              title: '<c:message code="label.clientstables.col_title.bethday"/>',
              width: '5%',
            },
            printDetail: {
              title: '',
              width: '1%',
              sorting: false,
              create: false,
              edit: false,
              list: true,
              display: function (data) {
                var $myVal = data.record.personnPrintUrl;
                var $print = '<a href="${pageContext.request.contextPath}' + $myVal+'" class ="PassServiceLink" target="new"><i class="fa fa-print fa-fw"></i></a>';
                return $print;
              }
            },
            info: {
              title: '<c:message code="label.clientstables.dosers.col_title.text"/>',
              width: '25%',
            },
            fileComments: {
              title: '<c:message code="label.clientstables.dosers.col_title.filecomments"/>',
              width: '5%',
            },
            creationTime: {
              title: '<c:message code="label.clientstables.dosers.col_title.created"/>',
              width: '5%',
            },
            fileType: {
              title: '<c:message code="label.clientstables.dosers.col_title.filytype"/>',
              width: '5%',
            },
            viewFile: {
              title: '',
              width: '1%',
              sorting: false,
              create: false,
              sort:false,
              edit: false,
              list: true,
              display: function (data) {
                if (data.record !== null && data.record !== undefined) {
                  var mimeType= data.record.mime;
                  if(mimeType!== null && mimeType!==undefined){
                    if(mimeType.match(/image*/)){
                      var $link = $('<a href="#" class ="PassServiceLink"><i class="fa fa-search fa-fw"></i></a>');
                      $link.on("click",function () {
                        $('#view_image_dialog_main').dialog({
                          autoOpen: false,
                          modal: true,
                          resizable: true,
                          autoResize:true,
                          width:'auto',
                          height:'auto',
                          closeOnEscape: true,
                          open: function (event, ui) {
                            $(this).empty();
                            $(this).append('<img src="${pageContext.request.contextPath}'+data.record.fileUrl+'" />');
                          }

                        });

                        $('#view_image_dialog_main').dialog("open");
                      });
                      return $link;
                    } else {
                      return '<a href="${pageContext.request.contextPath}'+data.record.fileUrl+'" class ="PassServiceLink" target="_blank"><i class="fa fa-search fa-fw"></i></a>';
                    }
                  }else {
                    return '<i class="fa fa-remove fa-fw"></i>';
                  }
                } else {
                  return '<i class="fa fa-remove fa-fw"></i>';
                }

              }
            },
            status: {
              title: '<c:message code="label.usertables.table.col_title.enabled"/>',
              width: '5%',
            },
          },
          //Register to selectionChanged event to hanlde events
        });

        $('#LoadRecordsButton').click(function (e) {
          e.preventDefault();
          $('#ExpenseTableContainer').jtable('load', {
            maxRecordCount: $('#maxRecordCount').val(),
          });
        });

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
  </sec:authorize>


<ol class="breadcrumb">
  <li><a href="${pageContext.request.contextPath}/"><c:message code="label.main"/></a></li>
  <li class="active">Dashboard</li>
</ol>
<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title">Dashboard</h3>
  </div>
  <div class="panel-body">
    <jsp:include page="view/totality.jsp" />
    <sec:authorize ifAnyGranted="ROLE_GROUP_INF_EDIT,ROLE_SUPER_INF_EDIT,ROLE_MAIN_INF_EDIT,ROLE_GROUP_INF_VIEW,ROLE_SUPER_INF_VIEW,ROLE_MAIN_INF_VIEW">
      <div class="filtering">
        <form>
          <c:message code="label.clientstables.dosers.col_title.subdiv"/>:
          <select name="maxRecordCount" id="maxRecordCount" multiple="multiple">
            <option value="10">10</option>
            <option value="25" selected>25</option>
            <option value="50">50</option>
            <option value="100">100</option>
          </select>


          <button type="submit" id="LoadRecordsButton" class="btn btn-default">'<c:message code="label.button.loadrecord"/>'</button>
        </form>
      </div>
      <div>
        <div id="ExpenseTableContainer" style="width:99%;"></div>
      </div>
      <div id="view_image_dialog_main"></div>
    </sec:authorize>
  </div>
</div>

