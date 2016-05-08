(function (angular) {
    'use strict';

    var moduloBusca = angular.module('busca', []);

    moduloBusca
        .controller('BuscaController', function ($scope) {
            $scope.container = {};

            $scope.campos = [
                { nome: "Título", id: "txTitulo", model: "titulo", modelPrioridade: "prioridadeTitulo" },
                { nome: "Banda", id: "txBanda", model: "banda", modelPrioridade: "prioridadeBanda" },
                { nome: "Artista", id: "txArtista", model: "artista", modelPrioridade: "prioridadeArtista" },
                { nome: "Gênero", id: "txGenero", model: "genero", modelPrioridade: "prioridadeGenero" },
                { nome: "Letra", id: "txLetra", model: "letra", modelPrioridade: "prioridadeLetra" },
                { nome: "Instrumento", id: "txInstrumento", model: "instrumento", modelPrioridade: "prioridadeInstrumento" },
                { nome: "Termos de Busca", id: "txTermosBusca", model: "termosBusca", modelPrioridade: "prioridadeTermosBusca" },
            ];

            $scope.inicializarComponentes = function () {
                $("#headerBuscaAvancada").click(function () {
                    $("#pnlBuscaAvancada").slideToggle(500);
                });

                $(".numerico-maximo-100").bind("input", function () {
                    if ($(this).val() > 100) {
                        $(this).val(100);
                    }
                    if ($(this).val().indexOf('e') > -1) {
                        var valorAtual = $(this).val();
                        $(this).val(valorAtual.replace('e', ''));
                    }
                });
            }
        });

    angular.module('login', ['ui-notification'])
        .controller('LoginController', ['Notification', '$location',
            function (Notification, $location) {
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

                        var campoSenha = $("#divSenha");
                        var glifoSenha = $("#glpSenha");

                        campoSenha.removeClass("has-error");
                        glifoSenha.removeClass("glyphicon-remove");
                        campoSenha.removeClass("has-success");
                        glifoSenha.removeClass("glyphicon-ok");
                    });
                }

                this.trocarTextoHeader = function (novoTexto) {
                    $("#lblHeader").fadeOut(500, function () {
                        $(this).text(novoTexto);
                    }).fadeIn();
                }

                this.validarSenhasIguais = function () {
                    if (this.modo == 'cadastro' && this.confirmacaoSenha != undefined) {
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

                this.confirmarCadastro = function () {
                    var mensagem = undefined;
                    var tipoMensagem = "success";

                    if (this.usuario == undefined || this.usuario.trim().length == 0) {
                        tipoMensagem = 'warning';
                        mensagem = this.adicionarMensagemErro(mensagem, 'Usuário não informado')
                    }

                    if (this.senha == undefined || this.senha.trim().length == 0) {
                        tipoMensagem = 'warning';
                        mensagem = this.adicionarMensagemErro(mensagem, 'Senha não informada');
                    }
                    else if (this.confirmacaoSenha == undefined || this.confirmacaoSenha.trim().length == 0) {
                        tipoMensagem = 'warning';
                        mensagem = this.adicionarMensagemErro(mensagem, 'Confirmação da senha não informada');
                    }
                    else if (this.senha != this.confirmacaoSenha) {
                        tipoMensagem = 'warning';
                        mensagem = this.adicionarMensagemErro(mensagem, 'Senhas são diferentes');
                    }

                    if (this.email == undefined || this.email.trim().length == 0) {
                        tipoMensagem = 'warning';
                        mensagem = this.adicionarMensagemErro(mensagem, 'Email informado não é valido');
                    }

                    if (mensagem == undefined) {
                        mensagem = 'Cadastro finalizado com sucesso! Foi enviado um email de confirmação para o endereço informado.';
                    }

                    Notification({ message: mensagem, positionX: "center", positionY: "bottom", delay: 5000 }, tipoMensagem);
                }

                this.adicionarMensagemErro = function (mensagem, campo) {
                    if (mensagem == undefined) {
                        mensagem = "Erros ao validar os campos:";
                    }
                    return mensagem += '<br>- ' + campo;
                }
            }]);

    angular.module('musica', [])
        .controller('MusicaController', function () {

            this.musica = musicaPrincipal;

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
                        musicaPrincipal,
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

    angular.module('historicoEdicoes', ['ui.bootstrap', 'ngAnimate'])
        .controller('HistoricoController', function ($scope, $uibModal) {
            $scope.historico = {
                musica: musicaPrincipal,
                alteracoes: [
                    {
                        usuario: "Sérgio Tomio",
                        tipoAlteracao: "Adição",
                        campo: "Sobre",
                        descricao: 'Adicionado o campo "Sobre" com o conteúdo:\n\nSeparate Ways (Worlds Apart) é uma canção da banda de rock norte-americana Journey, do álbum Frontiers. Lançado em 5 de fevereiro de 1983, o single subiu para a oitava posição na Billboard Hot 100 e ficou lá por seis semanas consecutivas. ',
                    },
                    {
                        usuario: "Guilherme Neto",
                        tipoAlteracao: "Alteração",
                        campo: "Letra",
                        descricao: 'Alterado o campo "Letra"' +
                        '<div style="background-color: #ff9999">How we touced</div>' +
                        '<div style="background-color: #aeeaae">How we touched</div>' +
                        '...\n<div style="background-color: #ff9999">If he eve hurts you</div>' +
                        '<div style="background-color: #aeeaae">If he ever hurts you</div>' +
                        '...\n<div style="background-color: #ff9999">True love on\'t desert you</div>' +
                        '<div style="background-color: #aeeaae">True love won\'t desert you</div>' +
                        '...\n<div style="background-color: #ff9999">You know I till love you</div>' +
                        '<div style="background-color: #aeeaae">You know I still love you</div>' +
                        '...\n<div style="background-color: #ff9999">Though we ouched</div>' +
                        '<div style="background-color: #aeeaae">Though we touche</div>',
                    },
                ],
            };

            $scope.visualizarAlteracao = function (indiceAlteracao) {
                var alteracao = $scope.historico.alteracoes[indiceAlteracao];

                $uibModal.open({
                    animation: true,
                    templateUrl: 'alteracoes.html',
                    controller: 'AlteracoesPopupController',
                    resolve: {
                        alteracao: function () {
                            return alteracao.descricao;
                        }
                    },
                });
            }
        });

    angular.module('historicoEdicoes')
        .controller('AlteracoesPopupController', function ($scope, $uibModalInstance, $sce, alteracao) {
            $scope.alteracao = $sce.trustAsHtml(alteracao);

            $scope.fechar = function () {
                $uibModalInstance.dismiss('cancel');
            }

            $scope.removerArtistaSelecionado
        });

    angular.module('edicao', [])
        .controller('EdicaoController', function ($scope) {
            $scope.musica = musicaPrincipal;
            $scope.artistaSelecionado = $scope.musica.artistas[0];
            $scope.artistaNovo;
            $scope.tiposArtista = [
                { idTipo: 1, descricao: "Guitarrista" },
                { idTipo: 2, descricao: "Cantor" },
                { idTipo: 3, descricao: "Escritor" },
                { idTipo: 4, descricao: "Produtor" },
            ];
            $scope.tipoNovoArtista;
            $scope.tagNova;
            $scope.tagSelecionada = $scope.musica.tags[0];

            $scope.removerArtistaSelecionado = function () {
                var selecionado = $scope.artistaSelecionado;
                if (selecionado != null) {
                    var indiceSelecionado = $scope.encontrarIndiceArtista(selecionado.idArtista);
                    $scope.musica.artistas.splice(indiceSelecionado, 1);
                    if (indiceSelecionado < $scope.musica.artistas.length) {
                        $scope.artistaSelecionado = $scope.musica.artistas[indiceSelecionado];
                    } else {
                        $scope.artistaSelecionado = $scope.musica.artistas[0];
                    }
                }
            }

            $scope.encontrarIndiceArtista = function (idArtista) {
                var artistas = $scope.musica.artistas;
                for (var i = 0; i < artistas.length; i++) {
                    if (artistas[i].idArtista == idArtista) return i;
                }
                return -1;
            }

            $scope.adicionarNovoArtista = function () {
                var artistas = $scope.musica.artistas;
                $scope.musica.artistas.push({
                    idArtista: (artistas[artistas.length - 1].idArtista + 1),
                    nome: $scope.artistaNovo,
                    tipo: $scope.tipoNovoArtista,
                });
                $scope.artistaNovo = null;
            }

            $scope.montarDescricaoTipoArtista = function (artista) {
                return artista.nome + ' (' + artista.tipo.descricao + ')';
            }

            $scope.removerTagSelecionada = function () {
                var selecionada = $scope.tagSelecionada;
                if (selecionada != null) {
                    var indiceSelecionada = $scope.encontrarIndiceTag(selecionada);
                    $scope.musica.tags.splice(indiceSelecionada, 1);
                    if (indiceSelecionada < $scope.musica.artistas.length) {
                        $scope.tagSelecionada = $scope.musica.tags[indiceSelecionada];
                    } else {
                        $scope.tagSelecionada = $scope.musica.tags[0];
                    }
                }
            }

            $scope.encontrarIndiceTag = function (tag) {
                var tags = $scope.musica.tags;
                for (var i = 0; i < tags.length; i++) {
                    if (tags[i] == tag) return i;
                }
                return -1;
            }

            $scope.adicionarNovaTag = function () {
                $scope.musica.tags.push($scope.tagNova);
                $scope.tagNova = null;
            }

        });

    angular.module('perfil', [])
        .controller('PerfilController', function ($scope) {
            $scope.usuario = {
                nome: "Guilherme Diegoli Neto",
                email: "gd_neto@hotmail.com",
                imagemPerfil: "../img/imagem_perfil.jpg",
                favoritos: [
                    musicaPrincipal,
                ],
                pesquisas: [
                    musicaPrincipal,
                ]
            };
        });

    angular.module('perfil')
        .controller('EditarPerfilController', function ($scope) {
            $scope.usuario = {
                nome: "Guilherme Diegoli Neto",
                email: "gd_neto@hotmail.com",
                imagemPerfil: "../img/imagem_perfil.jpg",
                favoritos: [
                    musicaPrincipal,
                ],
                pesquisas: [
                    musicaPrincipal,
                ]
            };
        });

})(window.angular)

var musicaPrincipal = criarMusica();

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
            { idArtista: 1, nome: "...", tipo: { idTipo: 1, descricao: "Guitarrista" } },
            { idArtista: 2, nome: "...", tipo: { idTipo: 2, descricao: "Cantor" } },
            { idArtista: 3, nome: "Jonathan Cain", tipo: { idTipo: 3, descricao: "Escritor" } },
            { idArtista: 4, nome: "Steve Perry", tipo: { idTipo: 3, descricao: "Escritor" } },
            { idArtista: 5, nome: "Kevin Elson", tipo: { idTipo: 4, descricao: "Produtor" } },
            { idArtista: 6, nome: "Mike Stone", tipo: { idTipo: 4, descricao: "Produtor" } },
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
        "\nNo",

        informacoes: "Separate Ways (Worlds Apart) é uma canção da banda de rock norte-americana Journey, do álbum Frontiers. Lançado em 5 de fevereiro de 1983, o single subiu para a oitava posição na Billboard Hot 100 e ficou lá por seis semanas consecutivas.",
        tags: ["Journey", "Frontier", "Separate Ways", "Worlds Apart", "Tron Legacy", "Yes Man"],
    };
}