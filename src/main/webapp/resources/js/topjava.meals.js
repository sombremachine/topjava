const mealAjaxUrl = "ajax/profile/meals/";

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: "ajax/profile/meals/filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get("ajax/profile/meals/", updateTableByData);
}

$(function () {
    makeEditable({
        ajaxUrl: mealAjaxUrl,
        datatableApi: $("#datatable").DataTable({
            "ajax": {
                "url": mealAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",

                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                        "orderable": false,
                        "defaultContent": "",
                        "render": renderEditBtn
                },
                {
                        "orderable": false,
                        "defaultContent": "",
                        "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                $(row).attr("data-mealExcess", data.excess);
            }
        }),
        updateTable: updateFilteredTable
    });

    $('#dateTime').datetimepicker({
        format: 'Y-m-d H:i'
    });
    $('#startDate').datetimepicker({
        timepicker: false,
        format: 'Y-m-d',
    });
    $('#endDate').datetimepicker({
        timepicker: false,
        format: 'Y-m-d',
    });
    $('#startTime').datetimepicker({
       datepicker: false,
       format: 'H:i'
    });
    $('#endTime').datetimepicker({
        datepicker: false,
        format: 'H:i'
    });
});