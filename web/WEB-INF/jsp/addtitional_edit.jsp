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
    function getVars(url)
    {
      var formData = new FormData();
      var split;
      $.each(url.split("&"), function(key, value) {
        split = value.split("=");
        formData.append(split[0], decodeURIComponent(split[1].replace(/\+/g, " ")));
      });

      return formData;
    }

// Variable to store your files
    var files;

    $( document ).delegate('#input-image','change', prepareUpload);

// Grab the files and set them to our variable
    function prepareUpload(event)
    {
      files = event.target.files;
    }


    //FORM HOBIE COMBOBOX
    $("#Edit-ac_hobbie_id").ajaxComboBox('${pageContext.request.contextPath}/hobbies/listing',
            { lang: 'en',
              select_only: true,
              field: 'hobbieName',
              primary_key: 'hobbieId',
              db_table: 'nation',
              per_page: 20,
              //hidden_name:'Edit-hobbie_id',
              <jp:if test="${not empty person.hobbie}">
              init_record: ['${person.hobbie.hobbieId}'],
              </jp:if>
            });
    //FORM activities COMBOBOX
    $("#Edit-ac_activities_id").ajaxComboBox('${pageContext.request.contextPath}/activities/listing',
            { lang: 'en',
              select_only: true,
              field: 'activitiesTypeName',
              primary_key: 'activitiesTypeId',
              db_table: 'nation',
              per_page: 20,
              //hidden_name:'Edit-activities_id',
              <jp:if test="${not empty person.activities}">
              init_record: ['${person.activities.activitiesTypeId}'],
              </jp:if>
            });
    //FORM Segments COMBOBOX
    $("#Edit-ac_sergments_id").ajaxComboBox('${pageContext.request.contextPath}/segments/listing',
            { lang: 'en',
              select_only: true,
              field: 'name',
              primary_key: 'sergmentsId',
              db_table: 'nation',
              per_page: 20,
              //hidden_name:'Edit-sergments_id',
              <jp:if test="${not empty person.additionalInfo and not empty person.additionalInfo.clientSegment}">
                init_record: [${person.additionalInfo.clientSegment.sergmentsId}],
              </jp:if>
            });
    //FORM FileType COMBOBOX
    $("#Edit-ac_filetype_id").ajaxComboBox('${pageContext.request.contextPath}/filetypes/listing',
            { lang: 'en',
              select_only: true,
              field: 'filesTypeName',
              primary_key: 'filesTypeId',
              db_table: 'nation',
              per_page: 20,
              //hidden_name:'Edit-filetype_id',
              <jp:if test="${not empty person.photo and not empty person.photo.filesType}">
              init_record: ['${person.photo.filesType.filesTypeId}'],
              </jp:if>
            });
    //=======================================================================================================================================================================
    //TABLES
    //TABLE contacts
    $('#contactsTableContainer').jtable({
      title: '<c:message code="label.edit.persons.contact.table_title"/>',
      selecting: true, //Enable selecting
      paging: true, //Enable paging
      pageSize: 10, //Set page size (default: 10)
      sorting: true, //Enable sorting
      useBootstrap: true,
      messages: <c:message code="label.messages"/>,
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
        contactTypeId: {
          title: '<c:message code="label.edit.persons.contactstable.contact_type.col_title"/>',
          list: false,
          create: true,
          edit: true,
          options:'${pageContext.request.contextPath}/contacttypes/options',
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
      messages: <c:message code="label.messages"/>,
      actions: {
        //listAction: 'datatable/getAllExpenses',
        listAction: '${pageContext.request.contextPath}/additional/fileList?personId=<jp:out value="${person.personId}" />',
        //createAction: '${pageContext.request.contextPath}/additional/add?personId=<jp:out value="${person.personId}" />',
        deleteAction: '${pageContext.request.contextPath}/additional/removeFile?personId=<jp:out value="${person.personId}" />',
        createAction: function (postData) {
          var formData = getVars(postData);

          if($('#input-image').val() !== ""){
            formData.append("file", $('#input-image').get(0).files[0]);
          }

          return $.Deferred(function ($dfd) {
            $.ajax({
              url: '${pageContext.request.contextPath}/additional/addFile?personId=<jp:out value="${person.personId}" />',
              type: 'POST',
              dataType: 'json',
              data: formData,
              processData: false, // Don't process the files
              contentType: false, // Set content type to false as jQuery will tell the server its a query string request
              success: function (data) {
                $dfd.resolve(data);
                $('#table-container').jtable('load');
              },
              error: function () {
                $dfd.reject();
              }
            });
          });
        },
      },
      fields: {
        fileId: {
          key: true,
          list: false,
        },
        fileNameComments: {
          title: '<c:message code="label.edit.persons.files.file_comments.col_title"/>',
          type:'textarea',
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
        filesTypeId: {
          title: '<c:message code="label.edit.persons.files.file_type.col_title"/>',
          width: '30%',
          options: '${pageContext.request.contextPath}/filetypes/options',
          list: false,
          create: true,
        },
        "FileUpload": {
          title: '<c:message code="label.edit.persons.files.file.col_title"/>',
          width: '10%',
          //type:'date',
          input: function (data) {
            if (data.record) {
              return '<input type="file" name="userfile" id="input-image"><iframe name="postiframe" id="postiframe" style="display: none" />';
            } else {
              return '<input type="file" name="userfile" id="input-image"><iframe name="postiframe" id="postiframe" style="display: none" />';
            }
          },
          list: false,
          edit: true,
          create: true,
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
      messages: <c:message code="label.messages"/>,
      actions: {
        //listAction: 'datatable/getAllExpenses',
        listAction: '${pageContext.request.contextPath}/additional/relationList?personId=<jp:out value="${person.personId}" />',
        createAction: '${pageContext.request.contextPath}/additional/addRelation?edit_personId=<jp:out value="${person.personId}" />',
        updateAction: '${pageContext.request.contextPath}/additional/updateRelation?edit_personId=<jp:out value="${person.personId}" />',
        deleteAction: '${pageContext.request.contextPath}/additional/removeRelation?edit_personId=<jp:out value="${person.personId}" />'
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
            if(data.record.person!==null && data.record.person!==undefined){
              return '<b>' + data.record.person.fio + '</b>';
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
            if(data.record.relatives!==null && data.record.relatives!==undefined){
              return '<b>' + data.record.relatives.name + '</b>';
            } else {
              return '<b>----</b>';
            }
          },
        },
        /*relatives_id: {
          title: 'RELATIVES',
          list: false,
          create: true,
          edit: true,
          options:'${pageContext.request.contextPath}/relatives/options',
        },/**/
        relatives_id: {
          title: '<c:message code="label.edit.persons.relation.relative_name.col_title"/>',
          list: false,
          create: true,
          edit: true,
          input:function(data){
            var relationId=null;
            if(data!==null && data!==undefined && data.record!==null && data.record!==undefined && data.record.relatives!==null && data.record.relatives!==undefined){
              relationId=data.record.relatives.relativiesId;
            }
            var div=$('<div style="width:450;height: 200;"></div>');
            var input1=$('<input id="ac01_relativiesId" name="relativiesId" type="text" style="width:250;"/>');
            $(div).append(input1);
            var input2=$(input1).ajaxComboBox('${pageContext.request.contextPath}/relatives/listing',
                    { lang: 'en',
                      select_only: true,
                      field: 'name',
                      primary_key: 'relativiesId',
                      db_table: 'nation',
                      init_record:[relationId],
                      order_by: [
                        'fio DESC'
                      ],
                      per_page: 20,
                      //hidden_name:'relativiesId',
                    });

            return div;
          },
        },
        persons_id: {
          title: '<c:message code="label.edit.persons.relation.person_name.col_title"/>',
          list: false,
          create: true,
          edit: true,
          input:function(data){
            var personId=null;
            if(data!==null && data!==undefined && data.record!==null && data.record!==undefined && data.record.person!==null && data.record.person!==undefined){
              personId=data.record.person.personId;
            }
            var div=$('<div style="width:450;height: 200;"></div>');
            var input1=$('<input id="ac01_personId_id" name="personId" type="text" style="width:250;"/>');
            $(div).append(input1);
            var input2=$(input1).ajaxComboBox('${pageContext.request.contextPath}/person/listing',
                    { lang: 'en',
                      select_only: true,
                      field: 'fio',
                      primary_key: 'personId',
                      db_table: 'nation',
                      order_by: [
                        'fio DESC'
                      ],
                      init_record:[personId],
                      sub_info: true,
                      sub_as: {
                        code: 'CODE:',
                        dateberthdey: 'BITHDAY:',
                      },
                      per_page: 20,
                      //hidden_name:'personId',
                    });

            return div;
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
        <form action="${pageContext.request.contextPath}/additional/save" enctype="multipart/form-data" encoding="multipart/form-data" method="post">
        <input type="hidden" name="personId" id="Edit-personId" value="<jp:out value="${person.personId}" />"/>
          <input type="hidden" name="person_version" id="Edit-person_version" value="<jp:out value="${person.version}" />"/>
          <input type="hidden" name="additional_version" id="Edit-additional_version" value="<jp:out value="${(person.additionalInfo!=null?person.additionalInfo.version:null)}" />"/>

    <div id="#myTabs">

      <!-- Nav tabs -->
      <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#info" aria-controls="home" role="tab" data-toggle="tab" style="color: #0E84EA;"><c:message code="label.edit.persons.info_title"/></a></li>
        <li role="presentation" ><a href="#additional" aria-controls="additional" role="tab" data-toggle="tab" style="color: #0E84EA;"><c:message code="label.edit.persons.additional_title"/></a></li>
        <li role="presentation" ><a href="#photo" aria-controls="photo" role="tab" data-toggle="tab" style="color: #0E84EA;"><c:message code="label.edit.persons.photo_title"/></a></li>
        <li role="presentation" ><a href="#contacts" aria-controls="contacts" role="tab" data-toggle="tab" style="color: #0E84EA;"><c:message code="label.edit.persons.contact_title"/></a></li>
        <li role="presentation" ><a href="#files" aria-controls="files" role="tab" data-toggle="tab" style="color: #0E84EA;"><c:message code="label.edit.persons.files_title"/></a></li>
        <li role="presentation" ><a href="#relations" aria-controls="relations" role="tab" data-toggle="tab" style="color: #0E84EA;"><c:message code="label.edit.persons.relations_title"/></a></li>
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
              <td><input type="file" accept="image/*"/></td>
            </tr>
            <tr>
              <td><c:message code="label.edit.persons.photo_file_comments"/></td>
              <td><input type="text" value="${empty person.photo?'':person.photo.fileNameComments}"/> </td>
            </tr>
            <tr>
              <td><c:message code="label.edit.persons.photo_file_type"/></td>
              <td>
                <input id="Edit-ac_filetype_id" name="filetype_ids" type="text" />
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
            <input type="submit" value="<c:message code="label.edit.persons.submit"/>">
          </div>
        </form>
  </div>
</div>
<div id="view_image_dialog"></div>