(function (angular) {
    'use strict';

    angular.module('busca', [])
        .controller('BuscaController', ['$scope',
            function ($scope) {
                $scope.campos = [
                    { descricao: "Título", id: "txTitulo", nomeCurto: "t" },
                    { descricao: "Banda", id: "txBanda", nomeCurto: "b" },
                    { descricao: "Gênero", id: "txGenero", nomeCurto: "g" },
                    { descricao: "Letra", id: "txLetra", nomeCurto: "l" },
                    { descricao: "Termos de Busca", id: "txTermosBusca", nomeCurto: "terB" },
                ];

                $("#headerBuscaAvancada").click(function () {
                    $("#pnlBuscaAvancada").slideToggle(500);
                });

            }]);

    angular.module('login', ['ui-notification', 'ngRoute'])
        .controller('LoginController', ['Notification', '$location', '$http', '$window', '$route',
            function (Notification, $location, $http, $window, $route) {
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
                        var parametros = {
                            usuario: this.usuario,
                            senha: this.senha,
                            email: this.email,
                        };

                        $http({
                            method: 'POST',
                            url: 'http://localhost:8080/Musicas/Cadastro',
                            contentType: 'application/json',
                            data: JSON.stringify(parametros),
                        }).then(function (retorno) {
                            if (retorno.data != null) {
                                mensagem = 'Cadastro finalizado com sucesso!';
                            } else {
                                mensagem = 'Ocorreu um erro ao finalizar o cadastro do usuário informado.'
                                tipoMensagem = 'warning';
                            }

                            Notification({ message: mensagem, positionX: "center", positionY: "bottom", delay: 5000 }, tipoMensagem);
                        });
                    } else {
                        Notification({ message: mensagem, positionX: "center", positionY: "bottom", delay: 5000 }, tipoMensagem);
                    }
                }

                this.realizarLogin = function () {
                    var parametros = {
                        usuario: this.usuario,
                        senha: this.senha,
                        email: this.email,
                    };

                    $http({
                        method: 'POST',
                        url: 'http://localhost:8080/Musicas/ControladorUsuarioLogado',
                        contentType: 'application/json',
                        data: JSON.stringify(parametros),
                    }).then(function (retorno) {
                        if (retorno.data) {
                            $window.location.href = '/git-furb/WEB/html/Busca.html#/';
                            $route.reload();
                        } else {
                            Notification({ message: 'Usuário ou senha incorretos.', positionX: "center", positionY: "bottom", delay: 5000 }, 'warning');
                        }
                    });
                }

                this.adicionarMensagemErro = function (mensagem, campo) {
                    if (mensagem == undefined) {
                        mensagem = "Erros ao validar os campos:";
                    }
                    return mensagem += '<br>- ' + campo;
                }
            }]);

    angular.module('musica', ['servico-usuario-logado'])
        .controller('MusicaController', ["$http", '$scope', 'ServicoUsuarioLogado',
            function ($http, $scope, ServicoUsuarioLogado) {
                $scope.nota = 0;
                $scope.favorita = true;
                $scope.idUsuarioLogado;

                // Constantes
                this.CAMINHO_ESTRELA_CHEIA = "../img/estrela_cheia.png";
                this.CAMINHO_ESTRELA_VAZIA = "../img/estrela_vazia.png";

                this.definirImagemNota = function (numeroEstrela) {
                    if (this.musica == null) return this.CAMINHO_ESTRELA_VAZIA;

                    if ($scope.nota >= numeroEstrela) {
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
                    for (var i = 0; i <= $scope.nota; i++) {
                        $("#imgEstrela" + i).attr("src", this.CAMINHO_ESTRELA_CHEIA);
                    }

                    for (var i = $scope.nota + 1; i <= 5; i++) {
                        $("#imgEstrela" + i).attr("src", this.CAMINHO_ESTRELA_VAZIA);
                    }
                }

                this.inicializar = function () {
                    var self = this;

                    this.inicializarComponentes();

                    ServicoUsuarioLogado.obterUsuarioLogado(function (retorno) {
                        $scope.idUsuarioLogado = retorno.data != null && retorno.data != "" ? retorno.data.idUsuario : null;
                        var idMusica = obterValorQueryString("id");

                        $http({
                            method: 'POST',
                            url: 'http://localhost:8080/Musicas/BuscaMusicas',
                            contentType: 'application/json',
                            data: JSON.stringify({ tipoBusca: "Id", id: idMusica }),
                        }).then(function (retorno) {
                            self.musica = retorno.data;
                        });

                        if ($scope.idUsuarioLogado) {
                            var parametros = {
                                operacao: "obter",
                                idMusica: idMusica,
                                idUsuario: $scope.idUsuarioLogado,
                            }

                            $scope.carregarNota();
                            $scope.carregarFavorita();
                        }
                    });
                }

                $scope.carregarNota = function () {
                    $scope.carregar('http://localhost:8080/Musicas/NotaServlet', function (retorno) {
                        $scope.nota = parseInt(retorno.data);
                    });
                }

                $scope.carregarFavorita = function () {
                    $scope.carregar('http://localhost:8080/Musicas/FavoritaServlet', function (retorno) {
                        $scope.favorita = retorno.data === 'true' ? true : false;
                    });
                }

                $scope.criarParametrosRequisicao = function () {
                    return {
                        operacao: "obter",
                        idMusica: obterValorQueryString("id"),
                        idUsuario: $scope.idUsuarioLogado,
                    };
                }

                $scope.carregar = function (url, callback) {
                    var parametros = $scope.criarParametrosRequisicao();

                    $http({
                        method: 'POST',
                        url: url,
                        contentType: 'application/json',
                        data: JSON.stringify(parametros),
                    }).then(callback);
                }

                $scope.atribuirNotaMusica = function (nota) {
                    var parametros = $scope.criarParametrosRequisicao();
                    parametros.operacao = 'atribuir';
                    parametros.nota = nota;

                    $http({
                        method: 'POST',
                        url: 'http://localhost:8080/Musicas/NotaServlet',
                        contentType: 'application/json',
                        data: JSON.stringify(parametros),
                    }).then(function (retorno) {
                        $scope.carregarNota();
                    });
                }

                $scope.atribuirMusicaFavorita = function (favorita) {
                    var parametros = $scope.criarParametrosRequisicao();
                    parametros.operacao = 'atribuir';
                    parametros.favorita = favorita;

                    $http({
                        method: 'POST',
                        url: 'http://localhost:8080/Musicas/FavoritaServlet',
                        contentType: 'application/json',
                        data: JSON.stringify(parametros),
                    }).then(function (retorno) {
                        $scope.carregarFavorita();
                    });
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
            }]);

    angular.module('resultadosBusca', [])
        .controller('ResultadosController', ["$location", "$scope", "$http",
            function ($location, $scope, $http) {
                $scope.carregarMusicas = function () {
                    var tipoBusca = obterValorQueryString("tb");
                    var parametros = { "tipoBusca": tipoBusca };

                    if (tipoBusca == "Basica") {
                        parametros.query = obterValorQueryString("q");
                    } else {
                        parametros.titulo = obterValorQueryString("t");
                        parametros.banda = obterValorQueryString("b");
                        parametros.genero = obterValorQueryString("g");
                        parametros.letra = obterValorQueryString("l");
                        parametros.termosBusca = obterValorQueryString("terB");
                    }

                    $http({
                        method: 'POST',
                        //http://localhost:8080/
                        url: 'Musicas/BuscaMusicas',
                        contentType: 'application/json',
                        data: JSON.stringify(parametros),
                    }).then(function (retorno) {
                        $scope.resultados = retorno.data;
                    });
                }

                $scope.determinarTipoBusca = function () {
                    return obterValorQueryString("tb") == "Basica" ? "básica" : "avançada";
                }
            }]);

    angular.module('historicoEdicoes', ['ui.bootstrap', 'ngAnimate'])
        .controller('HistoricoController', function ($scope, $uibModal, $http) {
            $scope.historico;
            $scope.nomeMusica

            $scope.carregar = function () {
                $scope.nomeMusica = obterValorQueryString('nome');

                $http({
                    method: 'POST',
                    url: 'http://localhost:8080/Musicas/HistoricoEdicoesServlet',
                    contentType: 'application/json',
                    data: JSON.stringify({ idMusica: obterValorQueryString("id") }),
                }).then(function (retorno) {
                    $scope.historico = retorno.data;
                });
            }

            $scope.visualizarAlteracao = function (indiceAlteracao) {
                var alteracao = $scope.historico[indiceAlteracao];

                $uibModal.open({
                    animation: true,
                    templateUrl: 'alteracoes.html',
                    controller: 'AlteracoesPopupController',
                    resolve: {
                        alteracao: function () {
                            return 'Alterado o campo "' + alteracao.campo + '", valor alterado de:\n\n' + alteracao.valorAntigo + '\n\nPara:\n\n' + alteracao.valorNovo;
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

    angular.module('edicao', ['servico-usuario-logado', 'ngRoute'])
        .controller('EdicaoController', function ($scope, $http, $window, $route, ServicoUsuarioLogado) {
            $scope.musica;

            $scope.generos;
            $scope.generoSelecionado;
            $scope.generoNovo;

            $scope.bandas;
            $scope.bandaSelecionada;
            $scope.bandaNova;

            $scope.albuns;
            $scope.albumSelecionado;
            $scope.albumNovo = {};

            $scope.gerarLinkMusica = function () {
                return 'Musica.html?id=' + $scope.musica.idMusica;
            }

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

            $scope.adicionarNovoGenero = function () {
                $scope.adicionar("genero", $scope.generoNovo, function (retorno) {
                    $scope.carregar('generos', function (retorno) { $scope.carregarGeneroMusica(retorno.data); });
                });
            }

            $scope.adicionarNovaBanda = function () {
                $scope.adicionar("banda", $scope.bandaNova, function (retorno) {
                    $scope.carregar('bandas', function (retorno) { $scope.carregarBandaMusica(retorno.data); });
                });
            }

            $scope.adicionarNovoAlbum = function () {
                var reader = new FileReader();

                reader.addEventListener("load", function () {
                    var parametros = {
                        tipo: 'album',
                        nome: $scope.albumNovo.nome,
                        ano: $scope.albumNovo.ano,
                        imagem: reader.result.substring(23),
                    };

                    $http({
                        method: 'POST',
                        url: 'http://localhost:8080/Musicas/InsercaoServlet',
                        contentType: 'application/json',
                        data: JSON.stringify(parametros),
                    }).then(function (retorno) {
                        $scope.carregar('albuns', function (retorno) { $scope.carregarAlbumMusica(retorno.data); });
                    });
                });

                reader.readAsDataURL(document.querySelector('#flAlbum').files[0]);
            }

            $scope.adicionar = function (tipo, nome, callback) {
                $http({
                    method: 'POST',
                    url: 'http://localhost:8080/Musicas/InsercaoServlet',
                    contentType: 'application/json',
                    data: JSON.stringify({ tipo: tipo, nome: nome }),
                }).then(callback);
            }

            $scope.montarDescricaoTipoArtista = function (artista) {
                return artista.nome + ' (' + artista.tipo.descricao + ')';
            }

            $scope.definirTitulo = function () {
                if (obterValorQueryString("m") == "nova") return "Nova música"

                return "Editando " + $scope.musica.nome;
            }

            $scope.carregarDados = function () {
                var self = this;

                if (obterValorQueryString("m") != "nova") {
                    $http({
                        method: 'POST',
                        url: 'http://localhost:8080/Musicas/BuscaMusicas',
                        contentType: 'application/json',
                        data: JSON.stringify({ tipoBusca: "Id", id: obterValorQueryString("id") }),
                    }).then(function (retorno) {
                        self.musica = retorno.data;

                        $scope.carregar('generos', function (retorno) { $scope.carregarGeneroMusica(retorno.data); });
                        $scope.carregar('bandas', function (retorno) { $scope.carregarBandaMusica(retorno.data); });
                        $scope.carregar('albuns', function (retorno) { $scope.carregarAlbumMusica(retorno.data); });
                    });
                } else {
                    $scope.carregar('generos', function (retorno) {
                        $scope.generos = retorno.data;
                        $scope.generoSelecionado = $scope.generos[0];
                    });
                    $scope.carregar('bandas', function (retorno) {
                        $scope.bandas = retorno.data;
                        $scope.bandaSelecionada = $scope.bandas[0];
                    });
                    $scope.carregar('albuns', function (retorno) {
                        $scope.albuns = retorno.data;
                        $scope.albumSelecionado = $scope.albuns[0];
                    });
                }
            }

            $scope.carregarGeneroMusica = function (generos) {
                $scope.generos = generos;

                for (var i = 0; i < generos.length; i++) {
                    if (generos[i].idGenero == $scope.musica.genero.idGenero)
                        $scope.generoSelecionado = generos[i];
                }
            }

            $scope.carregarBandaMusica = function (bandas) {
                $scope.bandas = bandas;

                for (var i = 0; i < bandas.length; i++) {
                    if (bandas[i].idBanda == $scope.musica.banda.idBanda)
                        $scope.bandaSelecionada = bandas[i];
                }
            }

            $scope.carregarAlbumMusica = function (albuns) {
                $scope.albuns = albuns;

                for (var i = 0; i < albuns.length; i++) {
                    if (albuns[i].idAlbum == $scope.musica.album.idAlbum)
                        $scope.albumSelecionado = albuns[i];
                }
            }

            $scope.carregar = function (tipoDado, callback) {
                $http({
                    method: 'POST',
                    url: 'http://localhost:8080/Musicas/CarregadorGeral',
                    contentType: 'application/json',
                    data: "{ 'tipo': '" + tipoDado + "' }"
                }).then(callback);
            }

            $scope.salvarAlteracoes = function () {
                if (obterValorQueryString("m") != "nova") {
                    ServicoUsuarioLogado.obterUsuarioLogado(function (retorno) {
                        var parametros = {
                            musica: $scope.montarMusica(),
                            usuario: retorno.data,
                        };

                        $http({
                            method: 'POST',
                            url: 'http://localhost:8080/Musicas/EdicaoMusicaServlet',
                            contentType: 'application/json',
                            data: JSON.stringify(parametros),
                        }).then(function (retorno) {
                            $window.location.href = '/git-furb/WEB/html/' + $scope.gerarLinkMusica() + '#/';
                            $route.reload();
                        });
                    });
                } else {
                    var parametros = {
                        musica: $scope.montarMusica(),
                        operacao: 'inserir',
                    };

                    $http({
                        method: 'POST',
                        url: 'http://localhost:8080/Musicas/EdicaoMusicaServlet',
                        contentType: 'application/json',
                        data: JSON.stringify(parametros),
                    }).then(function (retorno) {
                        $window.history.back();
                    });
                }
            }

            $scope.montarMusica = function () {
                return {
                    idMusica: $scope.musica.idMusica,
                    informacoes: $scope.musica.informacoes,
                    letra: $scope.musica.letra,
                    nome: $scope.musica.nome,
                    tags: $scope.musica.tags,
                    album: $scope.albumSelecionado,
                    banda: $scope.bandaSelecionada,
                    genero: $scope.generoSelecionado,
                };
            }
        });

    angular.module('perfil', ['ngRoute'])
        .controller('PerfilController', function ($scope, $http) {
            $scope.usuario;

            $scope.inicializar = function () {
                $http({
                    method: 'POST',
                    url: 'http://localhost:8080/Musicas/CarregadorUsuarioServlet',
                    contentType: 'application/json',
                    data: JSON.stringify({ id: obterValorQueryString("id"), carregarMusicas: true }),
                }).then(function (retorno) {
                    $scope.usuario = retorno.data;
                });
            }
        });

    angular.module('perfil')
        .controller('EditarPerfilController', function ($scope, $window, $route, $http) {
            $scope.usuario;

            $scope.inicializar = function () {
                $http({
                    method: 'POST',
                    url: 'http://localhost:8080/Musicas/CarregadorUsuarioServlet',
                    contentType: 'application/json',
                    data: JSON.stringify({ id: obterValorQueryString("id"), carregarMusicas: false }),
                }).then(function (retorno) {
                    $scope.usuario = retorno.data;
                });
            }

            $scope.salvar = function () {
                var reader = new FileReader();

                reader.addEventListener("load", function () {
                    $scope.usuario.imagemPerfil = reader.result.substring(23);

                    $http({
                        method: 'POST',
                        url: 'http://localhost:8080/Musicas/PersistidorUsuarioServlet',
                        contentType: 'application/json',
                        data: JSON.stringify($scope.usuario),
                    }).then(function (retorno) {
                        $window.location.href = '/git-furb/WEB/html/Perfil.html?id=' + $scope.usuario.idUsuario + '#/';
                        $route.reload();
                    });
                });

                reader.readAsDataURL(document.querySelector('#flUsuario').files[0]);
            }
        });

    angular.module('navegacao', ['servico-usuario-logado'])
        .controller('BarraNavegacaoController', ['$scope', 'ServicoUsuarioLogado',
            function ($scope, ServicoUsuarioLogado) {
                $scope.usuario;

                $scope.verificarUsuarioLogado = function () {
                    ServicoUsuarioLogado.obterUsuarioLogado(function (retorno) {
                        $scope.usuario = retorno.data;
                    });
                }
            }]);


})(window.angular);