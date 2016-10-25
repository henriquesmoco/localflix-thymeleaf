var commonComponents = (function() {

    var datatableComponent = (function (selectorList, options, context) {
        var _datatable;

        initialize(selectorList, options, context);

        function initialize(selectorList, options, context) {
            var listUrl = $(selectorList, context).data("url");
            var defaults = $.extend(true, datatableDefaults(), {"ajax": {"url": listUrl}});
            var datatableOptions = $.extend(true, defaults, options || {});
            addDatatableOnLoad(selectorList, datatableOptions, context);
            addDatatableRowCallback(datatableOptions);

            correctDatatableDefaultsForBootstrap();
            _datatable = $(selectorList, context).dataTable(datatableOptions);
            setDatatableOnClick(_datatable);
        }

        function addDatatableOnLoad(selectorList, options, context) {
            options.ajax.data = function (d) {
                var _datatable = $(selectorList, context).DataTable();
                d.page = (_datatable != undefined) ? _datatable.page.info().page : 0;
                d.size = (_datatable != undefined) ? _datatable.page.info().length : 5;
                d.sort = d.columns[d.order[0].column].data + ',' + d.order[0].dir;
            }
        }

        function addDatatableRowCallback(options) {
            options.rowCallback = function (row, data, index) {
                if (data[options.rowId] == thisDatatable.selectedId) {
                    $(row).addClass('info');
                }
            };
        }

        function setDatatableOnClick(datatable) {
            datatable.find("tbody").on('click', 'tr', function (event) {
                var id = datatable.DataTable().row(this).id();
                if (id) {
                    var row = $(event.currentTarget);
                    row.parent().find('tr.info').removeClass('info');
                    row.addClass('info');

                    thisDatatable.selectedId = id;
                    thisDatatable.onSelectionChange(id);
                }
            });
        }

        function refresh() {
            _datatable.DataTable().draw(false);
        }

        function getSelectedItem() {
            return _datatable.DataTable().row(_datatable.find("tbody tr.info")).data();
        }

        function onSelectionChange(id) {
        }

        function correctDatatableDefaultsForBootstrap() {
            $.extend($.fn.dataTable.ext.classes, {
                sWrapper: "dataTables_wrapper form-inline dt-bootstrap",
                sFilterInput: "form-control input-sm",
                sLengthSelect: "form-control input-sm",
                sProcessing: "dataTables_processing panel panel-default"
            });
        }

        function datatableDefaults() {
            return {
                "ajax": {},
                "columns": [],
                "rowId": "id",
                "autoWidth": false,
                "searching": true,
                "processing": true,
                "serverSide": true,
                "info": false,
                "lengthMenu": [5, 15, 30, 50, 100],
                "pagingType": "full_numbers",
                "dom": "<'row'<'col-sm-4 col-xs-4'l><'col-sm-8 col-xs-8'f>>" +
                "<'row'<'col-sm-12'tr>>" +
                "<'row'<''i><'col-sm-12'p>>",
                "renderer": 'bootstrap'
            };
        }

        var thisDatatable = {
            refresh: refresh,
            onSelectionChange: onSelectionChange,
            selectedItem: getSelectedItem,
            selectedId: "0"
        };
        return thisDatatable;
    });

    function formatLocalDate(date, format) {
        if(!date) { return ""; }

        var bits = [];
        function add(s) { bits.push(s); }
        function skipCharacters(n){ format = format.slice(n); }
        while(format.length) {
            switch (format.charAt(0)) {
                case 'y':
                    if (/^yyyy/.test(format)) {
                        add(date.year);
                        skipCharacters(4);
                        continue;
                    }
                    skipCharacters(1);
                    continue;
                case 'M':
                    if (/^MMMM/.test(format)) {
                        add(date.month);
                        skipCharacters(4);
                        continue;
                    }
                    if(/^MMM/.test(format)){
                        add(date.month.substring(0, 3));
                        skipCharacters(3);
                        continue;
                    }
                    if(/^MM/.test(format)){
                        if (date.monthValue < 10) {
                            add("0" + date.monthValue);
                        } else {
                            add(date.monthValue);
                        }
                        skipCharacters(2);
                        continue;
                    }
                    add(monthValue);
                    skipCharacters(1);
                    continue;
                case 'd':
                    if(/^dd/.test(format)){
                        if (date.dayOfMonth < 10) {
                            add("0" + date.dayOfMonth);
                        } else {
                            add(date.dayOfMonth);
                        }
                        skipCharacters(2);
                    }
            }
            add(format.charAt(0));
            skipCharacters(1);
        }
        return bits.join("");
    }


    return {
        datatable: datatableComponent,
        renders: {
            yesNo: function (data, type, full, meta) {
                return data == true ? "Yes" : "No";
            },
            localDate: function (data, type, full, meta) {
                var dateFormat = meta.settings.aoColumns[meta.col].dateFormat;
                return formatLocalDate(data, dateFormat);
            }
        }
    }
})();

