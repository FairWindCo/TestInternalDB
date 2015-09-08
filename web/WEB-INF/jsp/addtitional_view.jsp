<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 03.09.2015
  Time: 10:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="jp" uri="http://java.sun.com/jsp/jstl/core" %>

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
<script>
  $(document).ready(function() {
   //TABLES
    //TABLE contacts
    $('#contactsTableContainer').jtable({
      title: '<c:message code="label.edit.persons.contact.table_title"/>',
      selecting: true, //Enable selecting
      paging: true, //Enable paging
      pageSize: 10, //Set page size (default: 10)
      sorting: true, //Enable sorting
      useBootstrap: true,
      actions: {
        //listAction: 'datatable/getAllExpenses',
        listAction: '${pageContext.request.contextPath}/additional/contactsList?personId=<jp:out value="${person.personId}" />',
      },
      fields: {
        contactId: {
          key: true,
          list: false,
        },
        contactType: {
          title: '<c:message code="label.edit.persons.contactstable.contact_type.col_title"/>',
          list: true,
          create: false,
          edit: false,
          display: function (data) {
            if(data.record.contactType!==null && data.record.contactType!==undefined){
              return '<b>' + data.record.contactType.cobtactTypeName + '</b>';
            } else {
              return '<b>----</b>';
            }
          },
        },
        contactinfo: {
          title: '<c:message code="label.edit.persons.contactstable.contact_info.col_title"/>',
          list: true,
          create: true,
          edit: true,
        },
      }
    }).jtable('load');
    //TABLE files
    $('#filesTableContainer').jtable({
      title: '<c:message code="label.edit.persons.files.table_title"/>',
      selecting: true, //Enable selecting
      paging: true, //Enable paging
      pageSize: 10, //Set page size (default: 10)
      sorting: true, //Enable sorting
      useBootstrap: true,
      actions: {
        listAction: '${pageContext.request.contextPath}/additional/fileList?personId=<jp:out value="${person.personId}" />',
      },
      fields: {
        fileId: {
          key: true,
          list: false,
        },
        fileNameComments: {
          title: '<c:message code="label.edit.persons.files.file_comments.col_title"/>',
          width: '10%',
          edit: false,
          create: true,
        },
        fileMimeType: {
          title: '<c:message code="label.edit.persons.files.file_mimetime.col_title"/>',
          width: '10%',
          edit: false,
          create: false,
        },
        filesType: {
          title: '<c:message code="label.edit.persons.files.file_type.col_title"/>',
          width: '10%',
          edit: false,
          create: false,
          display: function (data) {
            if (data.record!== null && data.record!== undefined) {
              if(data.record.filesType !== null && data.record.filesType !== undefined) {
                return '<b>' + data.record.filesType.filesTypeName + '</b>';
              } else {
                return '<b>----</b>';
              }
            } else {
              return '<b>----</b>';
            }
          }
        },
        AddServiceToCart: {
          title: '',
          width: '1%',
          sorting: false,
          create: false,
          sort:false,
          edit: false,
          list: true,
          display: function (data) {
            if (data.record !== null && data.record !== undefined) {
              var $myVal = data.record.fileId;
              var mimeType= data.record.fileMimeType;
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
                        $(this).append('<img src="${pageContext.request.contextPath}/file/view?fileID='+$myVal+'" />');
                        //load('${pageContext.request.contextPath}/file/view?fileID=' + $myVal);
                      }

                    });

                    $('#view_image_dialog').dialog("open");
                  });
                  return $link;
                } else {
                  return '<a href="${pageContext.request.contextPath}/file/view?fileID='+$myVal+'" class ="PassServiceLink" target="_blank"><i class="fa fa-search fa-fw"></i></a>';
                }
              }else {
                return '<i class="fa fa-remove fa-fw"></i>';
              }
            } else {
              return '<i class="fa fa-remove fa-fw"></i>';
            }

          }
        }
      }
    }).jtable('load');

    //TABLE relations
    $('#relationsTableContainer').jtable({
      title: '<c:message code="label.edit.persons.relation.table_title"/>',
      selecting: true, //Enable selecting
      paging: true, //Enable paging
      pageSize: 10, //Set page size (default: 10)
      sorting: true, //Enable sorting
      useBootstrap: true,
      actions: {
        //listAction: 'datatable/getAllExpenses',
        listAction: '${pageContext.request.contextPath}/additional/relationList?personId=<jp:out value="${person.personId}" />',
      },
      fields: {
        id: {
          key: true,
          list: false,
        },
        person_name: {
          title: '<c:message code="label.edit.persons.relation.person_name.col_title"/>',
          list: true,
          create: false,
          edit: false,
          display: function (data) {
            if(data.record.contactType!==null && data.record.contactType!==undefined){
              return '<b>' + data.record.contactType.cobtactTypeName + '</b>';
            } else {
              return '<b>----</b>';
            }
          },
        },
        relatives_name: {
          title: '<c:message code="label.edit.persons.relation.relative_name.col_title"/>',
          list: true,
          create: false,
          edit: false,
          display: function (data) {
            if(data.record.contactType!==null && data.record.contactType!==undefined){
              return '<b>' + data.record.contactType.cobtactTypeName + '</b>';
            } else {
              return '<b>----</b>';
            }
          },
        },
      },
    }).jtable('load');

    $('#myTabs a').click(function (e) {
      e.preventDefault()
      $(this).tab('show')
    })
  });
