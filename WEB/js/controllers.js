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
                $("#btnRegistrar").fadeOut(347, function () {
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

            this.validarSenhasIguais = function () {
                if (this.senha != "" && this.confirmacaoSenha != "") {
                    var campoSenha = $("#divSenha");
                    var glifoSenha = $("#glpSenha");
                    var campoConfirmacaoSenha = $("#divConfirmacaoSenha");
                    var glifoConfirmacaoSenha = $("#glpConfirmacaoSenha");


                    var classeErro = "has-error";
                    var glifoErro = "glyphicon-remove"
                    var classeValido = "has-success";
                    var glifoValido = "glyphicon-ok";

                    var senhaValida = this.senha == this.confirmacaoSenha;

                    campoSenha.removeClass(senhaValida ? classeErro : classeValido);
                    glifoSenha.removeClass(senhaValida ? glifoErro : glifoValido);
                    campoConfirmacaoSenha.removeClass(senhaValida ? classeErro : classeValido);
                    glifoConfirmacaoSenha.removeClass(senhaValida ? glifoErro : glifoValido);

                    campoSenha.addClass(senhaValida ? classeValido : classeErro);
                    glifoSenha.addClass(senhaValida ? glifoValido : glifoErro);
                    campoConfirmacaoSenha.addClass(senhaValida ? classeValido : classeErro);
                    glifoConfirmacaoSenha.addClass(senhaValida ? glifoValido : glifoErro);
                }

            }
        });

    angular.module('musica', [])
        .controller('MusicaController', function () {

            this.musica = criarMusica();

            // Constantes
            this.CAMINHO_ESTRELA_CHEIA = "../img/estrela_cheia.png";
            this.CAMINHO_ESTRELA_VAZIA = "../img/estrela_vazia.png";

            this.definirImagemNota = function (numeroEstrela) {
                if (this.musica.nota >= numeroEstrela) {
                    return this.CAMINHO_ESTRELA_CHEIA;
                } else {
                    return this.CAMINHO_ESTRELA_VAZIA;
                }
            }

            this.ativarEstrelas = function (numeroEstrelas) {
                for (var i = 0; i <= numeroEstrelas; i++) {
                    $("#imgEstrela" + i).attr("src", this.CAMINHO_ESTRELA_CHEIA);
                }

                for (var i = numeroEstrelas + 1; i <= 5; i++) {
                    $("#imgEstrela" + i).attr("src", this.CAMINHO_ESTRELA_VAZIA);
                }
            }

            this.desativarEstrelas = function () {
                for (var i = 0; i <= this.musica.nota; i++) {
                    $("#imgEstrela" + i).attr("src", this.CAMINHO_ESTRELA_CHEIA);
                }

                for (var i = this.musica.nota + 1; i <= 5; i++) {
                    $("#imgEstrela" + i).attr("src", this.CAMINHO_ESTRELA_VAZIA);
                }
            }

            this.montarArtistasPorTipo = function (tipoArtista) {
                var artistas = "";

                for (var i = 0; i < this.musica.artistas.length; i++) {
                    var artista = this.musica.artistas[i];
                    if (artista.tipo.idTipo == tipoArtista) {
                        artistas += ", " + artista.nome;
                    }
                }

                return artistas.substring(2);
            }

            this.inicializarComponentes = function () {
                this.realizarSetupPanel("divHeaderLetra", "divLetra", 1000);
                this.realizarSetupPanel("divHeaderSobre", "divSobre", 500);
                this.realizarSetupPanel("divHeaderTags", "divTags", 500);
            }

            this.realizarSetupPanel = function (idHeader, idPainel, tempoEfeito) {
                $("#" + idHeader).click(function () {
                    $("#" + idPainel).slideToggle(tempoEfeito);
                });
            }

            this.montarTags = function () {
                var tags = "";
                for (var i = 0; i < this.musica.tags.length; i++) {
                    tags += ", " + this.musica.tags[i];
                }
                return tags.substring(2);
            }
        });

    angular.module('resultadosBusca', [])
        .controller('ResultadosController', ["$location",
            function ($location) {
                this.resultados = {
                    itens: [
                        criarMusica(),
                        {
                            nome: "Chain reaction",
                            banda: "Journey",
                            album: {
                                nome: "Frontiers",
                                ano: 1983,
                                capa: "../img/capa1.jpg"
                            }
                        },
                        {
                            nome: "Send her my love",
                            banda: "Journey",
                            album: {
                                nome: "Frontiers",
                                ano: 1983,
                                capa: "../img/capa1.jpg"
                            }
                        },
                        {
                            nome: "Sandstorm",
                            banda: "Darude",
                            album: {
                                nome: "Before the Storm",
                                ano: 2000,
                                capa: "../img/capa2.jpg"
                            }
                        },
                    ],
                    quantidadeTotal: 32
                };

                this.determinarTipoBusca = function () {
                    return $location.search().TipoBusca == "Basica" ? "básica" : "avançada";
                }
            }]);

    angular.module('historicoEdicoes', [])
        .controller('HistoricoController', function () {
            this.alteracoes = {
                musica: criarMusica(),
            }
            
        });

})(window.angular)


