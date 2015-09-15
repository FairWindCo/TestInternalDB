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


<%-- customized javascript code to manage JTable --%>
        <script>
            $(document).ready(function() {
                //setup the jtable that will display the results
                $('#ExpenseTableContainer').jtable({
                    title: '<c:message code="label.relatives.table.label"/>',
                    selecting: true, //Enable selecting
                    paging: true, //Enable paging
                    pageSize: 10, //Set page size (default: 10)
                    sorting: true, //Enable sorting
                    messages: <c:message code="label.messages"/>,
                    actions: {
                        //listAction: 'datatable/getAllExpenses',
                        listAction: '${pageContext.request.contextPath}/relatives/listedit',
                        createAction: '${pageContext.request.contextPath}/relatives/addfiletype',
                        updateAction: '${pageContext.request.contextPath}/relatives/updatefiletype',
                        deleteAction: '${pageContext.request.contextPath}/relatives/deletefiletype'
                    },
                    fields: {
                        relativiesId: {
                            title: 'ID',
                            key: true,
                            list: true,
                            create: false,
                            edit: false,
                            visibility:"hidden"
                        },
                        name: {
                            title: '<c:message code="label.relatives.table.col_title.name"/>',
                            width: '70%',
                        },
                        key1c: {
                            title: '<c:message code="label.sql.table.label"/>',
                            width: '15%',
                            list: true,
                            create: true,
                            edit: true,
                        },
                        version: {
                            title: '<c:message code="label.version"/>',
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
    <div class="filtering">
        <form>
            <c:message code="label.field.name2"/>: <input type="text" name="searchname" id="searchname" />
            <button type="submit" id="LoadRecordsButton">'<c:message code="label.button.loadrecord"/>'</button>
            <button type="submit" id="AllRecordsButton">'<c:message code="label.button.allrecord"/>'</button>
        </form>
    </div>
        <div>       
             <div id="ExpenseTableContainer" style="width:99%;"></div>
        </div>