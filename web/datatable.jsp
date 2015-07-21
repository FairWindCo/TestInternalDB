<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>JQuery JTable integration with Spring MVC3 </title>



        <%-- JQuery --%>
        <script src="<c:url value="/resources/jquery-1.11.3.min.js"/>"></script>
        <script src="<c:url value="/resources/jquery-ui.min.js"/>"></script>
        <link rel="stylesheet" href="<c:url value="/resources/jquery-ui.min.css"/>">
        <link rel="stylesheet" href="<c:url value="/resources/jquery-ui.structure.min.css"/>">
        
        <%-- JTable --%>
        <link rel="stylesheet" href="<c:url value="/resources/jtable/themes/metro/blue/jtable.css"/>">
        <script src="<c:url value="/resources/jtable/jquery.jtable.js"/>"></script>

        <link rel="stylesheet" href="<c:url value="/resources/css/jquery-ui-1.8.16.custom.css"/>">
        
        <%-- customized javascript code to manage JTable --%>
        <script src="<c:url value="/resources/expenseJTable.js"/>"></script>
    </head>
    <body>
        <div>       
             <div id="ExpenseTableContainer" style="width:99%;"></div>
        </div>
    </body>
</html>