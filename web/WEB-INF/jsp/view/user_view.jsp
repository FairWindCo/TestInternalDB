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

<%-- JCombobox --%>
<%@include file="/WEB-INF/include/jcombo_include.jsp" %>

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
      actions: {
        //listAction: 'datatable/getAllExpenses',
        listAction: '${pageContext.request.contextPath}/users/listedit',
        createAction: '${pageContext.request.contextPath}/users/adduser',
        updateAction: '${pageContext.request.contextPath}/users/updateuser',
        deleteAction: '${pageContext.request.contextPath}/users/deleteuser'
      },
      fields: {
        userID: {
          title: 'ID',
          key: true,
          list: true,
          create: false,
          edit: false,
          visibility:"hidden"
        },
        userName: {
          title: 'LOGIN NAME',
          width: '15%',
        },
        passwordHash: {
          title: 'PASSWORD',
          width: '15%',
          type: 'password',
        },
        fio: {
          title: 'F.I.O.',
          width: '15%',
        },
        enabled: {
          title: 'ENABLED',
          width: '5%',

          type: 'checkbox',
          values: { 'false': 'Passive', 'true': 'Active' },
          defaultValue: 'true'
        },
        mainsubdivisions_id: {
          title: 'Main Subdivisions',
          width: '10%',
          options: '${pageContext.request.contextPath}/users/subdivOpt',
          display: function (data) {
            if(data.record.mainsubdivisions!==null && data.record.mainsubdivisions!==undefined){
              return '<b>' + data.record.mainsubdivisions.name + '</b>';
            } else {
              return '<b>----</b>';
            }
          },
          input:function(data){
            return '<input id="ac01_mainsubdivisions_id" name="mainsubdivisions_ids" type="text" /><input id="mainsubdivisions_id" name="mainsubdivisions_id" type="hidden" value="0" />';
          },
        },
        userRoles:{
          title: 'ROLES',
          width: '2%',
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
                        title: rolesdata.record.userName + ' - user roles',
                        actions: {
                          listAction: '${pageContext.request.contextPath}/users/roles?userID=' + rolesdata.record.userID,
                          deleteAction: '${pageContext.request.contextPath}/users/removeuserrole?userId=' + rolesdata.record.userID,
                          createAction: '${pageContext.request.contextPath}/users/adduserrole?userId=' + rolesdata.record.userID
                        },
                        fields: {
                          roleId: {
                            key: true,
                            create: false,
                            edit: false,
                            list: false
                          },
                          newRoleID: {
                            title: 'ROLE NAME',
                            width: '30%',
                            //options: 'avaibleRolesOpt?userID=' + rolesdata.record.userID,
                            options: function(data) {
                              data.clearCache();
                              return '${pageContext.request.contextPath}/users/avaibleRolesOpt?userID=' + rolesdata.record.userID;
                            },
                            list: false
                          },
                          roleName: {
                            title: 'ROLE NAME',
                            width: '10%',
                            edit: false,
                            create: false,
                          },
                          roleDescription: {
                            title: 'DESCRIPTION',
                            width: '30%',
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
        trustedSubdivisions:{
          title: 'TRUSTED SUBDIVISIONS',
          width: '12%',
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
                        title:'SUBDIVISIONS TRUSTED TO USER '+ rolesdata.record.userName,
                        actions: {
                          listAction: '${pageContext.request.contextPath}/users/avaibleGrantedSubdiv?userID=' + rolesdata.record.userID,
                          deleteAction: '${pageContext.request.contextPath}/users/removegrantedsubdivision?userId=' + rolesdata.record.userID,
                          createAction: '${pageContext.request.contextPath}/users/addgrantedsubdivision?userId=' + rolesdata.record.userID
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
                              return '${pageContext.request.contextPath}/users/avaibleGrantedSubdivOpt?userID=' + rolesdata.record.userID;
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
        versionId: {
          title: '<c:message code="label.version"/>',
          defaultValue:'0',
          edit: true,
          create: false,
          width: '5%',
          input:function(data){
            return '<input name="versionId" id="Edit-versionId" value="'+data.value+'" readonly>';
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
      },
      formCreated:function(){
        $('#ac01_mainsubdivisions_id').ajaxComboBox('${pageContext.request.contextPath}/subdivisions/listing',
                { lang: 'en',
                  select_only: true,
                  field: 'name',
                  primary_key: 'subdivisionId',
                  order_by: [
                    'name DESC'
                  ],
                  per_page: 20,
                  hidden_name:'mainsubdivisions_id',
                  bind_to:'setup',
                }).bind('setup',function(){
                  $(":input[name=mainsubdivisions_id]").val($(":input[name=mainsubdivisions_ids_primary_key]").val());
                  //alert($(this).val() + ' is selected.');
                });
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
