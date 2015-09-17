<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 17.09.2015
  Time: 13:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>

<%-- JQuery --%>
<%@include file="/WEB-INF/include/jquery_include.jsp" %>

<!-- Bootstrap Core JavaScript -->
<%@include file="/WEB-INF/include/bootstrup_include.jsp" %>
<%-- JCombobox --%>
<%@include file="/WEB-INF/include/jcombo_include.jsp" %>
<%-- FormSubmit --%>
<%@include file="/WEB-INF/include/submit_include.jsp" %>
<script>
  $(document).ready(function() {
    //FORM FileType COMBOBOX
    $("#filetypeId").ajaxComboBox('${pageContext.request.contextPath}/filetypes/listing',
            { lang: '<c:message code="label.lang"/>',
              select_only: true,
              field: 'filesTypeName',
              primary_key: 'filesTypeId',
              db_table: 'nation',
              per_page: 20,
            });

    $("#personId").ajaxComboBox('${pageContext.request.contextPath}/person/listing',
            { lang: '<c:message code="label.lang"/>',
              select_only: true,
              field: 'fio',
              primary_key: 'personId',
              db_table: 'nation',
              order_by: [
                'fio DESC'
              ],
              sub_info: true,
              sub_as: {
                code: 'CODE:',
                dateberthdey: 'BITHDAY:',
              },
              per_page: 20,
              //hidden_name:'personId',
            });

    <sec:authorize ifAnyGranted="ROLE_GROUP_INF_EDIT,ROLE_SUPER_INF_EDIT,ROLE_MAIN_INF_EDIT,ROLE_GROUP_INF_VIEW,ROLE_SUPER_INF_VIEW,ROLE_MAIN_INF_VIEW">
    $("#subdivsId").ajaxComboBox('${pageContext.request.contextPath}/subdivisions/listing',
            { lang: '<c:message code="label.lang"/>',
              select_only: true,
              field: 'name',
              primary_key: 'subdivisionId',
              db_table: 'nation',
              per_page: 20,
            });
    </sec:authorize>

    var options = {
      target:        '#result',   // target element(s) to be updated with server response
      //beforeSubmit:  showRequest,  // pre-submit callback
      success:       showResponse,  // post-submit callback
      error:showError,
      clearForm: true,        // clear all form fields after successful submit
      resetForm: true,        // reset the form after successful submit

      // other available options:
      //url:       url         // override for form's 'action' attribute
      //type:      type        // 'get' or 'post', override for form's 'method' attribute
      //dataType:  null        // 'xml', 'script', or 'json' (expected server response type)
      //

      // $.ajax options can be used here too, for example:
      //timeout:   3000
    };
    function showError(xhr, status, error){
      $("#result").empty();
      $("#result").append(error);
      $("#result").addClass("alert alert-danger");
      $("#result").removeClass("alert alert-success");
    }
    // post-submit callback
    function showResponse(responseText, statusText, xhr, $form)  {
      // for normal html responses, the first argument to the success callback
      // is the XMLHttpRequest object's responseText property

      // if the ajaxForm method was passed an Options Object with the dataType
      // property set to 'xml' then the first argument to the success callback
      // is the XMLHttpRequest object's responseXML property

      // if the ajaxForm method was passed an Options Object with the dataType
      // property set to 'json' then the first argument to the success callback
      // is the json data object returned by the server
      if(statusText=="success" && responseText=="OK"){
        $("#result").removeClass("alert alert-danger");
        $("#result").addClass("alert alert-success");
      } else {
        $("#result").addClass("alert alert-danger");
        $("#result").removeClass("alert alert-success");
      }
      //alert('status: ' + statusText + '\n\nresponseText: \n' + responseText +'\n\nThe output div should have already been updated with the responseText.');
    }

    // bind form using 'ajaxForm'
    $('#theForm').ajaxForm(options);

  });
</script>


<ol class="breadcrumb">
  <li><a href="${pageContext.request.contextPath}/"><c:message code="label.main"/></a></li>
  <li><a href="#"><c:message code="label.addcomplaint"/></a></li>
</ol>
<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title"><c:message code="label.addcomplaint.title"/></h3>
  </div>
  <div class="panel-body">
    <form id="theForm" enctype="multipart/form-data" encoding="multipart/form-data" method="post" action="${pageContext.request.contextPath}/dossers/addComplaint">
      <table  class="table">
        <tr>
          <td><c:message code="label.clientstables.col_title.fio"/></td>
          <td><input type="text" name="personId" id="personId"/>
        </tr>
        <sec:authorize ifAnyGranted="ROLE_GROUP_INF_EDIT,ROLE_SUPER_INF_EDIT,ROLE_MAIN_INF_EDIT,ROLE_GROUP_INF_VIEW,ROLE_SUPER_INF_VIEW,ROLE_MAIN_INF_VIEW">
        <tr>
          <td><c:message code="label.clientstables.col_title.fio"/></td>
          <td><input type="text" name="subdivsId" id="subdivsId"/>
        </tr>
        </sec:authorize>
        <tr>
          <td><c:message code="label.clientstables.dosers.col_title.text"/></td>
          <td><input type="text"  name="info" id="info"/>
        </tr>
        <tr>
          <td><c:message code="label.clientstables.dosers.col_title.filytype"/></td>
          <td><input type="text"  name="filetypeId" id="filetypeId"/>
        </tr>
        <tr>
          <td><c:message code="label.clientstables.dosers.col_title.filecomments"/></td>
          <td>
            <input type="text"  name="filecomment" id="filecomment"/>
          </td>
        </tr>
        <tr>
          <td><c:message code="label.clientstables.dosers.col_title.file"/></td>
          <td>
            <input type="file"  name="file" id="file"/>
          </td>
        </tr>
        <tr>
          <td></td>
          <td><input type="submit"/></td>
        </tr>
      </table>
    </form>
    <div id="result"></div>
  </div>
</div>
