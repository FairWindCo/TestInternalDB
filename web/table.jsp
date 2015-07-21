<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 16.07.2015
  Time: 14:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<html>
  <head>
    <title></title>
    <script src="<c:url value="/resources/jquery-1.11.3.min.js"/>"></script>
    <script src="<c:url value="/resources/jquery-ui.min.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/resources/jquery-ui.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/jquery-ui.theme.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/jquery-ui.structure.min.css"/>">

    <link rel="stylesheet" href="<c:url value="/resources/slick.grid.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/controls/slick.pager.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/controls/slick.columnpicker.css"/>">
    <script src="<c:url value="/resources/lib/firebugx.js"/>"></script>
    <script src="<c:url value="/resources/lib/jquery.event.drag-2.2.js"/>"></script>
    <script src="<c:url value="/resources/plugins/slick.rowselectionmodel.js"/>"></script>
    <script src="<c:url value="/resources/slick.core.js"/>"></script>
    <script src="<c:url value="/resources/slick.grid.js"/>"></script>

    <script src="<c:url value="/resources/slick.formatters.js"/>"></script>
    <script src="<c:url value="/resources/slick.editors.js"/>"></script>

    <script src="<c:url value="/resources/slick.dataview.js"/>"></script>
    <script src="<c:url value="/resources/controls/slick.pager.js"/>"></script>
    <script src="<c:url value="/resources/controls/slick.columnpicker.js"/>"></script>

    <script>
      $(function() {
        $( "#accordion" ).accordion();
      });
    </script>
  </head>
  <body>
  <div id="accordion">
    <h3>Section 1</h3>
    <div>
      <p>
        <img src="<c:url value="/images/ui-icons_222222_256x240.png"/>">
        
      </p>
    </div>
    <h3>Section 2</h3>
    <div>
      <style>
        .cell-title {
          font-weight: bold;
        }
        .cell-effort-driven {
          text-align: center;
        }
        .cell-selection {
          border-right-color: silver;
          border-right-style: solid;
          background: #f5f5f5;
          color: gray;
          text-align: right;
          font-size: 10px;
        }
        .slick-row.selected .cell-selection {
          background-color: transparent; /* show default selected row background */
        }
      </style>
      </head>
      <body>
      <div style="position:relative">
        <div style="width:600px;">
          <div class="grid-header" style="width:100%">
            <label>SlickGrid</label>
      <span style="float:right" class="ui-icon ui-icon-search" title="Toggle search panel"
            onclick="toggleFilterRow()"></span>
          </div>
          <div id="myGrid" style="width:100%;height:500px;"></div>
          <div id="pager" style="width:100%;height:20px;"></div>
        </div>

        <div class="options-panel">
          <b>Search:</b>
          <hr/>
          <div style="padding:6px;">
            <label style="width:200px;float:left">Show tasks with % at least: </label>

            <div style="padding:2px;">
              <div style="width:100px;display:inline-block;" id="pcSlider"></div>
            </div>
            <br/>
            <label style="width:200px;float:left">And title including:</label>
            <input type=text id="txtSearch" style="width:100px;">
            <br/><br/>
            <button id="btnSelectRows">Select first 10 rows</button>

            <br/>

            <h2>Demonstrates:</h2>
            <ul>
              <li>a filtered Model (DataView) as a data source instead of a simple array</li>
              <li>grid reacting to model events (onRowCountChanged, onRowsChanged)</li>
              <li>
                <b>FAST</b> DataView recalculation and <b>real-time</b> grid updating in response to data changes.<br/>
                The grid holds <b>50'000</b> rows, yet you are able to sort, filter, scroll, navigate and edit as if it had 50
                rows.
              </li>
              <li>adding new rows, bidirectional sorting</li>
              <li>column options: cannotTriggerInsert</li>
              <li>events: onCellChange, onAddNewRow, onKeyDown, onSelectedRowsChanged, onSort</li>
              <li><font color=red>NOTE:</font> all filters are immediately applied to new/edited rows</li>
              <li>Handling row selection against model changes.</li>
              <li>Paging.</li>
              <li>inline filter panel</li>
            </ul>
            <h2>View Source:</h2>
            <ul>
              <li><A href="https://github.com/mleibman/SlickGrid/blob/gh-pages/examples/example4-model.html" target="_sourcewindow"> View the source for this example on Github</a></li>
            </ul>

          </div>
        </div>
      </div>

      <div id="inlineFilterPanel" style="display:none;background:#dddddd;padding:3px;color:black;">
        Show tasks with title including <input type="text" id="txtSearch2">
        and % at least &nbsp;
        <div style="width:100px;display:inline-block;" id="pcSlider2"></div>
      </div>
      <script>
        var dataView;
        var grid;
        var data = [];
        var columns = [
          {id: "sel", name: "#", field: "num", behavior: "select", cssClass: "cell-selection", width: 40, cannotTriggerInsert: true, resizable: false, selectable: false },
          {id: "title", name: "Title", field: "title", width: 120, minWidth: 120, cssClass: "cell-title", editor: Slick.Editors.Text, validator: requiredFieldValidator, sortable: true},
          {id: "duration", name: "Duration", field: "duration", editor: Slick.Editors.Text, sortable: true},
          {id: "%", defaultSortAsc: false, name: "% Complete", field: "percentComplete", width: 80, resizable: false, formatter: Slick.Formatters.PercentCompleteBar, editor: Slick.Editors.PercentComplete, sortable: true},
          {id: "start", name: "Start", field: "start", minWidth: 60, editor: Slick.Editors.Date, sortable: true},
          {id: "finish", name: "Finish", field: "finish", minWidth: 60, editor: Slick.Editors.Date, sortable: true},
          {id: "effort-driven", name: "Effort Driven", width: 80, minWidth: 20, maxWidth: 80, cssClass: "cell-effort-driven", field: "effortDriven", formatter: Slick.Formatters.Checkmark, editor: Slick.Editors.Checkbox, cannotTriggerInsert: true, sortable: true}
        ];
        var options = {
          editable: true,
          enableAddRow: true,
          enableCellNavigation: true,
          asyncEditorLoading: true,
          forceFitColumns: false,
          topPanelHeight: 25
        };
        var sortcol = "title";
        var sortdir = 1;
        var percentCompleteThreshold = 0;
        var searchString = "";
        function requiredFieldValidator(value) {
          if (value == null || value == undefined || !value.length) {
            return {valid: false, msg: "This is a required field"};
          }
          else {
            return {valid: true, msg: null};
          }
        }
        function myFilter(item, args) {
          if (item["percentComplete"] < args.percentCompleteThreshold) {
            return false;
          }
          if (args.searchString != "" && item["title"].indexOf(args.searchString) == -1) {
            return false;
          }
          return true;
        }
        function percentCompleteSort(a, b) {
          return a["percentComplete"] - b["percentComplete"];
        }
        function comparer(a, b) {
          var x = a[sortcol], y = b[sortcol];
          return (x == y ? 0 : (x > y ? 1 : -1));
        }
        function toggleFilterRow() {
          grid.setTopPanelVisibility(!grid.getOptions().showTopPanel);
        }
        $(".grid-header .ui-icon")
                .addClass("ui-state-default ui-corner-all")
                .mouseover(function (e) {
                  $(e.target).addClass("ui-state-hover")
                })
                .mouseout(function (e) {
                  $(e.target).removeClass("ui-state-hover")
                });
        $(function () {
          // prepare the data
          for (var i = 0; i < 50000; i++) {
            var d = (data[i] = {});
            d["id"] = "id_" + i;
            d["num"] = i;
            d["title"] = "Task " + i;
            d["duration"] = "5 days";
            d["percentComplete"] = Math.round(Math.random() * 100);
            d["start"] = "01/01/2009";
            d["finish"] = "01/05/2009";
            d["effortDriven"] = (i % 5 == 0);
          }
          dataView = new Slick.Data.DataView({ inlineFilters: true });
          grid = new Slick.Grid("#myGrid", dataView, columns, options);
          grid.setSelectionModel(new Slick.RowSelectionModel());
          var pager = new Slick.Controls.Pager(dataView, grid, $("#pager"));
          var columnpicker = new Slick.Controls.ColumnPicker(columns, grid, options);
          // move the filter panel defined in a hidden div into grid top panel
          $("#inlineFilterPanel")
                  .appendTo(grid.getTopPanel())
                  .show();
          grid.onCellChange.subscribe(function (e, args) {
            dataView.updateItem(args.item.id, args.item);
          });
          grid.onAddNewRow.subscribe(function (e, args) {
            var item = {"num": data.length, "id": "new_" + (Math.round(Math.random() * 10000)), "title": "New task", "duration": "1 day", "percentComplete": 0, "start": "01/01/2009", "finish": "01/01/2009", "effortDriven": false};
            $.extend(item, args.item);
            dataView.addItem(item);
          });
          grid.onKeyDown.subscribe(function (e) {
            // select all rows on ctrl-a
            if (e.which != 65 || !e.ctrlKey) {
              return false;
            }
            var rows = [];
            for (var i = 0; i < dataView.getLength(); i++) {
              rows.push(i);
            }
            grid.setSelectedRows(rows);
            e.preventDefault();
          });
          grid.onSort.subscribe(function (e, args) {
            sortdir = args.sortAsc ? 1 : -1;
            sortcol = args.sortCol.field;
            if ($.browser.msie && $.browser.version <= 8) {
              // using temporary Object.prototype.toString override
              // more limited and does lexicographic sort only by default, but can be much faster
              var percentCompleteValueFn = function () {
                var val = this["percentComplete"];
                if (val < 10) {
                  return "00" + val;
                } else if (val < 100) {
                  return "0" + val;
                } else {
                  return val;
                }
              };
              // use numeric sort of % and lexicographic for everything else
              dataView.fastSort((sortcol == "percentComplete") ? percentCompleteValueFn : sortcol, args.sortAsc);
            } else {
              // using native sort with comparer
              // preferred method but can be very slow in IE with huge datasets
              dataView.sort(comparer, args.sortAsc);
            }
          });
          // wire up model events to drive the grid
          dataView.onRowCountChanged.subscribe(function (e, args) {
            grid.updateRowCount();
            grid.render();
          });
          dataView.onRowsChanged.subscribe(function (e, args) {
            grid.invalidateRows(args.rows);
            grid.render();
          });
          dataView.onPagingInfoChanged.subscribe(function (e, pagingInfo) {
            var isLastPage = pagingInfo.pageNum == pagingInfo.totalPages - 1;
            var enableAddRow = isLastPage || pagingInfo.pageSize == 0;
            var options = grid.getOptions();
            if (options.enableAddRow != enableAddRow) {
              grid.setOptions({enableAddRow: enableAddRow});
            }
          });
          var h_runfilters = null;
          // wire up the slider to apply the filter to the model
          $("#pcSlider,#pcSlider2").slider({
            "range": "min",
            "slide": function (event, ui) {
              Slick.GlobalEditorLock.cancelCurrentEdit();
              if (percentCompleteThreshold != ui.value) {
                window.clearTimeout(h_runfilters);
                h_runfilters = window.setTimeout(updateFilter, 10);
                percentCompleteThreshold = ui.value;
              }
            }
          });
          // wire up the search textbox to apply the filter to the model
          $("#txtSearch,#txtSearch2").keyup(function (e) {
            Slick.GlobalEditorLock.cancelCurrentEdit();
            // clear on Esc
            if (e.which == 27) {
              this.value = "";
            }
            searchString = this.value;
            updateFilter();
          });
          function updateFilter() {
            dataView.setFilterArgs({
              percentCompleteThreshold: percentCompleteThreshold,
              searchString: searchString
            });
            dataView.refresh();
          }
          $("#btnSelectRows").click(function () {
            if (!Slick.GlobalEditorLock.commitCurrentEdit()) {
              return;
            }
            var rows = [];
            for (var i = 0; i < 10 && i < dataView.getLength(); i++) {
              rows.push(i);
            }
            grid.setSelectedRows(rows);
          });
          // initialize the model after all the events have been hooked up
          dataView.beginUpdate();
          dataView.setItems(data);
          dataView.setFilterArgs({
            percentCompleteThreshold: percentCompleteThreshold,
            searchString: searchString
          });
          dataView.setFilter(myFilter);
          dataView.endUpdate();
          // if you don't want the items that are not visible (due to being filtered out
          // or being on a different page) to stay selected, pass 'false' to the second arg
          dataView.syncGridSelection(grid, true);
          $("#gridContainer").resizable();
        })
      </script>
    </div>
    <h3>Section 3</h3>
    <div>
      <p>
        Nam enim risus, molestie et, porta ac, aliquam ac, risus. Quisque lobortis.
        Phasellus pellentesque purus in massa. Aenean in pede. Phasellus ac libero
        ac tellus pellentesque semper. Sed ac felis. Sed commodo, magna quis
        lacinia ornare, quam ante aliquam nisi, eu iaculis leo purus venenatis dui.
      </p>
      <ul>
        <li>List item one</li>
        <li>List item two</li>
        <li>List item three</li>
      </ul>
    </div>
    <h3>Section 4</h3>
    <div>
      <p>
        Cras dictum. Pellentesque habitant morbi tristique senectus et netus
        et malesuada fames ac turpis egestas. Vestibulum ante ipsum primis in
        faucibus orci luctus et ultrices posuere cubilia Curae; Aenean lacinia
        mauris vel est.
      </p>
      <p>
        Suspendisse eu nisl. Nullam ut libero. Integer dignissim consequat lectus.
        Class aptent taciti sociosqu ad litora torquent per conubia nostra, per
        inceptos himenaeos.
      </p>
    </div>
  </div>
  </body>
</html>
