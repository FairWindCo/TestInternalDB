<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>

<%@include file="/WEB-INF/include/jquery_lib_include.jsp" %>
<%-- JQueryUI --%>
<%@include file="/WEB-INF/include/jquery_ui_include.jsp" %>
<%@include file="/WEB-INF/include/morris_include.jsp" %>

<%-- customized javascript code to manage JTable --%>
<script>
  $(document).ready(function() {
    // Create a function that will handle AJAX requests
    function requestData(chart,startDate,endDate){
      $.ajax({
        type: "POST",
        data:{'startDate':startDate,'endDate':endDate},
        url: '${pageContext.request.contextPath}/stats/doosers', // This is the URL to the API
      })
              .done(function( data ) {
                // When the response to the AJAX request comes back render the chart with new data
                chart.setData(data);

              })
              .fail(function() {
                // If there is no communication between the server, show an error
                alert( "error occured" );
              });
    }
    function requestData2(chart,startDate,endDate){
      $.ajax({
        type: "POST",
        data:{'startDate':startDate,'endDate':endDate},
        url: '${pageContext.request.contextPath}/stats/plotters', // This is the URL to the API
      })
              .done(function( data ) {
                // When the response to the AJAX request comes back render the chart with new data
                chart.setData(data.Records);

              })
              .fail(function() {
                // If there is no communication between the server, show an error
                alert( "error occured" );
              });
    }
    var chart1 = Morris.Bar({
      // ID of the element in which to draw the chart.
      element: 'diagram1',
      data: [["01/01/70",3,2]],
      xkey: 0,
      ykeys: [1, 2],
      labels: [<c:message code="label.statistic.area1.label1"/>', '<c:message code="label.statistic.area1.label2"/>],
      hideHover: 'auto',
      resize: true
    });

    var chart2 = Morris.Area({
      // ID of the element in which to draw the chart.
      element: 'diagram2',
      data: [{"id":1,"importDateTime":0,"importerUserName":"administrator","personImportCounter":0,"dosserImportCounter":0,"subdivisionsImportCounter":0,"categoryImportCounter":0,"infotypeImportCounter":0,"activitiesImportCounter":0,"contacttypesImportCounter":0,"hobbiImportCounter":0,"relativesImportCounter":0,"segmentsImportCounter":0,"filetypesImportCounter":0}],
      xkey: 'importDateTime',
      ykeys: ['personImportCounter', 'dosserImportCounter','subdivisionsImportCounter','categoryImportCounter','infotypeImportCounter'],
      labels: ['<c:message code="label.statistic.area2.label1"/>', '<c:message code="label.statistic.area2.label2"/>',<c:message code="label.statistic.area2.label3"/>', '<c:message code="label.statistic.area2.label4"/>','<c:message code="label.statistic.area2.label5"/>''],
      dateFormat:function (x) {
        var options = {
          year: 'numeric',
          month: 'numeric',
          day: 'numeric',
          hour: 'numeric',
          minute: 'numeric',
        };
        var date=new Date(x);
        return date.toLocaleString("ru", options);
      },
      xLabelFormat:function (x) {
      var options = {
        year: 'numeric',
        month: 'numeric',
        day: 'numeric',
        hour: 'numeric',
        minute: 'numeric',
      };
      var date=new Date(x);
      return date.toLocaleString("ru", options);
      },
      hideHover: 'auto',
      resize: true
    });

    requestData(chart1);
    requestData2(chart2);


    $("#refresh_diagram1").click(function (e) {requestData(chart1,$('#startDate1').val(),$('#endDate1').val());});
    $("#refresh_diagram2").click(function (e) {requestData2(chart2,$('#startDate2').val(),$('#endDate2').val());});
  });
  $('#startDate1').datepicker();
  $('#endDate1').datepicker();
  $('#startDate2').datepicker();
  $('#endDate2').datepicker();
</script>
<style>
  .morris-hover{position:absolute;z-index:1000}
  .morris-hover.morris-default-style{border-radius:10px;padding:6px;color:#666;background:rgba(255,255,255,0.8);border:solid 2px rgba(230,230,230,0.8);font-family:sans-serif;font-size:12px;text-align:center}
  .morris-hover.morris-default-style .morris-hover-row-label{font-weight:bold;margin:0.25em 0}
  .morris-hover.morris-default-style
  .morris-hover-point{white-space:nowrap;margin:0.1em 0}
</style>

<ol class="breadcrumb">
  <li><a href="${pageContext.request.contextPath}/"><c:message code="label.main"/></a></li>
  <li><a href="#"><c:message code="label.statistic"/></a></li>
  <li class="active"><c:message code="label.statistic.buble"/></li>
</ol>
<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title"><c:message code="label.statistic.plot"/></h3>
  </div>
  <div class="panel-body">
    <jsp:include page="view/totality.jsp" />
  </div>
  <div class="row">
      <div class="panel panel-default">
        <div class="panel-heading">
          <i class="fa fa-bar-chart-o fa-fw"></i><c:message code="label.statistic.area1.title"/>
        </div>
        <div class="panel-body">
          <div id="diagram1"></div>
          <c:message code="label.field.startDate"/>: <input type="text" name="startDate1" id="startDate1" />
          <c:message code="label.field.endDate"/>: <input type="text" name="endDate1" id="endDate1" />
          <input id="refresh_diagram1" type="button" value="<c:message code="label.statistic.refresh"/>" class="btn btn-default">
        </div>
      </div>
  </div>
  <div class="row">
    <div class="panel panel-default">
      <div class="panel-heading">
        <i class="fa fa-bar-chart-o fa-fw"></i><c:message code="label.statistic.area2.title"/>
      </div>
      <div class="panel-body">
        <div id="diagram2"></div>
        <c:message code="label.field.startDate"/>: <input type="text" name="startDate2" id="startDate2" />
        <c:message code="label.field.endDate"/>: <input type="text" name="endDate2" id="endDate2" />
        <input id="refresh_diagram2" type="button" value="<c:message code="label.statistic.refresh"/>" class="btn btn-default">
      </div>
    </div>
  </div>
</div>

