(function (angular) {
    'use strict';
    angular.module('busca', [])
        .controller('BuscaController', function () {
            this.campos = [
                { nome: "Título", id: "Titulo" },
                { nome: "Banda", id: "Banda" },
                { nome: "Artista", id: "Artista" },
                { nome: "Gênero", id: "Genero" },
                { nome: "Letra", id: "Letra" },
                { nome: "Instrumento", id: "Instrumento" },
                { nome: "Termos de Busca", id: "TermosBusca" },
            ];
        });
})(window.angular)
