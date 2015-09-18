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
      title: '<c:message code="label.search.table_title"/>',
      selecting: true, //Enable selecting
      paging: true, //Enable paging
      pageSize: 10, //Set page size (default: 10)
      sorting: true, //Enable sorting
      messages: <c:message code="label.messages"/>,
      actions: {
        //listAction: 'datatable/getAllExpenses',
        listAction: '${pageContext.request.contextPath}/search/search',
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
                    $('#view_image_dialog').dialog({
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

                    $('#view_image_dialog').dialog("open");
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
        searchname: $('#searchname').val(),
        searchcode: $('#searchcode').val(),
        subdivisions: $('#subdivisions').val(),
        categoryes: $('#categoryes').val(),
        infotypes: $('#infotypes').val(),
      });
    });

    $('#AllRecordsButton').click(function (e) {
      e.preventDefault();
      $('#ExpenseTableContainer').jtable('load');
    });

    $('#ExpenseTableContainer').jtable('load');

    function refreshInfo(cntInput){
      $.ajax({
        type: "POST",
        url: "${pageContext.request.contextPath}/info/subdivisionsCategory",
        data: {categoryes:cntInput},
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function OnPopulateControl(response) {
          if(response.items!==null && response.items!==undefined) {
            list = response.items;
            if (list.length > 0) {
              $("#infotypes").removeAttr("disabled");
              $("#infotypes").empty();
              $.each(list, function () {
                $("#infotypes").append($("<option></option>").val(this['Value']).html(this['DisplayText']));
              });
              //$("#subdivisions").val(valueselected);
              $("#infotypes").multiselect('refresh');
            }
          }
        },
        error: function () {
          alert("Error");
        }
      });
    }

    function refreshCategory(cntInput){
      $.ajax({
        type: "POST",
        url: "${pageContext.request.contextPath}/category/subdivisionsCategory",
        data: {subdivisions:cntInput},
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function OnPopulateControl(response) {
          if(response.items!==null && response.items!==undefined) {
            list = response.items;
            if (list.length > 0) {
              $("#categoryes").removeAttr("disabled");
              $("#categoryes").empty();
              $.each(list, function () {
                $("#categoryes").append($("<option></option>").val(this['Value']).html(this['DisplayText']));
              });
              //$("#subdivisions").val(valueselected);
              $("#categoryes").multiselect('refresh');
            }
          }
        },
        error: function () {
          alert("Error");
        }
      });
    }
    function refreshSubdivision(){
      $.ajax({
        type: "POST",
        url: "${pageContext.request.contextPath}/subdivisions/optionsList",
        data: "",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function OnPopulateControl(response) {
          if(response.Options!==null && response.Options!==undefined) {
            list = response.Options;
            if (list.length > 0) {
              $("#subdivisions").removeAttr("disabled");
              $.each(list, function () {
                $("#subdivisions").append($("<option></option>").val(this['Value']).html(this['DisplayText']));
              });
              //$("#subdivisions").val(valueselected);
              $("#subdivisions").multiselect('refresh');
            }
          }
        },
        error: function () {
          alert("Error");
        }
      });
    }

    $("#subdivisions").multiselect({selectedList: 4,
      click: function(event, ui){
        alert($('#subdivisions').val());
        var cntInput=$('#subdivisions').val();
        refreshCategory(cntInput);
      }
    }).multiselectfilter();
    $("#categoryes").multiselect({selectedList: 4,
      click: function(event, ui){
        var cntInput=$('#subdivisions').val();
        refreshInfo(cntInput);
      }}).multiselectfilter();
    $("#infotypes").multiselect({selectedList: 4}).multiselectfilter();
    refreshSubdivision();
    refreshCategory('');
    refreshInfo('');

  });

  $('#print').click( function () {
    $.ajax({
      type: "POST",
      url: "${pageContext.request.contextPath}/search/print",
      data: {
        searchname: $('#searchname').val(),
        searchcode: $('#searchcode').val(),
        subdivisions: $('#subdivisions').val(),
        categoryes: $('#categoryes').val(),
        infotypes: $('#infotypes').val(),
      },
      success: function(data){
        var win = window.open();
        win.document.write(data);
      },
      error: function(data){
        alert("Error");
      }
    })

  })
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
    <c:message code="label.clientstables.dosers.col_title.subdiv"/>:<select name="subdivisions" id="subdivisions" multiple="multiple"></select>
    <c:message code="label.clientstables.dosers.col_title.categeory"/>:<select name="categoryes" id="categoryes" multiple="multiple"></select>
    <c:message code="label.clientstables.dosers.col_title.info"/>:<select name="infotypes" id="infotypes" multiple="multiple"></select><BR><BR>
    <c:message code="label.clientstables.col_title.fio"/>: <input type="text" name="searchname" id="searchname" />
    <c:message code="label.clientstables.col_title.code"/>: <input type="text" name="searchcode" id="searchcode" />

    <button type="button" id="LoadRecordsButton">'<c:message code="label.button.loadrecord"/>'</button>
    <button type="button" id="AllRecordsButton">'<c:message code="label.button.allrecord"/>'</button>
    <button type="button" id="print">'<c:message code="label.button.print"/>'</button>
  </form>
</div>
<div>
  <div id="ExpenseTableContainer" style="width:99%;"></div>
</div>
<div id="view_image_dialog"></div>