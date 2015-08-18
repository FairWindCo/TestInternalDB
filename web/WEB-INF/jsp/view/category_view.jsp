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
<%@include file="/WEB-INF/include/jquery_include.jsp" %>

<%-- JTable --%>
<%@include file="/WEB-INF/include/jtable_include.jsp" %>

<%-- JCombobox --%>
<%@include file="/WEB-INF/include/jcombo_include.jsp" %>

<%-- customized javascript code to manage JTable --%>
<script>
  $(document).ready(function() {
    //setup the jtable that will display the results
    $('#ExpenseTableContainer').jtable({
      title: '<c:message code="label.categorytables"/>',
      selecting: true, //Enable selecting
      paging: true, //Enable paging
      pageSize: 10, //Set page size (default: 10)
      sorting: true, //Enable sorting
      actions: {
        //listAction: 'datatable/getAllExpenses',
        listAction: '${pageContext.request.contextPath}/category/listedit',
        createAction: '${pageContext.request.contextPath}/category/add',
        updateAction: '${pageContext.request.contextPath}/category/update',
        deleteAction: '${pageContext.request.contextPath}/category/delete'
      },
      fields: {
        categoryId: {
          title: 'ID',
          key: true,
          list: true,
          create: false,
          edit: false,
          visibility:"hidden"
        },
        name: {
          title: 'CATEGORY NAME',
          width: '65%',
        },
        selsubdivisions:{
          title: 'SUBDIVISIONS',
          width: '10%',
          sorting: false,
          edit: false,
          create: false,
          display: function (rolesdata) {
            //Create an image that will be used to open child table
            var $img = $('<img src="<c:url value="/images/list_metro.png" />" title="Edit phone numbers" >');
            //Open child table when user clicks the image
            $img.click(function () {
              $('#ExpenseTableContainer').jtable('openChildTable',
                      $img.closest('tr'),
                      {
                        title:'SUBDIVISIONS ASSIGNED TO CATEGORY '+ rolesdata.record.name,
                        actions: {
                          listAction: '${pageContext.request.contextPath}/category/avaibleSubdiv?categoryId=' + rolesdata.record.categoryId,
                          deleteAction: '${pageContext.request.contextPath}/category/removesubdivision?categoryId=' + rolesdata.record.categoryId,
                          createAction: '${pageContext.request.contextPath}/category/addsubdivision?categoryId=' + rolesdata.record.categoryId
                        },
                        fields: {
                          subdivisionId: {
                            key: true,
                            create: false,
                            edit: false,
                            list: false
                          },
                          subdivId: {
                            title: 'SUBDIVISIONS NAME',
                            width: '30%',
                            //options: 'avaibleRolesOpt?userID=' + rolesdata.record.userID,
                            options: function(data) {
                              data.clearCache();
                              return '${pageContext.request.contextPath}/category/avaibleSubdivOpt?categoryId=' + rolesdata.record.categoryId;
                            },
                            list: false
                          },
                          name: {
                            title: 'SUBDIVISIONS NAME',
                            width: '10%',
                            edit: false,
                            create: false,
                          },
                        }
                      }, function (data) { //opened handler
                        data.childTable.jtable('load');
                      });
            });
            return $img;
          },
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
    Name: <input type="text" name="searchname" id="searchname" />
    <button type="submit" id="LoadRecordsButton">Load records</button>
    <button type="submit" id="AllRecordsButton">All records</button>
  </form>
</div>
<div>
  <div id="ExpenseTableContainer" style="width:99%;"></div>
</div>