function criarMusica() {
    return {
        nome: "Separate ways (Worlds apart)",
        nota: 3,
        favorita: true,
        banda: "Journey",
        album: {
            nome: "Frontier",
            ano: "1983",
            capa: "../img/capa1.jpg",
        },
        genero: "Classic Metal",
        artistas: [
            { nome: "...", tipo: { idTipo: 1 } },
            { nome: "...", tipo: { idTipo: 2 } },
            { nome: "Jonathan Cain", tipo: { idTipo: 3 } },
            { nome: "Steve Perry", tipo: { idTipo: 3 } },
            { nome: "Kevin Elson", tipo: { idTipo: 4 } },
            { nome: "Mike Stone", tipo: { idTipo: 4 } },
        ],

        letra: "Here we stand" +
        "\nWorld's apart, hearts broken in two, two, two" +
        "\nSleepless nights" +
        "\nLosing ground" +
        "\nI'm reaching for you, you, you" +
        "\n" +
        "\nFeelin' that it's gone" +
        "\nCan change your mind" +
        "\nIf we can't go on" +
        "\nTo survive the tide love divides" +
        "\n" +
        "\nSomeday love will find you" +
        "\nBreak those chains that bind you" +
        "\nOne night will remind you" +
        "\nHow we touched" +
        "\nAnd went our separate ways" +
        "\n" +
        "\nIf he ever hurts you" +
        "\nTrue love won't desert you" +
        "\nYou know I still love you" +
        "\nThough we touched" +
        "\nAnd went our separate ways" +
        "\n" +
        "\nTroubled times" +
        "\nCaught between confusions and pain, pain, pain" +
        "\nDistant eyes" +
        "\nPromises we made were in vain, in vain, in vain" +
        "\n" +
        "\nIf you must go, I wish you love" +
        "\nYou'll never walk alone" +
        "\nTake care my love" +
        "\nMiss you love" +
        "\n" +
        "\nSomeday love will find you" +
        "\nBreak those chains that bind you" +
        "\nOne night will remind you" +
        "\nHow we touched" +
        "\nAnd went our separate ways" +
        "\n" +
        "\nIf he ever hurts you" +
        "\nTrue love won't desert you" +
        "\nYou know I still love you" +
        "\nThough we touched" +
        "\nAnd went our separate ways" +
        "\n" +
        "\nSomeday love will find you" +
        "\nBreak those chains that bind you" +
        "\nOne night will remind you" +
        "\n" +
        "\nIf he ever hurts you" +
        "\nTrue love won't desert you" +
        "\nYou know I still love you" +
        "\n" +
        "\nI still love you girl" +
        "\nI really love you girl" +
        "\nAnd if he ever hurts you" +
        "\nTrue love won't desert you" +
        "\nNo, no" +
        "\nNo" +
        "\nTradução" +
        "\nAdd a playlist" +
        "\nCifra" +
        "\nImprimir" +
        "\nCorrigir",

        informacoes: "Separate Ways (Worlds Apart) é uma canção da banda de rock norte-americana Journey, do álbum Frontiers. Lançado em 5 de fevereiro de 1983, o single subiu para a oitava posição na Billboard Hot 100 e ficou lá por seis semanas consecutivas.",
        tags: ["Journey", "Frontier", "Separate Ways", "Worlds Apart", "Tron Legacy", "Yes Man"],
    };
}