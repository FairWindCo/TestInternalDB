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
                    title: '<c:message code="label.usertables"/>',
                    selecting: true, //Enable selecting
                    paging: true, //Enable paging
                    pageSize: 10, //Set page size (default: 10)
                    sorting: true, //Enable sorting
                    actions: {
                        //listAction: 'datatable/getAllExpenses',
                        listAction: '${pageContext.request.contextPath}/users/listedit',
                        createAction: '${pageContext.request.contextPath}/users/adduser',
                        updateAction: '${pageContext.request.contextPath}/users/updateuser',
                        deleteAction: '${pageContext.request.contextPath}/users/deleteuser'
                    },
                    fields: {
                        userID: {
                            title: 'ID',
                            key: true,
                            list: true,
                            create: false,
                            edit: false,
                            visibility:"hidden"
                        },
                        userName: {
                            title: 'LOGIN NAME',
                            width: '15%',
                        },
                        passwordHash: {
                            title: 'PASSWORD',
                            width: '15%',
                            type: 'password',
                        },
                        fio: {
                            title: 'F.I.O.',
                            width: '15%',
                        },
                        enabled: {
                            title: 'ENABLED',
                            width: '5%',
                            type: 'checkbox',
                            values: { 'false': 'Passive', 'true': 'Active' },
                            defaultValue: 'true'
                        },
                        userRoles:{
                            title: 'ROLES',
                            width: '2%',
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
                                                title: rolesdata.record.userName + ' - user roles',
                                                actions: {
                                                    listAction: '${pageContext.request.contextPath}/users/roles?userID=' + rolesdata.record.userID,
                                                    deleteAction: '${pageContext.request.contextPath}/users/removeuserrole?userId=' + rolesdata.record.userID,
                                                    createAction: '${pageContext.request.contextPath}/users/adduserrole?userId=' + rolesdata.record.userID
                                                },
                                                fields: {
                                                    roleId: {
                                                        key: true,
                                                        create: false,
                                                        edit: false,
                                                        list: false
                                                    },
                                                    roleName2: {
                                                        title: 'ROLE NAME',
                                                        width: '30%',
                                                        //options: 'avaibleRolesOpt?userID=' + rolesdata.record.userID,
                                                        options: function(data) {
                                                            data.clearCache();
                                                            return '${pageContext.request.contextPath}/users/avaibleRolesOpt?userID=' + rolesdata.record.userID;
                                                        },
                                                        list: false
                                                    },
                                                    roleName: {
                                                        title: 'ROLE NAME',
                                                        width: '10%',
                                                        edit: false,
                                                        create: false,
                                                    },
                                                    roleDescription: {
                                                        title: 'DESCRIPTION',
                                                        width: '30%',
                                                        edit: false,
                                                        create: false,
                                                    },
                                                }
                                            }, function (data) { //opened handler
                                                data.childTable.jtable('load');
                                            });
                                });
                                return $img;
                            },
                        },
                        versionId: {
                            title: 'VERSION',
                            defaultValue:'0',
                            edit: false,
                            create: false,
                            width: '5%',
                            editinline:{enable:true},
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