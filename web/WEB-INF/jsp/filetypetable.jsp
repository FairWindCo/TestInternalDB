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
        <script>
            $(document).ready(function() {
                //setup the jtable that will display the results
                $('#ExpenseTableContainer').jtable({
                    title: 'Table of File Types',
                    selecting: true, //Enable selecting
                    paging: true, //Enable paging
                    pageSize: 10, //Set page size (default: 10)
                    sorting: true, //Enable sorting
                    actions: {
                        //listAction: 'datatable/getAllExpenses',
                        listAction: 'listedit',
                        createAction: 'addfiletype',
                        updateAction: 'updatefiletype',
                        deleteAction: 'deletefiletype'
                    },
                    fields: {
                        filesTypeId: {
                            title: 'ID',
                            key: true,
                            list: true,
                            create: false,
                            edit: false,
                            visibility:"hidden"
                        },
                        filesTypeName: {
                            title: 'FILE TYPE NAME',
                            width: '70%',
                        },
                        version: {
                            title: 'VERSION',
                            defaultValue:'0',
                            edit: false,
                            create: false,
                            width: '15%'
                        },
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
    </head>
    <body>
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
    </body>
</html>