
interface JQuery {
    dataTable(param?: DataTables.Settings): JQuery;
}

declare namespace DataTables {
    interface ColumnSettings {
        dateFormat?: string;
    }
}