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
<sec:authentication var="user" property="principal" />
<html>
  <head>
    <title></title>
    <%-- JQuery --%>
    <%@include file="/WEB-INF/include/jquery_include.jsp" %>
    <!-- Bootstrap Core JavaScript -->
    <%@include file="/WEB-INF/include/bootstrup_include.jsp" %>
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

        $('#AllRecordsButton').click(function (e) {
          e.preventDefault();
          $('#ExpenseTableContainer').jtable('load');
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
    </head>
  <body>
  <div id="wrapper">

    <!-- Navigation -->
    <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
          <span class="sr-only">Toggle navigation</span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="index.html">Internal DB Admin v0.1</a>
      </div>
      <!-- /.navbar-header -->
      <ul class="nav navbar-top-links navbar-right">
      <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
          <i class="fa fa-user fa-fw"></i>
          Hi, ${user.username}
          <i class="fa fa-caret-down"></i>
        </a>

        <ul class="dropdown-menu dropdown-user">
          <li><a href="#"><i class="fa fa-user fa-fw"></i> User Profile</a>
          </li>
          <li><a href="#"><i class="fa fa-gear fa-fw"></i> Settings</a>
          </li>
          <li class="divider"></li>
          <li><a href="logout"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
          </li>
        </ul>
        <!-- /.dropdown-user -->
      </li>
      </ul>

      <div class="navbar-default sidebar" role="navigation">
        <div class="sidebar-nav navbar-collapse">
          <ul class="nav" id="side-menu">
            <li>
              <a href="index.jsp"><i class="fa fa-dashboard fa-fw"></i> Dashboard</a>
            </li>
            <li>
              <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i><c:message code="label.statistic"/><span class="fa arrow"></span></a>
              <ul class="nav nav-second-level">
                <li>
                  <a href="stats/" onclick="$('#page-wrapper').load('stats/'); return false;"><c:message code="label.statistic"/></a>
                </li>
                <li>
                  <a href="flot.html"><c:message code="label.statistic.buble"/></a>
                </li>
                <li>
                  <a href="morris.html"><c:message code="label.statistic.plot"/></a>
                </li>
              </ul>
              <!-- /.nav-second-level -->
            </li>
            <sec:authorize ifAnyGranted="ROLE_GROUP_INF_EDIT,ROLE_SUPER_INF_EDIT,ROLE_MAIN_INF_EDIT,ROLE_GROUP_INF_VIEW,ROLE_SUPER_INF_VIEW,ROLE_MAIN_INF_VIEW">
            <li>
              <a href="search/" onclick="$('#page-wrapper').load('search/'); return false;"><i class="fa fa-search fa-fw"></i><c:message code="label.search"/></a>
            </li>
            </sec:authorize>
              <a href="#"><i class="fa fa-inbox fa-fw"></i><c:message code="label.persons"/><span class="fa arrow"></span></a>
              <ul class="nav nav-second-level">
                <li>
                  <a href="person/" onclick="$('#page-wrapper').load('person/'); return false;"><c:message code="label.persons.clients"/></a>
                </li>
                <li>
                  <a href="person/worker" onclick="$('#page-wrapper').load('person/worker'); return false;"><c:message code="label.persons.workers"/></a>
                </li>
              </ul>
              <!-- /.nav-second-level -->
            </li>
            <sec:authorize ifAnyGranted="ROLE_ADMIN">
            <li>
              <a href=""><i class="fa fa-wrench fa-fw"></i><c:message code="label.administrate"/><span class="fa arrow"></span></a>
            <ul class="nav nav-second-level">
              <li>
                <a href="journal/" onclick="$('#page-wrapper').load('journal/'); return false;"><c:message code="label.journal"/></a>
              </li>
              <li>
                <a href="users/" onclick="$('#page-wrapper').load('users/'); return false;"><c:message code="label.administrate.users"/></a>
              </li>
              <li>
                <a href="subdivisions/" onclick="$('#page-wrapper').load('subdivisions/'); return false;"><c:message code="label.administrate.subdivisions"/></a>
              </li>
            </ul>
            </li>
            </sec:authorize>
            <sec:authorize ifAnyGranted="ROLE_GLOBAL_INFO_EDIT">
            <li>
              <a href=""><i class="fa fa-sitemap fa-fw"></i><c:message code="label.menu.globaldirectory"/><span class="fa arrow"></span></a>
              <ul class="nav nav-second-level">
                <li>
                  <a href="filetypes/" onclick="$('#page-wrapper').load('filetypes/'); return false;"><c:message code="label.menu.globaldirectory.filetype"/></a>
                </li>
                <li>
                  <a href="activities/" onclick="$('#page-wrapper').load('activities/'); return false;"><c:message code="label.menu.globaldirectory.activity"/></a>
                </li>
                <li>
                  <a href="contacttypes/" onclick="$('#page-wrapper').load('contacttypes/'); return false;"><c:message code="label.menu.globaldirectory.contacttype"/></a>
                </li>
                <li>
                  <a href="hobbies/" onclick="$('#page-wrapper').load('hobbies/'); return false;"><c:message code="label.menu.globaldirectory.hobbi"/></a>
                </li>
                <li>
                  <a href="relatives/" onclick="$('#page-wrapper').load('relatives/'); return false;"><c:message code="label.menu.globaldirectory.relative"/></a>
                </li>
                <li>
                  <a href="segments/" onclick="$('#page-wrapper').load('segments/'); return false;"><c:message code="label.menu.globaldirectory.segments"/></a>
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
                  <a href="category/" onclick="$('#page-wrapper').load('category/'); return false;"><c:message code="label.direcotry.category"/></a>
                </li>
                <li>
                  <a href="info/" onclick="$('#page-wrapper').load('info/'); return false;"><c:message code="label.direcotry.info"/></a>
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
      </nav>

    <div id="page-wrapper">
      <div class="row">
        <div class="col-lg-12">
          <h1 class="page-header">Dashboard</h1>
        </div>
        <div class="filtering">
          <form>
            <c:message code="label.clientstables.dosers.col_title.subdiv"/>:
            <select name="maxRecordCount" id="maxRecordCount" multiple="multiple">
              <option value="10">10</option>
              <option value="25" selected>25</option>
              <option value="50">50</option>
              <option value="100">100</option>
            </select>


            <button type="submit" id="LoadRecordsButton">'<c:message code="label.button.loadrecord"/>'</button>
            <button type="submit" id="AllRecordsButton">'<c:message code="label.button.allrecord"/>'</button>
          </form>
        </div>
        <div>
          <div id="ExpenseTableContainer" style="width:99%;"></div>
        </div>
        <div id="view_image_dialog_main"></div>
      </div>
    </div>
  </div>
  </body>
</html>