</script>

<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title"><c:message code="label.edit.persons.title"/>: <jp:out value="${person.fio}" /></h3>
  </div>
  <div class="panel-body">
    <div id="#myTabs">

      <!-- Nav tabs -->
      <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#info" aria-controls="home" role="tab" data-toggle="tab"><c:message code="label.edit.persons.info_title"/></a></li>
        <li role="presentation" ><a href="#additional" aria-controls="additional" role="tab" data-toggle="tab"><c:message code="label.edit.persons.additional_title"/></a></li>
        <li role="presentation" ><a href="#photo" aria-controls="photo" role="tab" data-toggle="tab"><c:message code="label.edit.persons.photo_title"/></a></li>
        <li role="presentation" ><a href="#contacts" aria-controls="contacts" role="tab" data-toggle="tab"><c:message code="label.edit.persons.contact_title"/></a></li>
        <li role="presentation" ><a href="#files" aria-controls="files" role="tab" data-toggle="tab"><c:message code="label.edit.persons.files_title"/></a></li>
        <li role="presentation" ><a href="#relations" aria-controls="relations" role="tab" data-toggle="tab"><c:message code="label.edit.persons.relations_title"/></a></li>
      </ul>

      <!-- Tab panes -->
      <div class="tab-content">
        <div role="tabpanel" class="tab-pane active" id="info">
          <table class="table">
            </tr>
            <tr>
              <td><c:message code="label.edit.persons.passportInfo"/></td><td>
              <jp:out value="${person.passportInfo}" />
            </td>
            </tr>
          <tr>
            <td><c:message code="label.edit.persons.hobbie_ids"/></td><td>
            ${empty person.hobbie?'':person.hobbie.hobbieName}
          </td>
          </tr>
          <tr>
            <td><c:message code="label.edit.persons.hobbiesComments"/></td><td>
            <jp:out value="${person.hobbiesComments}" />
          </td>
          </tr>
            <tr>
              <td><c:message code="label.edit.persons.activities_ids"/></td><td>
              ${empty person.activities?'':person.activities.activitiesTypeName}
            </td>
            </tr>
            <tr>
              <td><c:message code="label.edit.persons.activitiesComments"/></td><td>
              <jp:out value="${person.activitiesComments}" />
            </td>
            </tr>
        </table></div>
        <div role="tabpanel" class="tab-pane" id="additional">
          <table class="table">
          <tr>
            <td><c:message code="label.edit.persons.sergments_ids"/></td><td>
            ${(empty person.additionalInfo or empty person.additionalInfo.clientSegment)?'':person.additionalInfo.clientSegment.name}
          </td>
          </tr>
          <tr>
            <td><c:message code="label.edit.persons.clientColorCode"/></td><td>
            ${empty person.additionalInfo?'':person.additionalInfo.clientColorCODE}
          </td>
          </tr>
          <tr>
            <td><c:message code="label.edit.persons.clientColorComments"/></td><td>
            ${empty person.additionalInfo?'':person.additionalInfo.clientColorComments}
          </td>
          </tr>
          </table>
        </div>
        <div role="tabpanel" class="tab-pane" id="photo">
          <table class="table">
            <tr>
              <td rowspan="3">
                <jp:if test="${not empty person.photo}">
                  <img src="${pageContext.request.contextPath}/file/view?fileID=${person.photo.fileId}" width="100" height="100" /></td>
                </jp:if>
              <td><c:message code="label.edit.persons.photo_file"/></td>
              <td><input type="file" accept="image/*"/></td>
            </tr>
            <tr>
              <td><c:message code="label.edit.persons.photo_file_comments"/></td>
              <td>${empty person.photo?'':person.photo.fileNameComments}</td>
            </tr>
            <tr>
              <td><c:message code="label.edit.persons.photo_file_type"/></td>
              <td>
                ${(empty person.photo or empty person.photo.filesType)?'':person.photo.filesType.filesTypeName}
              </td>
            </tr>
          </table>
        </div>
        <div role="tabpanel" class="tab-pane" id="contacts"><div id="contactsTableContainer" style="width:99%;"></div></div>
        <div role="tabpanel" class="tab-pane" id="files"><div id="filesTableContainer" style="width:99%;"></div></div>
        <div role="tabpanel" class="tab-pane" id="relations"><div id="relationsTableContainer" style="width:99%;"></div></div>
      </div>

    </div>
  </div>
</div>
<div id="view_image_dialog"></div>