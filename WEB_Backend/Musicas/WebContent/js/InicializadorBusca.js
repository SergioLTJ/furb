$(document).ready(function () {
    $("form").submit(function () {
        $("input").each(function (index, obj) {
            if ($(obj).val() == "") {
                $(obj).remove();
            }
        });
    });
});

vincularElementoNgApp(window.angular, 'divBuscaAvancada', 'busca');