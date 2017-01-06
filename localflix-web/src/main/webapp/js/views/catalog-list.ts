import { Datatable, DatatableRenders } from "../components/datatable"


$(function(){
    new Datatable("#staff-list", {
        "columns": [
            { "data": "id" },
            { "data": "firstName" },
            { "data": "surname" },
            { "data": "email" },
            { "data": "sex" },
            { "data": "birthDate", "render": DatatableRenders.localDate("yyyy-MM-dd") },
            { "data": "enabled", "render": DatatableRenders.yesNo }
        ]
    });
});
