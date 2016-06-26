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
                            url: '../../Musicas/Cadastro',
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
                        operacao: 'login',
                    };

                    $http({
                        method: 'POST',
                        url: '../../Musicas/ControladorUsuarioLogado',
                        contentType: 'application/json',
                        data: JSON.stringify(parametros),
                    }).then(function (retorno) {
                        if (retorno.data) {
                            $window.location.href = '../html/Busca.html#/';
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
                            url: '../../Musicas/BuscaMusicas',
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
                    $scope.carregar('../../Musicas/NotaServlet', function (retorno) {
                        $scope.nota = parseInt(retorno.data);
                    });
                }

                $scope.carregarFavorita = function () {
                    $scope.carregar('../../Musicas/FavoritaServlet', function (retorno) {
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

                    $scope.nota = nota;
                    
                    $http({
                        method: 'POST',
                        url: '../../Musicas/NotaServlet',
                        contentType: 'application/json',
                        data: JSON.stringify(parametros),
                    });
                }

                $scope.atribuirMusicaFavorita = function (favorita) {
                    var parametros = $scope.criarParametrosRequisicao();
                    parametros.operacao = 'atribuir';
                    parametros.favorita = favorita;

                    $scope.favorita = favorita;
                    
                    $http({
                        method: 'POST',
                        url: '../../Musicas/FavoritaServlet',
                        contentType: 'application/json',
                        data: JSON.stringify(parametros),
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
                        url: '../../Musicas/BuscaMusicas',
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
                    url: '../../Musicas/HistoricoEdicoesServlet',
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
                        url: '../../Musicas/InsercaoServlet',
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
                    url: '../../Musicas/InsercaoServlet',
                    contentType: 'application/json',
                    data: JSON.stringify({ tipo: tipo, nome: nome }),
                }).then(callback);
            }

            $scope.montarDescricaoTipoArtista = function (artista) {
                return artista.nome + ' (' + artista.tipo.descricao + ')';
            }

            $scope.definirTitulo = function () {
                if (obterValorQueryString("m") == "nova") return "Nova música"

                if ($scope.musica) {
                	return "Editando " + $scope.musica.nome;
                }
                
                return "";
            }

            $scope.carregarDados = function () {
                var self = this;

                if (obterValorQueryString("m") != "nova") {
                    $http({
                        method: 'POST',
                        url: '../../Musicas/BuscaMusicas',
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

                if ($scope.musica.genero) {
                	for (var i = 0; i < generos.length; i++) {
                		if (generos[i].idGenero == $scope.musica.genero.idGenero)
                        	$scope.generoSelecionado = generos[i];
                	}
                }
            }

            $scope.carregarBandaMusica = function (bandas) {
                $scope.bandas = bandas;

                if ($scope.musica.banda) {
                	for (var i = 0; i < bandas.length; i++) {
                    	if (bandas[i].idBanda == $scope.musica.banda.idBanda)
                        	$scope.bandaSelecionada = bandas[i];
                	}
                }
            }

            $scope.carregarAlbumMusica = function (albuns) {
                $scope.albuns = albuns;

                if ($scope.musica.album) {
                	for (var i = 0; i < albuns.length; i++) {
                		if (albuns[i].idAlbum == $scope.musica.album.idAlbum)
                			$scope.albumSelecionado = albuns[i];
                	}
                }
            }

            $scope.carregar = function (tipoDado, callback) {
                $http({
                    method: 'POST',
                    url: '../../Musicas/CarregadorGeral',
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
                            url: '../../Musicas/EdicaoMusicaServlet',
                            contentType: 'application/json',
                            data: JSON.stringify(parametros),
                        }).then(function (retorno) {
                            $window.location.href = '../html/' + $scope.gerarLinkMusica() + '#/';
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
                        url: '../../Musicas/EdicaoMusicaServlet',
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
                    url: '../../Musicas/CarregadorUsuarioServlet',
                    contentType: 'application/json',
                    data: JSON.stringify({ id: obterValorQueryString("id"), carregarMusicas: true }),
                }).then(function (retorno) {
                    $scope.usuario = retorno.data;
                });
            }
            
            $scope.determinarImagem = function() {
            	if ($scope.usuario.imagemPerfil) {
            		return $scope.usuario.imagemPerfil;
            	}
            	
            	return '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';
            }
        });

    angular.module('perfil')
        .controller('EditarPerfilController', function ($scope, $window, $route, $http) {
            $scope.usuario;

            $scope.inicializar = function () {
                $http({
                    method: 'POST',
                    url: '../../Musicas/CarregadorUsuarioServlet',
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
                    $scope.enviarUsuario();
                });

                var seletorArquivo = document.querySelector('#flUsuario'); 
                if (seletorArquivo.files.length > 0) {
                	reader.readAsDataURL(seletorArquivo.files[0]);
                } else {
                	$scope.enviarUsuario();
                }
            }
            
            $scope.enviarUsuario = function() {
            	$http({
                    method: 'POST',
                    url: '../../Musicas/PersistidorUsuarioServlet',
                    contentType: 'application/json',
                    data: JSON.stringify($scope.usuario),
                }).then(function (retorno) {
                    $window.location.href = '../html/Perfil.html?id=' + $scope.usuario.idUsuario + '#/';
                    $route.reload();
                });
            }
        });

    angular.module('navegacao', ['servico-usuario-logado'])
        .controller('BarraNavegacaoController', ['$scope', '$http', 'ServicoUsuarioLogado',
            function ($scope, $http, ServicoUsuarioLogado) {
                $scope.container = { usuario: null };

                $scope.verificarUsuarioLogado = function () {
                	ServicoUsuarioLogado.obterUsuarioLogado(function (retorno) {
                		$scope.container.usuario = retorno.data;
                	});
                }
                
                $scope.deslogar = function() {
                	$scope.container.usuario = null;
                	
                	$http({
                		method: 'POST',
                		url: '../../Musicas/ControladorUsuarioLogado',
                		contentType: 'application/json',
                		data: { operacao: 'logoff' },
                	});
                }
            }]);


})(window.angular);