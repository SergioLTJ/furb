$(function () {
    inicializarComponentes();

    function inicializarComponentes() {
        $(".numerico-maximo-100").bind("input", function () {
            if ($(this).val() > 100) {
                $(this).val(100);
            }
            if ($(this).val().indexOf('e') > -1) {
                var valorAtual = $(this).val();
                $(this).val(valorAtual.replace('e', ''));
            }
        });
        
        $("#headerBuscaAvancada").click(function() {
           $("#pnlBuscaAvancada").slideToggle(500); 
        });
    }
});