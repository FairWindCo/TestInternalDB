/* 
 * Javascript to manage the JTable
 */

$(document).ready(function() {              
    //setup the jtable that will display the results
    $('#ExpenseTableContainer').jtable({
        title: 'Table of Expenses',
        selecting: true, //Enable selecting 
        paging: true, //Enable paging
        pageSize: 10, //Set page size (default: 10)
        sorting: true, //Enable sorting
        actions: {
            //listAction: 'datatable/getAllExpenses',
            listAction: 'person/list2',
            createAction: 'datatable/addExpense',
            updateAction: 'datatable/updateExpense',
            deleteAction: 'datatable/deleteExpense'
        },
        fields: {
            personId: {
                title: 'ID',
                key: true,
                list: true,
                create: false,
                edit: false
            },
            dateberthdey: {
                title: 'BIRTHDAY',
                type: 'date',
                displayFormat:'@',
                width: '30%',
                display: function (date) {
                    return $.datepicker.parseDate( "@", date.record.dateberthdey );
                },
            },
            fio: {
                title: 'FIO',
                width: '15%'
            },
            /*
            CategoryId: {
                title: 'Category',
                options: 'datatable/categories'
            },
            SubcategoryId: {
                title: 'Sub Category',
                dependsOn: 'CategoryId',
                options: function (data) {
                    if (data.source == 'list') {
                        //Return url of all countries for optimization.
                        //This method is called for each row on the table and jTable caches options based on this url.
                        return 'datatable/subcategories?categoryId=0';
                    }
                    return 'datatable/subcategories?categoryId=' + data.dependedValues.CategoryId;
                },
                list: false
            },    */
            personStatus: {
                title: 'Description',
                width: '25%'
            }
        },
        //Register to selectionChanged event to hanlde events                                     
        recordAdded: function(event, data){
            //after record insertion, reload the records
            $('#ExpenseTableContainer').jtable('load');
        },
        recordUpdated: function(event, data){
            //after record updation, reload the records
            $('#ExpenseTableContainer').jtable('load');
        }
    });
    $('#ExpenseTableContainer').jtable('load');              
                
});
