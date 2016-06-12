(function (angular) {
    angular.module("servico-usuario-logado", [])
        .service("ServicoUsuarioLogado", ['$http',
            function ($http) {
                this.obterUsuarioLogado = function (callback) {
                    callback({
                        data: {
                            idUsuario: 3,
                        },
                    });

                    // Browser não armazena cookies em sites que não tem domínio, 
                    // como estamos testando com a página no próprio file system 
                    // não é possível realizar um controle de usuário logado via sessão. 

                    // $http({
                    //     method: 'POST',
                    //     url: 'http://localhost:8080/Musicas/ControladorUsuarioLogado',
                    //     contentType: 'application/json',
                    //     data: null,
                    // }).then(function (retorno) {
                    //     callback(retorno);
                    // });
                }
            }]);
})(window.angular);