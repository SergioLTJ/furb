(function (angular) {
    'use strict';

    angular.module('busca', [])
        .controller('BuscaController', function () {
            this.campos = [
                { nome: "Título", id: "txTitulo" },
                { nome: "Banda", id: "txBanda" },
                { nome: "Artista", id: "txArtista" },
                { nome: "Gênero", id: "txGenero" },
                { nome: "Letra", id: "txLetra" },
                { nome: "Instrumento", id: "txInstrumento" },
                { nome: "Termos de Busca", id: "txTermosBusca" },
            ];
        });

    angular.module('login', [])
        .controller('LoginController', function () {
            this.usuario;
            this.senha;
            this.confirmacaoSenha;
            this.email;
            this.modo = 'login';

            this.mostrarDivCadastro = function () {
                this.modo = 'cadastro';

                this.trocarTextoHeader("Cadastro")
                $("#btnConfirmarLogin").fadeOut(500);
                $("#btnRegistrar").fadeOut(500, function () {
                    $("#divCadastro").slideToggle(500);
                });
            }

            this.esconderDivCadastro = function () {
                this.modo = 'login';

                this.trocarTextoHeader("Login");
                $("#divCadastro").slideToggle(500, function () {
                    $("#btnRegistrar").fadeIn(500);
                    $("#btnConfirmarLogin").fadeIn(500);
                });
            }

            this.trocarTextoHeader = function (novoTexto) {
                $("#lblHeader").fadeOut(500, function () {
                    $(this).text(novoTexto);
                }).fadeIn();
            }
        });
})(window.angular)
