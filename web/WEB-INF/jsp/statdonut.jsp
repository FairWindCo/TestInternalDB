<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>

<%@include file="/WEB-INF/include/jquery_lib_include.jsp" %>
<%@include file="/WEB-INF/include/morris_include.jsp" %>

<%-- customized javascript code to manage JTable --%>
<script>
    $(document).ready(function() {

          // Create a function that will handle AJAX requests
          function requestData(lasturl,chart){
          $.ajax({
          type: "POST",
          url: '${pageContext.request.contextPath}/stats/'+lasturl, // This is the URL to the API
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
        var chart1 = Morris.Donut({
            // ID of the element in which to draw the chart.
            element: 'diagram1',
            // Set initial data (ideally you would provide an array of default data)
            data: [{label:'',value:0}],
        });
        var chart2 = Morris.Donut({
            // ID of the element in which to draw the chart.
            element: 'diagram2',
            // Set initial data (ideally you would provide an array of default data)
            data: [{label:'',value:0}],
        });
        // Request initial data for the past 7 days:
        requestData('category',chart1);
        requestData('subdivisions',chart2);

        $("#refresh_diagram1").click(function (e) {requestData('category',chart1);});
        $("#refresh_diagram2").click(function (e) {requestData('subdivisions',chart2);});
  });

</script>

<ol class="breadcrumb">
  <li><a href="${pageContext.request.contextPath}/"><c:message code="label.main"/></a></li>
  <li><a href="#"><c:message code="label.statistic"/></a></li>
  <li class="active"><c:message code="label.statistic.buble"/></li>
</ol>
<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title"><c:message code="label.statistic.buble"/></h3>
  </div>
  <div class="panel-body">
    <jsp:include page="view/totality.jsp" />
  </div>
    <div class="row">
        <div class="col-lg-5">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <i class="fa fa-bar-chart-o fa-fw"></i> Donut Chart Example
                </div>
                <div class="panel-body">
                    <div id="diagram1"></div>
                    <input id="refresh_diagram1" type="button" value="REFRESH">
                </div>
            </div>
     </div>
        <div class="col-lg-5">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <i class="fa fa-bar-chart-o fa-fw"></i> Donut Chart Example
                </div>
                <div class="panel-body">
                    <div id="diagram2"></div>
                    <input id="refresh_diagram2" type="button" value="REFRESH">
                </div>
            </div>
        </div>
    </div>

</div>

