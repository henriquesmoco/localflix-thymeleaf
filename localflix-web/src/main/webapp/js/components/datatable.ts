/// <reference path="./../../../../../typings/jquery.d.ts"/>
/// <reference path="./../../../../../typings/jquery.dataTables.d.ts"/>
/// <reference path="./../../../../../typings/datatable.d.ts"/>

import { DateUtil } from "./../utils";


export class Datatable {
    private selectedId: string = "0";
    private jqDatatable: JQuery;
    private defaultSettings = {
        "ajax": {},
        "columns": <DataTables.ColumnSettings>[],
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

    constructor(selector: string, options: DataTables.Settings, context?: JQuery) {
        let listUrl = $(selector, context).data("url");
        let defaults = $.extend(true, this.defaultSettings, {"ajax": {"url": listUrl}});
        let datatableOptions = $.extend(true, defaults, options || {});
        this.addDatatableOnLoad(selector, datatableOptions, context);
        this.addDatatableRowCallback(datatableOptions);

        this.correctDatatableDefaultsForBootstrap();
        this.jqDatatable = $(selector, context).dataTable(datatableOptions);
        this.setDatatableOnClick(this.jqDatatable);
    }

    getSelectedItem() {
        return this.jqDatatable.DataTable().row(this.jqDatatable.find("tbody tr.info")).data();
    }

    refresh() {
        this.jqDatatable.DataTable().draw(false);
    }

    private addDatatableOnLoad(selector: string, options: DataTables.Settings, context: JQuery) {
        (<DataTables.AjaxSettings>options.ajax).data = function (d: any) {
            var _datatable = $(selector, context).DataTable();
            d.page = (_datatable != undefined) ? _datatable.page.info().page : 0;
            d.size = (_datatable != undefined) ? _datatable.page.info().length : 5;
            d.sort = d.columns[d.order[0].column].data + ',' + d.order[0].dir;
        }
    }

    private addDatatableRowCallback(options: DataTables.Settings) {
        options.rowCallback = function (row: Node, data: any[], index: number) {
            if (data[<any>options.rowId] == this.selectedId) {
                $(row).addClass('info');
            }
        };
    }

    private correctDatatableDefaultsForBootstrap() {
        $.extend($.fn.dataTable.ext.classes, {
            sWrapper: "dataTables_wrapper form-inline dt-bootstrap",
            sFilterInput: "form-control input-sm",
            sLengthSelect: "form-control input-sm",
            sProcessing: "dataTables_processing panel panel-default"
        });
    }

    private setDatatableOnClick(datatable: JQuery) {
        datatable.find("tbody").on('click', 'tr', function (event) {
            var id = datatable.DataTable().row(this).id();
            if (id) {
                var row = $(event.currentTarget);
                row.parent().find('tr.info').removeClass('info');
                row.addClass('info');

                this.selectedId = id;
                //this.jqDatatable.onSelectionChange(id);
            }
        });
    }
}

export class DatatableRenders {
    static yesNo(data: any, type: string, full: any, meta: DataTables.CellMetaSettings) {
        return data == true ? "Yes" : "No";
    }
    static localDate(data: any, type: string, full: any, meta: DataTables.CellMetaSettings) {
        var dateFormat = (<any>meta.settings).aoColumns[meta.col].dateFormat;
        return DateUtil.formatLocalDate(data, dateFormat);
    }
}
