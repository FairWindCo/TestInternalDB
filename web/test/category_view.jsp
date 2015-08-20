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

<%-- JCombobox --%>
<%@include file="/WEB-INF/include/jselectivity_include.jsp" %>


<%-- JCombobox --%>
<%@include file="/WEB-INF/include/jselect2_include.jsp" %>

<%-- JMultiSelect
<%@include file="/WEB-INF/include/jmultselect_include.jsp" %>
<%@include file="/WEB-INF/include/bmultselect_include.jsp" %>
<%@include file="/WEB-INF/include/jmultselect_include.jsp" %>
<%@include file="/WEB-INF/include/bootstrup_include.jsp" %>
--%>
<%@include file="/WEB-INF/include/bootstrup_include.jsp" %>
<%@include file="/WEB-INF/include/msldlist_include.jsp" %>


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
        /*
        "subdivs[multiple]": {
          title: 'SUBDIVISIONS:',
          width: '10%',
          edit: false,
          create: true,
          list: false,
          input:function(data){
            return '<div class="ui-widget"><select id="subdivisions-input33" class="selectivity-input" name="subdivs[multiple]" multiple ></select></div>';
          },
        },
        */

        subdivsId2: {
          title: 'SUBDIVISIONS NAME',
          width: '30%',
          //options: 'avaibleRolesOpt?userID=' + rolesdata.record.userID,
          options: function(data) {
            data.clearCache();
            return '${pageContext.request.contextPath}/category/subdivOpt';
          },
          list: false,
          edit: false
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
      formCreated:function(event, data){
        data.form.find('select[name=subdivsId2]').attr('size','10');
        data.form.find('select[name=subdivsId2]').attr('multiple','multiple');
        data.form.find('select[name=subdivsId2]').attr('name','subdivsId2[]');



        var countrylist = new MSDList("Edit-subdivsId2");
        countrylist.render();
        /*

         $('#Edit-subdivsId2').multiselect({
         includeSelectAllOption: true,
         enableFiltering: true
         });

         var parent=data.form.parent().parent();
        $('#Edit-subdivsId2').multiselect({
          show: "bounce",
          hide: "explode",
          selectedList: 5, // 0-based index,
          appendTo : parent,
        }).multiselectfilter();


        /*$('#subdivisions-input33').selectivity({
          ajax: {
            url: '${pageContext.request.contextPath}/category/subdivListOpt',
            dataType: 'json',
            //minimumInputLength: 3,
            //quietMillis: 250,
            allowClear: true,
            multiple: true,
            //dropdownCssClass:'ui-dialog ui-widget',
            //inputType:'Multiple',

            params: function(term, offset) {
              // GitHub uses 1-based pages with 30 results, by default
              var page = 1 + Math.floor(offset / 30);
              return { q: term, page: page };
            },
            processItem: function(item) {
              return {
                id: item.Value,
                text: item.DisplayText,
                description: null
              };
            },
            results: function(data, offset) {
              return {
                results: data.items,
                more: (offset + data.items.length > data.total_count)
              };
            }
          },
          placeholder: 'Search for a repository',
        });

        /*
        //data.form.find('select[name=subdivsId2]').attr('size','10');
        data.form.find('select[name=subdivsId2]').attr('multiple','multiple');
        data.form.find('select[name=subdivsId2]').attr('name','subdivsId2[]');

        $('#Edit-subdivsId2').selectivity({
          quietMillis: 250,
          allowClear: true,
          multiple: true,
          inputType:'Multiple',
        });
        */

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
