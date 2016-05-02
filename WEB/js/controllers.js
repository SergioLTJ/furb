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
        .controller('LoginController', function() {
           this.usuario = '';
           this.senha = '';
           
            this.teste = function() {
                var a = 1;
                var b = 2;
            }
        });
})(window.angular)
