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
    //FORM HOBIE COMBOBOX
    $("#Edit-ac_hobbie_id").ajaxComboBox('${pageContext.request.contextPath}/hobbies/listing',
            { lang: 'en',
              select_only: true,
              field: 'hobbieName',
              primary_key: 'hobbieId',
              db_table: 'nation',
              per_page: 20,
              hidden_name:'Edit-hobbie_id',
              <jp:if test="${not empty person.hobbie}">
              init_record: ['${person.hobbie.hobbieId}'],
              </jp:if>
              bind_to:'setup',
            }).bind('setup',function(){
              $(":input[name=hobbie_id]").val($(":input[name=hobbie_ids_primary_key]").val());
            });
    //FORM activities COMBOBOX
    $("#Edit-ac_activities_id").ajaxComboBox('${pageContext.request.contextPath}/activities/listing',
            { lang: 'en',
              select_only: true,
              field: 'activitiesName',
              primary_key: 'activitiesId',
              db_table: 'nation',
              per_page: 20,
              hidden_name:'Edit-activities_id',
              <jp:if test="${not empty person.activities}">
              init_record: ['${person.activities.activitiesId}'],
              </jp:if>
              bind_to:'setup',
            }).bind('setup',function(){
              $(":input[name=activities_id]").val($(":input[name=activities_ids_primary_key]").val());
            });
    //FORM Segments COMBOBOX
    $("#Edit-ac_sergments_id").ajaxComboBox('${pageContext.request.contextPath}/activities/listing',
            { lang: 'en',
              select_only: true,
              field: 'name',
              primary_key: 'sergmentsId',
              db_table: 'nation',
              per_page: 20,
              hidden_name:'Edit-sergments_id',
              <jp:if test="${not empty person.additionalInfo and not empty person.additionalInfo.clientSegment}">
                init_record: ${person.additionalInfo.clientSegment.sergmentsId},
              </jp:if>

              bind_to:'setup',
            }).bind('setup',function(){
              $(":input[name=sergments_id]").val($(":input[name=sergments_ids_primary_key]").val());
            });
    //FORM FileType COMBOBOX
    $("#Edit-ac_filetype_id").ajaxComboBox('${pageContext.request.contextPath}/filetypes/listing',
            { lang: 'en',
              select_only: true,
              field: 'filesTypeName',
              primary_key: 'filesTypeId',
              db_table: 'nation',
              per_page: 20,
              hidden_name:'Edit-filetype_id',
              <jp:if test="${not empty person.photo and not empty person.photo.filesType}">
              init_record: ['${person.photo.filesType.filesTypeId}'],
              </jp:if>
              bind_to:'setup',
            }).bind('setup',function(){
              $(":input[name=filetype_id]").val($(":input[name=filetype_ids_primary_key]").val());
            });
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
        createAction: '${pageContext.request.contextPath}/additional/addContact?personId=<jp:out value="${person.personId}" />',
        updateAction: '${pageContext.request.contextPath}/additional/updateContact?personId=<jp:out value="${person.personId}" />',
        deleteAction: '${pageContext.request.contextPath}/additional/removeContact?personId=<jp:out value="${person.personId}" />'
      },
      fields: {
        contactId: {
          key: true,
          list: false,
        },
        contactType: {
          title: 'CONTACT TYPE',
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
        contactTypeId: {
          title: 'CONTACT TYPE',
          list: false,
          create: true,
          edit: true,
          options:'${pageContext.request.contextPath}/contacttypes/options',
        },
        contactinfo: {
          title: 'CONTACT INFO',
          list: true,
          create: true,
          edit: true,
        },
      }
    }).jtable('load');
    //TABLE files
    $('#filesTableContainer').jtable({
      title: '<c:message code="label.edit.persons.contact.table_title"/>',
      selecting: true, //Enable selecting
      paging: true, //Enable paging
      pageSize: 10, //Set page size (default: 10)
      sorting: true, //Enable sorting
      useBootstrap: true,
      actions: {
        //listAction: 'datatable/getAllExpenses',
        listAction: '${pageContext.request.contextPath}/additional/fileList?personId=<jp:out value="${person.personId}" />',
        createAction: '${pageContext.request.contextPath}/additional/add?personId=<jp:out value="${person.personId}" />',
        updateAction: '${pageContext.request.contextPath}/additional/update?personId=<jp:out value="${person.personId}" />',
        deleteAction: '${pageContext.request.contextPath}/additional/delete?personId=<jp:out value="${person.personId}" />',
      },
      fields: {

      }
    });

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
        <form action="${pageContext.request.contextPath}/additional/save" enctype="multipart/form-data" encoding="multipart/form-data" method="post">
        <input type="hidden" name="personId" id="Edit-personId" value="<jp:out value="${person.personId}" />"/>

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
              <input type="text" name="passportInfo" id="Edit-passportInfo" value="<jp:out value="${person.passportInfo}" />"/>
            </td>
            </tr>
          <tr>
            <td><c:message code="label.edit.persons.hobbie_ids"/></td><td>
            <input id="Edit-ac_hobbie_id" name="hobbie_ids" type="text" />
            <input id="Edit-hobbie_id" name="hobbie_id" type="hidden" value="0" />
          </td>
          </tr>
          <tr>
            <td><c:message code="label.edit.persons.hobbiesComments"/></td><td>
            <input type="text" name="hobbiesComments" id="Edit-hobbiesComments" value="<jp:out value="${person.hobbiesComments}" />"/>
          </td>
          </tr>
            <tr>
              <td><c:message code="label.edit.persons.activities_ids"/></td><td>
              <input id="Edit-ac_activities_id" name="activities_ids" type="text" />
              <input id="Edit-activities_id" name="activities_id" type="hidden" value="0" />
            </td>
            </tr>
            <tr>
              <td><c:message code="label.edit.persons.activitiesComments"/></td><td>
              <input type="text" name="activitiesComments" id="Edit-activitiesComments" value="<jp:out value="${person.activitiesComments}" />"/>
            </td>
            </tr>
        </table></div>
        <div role="tabpanel" class="tab-pane" id="additional">
          <table class="table">
          <tr>
            <td><c:message code="label.edit.persons.sergments_ids"/></td><td>
            <input id="Edit-ac_sergments_id" name="sergments_ids" type="text" />
            <input id="Edit-sergments_id" name="sergments_id" type="hidden" value="0" />
          </td>
          </tr>
          <tr>
            <td><c:message code="label.edit.persons.clientColorCode"/></td><td>
            <input type="text" name="clientColorCode" id="Edit-clientColorCode" value="${empty person.additionalInfo?'':person.additionalInfo.clientColorCODE}"/>
          </td>
          </tr>
          <tr>
            <td><c:message code="label.edit.persons.clientColorComments"/></td><td>
            <input type="text" name="clientColorComments" id="Edit-clientColorComments" value="${empty person.additionalInfo?'':person.additionalInfo.clientColorComments}"/>
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
              <td><input type="file" /></td>
            </tr>
            <tr>
              <td><c:message code="label.edit.persons.photo_file_comments"/></td>
              <td><input type="text" value="${empty person.photo?'':person.photo.fileNameComments}"/> </td>
            </tr>
            <tr>
              <td><c:message code="label.edit.persons.photo_file_type"/></td>
              <td>
                <input id="Edit-ac_filetype_id" name="filetype_ids" type="text" />
                <input id="Edit-filetype_id" name="filetype_id" type="hidden" value="0" />
              </td>
            </tr>
          </table>
        </div>
        <div role="tabpanel" class="tab-pane" id="contacts"><div id="contactsTableContainer" style="width:99%;"></div></div>
        <div role="tabpanel" class="tab-pane" id="files"><div id="filesTableContainer" style="width:99%;"></div></div>
        <div role="tabpanel" class="tab-pane" id="relations"><div id="relationsTableContainer" style="width:99%;"></div></div>
      </div>

    </div>
          <div class="panel panel-default">
            <input type="submit">
          </div>
        </form>
  </div>
</div>