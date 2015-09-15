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
<%-- JCombobox --%>
<%@include file="/WEB-INF/include/jcombo_include.jsp" %>

<%-- customized javascript code to manage JTable --%>
<script>
  $(document).ready(function() {
    // Read a page's GET URL variables and return them as an associative array.
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

    //setup the jtable that will display the results
    $('#ExpenseTableContainer').jtable({
      title: '<c:message code="label.clientstables"/>',
      selecting: true, //Enable selecting
      paging: true, //Enable paging
      pageSize: 10, //Set page size (default: 10)
      sorting: true, //Enable sorting
      useBootstrap: true,
      actions: {
        //listAction: 'datatable/getAllExpenses',
        listAction: '${pageContext.request.contextPath}/person/listClients',
        createAction: '${pageContext.request.contextPath}/person/add',
        updateAction: '${pageContext.request.contextPath}/person/update',
        deleteAction: '${pageContext.request.contextPath}/person/delete'
      },
      fields: {
        personId: {
          title: 'ID',
          key: true,
          list: true,
          create: false,
          edit: false,
          visibility:"hidden"
        },
        fio: {
          title: 'F.I.O.',
          width: '15%',
        },
        code: {
          title: 'CODE',
          width: '10%',
        },
        bethday: {
          title: 'bethday',
          width: '10%',
        },
        personStatus: {
          title: 'status',
          width: '10%',
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

        dossers:{
          title: 'dossers',
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
                        title:'DOSSERS FOR CLIENT: '+ rolesdata.record.fio,
                        paging: true, //Enable paging
                        pageSize: 10, //Set page size (default: 10)
                        sorting: true, //Enable sorting
                        useBootstrap: true,
                        actions: {
                          <sec:authorize ifAnyGranted="ROLE_GROUP_EDIT,ROLE_SUPER_EDIT,ROLE_MAIN_EDIT">


                          //deleteAction: '${pageContext.request.contextPath}/dossers/remove',
                          deleteAction: '${pageContext.request.contextPath}/dossers/delete',
                          //createAction: '${pageContext.request.contextPath}/dossers/add?personId=' + rolesdata.record.personId,
                          createAction: '${pageContext.request.contextPath}/dossers/add',
                          /*createAction: function (postData) {
                           console.log("creating from custom function...");
                           var deferred = $.Deferred();

                           // Capture form submit result from the hidden iframe
                           $("#postiframe").load(function () {
                           iframeContents = $("#postiframe").contents().find("body").text();
                           var result = $.parseJSON(iframeContents);
                           deferred.resolve(result);
                           });

                           // Submit form with file upload settings
                           var form = $('#jtable-create-form');
                           form.unbind("submit");
                           console.log(form.attr("action"));
                            form.attr("action", "${pageContext.request.contextPath}/dossers/add?personId=' + rolesdata.record.personId");
                           form.attr("method", "post");
                           form.attr("enctype", "multipart/form-data");
                           form.attr("encoding", "multipart/form-data");
                           //form.attr("target", "postiframe");
                           form.submit();

                           return $.Deferred(function ($dfd) {
                           $.ajax({
                           url: '${pageContext.request.contextPath}/dossers/add?personId=' + rolesdata.record.personId,
                           type: 'POST',
                           dataType: 'json',
                           data: postData,
                           success: function (data) {
                           $dfd.resolve(data);
                           },
                           error: function () {
                           $dfd.reject();
                           }
                           });
                           });
                           },/**/
                          /*createAction: function (postData) {
                           console.log("creating from custom function...");
                           var deferred = $.Deferred();

                            // Capture form submit result from the hidden iframe
                            $("#postiframe").load(function () {
                              iframeContents = $("#postiframe").contents().find("body").text();
                              var result = $.parseJSON(iframeContents);
                              deferred.resolve(result);
                            });

                           // Submit form with file upload settings
                           var form = $('#jtable-create-form');
                           form.unbind("submit");
                           form.attr("action", "${pageContext.request.contextPath}/dossers/add");
                           form.attr("method", "post");
                           form.attr("enctype", "multipart/form-data");
                           form.attr("encoding", "multipart/form-data");
                           form.attr("target", "postiframe");
                           form.submit();
                           return deferred;
                           },/**/
                          createAction: function (postData) {
                            var formData = getVars(postData);

                            if($('#input-image').val() !== ""){
                              formData.append("file", $('#input-image').get(0).files[0]);
                            }

                            return $.Deferred(function ($dfd) {
                              $.ajax({
                                url: '${pageContext.request.contextPath}/dossers/add?personId=' + rolesdata.record.personId,
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
                          updateAction: function (postData) {
                            var formData = getVars(postData);

                            if($('#input-image').val() !== ""){
                              formData.append("file", $('#input-image').get(0).files[0]);
                            }

                            return $.Deferred(function ($dfd) {
                              $.ajax({
                                url: '${pageContext.request.contextPath}/dossers/update',
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
                          </sec:authorize>
                          listAction: '${pageContext.request.contextPath}/dossers/listClientDossers?personId=' + rolesdata.record.personId
                        },
                        fields: {
                          dossierId: {
                            key: true,
                            create: false,
                            edit: false,
                            list: false
                          },
                          <sec:authorize ifAnyGranted="ROLE_GROUP_EDIT,ROLE_SUPER_EDIT,ROLE_MAIN_EDIT">
                          personId: {
                            create: true,
                            edit: true,
                            list: false,
                            defaultValue:rolesdata.record.personId,
                            type:'hidden'
                          },
                          subdivId: {
                            title: 'SUBDIVISIONS NAME',
                            width: '30%',
                            options: '${pageContext.request.contextPath}/subdivisions/optionsList',
                            list: false
                          },
                          categoryId: {
                            title: 'CATEGORY NAME',
                            width: '30%',
                            dependsOn:'subdivId',
                            //options: 'avaibleRolesOpt?userID=' + rolesdata.record.userID,
                            options: function(data) {
                              data.clearCache();
                              if(data.dependedValues.subdivId!==null && data.dependedValues.subdivId!==undefined){
                              return '${pageContext.request.contextPath}/category/subdivisionsCategories?subdivisionId='+data.dependedValues.subdivId;
                              } else {
                                return ['----'];
                              }
                            },
                            list: false
                          },
                          infoTypeId: {
                            title: 'INFOTYPE NAME',
                            width: '30%',
                            dependsOn:'categoryId',
                            //options: 'avaibleRolesOpt?userID=' + rolesdata.record.userID,
                            options: function(data) {
                              data.clearCache();
                              if(data.dependedValues.categoryId!==null && data.dependedValues.categoryId!==undefined){
                              return '${pageContext.request.contextPath}/info/avaibleInfoTypes?categoryId='+data.dependedValues.categoryId;
                              } else {
                                return ['----'];
                              }
                            },
                            list: false
                          },
                          fileTypeId: {
                            title: 'FILE TYPE',
                            width: '30%',
                            options: '${pageContext.request.contextPath}/filetypes/options',
                            list: false
                          },
                          </sec:authorize>
                          "subdivision.name": {
                            title: 'SUBDIVISIONS NAME',
                            width: '10%',
                            edit: false,
                            create: false,
                            display: function (data) {
                              if (data.record.subdivision !== null && data.record.subdivision !== undefined) {
                                return '<b>' + data.record.subdivision.name + '</b>';
                              } else {
                                return '<b>----</b>';
                              }
                            }
                          },
                          "category.name": {
                            title: 'CATEGORY NAME',
                            width: '10%',
                            edit: false,
                            create: false,
                            display: function (data) {
                              if (data.record.category !== null && data.record.category !== undefined) {
                                return '<b>' + data.record.category.name + '</b>';
                              } else {
                                return '<b>----</b>';
                              }
                            }
                          },
                          "infotype.typeName": {
                            title: 'INFOTYPE NAME',
                            width: '10%',
                            edit: false,
                            create: false,
                            display: function (data) {
                              if (data.record.infotype !== null && data.record.infotype !== undefined) {
                                return '<b>' + data.record.infotype.typeName + '</b>';
                              } else {
                                return '<b>----</b>';
                              }
                            }
                          },
                          "textinfo": {
                            title: 'INFO',
                            width: '20%',
                          },
                          "fileinfo.filesType": {
                            title: 'FILE TYPE',
                            width: '10%',
                            edit: false,
                            create: false,
                            display: function (data) {
                              if (data.record.fileinfo !== null && data.record.fileinfo !== undefined) {
                                if(data.record.fileinfo.filesType !== null && data.record.fileinfo.filesType !== undefined) {
                                  return '<b>' + data.record.fileinfo.filesType.filesTypeName + '</b>';
                                } else {
                                  return '<b>----</b>';
                                }
                              } else {
                                return '<b>----</b>';
                              }
                            }
                          },
                          "FileUpload": {
                            title: 'FILE',
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
                          "creationTime": {
                            title: 'CREATED',
                            width: '10%',
                            //type:'date',
                            edit: false,
                            create: false,
                          },
                          AddServiceToCart: {
                            title: '',
                            width: '1%',
                            sorting: false,
                            create: false,
                            edit: false,
                            list: true,
                            display: function (data) {
                              if (data.record.fileinfo !== null && data.record.fileinfo !== undefined) {
                              var $myVal = data.record.fileinfo.fileId;
                              var mimeType= data.record.fileinfo.fileMimeType;
                              if(mimeType!== null && mimeType!==undefined){
                                    if(mimeType.match(/image*/)){
                                        var $link = $('<a href="#" class ="PassServiceLink"><i class="fa fa-search fa-fw"></i></a>');
                                        $link.on("click",function () {
                                          $('#add_service_to_cart_dialog').dialog({
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

                                          $('#add_service_to_cart_dialog').dialog("open");
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
                        },
                        formCreated:function(event, data){
                          data.form.attr("enctype", "multipart/form-data");
                          data.form.attr("encoding", "multipart/form-data");


                          var parent=data.form.parent().parent();
                          $('#Edit-subdivId').multiselect({
                            multiple: false,
                            appendTo : parent,
                            selectedList: 1
                          }).multiselectfilter();

                          $('#Edit-categoryId').multiselect({
                            multiple: false,
                            appendTo : parent,
                            selectedList: 1
                          }).multiselectfilter();

                          $('#Edit-infoTypeId').multiselect({
                            multiple: false,
                            appendTo : parent,
                            selectedList: 1
                          }).multiselectfilter();

                          $('#Edit-fileTypeId').multiselect({
                            multiple: false,
                            appendTo : parent,
                            selectedList: 1
                          }).multiselectfilter();
                          /*
                          var parent=data.form.parent().parent();
                          $('#Edit-subdivsId2').multiselect({
                            show: "bounce",
                            hide: "explode",
                            appendTo : parent,
                          }).multiselectfilter();/**/


                        },
                      }, function (data) { //opened handler
                        data.childTable.jtable('load');
                      });
            });
            return $img;
          },
        },
        editAdditional: {
          title: '',
          width: '1%',
          sorting: false,
          create: false,
          edit: false,
          list: true,
          display: function (data) {
            var $link_edit = $('<a href="#" class ="PassServiceLink"><i class="fa fa-search fa-fw"></i></a>');
            $link_edit.on("click",function () {

              var dialog=$('<div>We failed</div>')
                      .dialog(
                      {
                        title: 'Edit Info for:'+data.record.fio,
                        autoOpen: false,
                        modal: true,
                        resizable: true,
                        autoResize:true,
                        width:400,
                        height:250,
                        closeOnEscape: true,
                        close: function(event, ui)
                        {
                          $(this).destroy().remove();
                        },
                        open: function (event, ui) {
                          $(this).empty();
                          var form=$('<form action="" ></form>');
                          var input1=$('<input id="ac01_mainsubdivisions_id" name="mainsubdivisions_ids" type="text" />');
                          var input2=$('<input id="mainsubdivisions_id" name="mainsubdivisions_id" type="hidden" value="0" />');
                          $(this).append(form);
                          form.append(input1);
                          form.append(input2);
                          $(input1).ajaxComboBox('${pageContext.request.contextPath}/subdivisions/listing',
                                  { lang: 'en',
                                    select_only: true,
                                    field: 'name',
                                    primary_key: 'subdivisionId',
                                    db_table: 'nation',
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
              dialog.dialog("open");
            });
            return $link_edit;
          }
        },
        version: {
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
<div id="add_service_to_cart_dialog"></div>
